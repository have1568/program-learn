package com.wang.base;

/**
 * 分类：
 * 成员内部类（静态成员内部类和非静态成员内部类）
 * 可以理解为类的一个属性
 * 局部内部类（匿名和非匿名）
 */

public class InnerClassExample {

    //静态成员内部类
    private final static class InnerClass {

    }

    //非静态成员内部类
    class InnerClass2 {

    }


    //定义在静态代码块的局部内部类
    static {
        class InnerClass3 {

        }
    }

    //定义在代码块里的局部内部类
    {
        class InnerClass4 {

        }
    }

    //定义在构造方法里的局部内部类
    public InnerClassExample() {
        class InnerClass4 {

        }

    }

    //定义在方法里的局部内部类
    public void test() {

        class InnerClass5 {

        }
    }

    //定义在方法里的局部匿名内部类
    public Comparable getCompare() {
        return new Comparable() {
            @Override
            public int compareTo(Object o) {
                return 0;
            }
        };
    }
}
