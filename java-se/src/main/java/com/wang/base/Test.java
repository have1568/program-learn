package com.wang.base;

public interface Test {

    default void test(){

    }
}

class  Aa implements Test{
    @Override
    public void test() {
        Test.super.test();
    }
}
