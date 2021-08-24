package com.yx.springboot.demospring.leetcode;

/**
 * 实现 int sqrt(int x) 函数。 平方根
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 *
 * @author Yuan_xin
 */
public class MySqrt69 {

    public static int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        long begin = 0;
        long end = x;
        long mid;
        while (true) {
            if (end - begin == 1) {
                return (int) begin;
            }
            mid = begin + (end - begin) / 2;
            if (mid * mid == x) {
                return (int) mid;
            } else if (mid * mid > x) {
                end = mid;
            } else {
                begin = mid;
            }
        }
    }

    public int mySqrt0(int x) {
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    public int mySqrt1(int x) {
        if (x == 0) {
            return 0;
        }
        int ans = (int) Math.exp(0.5 * Math.log(x));
        return (long) (ans + 1) * (ans + 1) <= x ? ans + 1 : ans;
    }

    public static void main(String[] args) {
        int x = 2147395599;
        System.out.println(x + "的平方根为：" + mySqrt(x));
    }
}
