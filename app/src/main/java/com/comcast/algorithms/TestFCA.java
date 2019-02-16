package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/12/15.
 */
public class TestFCA {
    public interface FirstCommonAncestor {

        /**
         * Given two nodes of a tree,
         * method should return the deepest common ancestor of those nodes.
         *
         * A
         * / \
         * B C
         * / \ \
         * D E M
         * / \
         * G F
         *
         * commonAncestor(D, F) = B
         * commonAncestor(C, G) = A
         */

        public Node commonAncestor(Node nodeOne, Node nodeTwo);
    }

    public static class Node {

        public int data;
        public Node parent;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
        public boolean isRoot() {
            return parent == null;
        }
    }

    public static class FirstCommonAncestorClass implements FirstCommonAncestor{

        @Override
        public Node commonAncestor(Node nodeOne, Node nodeTwo) {
            if (nodeOne == null)
                return nodeTwo;
            if (nodeTwo == null)
                return nodeOne;

            if (nodeOne.isRoot())
                return nodeOne;
            if (nodeTwo.isRoot())
                return  nodeTwo;

            if (nodeOne.parent == nodeTwo.parent)
                return nodeOne.parent;

            if (nodeOne == nodeTwo.parent)
                return nodeOne;
            if (nodeOne.parent == nodeTwo)
                return nodeTwo;

            return commonAncestor(nodeOne.parent, nodeTwo.parent);
        }
    }
}

