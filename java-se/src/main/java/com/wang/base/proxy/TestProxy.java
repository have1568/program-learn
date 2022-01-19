package com.wang.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy {
    public static void main(String[] args) {
        People people = new Man();
        People proxyInstance = (People) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{People.class}, new Worker(people));
        proxyInstance.say();
    }
}

interface People {
    void say();
}


class Man implements People {

    @Override
    public void say() {
        System.out.println("MEN");
    }
}

class Worker implements InvocationHandler {

    //被代理的对象
    public Object obj;

    public Worker(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        //执行代理对象的方法
        Object invoke = method.invoke(obj, args);
        System.out.println("after");
        return invoke;
    }
}