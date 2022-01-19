package com.wang.multithing;

public class ThreadTest {

    public static void main(String[] args) {
        Thread thread = new ThreadExample();
        thread.setName("线程1");
        thread.start();

        Thread.currentThread().setName("主线程");
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);

            if (i == 40) {
                try {
                    //线程获得执行权，并且直到执行完
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (i == 50) {
                thread.interrupt();
                System.out.println(thread.isInterrupted());
            }
        }


    }
}


class ThreadExample extends Thread {

    @Override
    public void run() {
        System.out.println("ThreadExample:::" + Thread.currentThread().getName());
        System.out.println("ThreadExample RUN !");
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
            //如果条件满足，当前线程让出执行权
            if (i == 20) {
                Thread.yield();
            }
        }
    }
}
