package com.wang.base;

/**
 * 冒泡，选择，插入三种排序，TODO 其他几种
 */
public class SortExample {

    //冒泡排序，大的数放在最后面,内循环不用去比较最后免得有序大数
    public int[] bubbleSort(int arr[]) {
        int count = 0;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
                count++;
            }
        }
        System.out.println(count);
        return arr;
    }


    //每一次拿前面没有排序的第一个数和后面的依次比较，遇到比自己小的数就交换位置
    public int[] selectionSort(int arr[]) {
        int count = 0;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {  //-1是为了防止j=i+1越界，最后一个也没有必要比较
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                count++;
            }
        }
        System.out.println(count);
        return arr;
    }

    public int[] insertSort(int arr[]) {
        int count = 0;
        int temp;

        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            for (int j = i; j > 0; j--) {
                if (temp > arr[j - 1]) {
                    continue;
                } else {  //temp = arr[i]=arr[j],将j-1移至j,再将j-1处赋值为当前用来比较插入的值
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
                count++;
            }
        }
        System.out.println(count);
        return arr;
    }
}
