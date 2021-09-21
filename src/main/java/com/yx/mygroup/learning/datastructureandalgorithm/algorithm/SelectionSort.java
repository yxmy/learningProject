package com.yx.mygroup.learning.datastructureandalgorithm.algorithm;

/**
 * 选择排序
 */
public class SelectionSort {

    public static void sort(int [] arr){
        int k = 0;
        int tmp = 0;
        for(int i=0;i<arr.length-1;i++){
            k = i;
            for(int j = i; j <arr.length; j++){
                if(arr[j] < arr[k]){
                    k = j;
                }
            }
            tmp = arr[i];
            arr[i] = arr[k];
            arr[k] = tmp;
        }
    }

    public static void main(String[] args) {
        int [] arr = {0,2,5,4,3,-1};
        sort(arr);
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

}
