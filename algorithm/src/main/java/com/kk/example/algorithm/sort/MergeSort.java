package com.kk.example.algorithm.sort;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 2, 4, 6, 8};
        merge(array, 0, 4, 8);
        System.out.println("test merge method: ");
        for (int i: array)
            System.out.print(i + ", ");

        System.out.println();
        array = new int[]{3, 5, 7, 4, 1, 2, 6, 9, 8};
        mergeSort(array, 0, 8);
        System.out.println("merge sort result:");
        for (int i: array)
            System.out.print(i + ", ");

        System.out.println();
        array = new int[]{3, 5, 7, 4, 1, 2, 6, 9, 8};
        mergeSort(array);
        System.out.println("just 1 array merge sort result:");
        for (int i: array)
            System.out.print(i + ", ");
    }

    public static void mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    public static void mergeSort(int[] array, int start, int end) {
        if (start < 0 || start > end || end >= array.length) {
            return;
        }
        if (start < end) {
            int mid = (start + end) >> 1;  /* (start + end) / 2 */
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            merge(array, start, mid, end);
        }
    }

    // 1.copy to new left and right array
    // 2.merge left and right to original array
    // 0 <= var1 <= var2 < var3 < array.length no check
    private static void merge(int[] array, int var1, int var2, int var3) {
        int[] left = new int[var2 - var1 + 1];
        int[] right = new int[var3 - var2];

        // copy array[var1:var2] to left[] and array[var2+1:var3] to right[]
        int index = var1;
        for (int i = 0; i < left.length; i++) {
            left[i] = array[index++];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = array[index++];
        }

        // merge array left and right to array
        int l = 0;
        int r = 0;
        index = var1;
        while (l < left.length && r < right.length) {
            if (left[l] > right[r]) {
                array[index] = right[r++];
            } else {
                array[index] = left[l++];
            }
            index++;
        }

        while (l < left.length) {
            array[index++] = left[l++];
        }

        while (r < right.length) {
            array[index++] = right[r++];
        }
    }
}
