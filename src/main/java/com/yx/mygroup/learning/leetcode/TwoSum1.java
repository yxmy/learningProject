package com.yx.mygroup.learning.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现，你可以按任意顺序返回答案。
 * 2 <= nums.length <= 103
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * 只会存在一个有效答案
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class TwoSum1 {

    /**
     * 参照后修改
     */
    public static int[] twoSum(int[] nums, int target) {
        int[] returnIndex = new int[2];
        Map<Integer, Integer> num2Index = new HashMap<>(16);
        for (int i = 0; i < nums.length; i++) {
            int num1 = nums[i];
            int num2 = target - num1;
            if (num2Index.containsKey(num2)) {
                returnIndex[0] = num2Index.get(num2);
                returnIndex[1] = i;
                break;
            }
            num2Index.put(num1, i);
        }
        return returnIndex;
    }

    /**
     * 标签：哈希映射
     * 这道题本身如果通过暴力遍历的话也是很容易解决的，时间复杂度在 O(n2)
     * 由于哈希查找的时间复杂度为 O(1)，所以可以利用哈希容器 map 降低时间复杂度
     * 遍历数组 nums，i 为当前下标，每个值都判断map中是否存在 target-nums[i] 的 key 值
     * 如果存在则找到了两个值，如果不存在则将当前的 (nums[i],i) 存入 map 中，继续遍历直到找到为止
     * 如果最终都没有结果则抛出异常
     * 时间复杂度：*
     */
    public int[] twoSumSolution(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 3};
        int[] ints = twoSum(arr, 6);
        System.out.println(ints[0] + "," + ints[1]);
    }
}
