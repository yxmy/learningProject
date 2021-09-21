package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author yuanxin
 * @date 2021/9/21.
 */
public class BubbleSort implements SortInterface {

    @Override
    public void sort(int[] arr) {
        //用来标记该冒泡排序是否交换过数据
        boolean flag = true;
        //如果没有交换过，说明就是有序的
        for (int i = 0; i < arr.length && flag; i++) {
            flag = false;
            for (int j = arr.length - 1; j > i; j--) {
                //两两比较，从后向前
                if (arr[j - 1] > arr[j]) {
                    int tmp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = tmp;
                    flag = true;
                }
            }
            System.out.println("第" + (i + 1) + "次排序后：" + Arrays.toString(arr));
        }
    }
}
