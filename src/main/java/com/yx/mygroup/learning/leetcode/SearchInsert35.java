package com.yx.mygroup.learning.leetcode;

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 你可以假设数组中无重复元素。
 *
 * @author yuanxin
 * @date 2021/5/21
 */
public class SearchInsert35 {

    /**
     * 二分法
     */
    public static int searchInsert(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums[0] >= target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        int beginIndex = 0;
        int endIndex = nums.length - 1;
        int intermediate;
        while (endIndex - beginIndex > 1) {
            if (nums[beginIndex] == target) {
                return beginIndex;
            }
            if (nums[endIndex] == target) {
                return endIndex;
            }
            intermediate = (endIndex + beginIndex) / 2;
            if (nums[intermediate] == target) {
                return intermediate;
            } else if (nums[intermediate] > target) {
                endIndex = intermediate;
            } else if (nums[intermediate] < target) {
                beginIndex = intermediate;
            }
        }
        if (endIndex - beginIndex == 1) {
            if (nums[beginIndex] == target) {
                return beginIndex;
            } else if (nums[endIndex] == target) {
                return endIndex;
            }
        }
        return beginIndex + 1;
    }

    public int searchInsert1(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, ans = n;
        while (left <= right) {
            int mid = ((right - left) >> 1) + left;
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int [] arr = {1,3,5,6};
        System.out.println(searchInsert(arr, 0));
    }

}
