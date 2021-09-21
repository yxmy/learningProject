package com.yx.mygroup.learning.datastructureandalgorithm.algorithm.search;

/**
 * 二叉树排序
 *
 * @author yuanxin
 * @date 2021/9/12.
 */
public class SearchBST {

    private static BiTreeNode lastNode = new BiTreeNode();

    /**
     * 查找findData是否在树中存在
     *
     * @param currentNode 当前遍历的结点
     * @param findData    需要查找的数据
     * @param parentNode  currentNode的双亲
     * @return 是否存在
     */
    public static boolean searchBst(BiTreeNode currentNode, int findData, BiTreeNode parentNode) {
        if (currentNode == null || currentNode.data == 0) {
            lastNode = parentNode;
            return false;
        }
        if (currentNode.data == findData) {
            lastNode = currentNode;
            return true;
        } else if (currentNode.data < findData) {
            return searchBst(currentNode.rChild, findData, currentNode);
        } else {
            return searchBst(currentNode.lChild, findData, currentNode);
        }
    }

    public static BiTreeNode insertBst(BiTreeNode root, int key) {
        if (root == null) {
            root = new BiTreeNode();
        }
        BiTreeNode newNode;
        boolean contain = searchBst(root, key, null);
        if (!contain) {
            newNode = new BiTreeNode();
            newNode.data = key;
            newNode.lChild = newNode.rChild = null;
            if (lastNode == null) {
                root = newNode;
            } else if (key < lastNode.data) {
                lastNode.lChild = newNode;
            } else {
                lastNode.rChild = newNode;
            }
            preOrderTraverse1(root);
        }
        return root;
    }

    public static boolean deleteBst(BiTreeNode root, int key) {
        if (root == null) {
            return false;
        } else {
            if (root.data == key) {
                return delete(root);
            } else if (root.data < key) {
                return deleteBst(root.rChild, key);
            } else {
                return deleteBst(root.lChild, key);
            }
        }
    }

    private static boolean delete(BiTreeNode deleteNode) {
        if (deleteNode.lChild == null) {
            //左子树等于空，只需重接它的右子树
            deleteNode = deleteNode.rChild;
        } else if (deleteNode.rChild == null) {
            //左子树等于空，只需重接它的右子树
            deleteNode = deleteNode.lChild;
        } else {
            //左右子树都不为空
            BiTreeNode tmpNode = deleteNode;
            //下面是为了找到要删除节点的前驱，向左找到比当前要删除结点小的数据，
            //然后一直向右找，就可以找到最接近要删除结点的数据deleteTmp（也就是前驱结点）
            //而tmpNode可以一直指向需要操作的结点，就是当deleteTmp被删除之后，
            //该节点要与deleteTmp的左子树连接起来
            BiTreeNode deleteTmp = deleteNode.lChild;
            while (deleteTmp.rChild != null) {
                tmpNode = deleteTmp;
                deleteTmp = deleteTmp.rChild;
            }
            //用deleteNode的前驱结点deleteTmp的数据替换掉deleteNode结点的数据
            deleteNode.data = deleteTmp.data;
            if (tmpNode != deleteNode) {
                //因为前驱结点deleteTmp永远比待操作结点的数据大（因为一直在找右孩子）
                //所以拼接树的时候，只能吧deleteTmp的左子树拼接到待操作结点的右子树
                tmpNode.rChild = deleteTmp.lChild;
            } else {
                //如果待操作结点tmpNode和要删除的结点deleteNode相同，
                //说明要删除的结点deleteNode的左子树根结点，没有右子树，只有左子树
                //把待操作结点与前驱结点deleteTmp的左子树连接起来。
                //就跳过（删除）了deleteTmp结点
                tmpNode.lChild = deleteTmp.lChild;
            }
        }
        return true;
    }

    public static void preOrderTraverse1(BiTreeNode root) {
        if (root != null) {
            preOrderTraverse1(root.lChild);
            System.out.print(root.data + "->");
            preOrderTraverse1(root.rChild);
        }
    }

    static class BiTreeNode {
        private int data;

        private BiTreeNode lChild;

        private BiTreeNode rChild;
    }

    public static void main(String[] args) {
        BiTreeNode tree = insertBst(null, 12);
        System.out.println();
        insertBst(tree, 12);
        System.out.println();
        insertBst(tree, 34);
        System.out.println();
        insertBst(tree, 23);
        System.out.println();
        insertBst(tree, 52);
    }

}
