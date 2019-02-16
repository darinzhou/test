package com.comcast.algorithms;

import java.util.List;

/**
 * Replace Words
 In English, we have a concept called root, which can be followed by some other words to form another longer word - let's call this word successor. For example, the root an, followed by other, which can form another word another.

 Now, given a dictionary consisting of many roots and a sentence. You need to replace all the successor in the sentence with the root forming it. If a successor has many roots can form it, replace it with the root with the shortest length.

 You need to output the sentence after the replacement.

 Example 1:
 Input: dict = ["cat", "bat", "rat"]
 sentence = "the cattle was rattled by the battery"
 Output: "the cat was rat by the bat"
 */

public class ReplaceWordWithRoot {

    class Node {
        char c;
        boolean end;
        Node[] children = new Node[26];

        Node() {
        }

        Node(char c) {
            this.c = c;
        }
    }

    class Trie {
        Node root;

        Trie() {
            root = new Node();
        }

        void insert(String word) {
            Node node = root;
            int idx = 0;
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                idx = c - 'a';
                if (node.children[idx] == null) {
                    Node n = new Node(c);
                    node.children[idx] = n;
                }
                node = node.children[idx];
            }
            node.end = true;
        }

        String findRoot(String str) {
            Node node = root;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    break;
                }
                sb.append(c);
                if (node.children[idx].end) {
                    return sb.toString();
                }
                node = node.children[idx];
            }
            return null;
        }
    }


    public String replaceWords(List<String> dict, String sentence) {
        Trie root = new Trie();
        for (String s : dict) {
            root.insert(s);
        }

        String[] ss = sentence.split(" ");
        for (int i = 0; i < ss.length; ++i) {
            String r = root.findRoot(ss[i]);
            if (r != null) {
                ss[i] = r;
            }
        }

        String ns = "";
        for (int i = 0; i < ss.length - 1; ++i) {
            ns += ss[i] + " ";
        }
        ns += ss[ss.length - 1];
        return ns;
    }
}