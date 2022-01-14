package com.wang.design.pattern.proxy;

/**
 * 静态代理
 */
public class StaticProxyExample {
    public static void main(String[] args) {
        ProxyUserService userService = new ProxyUserService(new UserService());
        userService.save();
    }
}


// 公用接口
interface IUserService {

    void save();
}

//目标对象
class UserService implements IUserService {
    @Override
    public void save() {
        System.out.println("-----保存数据-----");
    }
}

//代理对象
class ProxyUserService implements IUserService {

    private IUserService userService;

    //装饰设计模式的运用
    public ProxyUserService(IUserService userService) {
        this.userService = userService;
    }

    public void before() {
        System.out.println("Proxy Before");
    }

    public void after() {
        System.out.println("Proxy After");
    }

    @Override
    public void save() {
        before();
        userService.save();
        after();
    }
}




