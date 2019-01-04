package com.lanshifu.demo_module.suanfa;

/**
 * 最小堆
 * https://mp.weixin.qq.com/s/JIwmkT52G2A1PajLWijiuA
 *
 * @param <E>
 */
public class MinHeap<E> {

    private int[] data;
    private int capacity = 12;
    private int size = 0;

    public MinHeap() {
        data = new int[capacity];
    }

    public int size() {
        return size;
    }

    /**
     * 推 空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 该节点的父节点
     *
     * @param index
     * @return
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * 左孩子的下标
     *
     * @param index
     * @return
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 右孩子下标
     *
     * @param index
     * @return
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * 交换位置
     *
     * @param i
     * @param j
     * @return
     */
    private void swap(int i, int j) {
        if (i < 0 || i > size || j < 0 || j > size) {
            new IllegalAccessException("角标越界");
        }

        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 添加
     *
     * @param e
     */
    public void add(int e) {
        //添加到最后
        data[size] = e;
        siftUp(size);
        size++;
        //上浮

    }

    /**
     * 上浮，插入节点与父节点比较
     *
     * @param i
     */
    private void siftUp(int i) {
        while (i > 0) {
            int parent = data[parent(i)];
            int current = data[i];
            if (parent >= current) {
                //不需要上浮
                return;
            }
            //上浮，切换位置，i赋值为父节点下标
            swap(current, parent);
            i = parent(i);

        }

    }

    public int findMin() {
        return data[0];
    }

    public int deleteMin() {
        int min = findMin();
        //跟最后一个替换位置
        swap(min, data[size - 1]);
        //下沉比较
        siftDown(0);
        size--;
        return min;
    }

    /**
     * 下沉
     *
     * @param i
     */
    public void siftDown(int i) {
        while (leftChild(i) < size) {
            int parent = data[i];
            //找左右孩子的最小值，然后跟parent 比较
            int leftChildIndex = leftChild(i);
            int rightChildIndex = rightChild(i);
            int leftRightMinIndex = (leftChildIndex - rightChildIndex > 0 ? leftChildIndex : rightChildIndex);
            if (parent <= data[leftRightMinIndex]) {
                //parent小，return
                return;
            }
            swap(parent, leftRightMinIndex);

            i = leftRightMinIndex;
        }


    }



}
