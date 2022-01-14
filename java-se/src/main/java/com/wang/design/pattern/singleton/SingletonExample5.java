package com.wang.design.pattern.singleton;

/**
 * 饿汉式（静态内部类）  推荐使用
 */
public class SingletonExample5 {


    private SingletonExample5() {

    }

    //在调用 getInstance()时才会装在内部类
    private static class InnerSingletonExample5{
        private static final SingletonExample5 INSTANCE = new SingletonExample5();
    }

    public static  SingletonExample5 getInstance() {
        return InnerSingletonExample5.INSTANCE;
    }
}
