package com.yx.springboot.demospring.leetcode;

/**
 * 给你一个字符串 s，由若干单词组成，单词之间用空格隔开。返回字符串中最后一个单词的长度。如果不存在最后一个单词，请返回 0 。
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 * @author yuanxin
 * @date 2021/5/25
 */
public class LengthOfLastWord58 {

    public static int lengthOfLastWord(String s) {
        final String[] s1 = s.split(" ");
        for (int i = s1.length - 1; i >= 0; i--) {
            if (s1[i] != null) {
                return s1[i].length();
            }
        }
        return 0;
    }

    public int lengthOfLastWord1(String s) {
        int end = s.length() - 1;
        while(end >= 0 && s.charAt(end) == ' ') {
            end--;
        }
        if(end < 0) {
            return 0;
        }
        int start = end;
        while(start >= 0 && s.charAt(start) != ' ') {
            start--;
        }
        return end - start;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord(" "));
    }

}
