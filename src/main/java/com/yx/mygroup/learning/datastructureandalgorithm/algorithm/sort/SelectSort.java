package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序
 *
 * @author yuanxin
 * @date 2021/9/21.
 */
public class SelectSort implements SortInterface {

    @Override
    public void sort(int[] arr) {
        int min;
        for (int i = 0; i < arr.length; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                int tmp = arr[min];
                arr[min] = arr[i];
                arr[i] = tmp;
            }
            System.out.println("第" + (i + 1) + "次排序后：" + Arrays.toString(arr));
        }
    }
}
