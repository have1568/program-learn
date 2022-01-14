package com.wang.base;

import java.lang.reflect.Field;

/**
 * 实现交换只能借助引用类型的数据去实现  定义一个类，或者通过数组，Integer也不行
 * Integer hack 之后就可以
 * Unsafe 也可以
 */
public class SwapExample {


    public static void main(String[] args) {
        Integer a = 5;
        Integer b = 10;
        swap(a, b);
        System.out.println("交换结束后，变量a的值是" + a + "；变量b的值是" + b);
        swapInteger(a, b);
        System.out.println("交换结束后，变量a的值是" + a + "；变量b的值是" + b);

    }

    public static void swap(Integer a, Integer b) {
        Integer tmp = a;
        a = b;
        b = tmp;
        System.out.println("swap方法里，a的值是" + a + "；b的值是" + b);
    }

    public static void swapInteger(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int temp = a;
            field.setInt(a, b);
            field.setInt(b, temp);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
