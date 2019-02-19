package com.comcast.algorithms;

import java.util.Stack;

/**
 * Created by zzhou200 on 7/22/15.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int v) {
        val = v;
        left = null;
        right = null;
    }

    public int getData() {
        return val;
    }

    public void setData(int val) {
        this.val = val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void postorder(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();

        TreeNode current = root;;
        s.push(current);

        while (!s.isEmpty()) {
            current = s.pop();
            s2.push(current);

            if (current.left != null) {
                s.push(current.left);
            }
            if (current.right != null) {
                s.push(current.right);
            }
        }

        while (!s2.isEmpty()) {
            System.out.println(s2.pop().val + " ");
        }
    }

    public void preorder(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        TreeNode  current = root;
        while (true) {
            while (current != null) {
                System.out.print(current.val + " ");
                s.push(current);
                current = current.left;
            }

            // go right if stack is not empy
            if (!s.isEmpty()) {
                current = s.pop();
                current = current.right;
            } else {
                break;
            }
        }
    }

    public void inorder(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        TreeNode current = root;
        while (true) {
            while (current != null) {
                s.push(current);
                current = current.left;
            }

            if (!s.isEmpty()) {
                current = s.pop();
                System.out.print(current.val + " ");
                current = current.right;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        root.postorder(root);
        root.preorder(root);
        root.inorder(root);
    }
}
