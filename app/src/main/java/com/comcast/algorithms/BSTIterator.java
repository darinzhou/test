package com.comcast.algorithms;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by zzhou200 on 7/25/15.
 */

/*
    Implement an iterator over a binary search tree (BST).
    Your iterator will be initialized with the root node of a BST.
    Calling next() will return the next smallest number in the BST.
    Note: next() and hasNext() should run in average O(1) time and uses O(h) memory,
    where h is the height of the tree.

 */

public class BSTIterator {
    private Stack<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        pushNodeAndLeftChainInStack(root);
    }

    private void pushNodeAndLeftChainInStack(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public TreeNode next() {
        TreeNode node = stack.pop();

        if (node.right!= null) {
            // update stack for next smallest value on top
            pushNodeAndLeftChainInStack(node.right);
        }
        return node;
    }
}
