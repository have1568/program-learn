package com.wang.design.pattern.singleton;

/**
 * 懒汉式（线程不安全）  不可用
 */
public class SingletonExample2 {

    private final static SingletonExample2 INSTANCE = new SingletonExample2();

    private SingletonExample2() {

    }

    public static SingletonExample2 getInstance() {
        if (INSTANCE == null) {
            return new SingletonExample2();
        }
        return INSTANCE;
    }
}
