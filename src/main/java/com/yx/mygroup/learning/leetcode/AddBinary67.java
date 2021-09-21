package com.yx.mygroup.learning.leetcode;

/**
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * 输入为 非空 字符串且只包含数字 1 和 0。
 *
 * @author yuanxin
 * @date 2021/5/26
 */
public class AddBinary67 {

    /**
     * 先将比较短的字符串前面补0，使两个字符串长度都一样；
     * 做小学加法
     */
    public static String addBinary(String a, String b) {
        String max = a.length() >= b.length()? a : b;
        StringBuilder min = new StringBuilder(a.length() < b.length() ? a : b);
        int length = max.length() - min.length();
        for (int i = 0; i < length; i++) {
            min.insert(0, "0");
        }
        boolean plusOne = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = max.length() - 1; i >= 0; i--) {
            final char minC = min.charAt(i);
            final char maxC = max.charAt(i);
            if (maxC == '1' && minC == '1') {
                if (plusOne) {
                    stringBuilder.insert(0, "1");
                } else {
                    stringBuilder.insert(0, "0");
                }
                plusOne = true;
            } else if (maxC == '1' || minC == '1') {
                if (plusOne) {
                    stringBuilder.insert(0, "0");
                    plusOne = true;
                } else {
                    stringBuilder.insert(0, "1");
                    plusOne = false;
                }
            } else {
                if (plusOne) {
                    stringBuilder.insert(0, "1");
                } else {
                    stringBuilder.insert(0, "0");
                }
                plusOne = false;
            }
        }
        if (plusOne) {
            stringBuilder.insert(0, "1");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String a = "11111";
        String b = "1";
        System.out.println(addBinary(a, b));
    }

}
