package com.comcast.algorithms;

import java.util.HashMap;
import java.util.List;

/*
Given a list of child->parent relationships, build a binary tree out of it. All the element Ids inside the tree are unique.

        Example:

        Given the following relationships:

        Child Parent IsLeft
        15 20 true
        19 80 true
        17 20 false
        16 80 false
        80 50 false
        50 null false
        20 50 true


        You should return the following tree:
        50
        / \
        20 80
        / \ / \
        15 17 19 16

*/

public class RelationBinaryTree {
    /**
     * Represents a pair relation between one parent node and one child node inside a binary tree
     * If the _parent is null, it represents the ROOT node
     */
    public static class Relation {
        public Integer _parent;
        public Integer _child;
        public boolean _isLeft;
    }


    /**
     * Represents a single Node inside a binary tree
     */
    public static class Node {
        public Integer _id;
        public Node _left;
        public Node _right;
    }

    /**
     * Implement a method to build a tree from a list of parent-child relationships
     * And return the root Node of the tree
     */
    //Time: O(n)
    //Space: O(n)
    public static Node buildTree (List<Relation> data)
    {
        if (data == null || data.size() == 0)
            return null;

        HashMap<Integer, Node> map = new HashMap<>();

        Node root = null;
        for (Relation r : data) {
            if (r._parent == null) {
                Node rt = map.get(r._child);
                if (rt == null) {
                    root = new Node();
                    root._id = r._child;
                    map.put(root._id, root);
                } else
                    root = rt;
            } else {
                // check child
                Node c = map.get(r._child);
                if (c == null) {
                    c = new Node();
                    c._id = r._child;
                    map.put(c._id, c);
                }
                // check parent
                Node p = map.get(r._parent);
                if (p == null) {
                    p = new Node();
                    p._id = r._parent;
                    map.put(p._id, p);
                }

                if (r._isLeft)
                    p._left = c;
                else
                    p._right = c;
            }
        }

        return root;
    }

}
