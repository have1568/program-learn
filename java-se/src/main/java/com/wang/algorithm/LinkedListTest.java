package com.wang.algorithm;

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListTest {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println(list);
        ListIterator<Integer> iterator = list.listIterator();


        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }

        while (iterator.hasPrevious()) {
            Integer previous = iterator.previous();
            System.out.println(previous);

        }
    }
}
