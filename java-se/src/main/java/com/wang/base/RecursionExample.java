package com.wang.base;

/**
 * 递归示例
 */
public class RecursionExample {

    //求阶乘
    public int factorial(int number) {
        return number > 1 ? number * this.factorial(number - 1) : number;
    }

    //数列

    /*
     * 已知有一个数列：f(0) = 1,f(1) = 4,f(n+2)=2*f(n+1) + f(n),其中n是大于0
     * 的整数，求f(10)的值。
     * f(n+2)=2*f(n+1) + f(n) 等价于 f(n)=2*f(n-1) + f(n-2)
     */
    public int sequence(int number) {
        if (number < 0) return number;
        switch (number) {
            case 0:
                return 1;
            case 1:
                return 4;
            default:
                //return sequence(number + 2) - 2 * sequence(number + 1); //求10 会去计算11和12，这样就会导致无限递归，改成减法就会计算9和8，知道
                return 2 * sequence(number - 1) + sequence(number - 2);
        }
    }

    /*
     * 计算斐波那契数列(Fibonacci)
     */
    public int fibonacci(int number) {
        switch (number) {
            case 1:
            case 2:
                return 1;
            default:
                return fibonacci(number - 1) + fibonacci(number - 2);
        }
    }
}
