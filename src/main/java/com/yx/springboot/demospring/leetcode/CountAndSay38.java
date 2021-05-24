package com.yx.springboot.demospring.leetcode;

/**
 * 给定一个正整数 n ，输出外观数列的第 n 项。
 * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 第一项是数字 1
 * 描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
 * 描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
 * 描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211"
 * 描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
 *
 * @author yuanxin
 * @date 2021/5/21
 */
public class CountAndSay38 {

    /**
     * 根据传递的值，先组装出一个集合，然后再获取
     * 组装逻辑：
     * 1、需要一个字段num保存具体的数值，一个字段count来记录出现的次数
     * 2、和下一个对比，如果下一个和当前的不相等，说明需要开始记录了
     *
     */
    public static String countAndSay(int n) {
        String [] arr = new String[n];
        arr[0] = "1";
        for (int i = 1; i < n; i++) {
            final String s = arr[i - 1];
            StringBuilder stringBuilder = new StringBuilder();
            char num;
            int count = 1;
            for (int j = 0; j < s.length();) {
                num = s.charAt(j);
                char nextNum = 0;
                if (j + 1 < s.length()) {
                    nextNum = s.charAt(j + 1);
                }
                if (num == nextNum) {
                    count++;
                } else {
                    stringBuilder.append(count).append(num);
                    count = 1;
                }
                j++;
            }
            if (stringBuilder.length() > 0) {
                arr[i] = stringBuilder.toString();
            }
        }
        return arr[n - 1];
    }

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println(countAndSay(i));
        }
    }

}
