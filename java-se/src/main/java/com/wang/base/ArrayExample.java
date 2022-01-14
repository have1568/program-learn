package com.wang.base;

public class ArrayExample {


    private static int arr[] = {3, 5, 3, 8, 9, 21, 6, 7, 3, 8};//定义一个用于数组练习的数组对象

    public static void main(String[] args) {
        //定义一个一维数组
        //方式一
        int arr[] = {1, 3, 5};
        int[] arr1 = new int[]{1, 3, 5};
        //方式2
        int arr2[] = new int[3];
        arr2[0] = 1;
        arr2[1] = 2;
        arr2[2] = 3;

        //遍历一维数组
        for (int i = 0; i < arr2.length; i++) {
            System.out.println(arr2[i]);
        }

        pascalTriangleExample(12);
    }


    //杨辉三角示例
    public static void pascalTriangleExample(int row) {
        int[][] y = new int[row][];
        //创建二维数组
        for (int i = 0; i < row; i++) {
            y[i] = new int[i + 1];
            for (int j = 0; j < i + 1; j++) {
                if (j < 1 || i == j) {
                    //每行第一个元素和最后一个元素赋值为1
                    y[i][j] = 1;
                } else {
                    y[i][j] = y[i - 1][j - 1] + y[i - 1][j];
                }
            }
        }

        //遍历输出二维数组
        for (int i = 0; i < y.length; i++) {
            System.out.println();
            for (int j = 0; j < y[i].length; j++) {
                System.out.print(y[i][j] + "\t");
            }
        }
    }

    //数组复制
    public static void copyArray() {
        int arrCopy[] = new int[arr.length];
        for (int i = 0; i < arrCopy.length; i++) {
            arrCopy[i] = arr[i];
        }
    }

    //数组的反转
    public static void arrReverse() {
        int arrReverse[] = new int[arr.length];
        for (int i = 0; i < arrReverse.length; i++) {
            arrReverse[i] = arr[arrReverse.length - i];
        }
    }


    //二分查找,在有序集合里查找对应元素的索引
    public int binarySearch(int sortedArray[], int des) {


        int start = 0;
        int end = sortedArray.length - 1;

        while (start <= end) {
            int middle = (start + end) >>> 1;
            int middleValue = sortedArray[middle];
            if (middleValue == des) {
                return middle;
            } else if (middleValue < des) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }

        }
        return -(start); //找不到元素时，返回该元素理论上在有序数组里的位置索引的相反数
    }


}
