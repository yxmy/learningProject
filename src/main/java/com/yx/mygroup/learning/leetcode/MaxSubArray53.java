package com.yx.mygroup.learning.leetcode;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * @author yuanxin
 * @date 2021/5/24
 */
public class MaxSubArray53 {

    /**
     * 定义一个变量maxNum，
     * 从数组第一个开始向后面相加，直到最后一个数，找到最大的数值赋值给maxNum。
     * 再从数组的第二个开始向后面相加，直到最后一个数，和maxNum相比，较大的赋值给maxNum
     * 以此类推、、、
     */
    public static int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int maxNum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int subMax = Integer.MIN_VALUE;
            int currSum = Integer.MIN_VALUE;
            for (int j = i; j < nums.length; j++) {
                if (currSum == Integer.MIN_VALUE) {
                    currSum = nums[j];
                } else {
                    currSum += nums[j];
                }
                if (currSum > subMax) {
                    subMax = currSum;
                }
            }
            if (subMax > maxNum) {
                maxNum = subMax;
            }
        }
        return maxNum;
    }

    public int maxSubArray1(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    public static void main(String[] args) {
        int [] nums = {1,-1};
        System.out.println(maxSubArray(nums));
    }

}
