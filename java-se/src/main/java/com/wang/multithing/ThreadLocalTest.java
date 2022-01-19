package com.wang.multithing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        NameHolder.setName("hello");

        ExecutorService one = Executors.newSingleThreadExecutor();
        one.execute(() -> {
            countDownLatch.countDown();
            //每个线程只能获取自己内部设置的值
            NameHolder.setName("world");
            //输出 world
            System.out.println(Thread.currentThread().getName() + ":" + NameHolder.getName());
        });
        ExecutorService two = Executors.newSingleThreadExecutor();

        two.execute(() -> {
            //此线程为null
            countDownLatch.countDown();
            //输出NUll
            System.out.println(Thread.currentThread().getName() + ":" + NameHolder.getName());
        });

        countDownLatch.await();
        //输出 Hello
        System.out.println(Thread.currentThread().getName() + ":" + NameHolder.getName());
        one.shutdown();
        two.shutdown();
    }
}


class NameHolder {
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void setName(String value) {
        local.set(value);
    }

    public static String getName() {
        return local.get();
    }

    public void removeName() {
        local.remove();
    }
}