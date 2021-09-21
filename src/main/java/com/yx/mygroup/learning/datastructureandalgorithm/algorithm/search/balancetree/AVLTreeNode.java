package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.search.balancetree;

/**
 * 平衡二叉树结点
 *
 * @author yuanxin
 * @date 2021/9/13.
 */
public class AVLTreeNode {
    /**数据 */
    int data;
    /** 当前结点的平衡因子 */
    int bf;
    /** 左孩子 */
    AVLTreeNode lChild;
    /** 右孩子 */
    AVLTreeNode rChild;
}
