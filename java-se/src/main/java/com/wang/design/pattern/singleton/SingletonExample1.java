package com.wang.design.pattern.singleton;

/**
 * 饿汉式(静态常量)  可用
 */
public class SingletonExample1 {

    private final static SingletonExample1 INSTANCE = new SingletonExample1();

    private SingletonExample1() {

    }

    public static SingletonExample1 getInstance() {
        return INSTANCE;
    }
}
