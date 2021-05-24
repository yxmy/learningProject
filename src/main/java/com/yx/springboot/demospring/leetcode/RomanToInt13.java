package com.yx.springboot.demospring.leetcode;

/**
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * //字符          数值
 * //I             1
 * //V             5
 * //X             10
 * //L             50
 * //C             100
 * //D             500
 * //M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做 XXVII, 即为 XX + V + I
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5
 * 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * <p>
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 *
 * @author yuanxin
 * @date 2021/5/18
 */
public class RomanToInt13 {

    /**
     * 将字符串的当前字母和下一个字母进行比较，如果当前字母小于下一个字母，则表示此时满足《计算减法》的条件，将这两个数字相减；
     * 否则只需要将当前字母的数字加上即可。
     */
    public static int romanToInt(String s) {
        int returnValue = 0;
        for (int i = 0; i < s.length(); ) {
            char currChar = s.charAt(i);
            if (i + 1 < s.length()) {
                char nextChar = s.charAt(i + 1);
                if (getValue(currChar) < getValue(nextChar)) {
                    returnValue += (getValue(nextChar) - getValue(currChar));
                    i += 2;
                } else {
                    returnValue += getValue(currChar);
                    i++;
                }
            } else {
                returnValue += getValue(currChar);
                i++;
            }
        }
        return returnValue;
    }


    public static int romanToInt1(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum;
            } else {
                sum += preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private static int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(romanToInt("IX"));
    }
}
