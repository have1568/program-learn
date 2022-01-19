package com.wang.multithing;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService writer = Executors.newFixedThreadPool(5);
        ExecutorService reader = Executors.newFixedThreadPool(10);
        ReadWriteLockWorker worker = new ReadWriteLockWorker();
        //CountDownLatch需要分别设置
        CountDownLatch writeCount = new CountDownLatch(100);
        CountDownLatch readCount = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            writer.execute(() -> {
                writeCount.countDown();
                worker.writeNumber(finalI);
            });
        }

        for (int i = 0; i < 100; i++) {
            writeCount.countDown();
            reader.execute(() -> {
                readCount.countDown();
                worker.readNumber();
            });
        }

        writeCount.await();
        readCount.await();
        System.out.println(worker.countRead);
        System.out.println(worker.countRead);
        writer.shutdown();
        reader.shutdown();

    }
}


class ReadWriteLockWorker {

    private int number = 100;

    //由于琐事可重入
    ReadWriteLock lock = new ReentrantReadWriteLock();

    AtomicInteger countRead = new AtomicInteger();
    AtomicInteger countWrite = new AtomicInteger();

    public void readNumber() {
        try {
            lock.readLock().lock();
            countRead.incrementAndGet();
            System.out.println("读：" + Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    public  void writeNumber(int value) {
        try {
            lock.writeLock().lock();
            number = value;
            countWrite.incrementAndGet();
            System.out.println("写：" + Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.writeLock().unlock();
        }
    }


}
