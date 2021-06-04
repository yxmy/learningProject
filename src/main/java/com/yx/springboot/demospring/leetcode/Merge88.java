package com.yx.springboot.demospring.leetcode;

/**
 * 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nu
 * ms2 的元素。
 *
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 *
 * @author yuanxin
 * @date 2021/6/3
 */
public class Merge88 {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int [] numsNew = new int[m + n];
        int p1 = 0, p2 = 0;
        while (p1 < m || p2 < n) {
            int p1Num = p1 < m ? nums1[p1] : Integer.MAX_VALUE;
            int p2Num = p2 < n ? nums2[p2] : Integer.MAX_VALUE;
            if (p1Num < p2Num) {
                numsNew[p1 + p2] = p1Num;
                p1++;
            } else if (p1Num == p2Num) {
                numsNew[p1 + p2] = p1Num;
                numsNew[p1 + p2 + 1] = p2Num;
                p1++;
                p2++;
            } else {
                numsNew[p1 + p2] = p2Num;
                p2++;
            }
        }
        System.arraycopy(numsNew, 0, nums1, 0, numsNew.length);
    }

    public static void main(String[] args) {
        int [] nums1 = {0};
        int m = 0;
        int [] nums2 = {1};
        int n = 1;
        merge(nums1, m, nums2, n);
        for (int i : nums1) {
            System.out.print(i + ",");
        }
    }

}
