package com.yx.springboot.demospring.learning.leetcode;

/**
 * @author yuanxin
 * @date 2021/6/2
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
