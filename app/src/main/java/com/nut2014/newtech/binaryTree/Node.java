package com.nut2014.newtech.binaryTree;

public class Node {
    private int num;
    private Node left;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    private Node right;

    public Node(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Node{" +
                "num=" + num +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
    //前序查找
    public Node preNode(int num) {
        System.out.println(this.num+">>>"+num);
        if (this.num == num) {

            return this;
        }
        Node resoureNode = null;
        if (this.left != null) {
            resoureNode = this.left.preNode(num);
        }

        if (resoureNode != null) {
            return resoureNode;
        }
        if (this.right != null) {
            resoureNode = this.right.preNode(num);
        }
        return resoureNode;
        //          1
        //      2       3
        //    7   6   5   4
        //  8  9
        //
        //

        //          1
        //      2       3
        //                 4
        //
        //
        //          1
        //        |
        //      2       3
        //                 4
        //
    }


    public Node afterNode(int num) {


        Node resoureNode = null;
        if (this.left != null) {
            resoureNode = this.left.afterNode(num);
        }

        if (resoureNode != null) {
            return resoureNode;
        }
        if (this.right != null) {
            resoureNode = this.right.afterNode(num);
        }

        System.out.println(this.num+">>>"+num);
        if (this.num == num) {
            return this;
        }

        return resoureNode;
    }

    public Node centerNode(int num) {


        Node resoureNode = null;
        if (this.left != null) {
            resoureNode = this.left.centerNode(num);
        }

        if (resoureNode != null) {
            return resoureNode;
        }
        System.out.println(this.num+">>>"+num);
        if (this.num == num) {
            return this;
        }
        if (this.right != null) {
            resoureNode = this.right.centerNode(num);
        }


        return resoureNode;

    }
}
