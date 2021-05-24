package com.yx.springboot.demospring.leetcode;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 说明:
 * <p>
 * 为什么返回数值是整数，但输出的答案是数组呢?
 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
 *
 * @author yuanxin
 * @date 2021/5/20
 */
public class RemoveDuplicates26 {

    /**
     * 定义两个指针，fast和slow，fast记录遍历到的位置，slow记录下一个元素要放的位置
     * 从1开始，如果fast当前坐标数值和它前一位的数值不相等，把当前fast指向的数值移动到slow的位置，然后slow++
     * 如果相等，则fast继续向后面找
     *
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return nums.length;
        }
        int fast = 1;
        int slow = 1;
        while (fast < nums.length) {
            final int currFastNum = nums[fast];
            final int preFastNum = nums[fast - 1];
            if (currFastNum != preFastNum) {
                nums[slow] = currFastNum;
                slow++;
            }
            fast++;
        }
        return slow;
    }

    public static void main(String[] args) {
        int [] nums = {0,0,1,1,1,2,2,3,3,4};
        final int i = removeDuplicates(nums);
        System.out.println(i);
        for (int j = 0; j < i; j++) {
            System.out.print(nums[j] + ",");
        }
    }
}
