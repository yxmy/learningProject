package com.yx.springboot.demospring.learning.leetcode;

/**
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * <p>
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 *
 * @author yuanxinØ
 * @date 2021/5/25
 */
public class PlusOne66 {

    public static int[] plusOne(int[] digits) {
        boolean copyNew = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] != 9) {
                digits[i] = digits[i] + 1;
                return digits;
            } else if (i > 0) {
                digits[i] = 0;
            } else {
                digits[i] = 0;
                copyNew = true;
            }
        }
        if (!copyNew) {
            return digits;
        }
        final int[] newDigits = new int[digits.length + 1];
        newDigits[0] = 1;
        System.arraycopy(digits, 0, newDigits, 1, digits.length);
        return newDigits;
    }

    public static void main(String[] args) {
        int [] digits = {1,9};
        final int[] newArray = plusOne(digits);
        for (int i : newArray) {
            System.out.print(i + ",");
        }
    }
}
