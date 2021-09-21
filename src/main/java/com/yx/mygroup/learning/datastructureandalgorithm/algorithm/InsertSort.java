package com.yx.mygroup.learning.datastructureandalgorithm.algorithm;

/**
 * 插入排序
 */
public class InsertSort {

    public static void sort(int [] arr){
        int tmp = 0;
        for(int i = 1; i<arr.length; i++){
            tmp = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp;
        }
    }
}
