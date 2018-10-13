package example.algorithm.heap;

public class MaxHeap {

    int[] array;
    int size;
    public void buildMaxHeap(int[] array) {
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            maxHeapify(array, i);
        }
    }

    /**
     * 扩增策略未完成
     */
    public void grow() {

    }

    public void add(int element) {
        int index = size;
        if (index > array.length)
            grow();

        size = index + 1;
        if (index == 0)
            array[size] = element;
        else
            shiftUp(index, element);
    }
    // 和插入排序类似。第k个元素应该放到数组的最后
    // 放好后就需要上滤，但这2个步骤可以合并到一起。
    public void shiftUp(int index, int element) {
        while (index > 0) {
            int parent = (index - 1) >>> 1;
            if (array[parent] > array[index])
                break;

            array[index] = array[parent];
            index = parent;
        }
        array[index] = element;
    }

    public void maxHeapify(int[] array, int index) {
        int largest;
        int left = (index << 1)  + 1;
        int right = (index << 1) + 2;
        if (left < array.length && array[left] > array[index])
            largest = left;
        else
            largest = index;

        if (right < array.length && array[right] > array[largest])
            largest = right;

        if (largest != index) {
            swap(array, index, largest);
            maxHeapify(array, largest);
        }
    }

    public void maxHeapify1(int[] array, int index) {
        int largest, left, right;
        while(true) {
            left = (index << 1) + 1;
            right = (index << 1) + 2;
            if (left < array.length && array[left] < array[index])
                largest = left;
            else
                largest = index;

            if (right < array.length && array[right] < array[largest])
                largest = right;

            if (largest != index) {
                swap(array, largest, index);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}
