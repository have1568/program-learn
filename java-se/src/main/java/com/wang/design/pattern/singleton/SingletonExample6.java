package com.wang.design.pattern.singleton;

/**
 * 枚举  推荐使用 <<effective java>> 里有讲到
 */
public enum SingletonExample6 {
    INSTANCE;

    public void doSomething() {
        System.out.println("doSomething");
    }
}

