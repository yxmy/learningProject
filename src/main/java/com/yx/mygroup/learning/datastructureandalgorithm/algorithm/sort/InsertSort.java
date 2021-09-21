package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @author yuanxin
 * @date 2021/9/21.
 */
public class InsertSort implements SortInterface {

    @Override
    public void sort(int[] arr) {
        //从下标为1的开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < arr.length; i++) {
            //要插入的数据
            int tmp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > tmp) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != i) {
                arr[j] = tmp;
            }
            System.out.println("第" + (i + 1) + "次排序后：" + Arrays.toString(arr));
        }
    }
}
