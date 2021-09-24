package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.sort;

/**
 * 希尔排序
 *
 * @author yuanxin
 * @date 2021/9/23
 */
public class ShellSort implements SortInterface {

    @Override
    public void sort(int[] arr) {
        //gap是增量，并逐步缩小，直到 = 1
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap的元素开始，逐个对其所在组进行直接插入排序操作。
            // 相当于把坐标为 2，4，6，8，10 这几个元素看成一个数组，对他进行插入排序
            //内部的循环 j 元素 只和前面的比较，所以这个循环需要一直向后走，让j后面的元素也比较到
            // 就是说 4 只和 2 比较了，6还需要和4 和 2 比较。。。
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - gap];
                    arr[j - gap] = tmp;
                    j -= gap;
                }
            }
        }
    }
}
