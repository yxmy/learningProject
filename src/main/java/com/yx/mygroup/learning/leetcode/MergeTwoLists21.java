package com.yx.mygroup.learning.leetcode;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * @author yuanxin
 * @date 2021/5/19
 */
public class MergeTwoLists21 {

    /**
     * 将长度较短的链表融入长度较长的链表（减少遍历次数）
     * 1、先把头元素最小的放到第一个参数位
     * 2、用第二参数的第一个元素开始对比，找到位置之后放进去，然后用第二个元素再从头开始对比
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        final int val1 = l1.val;
        final int val2 = l2.val;
        if (val1 > val2) {
            ListNode tmp = l2;
            l2 = l1;
            l1 = tmp;
        }
        mergeTwoListSub(l1, l2);
        while (l2.next != null) {
            mergeTwoListSub(l1, l2.next);
            l2 = l2.next;
        }
        return l1;
    }

    private static void mergeTwoListSub(ListNode l1, ListNode l2) {
        int l1CurrVal = l1.val;
        ListNode l1Next = l1.next;
        int l1NextVal;
        if (l1Next == null) {
            l1.next = new ListNode(l2.val, null);
            return;
        } else {
            l1NextVal = l1Next.val;
        }
        int l2CurrVal = l2.val;
        if (l1CurrVal <= l2CurrVal && l2CurrVal < l1NextVal) {
            l1.next = new ListNode(l2CurrVal, l1Next);
        } else {
            mergeTwoListSub(l1Next, l2);
        }
    }

    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists1(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists1(l1, l2.next);
            return l2;
        }

    }

    public static void main(String[] args) {
        ListNode listNode1_1 = new ListNode(1);
        ListNode listNode1_2 = new ListNode(2);
        ListNode listNode1_3 = new ListNode(4);
        listNode1_1.next = listNode1_2;
        listNode1_2.next = listNode1_3;

        ListNode listNode2_1 = new ListNode(1);
        ListNode listNode2_2 = new ListNode(3);
        ListNode listNode2_3 = new ListNode(4);
        listNode2_1.next = listNode2_2;
        listNode2_2.next = listNode2_3;

        ListNode listNode = mergeTwoLists(listNode1_1, listNode2_1);
        StringBuilder stringBuilder = new StringBuilder("[");
        while (listNode != null) {
            stringBuilder.append(listNode.val).append(",");
            listNode = listNode.next;
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        }
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }
}
