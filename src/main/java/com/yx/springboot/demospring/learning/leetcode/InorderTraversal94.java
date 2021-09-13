package com.yx.springboot.demospring.learning.leetcode;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树中序遍历
 *
 * @author yuanxin
 * @date 2021/9/13
 */
public class InorderTraversal94 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        preOrderTraverse1(list, root);
        return list;
    }

    private void preOrderTraverse1(List<Integer> list, TreeNode root) {
        if (root != null) {
            preOrderTraverse1(list, root.left);
            list.add(root.val);
            preOrderTraverse1(list, root.right);
        }
    }
}
