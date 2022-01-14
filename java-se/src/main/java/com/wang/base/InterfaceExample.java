package com.wang.base;

interface A {
    int x = 0;//不可变常量
}

class B{
    int x = 1;
}


class C extends B implements A{

    public void test(){
        //编译不通过
        //System.out.println(x);
        System.out.println(super.x);
        System.out.println(A.x);
    }

    public static void main(String[] args) {
        new C().test();
    }
}