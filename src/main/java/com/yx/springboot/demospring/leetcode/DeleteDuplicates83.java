package com.yx.springboot.demospring.leetcode;

/**
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次
 * 返回同样按升序排列的结果链表。
 *
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 *
 * @author yuanxin
 * @date 2021/6/2
 */
public class DeleteDuplicates83 {

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.val == currentNode.next.val) {
                currentNode.next = currentNode.next.next;
            } else {
                currentNode = currentNode.next;
            }
        }
        return head;
    }

}
