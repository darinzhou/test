package com.comcast.algorithms;

import java.util.HashMap;

/**
 * Created by jianxin on 3/24/2018.
 */

class Trie {

    class Node {
        char c;
        boolean isLeaf;
        HashMap<Character, Node> children = new HashMap<>();
        public Node() {}
        public Node(char c) {
            this.c = c;
        }
    }

    // declare root
    Node root;

    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        HashMap<Character, Node> children = root.children;

        Node cNode = null;
        for (int i=0; i<word.length(); ++i) {
            char c = word.charAt(i);
            cNode = children.get(c);
            if (cNode == null) {
                cNode = new Node(c);
                children.put(c, cNode);
            }

            // next
            children = cNode.children;
        }

        if (cNode != null) {
            cNode.isLeaf = true;
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node node = searchNode(word);
        if (node == null) {
            return false;
        }
        return node.isLeaf;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node node = searchNode(prefix);
        if (node == null) {
            return false;
        }
        return true;
    }

    public Node searchNode(String str) {
        HashMap<Character, Node> children = root.children;

        Node cNode = null;
        for (int i=0; i<str.length(); ++i) {
            char c = str.charAt(i);
            cNode = children.get(c);
            if (cNode == null) {
                break;
            }

            // next
            children = cNode.children;
        }

        return cNode;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
