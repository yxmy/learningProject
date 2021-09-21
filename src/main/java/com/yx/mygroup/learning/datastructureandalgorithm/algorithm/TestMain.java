package com.yx.mygroup.learning.datastructureandalgorithm.algorithm;

public class TestMain {
    public static void main(String[] args) {
        int [] arr = new int[]{0,2,3,1,4,5,8,3};
        for(int data: arr){
            System.out.print(data);
        }
        System.out.println();
//        BubbleSort.sort(arr);
//        SelectionSort.sort(arr);
        QuickSort.sort(arr, 0, arr.length - 1);
        for(int data: arr){
            System.out.print(data);
        }
    }
}
