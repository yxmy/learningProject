package com.yx.springboot.demospring.datastructure;


/**
 * 堆 https://blog.csdn.net/u013728021/article/details/84034420
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class Heap {

    /**
     * 获取左孩子节点的下标
     *
     * @param i 父节点
     * @return 左孩孑节点坐标
     */
    public int left(int i) {
        return (i * 2) - 1;
    }

    /**
     * 获取右孩子节点的下标
     *
     * @param i 父节点
     * @return 右孩子节点坐标
     */
    public int right(int i) {
        return i * 2;
    }

    /**
     * 获取父节点的下标
     *
     * @param i 子节点
     * @return 父节点下标
     */
    public int parent(int i) {
        if (i == 0) {
            return -1;
        }
        return (i - 1) / 2;
    }

    /**
     * 将坐标为i的元素按照大根堆的特性存储
     *
     * @param a          堆数组
     * @param i          非叶子节点坐标
     * @param heapLength 堆长度
     */
    public void maxRootHeap(Integer[] a, int i, int heapLength) {
        int left = left(i);
        int right = right(i);
        int largest = -1;
        if (left < heapLength && a[i].compareTo(a[left]) < 0) {
            largest = left;
        } else {
            largest = i;
        }
        if (right < heapLength && a[largest].compareTo(a[right]) < 0) {
            largest = right;
        }
        if (largest != i) {
            int tmp = a[i];
            a[i] = a[largest];
            a[largest] = tmp;
            maxRootHeap(a, largest, heapLength);
        }
    }

    /**
     * 将坐标为i的元素按照小根堆的特性存储
     *
     * @param a          堆数组
     * @param i          非叶子节点坐标
     * @param heapLength 堆长度
     */
    public void minRootHeap(Integer[] a, int i, int heapLength) {
        int left = left(i);
        int right = right(i);
        int smallest;
        if (left < heapLength && a[i].compareTo(a[left]) > 0) {
            smallest = left;
        } else {
            smallest = i;
        }
        if (right < heapLength && a[smallest].compareTo(a[right]) > 0) {
            smallest = right;
        }
        if (smallest != i) {
            int tmp = a[i];
            a[i] = a[smallest];
            a[smallest] = tmp;
            maxRootHeap(a, smallest, heapLength);
        }
    }

    /**
     * 构建堆
     *
     * @param a          堆数组元素
     * @param heapLength 堆长度
     * @param isMaxRoot  是否构建大根堆
     */
    public void buildHeap(Integer[] a, int heapLength, boolean isMaxRoot) {
        //最后一个元素必定是叶子节点
        int parentLength = parent(heapLength - 1);
        for (int i = parentLength; i >= 0; i--) {
            if (isMaxRoot) {
                maxRootHeap(a, i, heapLength);
            } else {
                minRootHeap(a, i, heapLength);
            }
        }
    }

}
