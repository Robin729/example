package Java;

public class InsertionSort {

    /**
     * 1.类似扑克牌的排序，j-1排好了，该排序第j个了
     *   抽出第j张牌， key = A[j]
     * 2.从j-1开始，i = j-1， A[i] 和 key 比较大小，大于就后移
     * 3.直到找到key应该到到位置，把扑克牌插入正确到位置
     * 简洁步骤：抽牌，大于后移，插入
     *
     */
    public static void main(String[] args) {
        int[] A = {5, 4, 6, 2, 9};
        System.out.print("small to big");
        insertionSort(A);
        for (int i: A)
            System.out.print(i + ",");
        System.out.println();

        System.out.print("big to small: ");
        insertionSortReverse(A);
        for (int i: A)
            System.out.print(i + ",");
    }

    // small to big
    public static void insertionSort(int[] A) {
        for (int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while(i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i + 1] = key;
        }
    }

    // big to small
    public static void insertionSortReverse(int[] A) {
        for(int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while(i >= 0 && A[i] < key) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i + 1] = key;
        }
    }
}


