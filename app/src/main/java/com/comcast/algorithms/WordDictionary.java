package com.comcast.algorithms;

/**
 * Add and Search Word - Data structure design
 Design a data structure that supports the following two operations:

 void addWord(word)
 bool search(word)
 search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

 For example:

 addWord("bad")
 addWord("dad")
 addWord("mad")
 search("pad") -> false
 search("bad") -> true
 search(".ad") -> true
 search("b..") -> true
 Note:
 You may assume that all words are consist of lowercase letters a-z.

 click to show hint.

 You should be familiar with how a Trie works. If not, please work on this problem: Implement Trie (Prefix Tree) first.
 */

class WordDictionary {
    class Node {
        char c;
        boolean end;
        Node[] children = new Node[26];
        Node() {}
        Node(char c) {
            this.c = c;
        }
    }

    Node root;

    /** Initialize your data structure here. */
    public WordDictionary() {
        root = new Node();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        Node node = root;
        for (int i=0;i<word.length(); ++i) {
            char c = word.charAt(i);
            int idx = c-'a';
            if (node.children[idx] == null) {
                node.children[idx] = new Node(c);
            }
            node = node.children[idx];
        }
        node.end = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(root, word, 0);
    }

    private boolean search(Node node, String word, int idx) {
        if (idx == word.length()) {
            return node.end;
        }

        char c = word.charAt(idx);
        if (c != '.') {
            int i = c-'a';
            Node n = node.children[i];
            return n != null && search(n, word, idx+1);
        }

        // handle '.', try each child node
        for (Node n : node.children) {
            if (n !=null && search(n, word, idx+1)) {
                return true;
            }
        }
        return false;
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */