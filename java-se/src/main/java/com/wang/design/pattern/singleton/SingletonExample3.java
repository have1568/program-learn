package com.wang.design.pattern.singleton;

/**
 * 懒汉式（同步方法）  不推荐使用
 */
public class SingletonExample3 {

    private static SingletonExample3 INSTANCE = new SingletonExample3();

    private SingletonExample3() {

    }

    public static synchronized SingletonExample3 getInstance() {
        if (INSTANCE == null) {
            return new SingletonExample3();
        }
        return INSTANCE;
    }
}
