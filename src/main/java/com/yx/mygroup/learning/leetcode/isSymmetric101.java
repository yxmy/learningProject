package com.yx.mygroup.learning.leetcode;

/**
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 * 输入：root = [1,2,2,3,4,4,3]   输出：true
 * 输入：root = [1,2,2,null,3,null,3] 输出：false
 */
public class isSymmetric101 {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSame(root.left, root.right);
    }

    public boolean isSame(TreeNode leftNode, TreeNode rightNode) {
        if (leftNode == null && rightNode == null) {
            return true;
        } else if (leftNode == null || rightNode == null) {
            return false;
        }
        if (leftNode.val != rightNode.val) {
            return false;
        }
        return isSame(leftNode.left, rightNode.right) && isSame(leftNode.right, rightNode.left);
    }

}
