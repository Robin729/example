package example.algorithm.sort;

public class QuickSort {

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 2, 4, 6, 8};
        quickSort(array, 0, array.length - 1);
        System.out.println("test quickSort method: ");
        for (int i: array)
            System.out.print(i + ", ");
    }

    /**
     * 思路：
     * 1.抽出第一本书，以这本书为基准，大的放右边，小的放左边
     * 2.最后这本书一定是在正确的位置
     * 3.然后再将左边和右边的排序即可
     * 本质：
     * 每次排一本，并且该本的左边的书都比这本小，右边都比这本大
     * @param array
     * @param left
     * @param right
     */
    public static void quickSort(int[] array, int left, int right) {
        int l = left;
        int r = right;
        int key = array[l];
        while (l < r) {
            while (l < r && array[r] > key)
                r--;
            if (l < r) {
                swap(array, l, r);
                l++;
            }
            while (l < r && array[l] <= key)
                l++;
            if (l < r) {
                swap(array, l, r);
                r--;
            }
        }
        array[l] = key;
        if (left < l - 1)
            quickSort(array, left, l);
        if (right > l + 1)
            quickSort(array, l + 1, right);
    }

    private static void swap(int[] array, int var1, int var2) {
        int temp = array[var1];
        array[var1] = array[var2];
        array[var2] = temp;
    }
}
