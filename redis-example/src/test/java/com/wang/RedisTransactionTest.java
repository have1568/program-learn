package com.wang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Redis 事务测试
 */
public class RedisTransactionTest extends RedisExampleApplicationTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void before() {
        //开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        //每个测试清空数据库
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
    }

    @Test
    void test_transaction_with_exec() {

        //开启
        redisTemplate.multi();
        redisTemplate.opsForList().leftPushAll("T1", "V1", "V2", "V3");
        redisTemplate.opsForList().leftPushAll("T2", "V1", "V2");
        //未提交前为空
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 0);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 0);
        //提交
        redisTemplate.exec();
        //提交后有数据
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 3L);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 2L);


    }


    @Test
    void test_transaction_with_discard() {
        //开启
        redisTemplate.multi();
        redisTemplate.opsForList().leftPushAll("T1", "V1", "V2", "V3");
        redisTemplate.opsForList().leftPushAll("T2", "V1", "V2");
        //回滚
        redisTemplate.discard();
        //数据并没有存储
        Assertions.assertEquals(redisTemplate.opsForList().range("T1", 0, -1).size(), 0);
        Assertions.assertEquals(redisTemplate.opsForList().range("T2", 0, -1).size(), 0);
    }
}
