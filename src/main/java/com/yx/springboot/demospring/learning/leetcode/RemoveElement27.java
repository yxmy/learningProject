package com.yx.springboot.demospring.learning.leetcode;

/**
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 *
 * @author yuanxin
 * @date 2021/5/21
 */
public class RemoveElement27 {

    /**
     * fast遍历，slow记录下一个不等于val的值存放的位置
     */
    public static int removeElement(int[] nums, int val) {
        int fast = 0;
        int slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    public static void main(String[] args) {
        int [] nums = {1,1,1,1};
        final int length = removeElement(nums, 1);
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            System.out.print(nums[i] + ",");
        }
    }


}
