package com.comcast.algorithms;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class BstIterative {

    static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
            left = null;
            right = null;
        }

        public void print() {
            System.out.println(val);
        }
    }

    public static List<TreeNode> preOrder(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        if (root == null) {
            return nodes;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (true) {
            // push all left nodes to stack
            while (current != null) {

                // visit current node
                nodes.add(current);

                // push to stack
                stack.push(current);
                // go to left child
                current = current.left;
            }

            if (stack.isEmpty()) {
                break;
            }

            // pop node from stack
            current = stack.pop();
            // go to its right child
            current = current.right;
        }

        return nodes;
    }

    public static List<TreeNode> inOrder(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        if (root == null) {
            return nodes;
        }

        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;

        while (true) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            if (stack.isEmpty()) {
                break;
            }

            current = stack.pop();

            nodes.add(current);

            current = current.right;
        }

        return nodes;
    }

    public static List<TreeNode> postOrder(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        if (root == null) {
            return nodes;
        }

        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        TreeNode current = root;

        stack.push(current);

        while (!stack.isEmpty()) {

            current = stack.pop();
            stack2.push(current);

            if (current.left != null) {
                stack.push(current.left);
            }

            if (current.right != null) {
                stack.push(current.right);
            }
        }

        // get post order list from stack 2
        while (!stack2.isEmpty()) {
            nodes.add(stack2.pop());
        }

        return nodes;
    }

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);

        n2.left = n4;
        n2.right = n5;
        n1.left = n2;
        n1.right = n3;

        List<TreeNode> l = preOrder(n1);
        for (TreeNode n : l) {
            System.out.print(n.val + ", ");
        }

        System.out.println();

        l = inOrder(n1);
        for (TreeNode n : l) {
            System.out.print(n.val + ", ");
        }

        System.out.println();

        l = postOrder(n1);
        for (TreeNode n : l) {
            System.out.print(n.val + ", ");
        }

        // Node n31 = new Node(4, null, null);
        // Node n32 = new Node(2, null, null);
        // Node n33 = new Node(5, null, null);
        // Node n34= new Node(3, null, null);
        // Node n21 = new Node(2, n31, n32);
        // Node n22 = new Node(3, n33, n34);
        // Node n11 = new Node(2, n21, n22);

    }

}
