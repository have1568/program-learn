package com.wang.design.pattern.singleton;

/**
 * 懒汉式（同步代码块,双重检查）  推荐使用
 */
public class SingletonExample4 {

    private static SingletonExample4 INSTANCE = new SingletonExample4();

    private SingletonExample4() {

    }

    public static synchronized SingletonExample4 getInstance() {
        if(INSTANCE == null){
            synchronized (SingletonExample4.class){
                if (INSTANCE == null) {
                    return new SingletonExample4();
                }
            }
        }
        return INSTANCE;
    }
}
