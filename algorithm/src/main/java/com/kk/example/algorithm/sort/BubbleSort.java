package com.kk.example.algorithm.sort;

public class BubbleSort {
    /**
     * 思路：
     * 1.就是每次找到array的最小值放在首位
     * 2.依次找最小值放在首位
     * 3.如何找？从最后一位开始a[j]和a[j-1]比较，a[j]<a[j-1]，就交换
     * 4.所以最小的就这样冒泡到了首位
     *
     * 核心：依次找最大值，每次冒泡
     */
    public static void main(String[] args) {
        int[] array = new int[]{3, 5, 7, 4, 1, 2, 6, 9, 8};
        bubbleSort(array, 0, array.length - 1);
        System.out.println("bubble sort result:");
        for (int i: array)
            System.out.print(i + ",");
    }

    public static void bubbleSort(int[] array, int start, int end) {
        if (start < 0 || start > end || end >= array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = start; i < end; i++) {
            for(int j = end; j > i; j--) {
                if (array[j] < array[j - 1])
                    swap(array, j - 1, j);
            }
        }
    }

    public static void swap(int[] array, int var1, int var2) {
        int temp = array[var1];
        array[var1] = array[var2];
        array[var2] = temp;
    }
}
