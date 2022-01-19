package com.wang.multithing;

import java.util.concurrent.*;

/**
 * 线程池示例
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                1,
                1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
}
