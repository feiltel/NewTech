package com.nut2014.newtech.binaryTree;

public class BinaryTree {
    private Node root;

    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空无法遍历");
        }
    }

    public void preNode(int num) {
        if (this.root != null) {
            this.root.preNode(num);
        } else {
            System.out.println("二叉树为空无法遍历");
        }
    }

    public void afterNode(int num) {
        if (this.root != null) {
            this.root.afterNode(num);
        } else {
            System.out.println("二叉树为空无法遍历");
        }
    }
    public void centerNode(int num) {
        if (this.root != null) {
            this.root.centerNode(num);
        } else {
            System.out.println("二叉树为空无法遍历");
        }
    }

    public static void main(String[] args) {
        BinaryTree treenew = new BinaryTree();
        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        treenew.setRoot(root);
        //遍历节点
        //  treenew.preOrder();
        //查找节点
        System.out.println("                 1");
        System.out.println("               2    3");
        System.out.println("                       4");
        System.out.println("前置查找");
        treenew.preNode(4);
        System.out.println("后置查找");
        treenew.afterNode(4);
        System.out.println("中置查找");
        treenew.centerNode(4);
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
