package com.yx.springboot.demospring.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
 * 在杨辉三角中，每个数是它左上方和右上方的数的和。
 *
 *     [1],
 *    [1,1],
 *   [1,2,1],
 *  [1,3,3,1],
 * [1,4,6,4,1]
 *
 * 输入: 3
 * 输出: [1,3,3,1]
 *
 * @author yuanxin
 * @date 2021/6/7
 */
public class GetRow119 {

    public static List<Integer> getRow(int rowIndex) {
        List<Integer> preList = new ArrayList<>();
        for (int i = 0; i <= rowIndex; i++) {
            List<Integer> currList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    currList.add(1);
                } else {
                    currList.add(preList.get(j - 1) + preList.get(j));
                }
            }
            preList = currList;
        }
        return preList;
    }

    public static void main(String[] args) {
        System.out.print("[");
        for (Integer integer : getRow(3)) {
            System.out.print(integer + ",");
        }
        System.out.print("]");
    }
}
