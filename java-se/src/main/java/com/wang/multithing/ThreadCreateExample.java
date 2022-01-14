package com.wang.multithing;

import java.util.concurrent.*;

public class ThreadCreateExample {

    public static void main(String[] args) {

        //方式1：继承Thread
        Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.setName("方式1：");
        thread.start();

        //方式2：实现Runnable接口
        Runnable runnable = () -> System.out.println("方式2：" + Thread.currentThread().getName());
        runnable.run();

        //方式3：实现Callable接口 可以获取call的返回值
        FutureTask<Integer> task = new FutureTask<>(() -> {
            System.out.println("方式3：");
            return 3;
        });
        task.run();
        //获取 Callable call()的返回值
        try {
            System.out.println(task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //方式4：使用线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
        }
        executorService.shutdown();

    }
}
