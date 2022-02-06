package com.wang.huawei;

import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class HJ4 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String next = scanner.next();

            try {
                int randomTotal = Integer.parseInt(next);
                if (randomTotal < 1 || randomTotal > 500) {
                    System.out.println("输入数字在1~500之间，包含1和500");
                }
                Random random = new Random();
                TreeSet<Integer> numbers = new TreeSet<>();
                for (int i = 0; i < randomTotal; i++) {
                    numbers.add(random.nextInt(1000) + 1);
                }
                numbers.forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("请输入合法的数字@" + next);
            }

        }
    }

}
