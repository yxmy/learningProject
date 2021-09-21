package com.yx.mygroup.learning.leetcode;

/**
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
 * <p>
 * 输入: prices = [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 *
 * @author yuanxin
 * @date 2021/6/2
 */
public class MaxProfit122 {

    /**
     * 每次都是在低点买入，高点卖出。
     */
    public static int maxProfit(int[] prices) {
        int totalSum = 0, minNum = prices[0], maxNum = prices[0];
        for (int i = 1; i <= prices.length; i++) {
            if (i == prices.length || prices[i] < maxNum) {
                totalSum += (maxNum - minNum);
                minNum = i == prices.length ? prices[i - 1] : prices[i];
            }
            maxNum = i == prices.length ? prices[i - 1] : prices[i];
        }
        return totalSum;
    }

    public static void main(String[] args) {
        int [] prices = {7,1,5,3,6,5};
        System.out.println(maxProfit(prices));
    }

}
