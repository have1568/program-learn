package com.wang.multithing;

public class Example1 {

    public static int num = 0;

    public static void main(String[] args) throws InterruptedException {


        new Worker1().start();
        new Worker2().start();

    }

    static class Worker1 extends Thread {


        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                num++;
            }
        }
    }

    static class Worker2 extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                num++;
            }
        }
    }
}
