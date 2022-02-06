package com.wang.huawei;

public class HJ1 {
    public static void main(String[] args) {

        lastStrLen("hello world");
    }

    public static void lastStrLen(String str) {
        String[] strs = str.split(" ");
        System.out.println(strs[strs.length < 1 ? 1 : strs.length - 1].length());
    }
}
