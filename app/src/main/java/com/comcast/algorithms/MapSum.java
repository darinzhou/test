package com.comcast.algorithms;

import java.util.HashMap;
import java.util.Map;

/**
 * Map Sum Pairs
 Implement a MapSum class with insert, and sum methods.

 For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer represents the value. If the key already existed, then the original key-value pair will be overridden to the new one.

 For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the pairs' value whose key starts with the prefix.

 Example 1:
 Input: insert("apple", 3), Output: Null
 Input: sum("ap"), Output: 3
 Input: insert("app", 2), Output: Null
 Input: sum("ap"), Output: 5
 */

class MapSum {

    class Node {
        char c;
        int value;
        HashMap<Character, Node> children = new HashMap<>();
        public Node() {}
        public Node(char c) {
            this.c = c;
        }
    }

    // declare root
    Node root;

    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }

    public void insert(String key, int value) {
        HashMap<Character, Node> children = root.children;

        Node cNode = null;
        for (int i=0; i<key.length(); ++i) {
            char c = key.charAt(i);
            cNode = children.get(c);
            if (cNode == null) {
                cNode = new Node(c);
                children.put(c, cNode);
            }

            // next
            children = cNode.children;
        }

        cNode.value = value;
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

    public int sum(String prefix) {
        Node node = searchNode(prefix);

        int[] addition = new int[1];
        addition[0] = 0;
        sum(node, addition);

        return addition[0];
    }

    private void sum(Node node, int[] addition) {
        if (node == null) {
            return;
        }
        addition[0] += node.value;
        HashMap<Character, Node> children = node.children;
        for (Map.Entry e : children.entrySet()) {
            sum((Node)e.getValue(), addition);
        }
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */