package example.algorithm.heap;

public class MinHeap {

    public void minHeapify(int[] array, int index) {
        int smallest;
        int left = (index << 1) + 1;
        int right = (index << 1) + 2;

        if (left < array.length && array[left] < array[index])
            smallest = left;
        else
            smallest = index;

        if (right < array.length && array[right] < array[smallest])
            smallest = right;

        if (smallest != index) {
            swap(array, smallest, index);
            minHeapify(array, smallest);
        }
    }

    private void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
