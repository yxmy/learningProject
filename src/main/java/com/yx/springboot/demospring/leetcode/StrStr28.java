package com.yx.springboot.demospring.leetcode;

/**
 * @author yuanxin
 * @date 2021/5/21
 */
public class StrStr28 {

    public static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public static void main(String[] args) {
        System.out.println(strStr("", ""));
    }
}
