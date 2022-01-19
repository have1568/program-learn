package com.wang.multithing;

import java.util.concurrent.*;

public class FutureTest {

    public static int sum = 0;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        Future<Integer> future = null;
        for (int i = 0; i <= 100; i++) {

            int finalI = i;
            future = pool.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    System.out.println(finalI);
                    return sum += finalI;
                }
            });


        }
        // future.get()会阻塞线程，等待线程任务都已完成
        Integer r = future.get();
        pool.shutdown();
        System.out.println("=====" + sum);
    }
}
