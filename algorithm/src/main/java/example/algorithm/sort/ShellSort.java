package example.algorithm.sort;

public class ShellSort {

    public static void main(String[] args) {
        int[] array = new int[]{3, 5, 7, 4, 1, 2, 6, 9, 8};
        shellSort1(array);
        System.out.println("bubble sort result:");
        for (int i: array)
            System.out.print(i + ",");
    }

    // 移动法希尔排序
    public static void shellSort(int[] array) {
        int gap = array.length / 2;
        while (gap > 0) {
            for (int i = gap; i < array.length; i++) {
                // 本质上是插入排序，插入的间隔是gap
                int temp = array[i];
                int index = i;
                while (index - gap >= 0 && array[index - gap] > array[index]) {
                    array[index] = array[index - gap];
                    index -= gap;
                }
                array[index] = temp;
            }
            gap = gap / 2;
        }
    }

    // 交换法
    public static void shellSort1(int[] array) {
        int gap = array.length / 2;
        while (gap > 0) {
            for (int i = gap; i < array.length; i++) {
                // 本质上是插入排序，这里用的是交换
                int index = i;
                while (index - gap >= 0 && array[index - gap] > array[index]) {
                    swap(array, index, index - gap);
                    index -= gap;
                }
            }
            gap = gap / 2;
        }
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
