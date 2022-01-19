package com.wang.multithing;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


public class ForkJoinTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long l = 1000000000L;
        Instant start = Instant.now();
        ForkJoinTask<Long> result = forkJoinPool.submit(new Worker(1, l));
        //阻塞
        Long aLong = result.get();
        forkJoinPool.shutdown();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
        System.out.println(aLong);

        long sum = 0;
        Instant start1 = Instant.now();
        for (long i = 0; i <= l; i++) {
            sum += i;
        }
        Instant end1 = Instant.now();
        System.out.println(Duration.between(start1, end1));
        System.out.println(sum);
    }
}

class Worker extends RecursiveTask<Long> {

    private final long start;
    private final long end;
    long sum;

    //超过10000分解任务
    public static final long FLAG = 10000L;

    Worker(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long len = end - start;
        if (len <= FLAG) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //递归拆分任务
            long mid = (start + end) / 2;
            Worker left = new Worker(start, mid);
            left.fork();
            Worker right = new Worker(mid + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}