package com.yx.springboot.demospring.learning.datastructureandalgorithm.algorithm.search.balancetree;

/**
 * @author yuanxin
 * @date 2021/9/13.
 */
public class AVLTree {

    /**
     * 对以p为根的二叉排序树作右旋处理
     * 处理之后p指向新的树根结点，即旋转处理之前p的左子树根结点
     */
    public void rRotate(AVLTreeNode p) {
        //l是p结点的左孩子
        AVLTreeNode l = p.lChild;
        //p结点的左孩子指向l结点的右孩子
        p.lChild = l.rChild;
        //把l的右孩子指向p
        l.rChild = p;
    }


    /**
     * 对以p为根的二叉排序树作左旋处理
     * 处理之后p指向新的树根结点，即旋转处理之前p的右子树根结点
     */
    public void lRotate(AVLTreeNode p) {
        //l是p结点的右孩子
        AVLTreeNode l = p.rChild;
        //p结点的右孩子指向l结点的左孩子
        p.rChild = l.lChild;
        //把l的左孩子指向p
        l.lChild = p;
    }


}
