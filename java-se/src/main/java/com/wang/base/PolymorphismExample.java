package com.wang.base;

/**
 * 运行时多态：方法重写时
 * 编译时多态：方法重载
 * 可以反编译class文件，编译后重载方法已编译，但是父类里没有子类重写的方法
 */
public class PolymorphismExample {


    public  void test() {
        People people = new Man();
        people.say();
    }

    class People{
        public int height = 178;
        public int age = 30;

        public void say(){
            System.out.println("People");
        }

        public void say(String word){
            System.out.println(word);
        }
    }

    class Man extends People{
        @Override
        public void say() {
            System.out.println("Man");
        }
    }

    class Woman extends People{
        @Override
        public void say() {
            System.out.println("Woman");
        }
    }
}
