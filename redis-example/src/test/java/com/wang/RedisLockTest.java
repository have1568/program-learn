package com.wang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis 锁测试
 */
@Slf4j
public class RedisLockTest extends RedisExampleApplicationTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RedisLockRegistry redisLockRegistry;

    @BeforeEach
    void before() {
        //开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        //每个测试清空数据库
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    /**
     * 简单版锁
     */
    public boolean lock(String lockKey, String lockValue) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 1, TimeUnit.SECONDS));
    }

    /**
     * 简单版解锁
     */
    public boolean unlock(String lockKey) {
        return Boolean.TRUE.equals(redisTemplate.delete(lockKey));
    }


    /**
     * 测试没有锁演示失败的情况
     */
    @Test
    void test_with_out_lock() throws InterruptedException {

        int threadNumber = 100000; //秒杀线程

        //倒计时闩锁
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);


        ExecutorService service = Executors.newFixedThreadPool(5);
        ProductService productService = new ProductService();
        log.info("======秒杀开始=======");
        for (int i = 0; i < threadNumber; i++) {
            service.submit(() -> {
                //调用秒杀业务
                countDownLatch.countDown();
                productService.spike();

            });
        }
        //阻塞
        countDownLatch.await();
        log.info("======秒杀结束=======");
        System.out.println(productService.success);
        System.out.println(productService.success.size());
        System.out.println(productService.fail);
        Assertions.assertEquals(threadNumber, productService.success.size() + productService.fail.get());
    }

    /**
     * 测试有线redis锁的情况
     */
    @Test
    void test_with_redis_lock() throws InterruptedException {

        int threadNumber = 100000; //秒杀线程

        String LOCK_KEY = "product_id";
        Long LOCK_VALUE = System.currentTimeMillis();

        //倒计时闩锁
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);


        ExecutorService service = Executors.newFixedThreadPool(5);
        ProductService productService = new ProductService();
        log.info("======秒杀开始=======");
        for (int i = 0; i < threadNumber; i++) {
            service.execute(() -> {
                countDownLatch.countDown();
                if (lock(LOCK_KEY, String.valueOf(LOCK_VALUE))) {
                    try {
                        //调用秒杀业务
                        productService.spike();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //删除锁不成功就会出现死锁情况
                        unlock(LOCK_KEY);
                    }
                } else {
                    productService.fail.incrementAndGet();
                }

            });
        }
        //阻塞
        countDownLatch.await();
        log.info("======秒杀结束=======");
        System.out.println(productService.success);
        System.out.println(productService.success.size());
        System.out.println(productService.fail);
        Assertions.assertEquals(threadNumber, productService.success.size() + productService.fail.get());
    }


    /**
     * spring-integration-redis 底层实现Lua脚本
     * https://zhuanlan.zhihu.com/p/141023090
     */
    @Test
    void test_with_redis_script_lock() throws InterruptedException {

        int threadNumber = 10000; //秒杀线程

        String LOCK_KEY = "product_id";

        //倒计时闩锁
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);


        ExecutorService service = Executors.newFixedThreadPool(10);
        ProductService productService = new ProductService();
        Lock lock = redisLockRegistry.obtain(LOCK_KEY);
        log.info("======秒杀开始=======");
        for (int i = 0; i < threadNumber; i++) {
            service.execute(() -> {
                try {
                    if (lock.tryLock(3, TimeUnit.SECONDS)) {
                        //拿到锁之后在减 1
                        countDownLatch.countDown();
                        //调用秒杀业务
                        productService.spike();
                    }
                } catch (Exception e) {
                    System.out.println("error");
                    countDownLatch.countDown();
                } finally {
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                        System.out.println("error");
                        countDownLatch.countDown();
                    }

                }

            });
        }
        //阻塞
        countDownLatch.await();
        log.info("======秒杀结束=======");
        System.out.println(productService.success);
        System.out.println(productService.success.size());
        System.out.println(productService.fail);
        //不通过原因 TODO
        Assertions.assertEquals(threadNumber, productService.success.size() + productService.fail.get());
    }


    /**
     * 测试有线程锁的情况
     */
    @Test
    void test_with_thread_lock() throws InterruptedException {

        int threadNumber = 100000; //秒杀线程

        //倒计时闩锁
        CountDownLatch countDownLatch = new CountDownLatch(threadNumber);


        ExecutorService service = Executors.newFixedThreadPool(5);
        ProductService productService = new ProductService();
        log.info("======秒杀开始=======");
        for (int i = 0; i < threadNumber; i++) {
            service.execute(() -> {
                //调用秒杀业务
                countDownLatch.countDown();
                productService.lockSpike();

            });
        }
        //阻塞
        countDownLatch.await();
        log.info("======秒杀结束=======");
        System.out.println(productService.success);
        System.out.println(productService.success.size());
        System.out.println(productService.fail);
        Assertions.assertEquals(threadNumber, productService.success.size() + productService.fail.get());
    }


    class ProductService {
        //共享数据有线程安全问题
        int stock = 30; //库存
        //记录秒杀成功的商品
        CopyOnWriteArrayList<Integer> success = new CopyOnWriteArrayList<>();

        //记录秒失败的次数
        AtomicInteger fail = new AtomicInteger(0);

        ReentrantLock lock = new ReentrantLock();

        //秒杀方法
        public boolean spike() {

            boolean flag = false;
            if (stock > 0) {
                flag = true;
                success.add(stock);
                stock = stock - 1;
            } else {
                fail.incrementAndGet();
            }

            return flag;
        }
        //秒杀方法
        public boolean lockSpike() {
            lock.lock();
            boolean flag = false;
            if (stock > 0) {
                flag = true;
                success.add(stock);
                --stock;
            } else {
                fail.incrementAndGet();

            }
            lock.unlock();
            return flag;
        }

    }
}
