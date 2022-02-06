package com.wang.huawei;

public class HJ2 {
    public static void main(String[] args) {


        System.out.println(charCounter("ABCabc",'A'));

    }

    public static int charCounter(String str, char target) {

        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (Character.toLowerCase(target) == Character.toLowerCase(str.charAt(i))) {
                count++;
            }
        }

        return count;

    }
}
