package com.nut2014.newtech.binaryTree;

public class BinaryTree {
    private Node root;

    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空无法遍历");
        }
    }
    public void preNode(int num) {
        if (this.root != null) {
            this.root.preNode(num);
        }else {
            System.out.println("二叉树为空无法遍历");
        }
    }
    public static void main(String[] args) {
        BinaryTree treenew =new BinaryTree();
        Node root=new Node(1);
        Node node2=new Node(2);
        Node node3=new Node(3);
        Node node4=new Node(4);

        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        treenew.setRoot(root);

        treenew.preOrder();
        treenew.preNode(4);
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
