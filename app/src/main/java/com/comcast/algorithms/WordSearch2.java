package com.comcast.algorithms;

import java.util.ArrayList;

/**
 * Word Search II
 *
 Given a 2D board and a list of words from the dictionary, find all words in the board.

 Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 For example,
 Given words = ["oath","pea","eat","rain"] and board =

 [
 ['o','a','a','n'],
 ['e','t','a','e'],
 ['i','h','k','r'],
 ['i','f','l','v']
 ]
 Return ["eat","oath"].
 Note:
 You may assume that all inputs are consist of lowercase letters a-z.

 click to show hint.

 You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?

 If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? How about a Trie? If you would like to learn how to implement a basic trie, please work on this problem: Implement Trie (Prefix Tree) first.

 */

public class WordSearch2 {

        class TrieNode {
            char c;
            String word;
            TrieNode[] children = new TrieNode[26];

            TrieNode() {
            }

            TrieNode(char c) {
                this.c = c;
            }
        }

        public ArrayList<String> findWords(char[][] board, String[] words) {
            TrieNode root = buildTrie(words);

            ArrayList<String> result = new ArrayList<>();
            boolean[][] visited = new boolean[board.length][board[0].length];
            for (int i = 0; i < board.length; ++i) {
                for (int j = 0; j < board[i].length; ++j) {
                    find(root, board, i, j, visited, result);
                }
            }

            return result;
        }

        TrieNode buildTrie(String[] words) {
            TrieNode root = new TrieNode();
            for (String word : words) {
                addWord(root, word);
            }
            return root;
        }

        void addWord(TrieNode node, String word) {
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode(c);
                }
                node = node.children[index];
            }
            node.word = word;
        }

        TrieNode findNode(TrieNode node, String str) {
            for (int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                int index = c - 'a';
                if (node.children[index] == null)
                    return null;
                node = node.children[c - 'a'];
            }
            return node;
        }

        boolean findWord(TrieNode node, String word) {
            TrieNode n = findNode(node, word);
            if (n == null) {
                return false;
            }
            return (n.word != null) && n.word.equals(word);
        }

        boolean startsWith(TrieNode node, String str) {
            TrieNode n = findNode(node, str);
            if (n == null) {
                return false;
            }
            return true;
        }

        void find(TrieNode node, char[][] board, int i, int j, boolean[][] visited, ArrayList<String> result) {
            if (node == null) {
                return;
            }
            if (i < 0 || j < 0 || i >= board.length || j >= board[i].length) {
                return;
            }
            if (visited[i][j]) {
                return;
            }

            char c = board[i][j];
            int index = c - 'a';
            node = node.children[index];
            if (node == null) {
                return;
            }
            if (node.word != null && !node.word.isEmpty()) {
                if (!result.contains(node.word)) {
                    result.add(node.word);
                }
            }

            visited[i][j] = true;
            find(node, board, i + 1, j, visited, result);
            find(node, board, i, j + 1, visited, result);
            find(node, board, i - 1, j, visited, result);
            find(node, board, i, j - 1, visited, result);
            visited[i][j] = false;
        }
    }