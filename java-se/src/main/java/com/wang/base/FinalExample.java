package com.wang.base;

public class FinalExample {
    public int addOne(final int x){
        // return ++x 编译不通过
        return x+1;
    }
}
