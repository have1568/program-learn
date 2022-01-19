package com.wang.multithing;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

    public static AtomicLong value = new AtomicLong(100);

    public static void main(String[] args) {

        //这种代码是不可行的 TODO 原因？
        value.set(Math.max(value.get(), 10));
        //正确姿势
        value.updateAndGet(x -> Math.max(value.get(), 10));
        //或者
        value.accumulateAndGet(10,Math::max);
    }
}
