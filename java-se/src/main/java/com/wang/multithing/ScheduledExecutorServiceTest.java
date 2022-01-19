package com.wang.multithing;

import java.util.concurrent.*;

public class ScheduledExecutorServiceTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            ScheduledFuture<Integer> future = pool.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + finalI);
                return finalI;
            }, 3, TimeUnit.SECONDS);

            //阻塞线程,这样就是3秒执行一次
            future.get(); //不阻塞在3秒后求全部输出
        }


        pool.shutdown();

    }
}
