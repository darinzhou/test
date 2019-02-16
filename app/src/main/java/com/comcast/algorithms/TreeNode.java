package com.comcast.algorithms;

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
}
