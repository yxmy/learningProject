package com.yx.springboot.demospring.leetcode;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 * @author yuanxin
 * @date 2021/6/1
 */
public class ClimbStairs70 {

    /**
     * 找规律：斐波那契数列
     * f(1): 1
     * f(2): 11, 2
     * f(3): 111, 12, 21
     * f(4): 1111, 121, 112, 211, 22
     * 、、、
     * f(n): f(n-1) + f(n-2)
     */
    public static int climbStairs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int [] climb = new int[n];
        climb[0] = 1;
        climb[1] = 2;
        for (int i = 2; i < n; i++) {
            climb[i] = climb[i-1] + climb[i-2];
        }
        return climb[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(13));
    }

}
