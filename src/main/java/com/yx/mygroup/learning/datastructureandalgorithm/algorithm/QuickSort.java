package com.yx.mygroup.learning.datastructureandalgorithm.algorithm;

/**
 * 快速排序
 */
public class QuickSort {

    public static int partition(int [] arr, int left, int right, int point){
        int leftpar = left - 1;
        int rightpar = right;
        while (true) {
            while (leftpar < rightpar && arr[++leftpar] < point){}
            while (rightpar < leftpar && arr[--rightpar] > point){}
            if(leftpar >= rightpar){
                break;
            }else {
                int tmp = arr[leftpar];
                arr[leftpar] = arr[rightpar];
                arr[rightpar] = tmp;
            }
        }
        int tmp = arr[right];
        arr[right] = arr[leftpar];
        arr[leftpar] = tmp;
        return leftpar;
    }

    public static void sort(int [] arr, int left, int right){
        if (right - left <= 0){
            return;
        }
        int point = arr[right];
        int partition = partition(arr, left, right, point);
        sort(arr, left, partition - 1);
        sort(arr, partition + 1, right);
    }
}
