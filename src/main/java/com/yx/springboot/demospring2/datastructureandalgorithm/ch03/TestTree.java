package com.yx.springboot.demospring2.datastructureandalgorithm.ch03;

public class TestTree {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(10);
        tree.insert(15);
        tree.insert(3);
        tree.insert(11);
        tree.insert(18);
        tree.insert(30);
        tree.insert(9);

        tree.delete(9);
        tree.lastOrder(tree.root);
    }
}
