package com.yx.springboot.demospring.learning.datastructureandalgorithm.algorithm.search;

/**
 * 二分法
 * @author yuanxin
 * @date 2021/9/12.
 */
public class BinarySearch {

    /**
     * 查找num是否存在于arr中
     */
    public static boolean search1(int [] arr, int num) {
        int small = 0;
        int big = arr.length - 1;
        int middleIndex;
        while (small <= big) {
//            middleIndex = small + (big - small) / 2;
            //插值算法，适用于数据分布比较均匀的数组
            middleIndex = small + (big - small) * (num - arr[small]) / (arr[big] - arr[small]);
            if (arr[middleIndex] == num) {
                return true;
            } else if (arr[middleIndex] < num) {
                small = middleIndex + 1;
            } else if (arr[middleIndex] > num) {
                big = big - 1;
            }
        }
        return false;
    }

    public static void main(String [] args) {
        int [] arr = {16,35,47,59,62,73,99};
        System.out.println(search1(arr, 99));
    }
}
