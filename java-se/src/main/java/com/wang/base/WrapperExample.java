package com.wang.base;

public class WrapperExample {

    public static void main(String[] args) {
        Integer i = new Integer(1);
        Integer j = new Integer(1);
        System.out.println(i.equals(j));
        Integer m = 1; //等效于 Integer m Integer.valueOf(1);
        Integer n = 1; //等效于 Integer n Integer.valueOf(1);
        System.out.println(m.equals(n));//  缓存命中
        Integer x = 128; //等效于 Integer x Integer.valueOf(128);
        Integer y = 128; //等效于 Integer y Integer.valueOf(128);
        System.out.println(x.equals(y));// 缓存未命中
    }

}
