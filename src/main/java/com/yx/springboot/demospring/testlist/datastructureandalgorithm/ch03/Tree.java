package com.yx.springboot.demospring.testlist.datastructureandalgorithm.ch03;

/**
 * 二叉树
 */
public class Tree {
    //根节点
    public Node root;

    public void insert(long value){
        Node newNode = new Node(value);
        Node current = root;
        Node parent;
        if(root == null){
            root = newNode;
        }else{
            while (true) {
                parent = current;
                if (value < current.data) {
                    current = current.leftChild;
                    if(current == null){
                        parent.leftChild = newNode;
                        return;
                    }
                } else {
                    current = current.rightChild;
                    if(current == null){
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    public Node find(long value){
        Node current = root;
        while (current.data != value) {
            if (value < current.data) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
            if (current == null){
                return null;
            }
        }
        return current;
    }

    public boolean delete(long value){
        Node current = root;
        Node parent = root;
        boolean ifLeft = true;
        while (current.data != value) {
            parent = current;
            if (value < current.data) {
                current = current.leftChild;
                ifLeft = true;
            } else {
                current = current.rightChild;
                ifLeft = false;
            }
            if (current == null){
                return false;
            }
        }

        if(current.leftChild == null && current.rightChild == null){
            if(current == root){
                root = null;
            }else if(ifLeft){
                parent.leftChild = null;
            }else{
                parent.rightChild = null;
            }
        }else if(current.rightChild == null){
            if(current == root){
                root = current.leftChild;
            }else if(ifLeft){
                parent.leftChild = current.leftChild;
            }else {
                parent.rightChild = current.leftChild;
            }
        }else if(current.leftChild == null){
            if(current == root){
                root = current.rightChild;
            }else if(ifLeft){
                parent.leftChild = current.rightChild;
            }else {
                parent.rightChild = current.rightChild;
            }
        }else {
            Node successor = getSuccessor(current);
            if (current == root){
                root = successor;
            }else if(ifLeft){
                parent.leftChild = successor;
            }else{
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }

        return true;
    }

    public Node getSuccessor(Node delNode){
        Node successor = delNode;
        Node successorParent = delNode;
        Node current = delNode.rightChild;

        while (current != null){
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }

        if (successor != delNode.rightChild){
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }


    public void frontOrder(Node rootNode){
        if(rootNode != null){
            System.out.println(rootNode.data);
            frontOrder(rootNode.leftChild);
            frontOrder(rootNode.rightChild);
        }
    }

    public void inOrder(Node rootNode){
        if(rootNode != null){
            inOrder(rootNode.leftChild);
            System.out.println(rootNode.data);
            inOrder(rootNode.rightChild);
        }
    }

    public void lastOrder(Node rootNode){
        if(rootNode != null){
            lastOrder(rootNode.leftChild);
            lastOrder(rootNode.rightChild);
            System.out.println(rootNode.data);
        }
    }
}
