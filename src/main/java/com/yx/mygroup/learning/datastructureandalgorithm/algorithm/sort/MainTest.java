package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.sort;

import java.util.Arrays;

/**
 * @author yuanxin
 * @date 2021/9/21.
 */
public class MainTest {

    public static void main(String[] args) {
        int [] arr = {9,1,5,8,3,7,4,6,2};
        System.out.println("排序前：" + Arrays.toString(arr));
//        SortInterface sort = new BubbleSort();
//        SortInterface sort = new SelectSort();
//        SortInterface sort = new InsertSort();
        SortInterface sort = new ShellSort();
        sort.sort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }

}
