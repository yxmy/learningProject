package com.yx.springboot.demospring.learning.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class LongestCommonPrefix14 {

    /**
     * 要所有的字符串都包含某一段开头，只需要判断最短的字符串就行了，因为如果最短的字符串都没包括，其他肯定也不会包括
     */
    public static String longestCommonPrefix(String[] strs) {
        List<String> min = getMin(strs);
        //随便取其中某一个就行
        String minLengthStr = min.get(0);
        boolean breakWhile;
        while (minLengthStr.length() > 0) {
            breakWhile = true;
            for (String str : strs) {
                if (!str.startsWith(minLengthStr)) {
                    minLengthStr = minLengthStr.substring(0, minLengthStr.length() - 1);
                    breakWhile = false;
                    break;
                }
            }
            if (breakWhile) {
                return minLengthStr;
            }
        }
        return minLengthStr;
    }

    private static List<String> getMin(String[] str) {
        int minLength = Integer.MAX_VALUE;
        List<String> minList = new ArrayList<>();
        for (String s : str) {
            if (s.length() < minLength) {
                minLength = s.length();
                minList.clear();
                minList.add(s);
            } else if (s.length() == minLength) {
                minList.add(s);
            }
        }
        return minList;
    }

    public static void main(String[] args) {
        String [] strs = new String[]{"flower","flow","flight"};
        System.out.println(longestCommonPrefix(strs));
    }
}
