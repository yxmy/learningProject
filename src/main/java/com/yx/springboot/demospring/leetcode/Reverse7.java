package com.yx.springboot.demospring.leetcode;

import java.util.Stack;

/**
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2的31次方, 2的31次方 − 1] ，就返回 0。
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class Reverse7 {

    /**
     * 依靠栈先进后出反转
     */
    public static int reverse(int x) {
        String xStr = x + "";
        if (x > 0) {
            xStr = "+" + xStr;
        }
        char[] chars = xStr.toCharArray();
        Stack<String> stack = new Stack<>();
        for (char aChar : chars) {
            stack.push(String.valueOf(aChar));
        }
        StringBuilder reverseNum = new StringBuilder();
        while (stack.size() > 1) {
            reverseNum.append(stack.pop());
        }
        String symbol = stack.pop();
        reverseNum.insert(0, symbol);
        int newReverse = 0;
        try {
            newReverse = Integer.parseInt(reverseNum.toString());
        } catch (NumberFormatException e) {
        }
        return newReverse;
    }

    /**
     * 1、将12345 % 10 得到5，之后将12345 / 10
     * 2、将1234 % 10 得到4，再将1234 / 10
     * 3、将123 % 10 得到3，再将123 / 10
     * 4、将12 % 10 得到2，再将12 / 10
     * 5、将1 % 10 得到1，再将1 / 10
     *
     * 然后给取余得到的数值一直乘以10就可以得到反转的数值，但是在相加之前要判断是否要超过21E了
     *
     */
    public static int reverse1(int x) {
        int res = 0;
        while (x != 0) {
            //每次取末尾数字
            int tmp = x % 10;
            //判断是否 大于 最大32位整数
            if (res > 214748364 || (res == 214748364 && tmp > 7)) {
                return 0;
            }
            //判断是否 小于 最小32位整数
            if (res < -214748364 || (res == -214748364 && tmp < -8)) {
                return 0;
            }
            res = res * 10 + tmp;
            x /= 10;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(reverse1(1147483649));
    }
}
