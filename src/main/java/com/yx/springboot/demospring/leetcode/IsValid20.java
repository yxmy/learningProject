package com.yx.springboot.demospring.leetcode;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 *
 * @author yuanxin
 * @date 2021/5/19
 */
public class IsValid20 {

    /**
     * 从左向右解析，如果遇到 ( [ { 则入栈，如果遇到 ) ] }，则从栈中取出top元素，查看是否和当前的匹配，
     * 如果匹配成功，继续向后遍历字符串，否则说明不是一个有效的括号
     *
     * 遍历前提判断：字符串的长度必须是2的倍数；
     * 从栈中取元素的时候必须保证栈中存在元素
     *
     */
    public static boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            boolean isPush = pushStack(ch);
            if (isPush) {
                stack.push(ch);
            } else {
                if (stack.size() == 0) {
                    return false;
                }
                if (!match(stack.pop(), ch)) {
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    private static boolean pushStack(char ch) {
        switch (ch) {
            case '(':
            case '[':
            case '{':
                return true;
            default:
                return false;
        }
    }

    private static boolean match(char firstCh, char secondCh) {
        if (firstCh == '(') {
            return secondCh == ')';
        } else if (firstCh == '[') {
            return secondCh == ']';
        } else if (firstCh == '{') {
            return secondCh == '}';
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValid("]"));
    }
}
