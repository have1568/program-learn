package com.wang.huawei;

import java.util.Scanner;
import java.util.TreeSet;

public class HJ3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TreeSet<Integer> numbers = new TreeSet<>();
        System.out.println("输入： EXIT 结束输入");
        while (scanner.hasNext()) {
            String next = scanner.next();
            if ("EXIT".equals(next)) {
                scanner.close();
                break;
            }
            try {
                int inputNumber = Integer.parseInt(next);
                numbers.add(inputNumber);
            } catch (Exception e) {
                System.out.println("请输入合法的数字@" + next);
            }

        }

        numbers.forEach(System.out::println);
    }

}
