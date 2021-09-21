package com.yx.mygroup.learning.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
 * 在杨辉三角中，每个数是它左上方和右上方的数的和。
 *
 * 输入: 5
 * //输出:
 * //[
 * //     [1],
 * //    [1,1],
 * //   [1,2,1],
 * //  [1,3,3,1],
 * // [1,4,6,4,1]
 * //]
 *
 * @author yuanxin
 * @date 2021/6/7
 */
public class Generate118 {

    /**
     * 下一个数组的第i个元素的值等于 上一个数组的第(i-1) + 第(i)个元素，
     * 每个数组的第一个元素和最后一个元素必定是1
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> yhList = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        yhList.add(list);
        for (int i = 1; i < numRows; i++) {
            final List<Integer> preList = yhList.get(i - 1);
            List<Integer> currList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    currList.add(1);
                } else {
                    currList.add(preList.get(j - 1) + preList.get(j));
                }
            }
            yhList.add(currList);
        }
        return yhList;
    }

    public static List<List<Integer>> generate1(int numRows) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i = 0; i < numRows; ++i) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(row);
        }
        return ret;
    }

    public static void main(String[] args) {
        final List<List<Integer>> generate = generate(5);
        for (List<Integer> list : generate) {
            System.out.print("[");
            for (Integer integer : list) {
                System.out.print(integer + ",");
            }
            System.out.print("]");
            System.out.println();
        }
    }
}
