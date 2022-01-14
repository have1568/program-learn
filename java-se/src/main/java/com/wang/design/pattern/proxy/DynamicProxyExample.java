package com.wang.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxyExample {


    public static void main(String[] args) {

        //设置变量可以保存动态代理类，默认名称以 $Proxy0 格式命名
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        IHello hello = new Hello();
        //Proxy.newProxyInstance()
        //hello.getClass().getClassLoader()  被代理类的类加载器
        //hello.getClass().getInterfaces() //被代理类的父类接口
        //new ProxyHandler(hello) //代理处理器
        IHello proxy = (IHello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), new ProxyHandler(hello));
        proxy.doSomething();
    }
}

class ProxyHandler implements InvocationHandler{

    private Object object;

    public ProxyHandler(Object object) {
        this.object = object;
    }

    public void before() {
        System.out.println("Dynamic Proxy Before");
    }

    public void after() {
        System.out.println("Dynamic Proxy After");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(object,args);
        after();
        return null;
    }
}


interface IHello{
    void doSomething();
}

class Hello implements IHello{
    @Override
    public void doSomething() {
        System.out.println("Do Something!");
    }
}
