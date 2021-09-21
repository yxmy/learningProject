package com.yx.mygroup.learning.leetcode;

/**
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
 * 121 = true    -121 = false
 * -2的31次方< x < 2的31次方-1
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class IsPalindrome9 {

    /**
     * 如果是负数，肯定不是
     *
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int oldX = x;
        int reverseNum = 0;
        while (x > 0) {
            int tmp = x % 10;
            reverseNum = reverseNum * 10 + tmp;
            x /= 10;
        }
        return reverseNum == oldX;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(123454321));
    }
}
