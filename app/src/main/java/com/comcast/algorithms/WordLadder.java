package com.comcast.algorithms;

import java.util.*;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 Given two words (start and end), and a dictionary, find all shortest transformation sequence(s) from start to end, such that:
 1) Only one letter can be changed at a time, 2) Each intermediate word must exist in the dictionary.

 For example, given: start = "hit", end = "cog", and dict = ["hot","dot","dog","lot","log"], return:

 [
 ["hit","hot","dot","dog","cog"],
 ["hit","hot","lot","log","cog"]
 ]
 */

public class WordLadder {
    public class Node {
        public String word;
        public HashSet<Node> neighbors;

        public Node(String word) {
            neighbors = new HashSet<>();
            this.word = word;
        }

        public void addNeighbor(Node n) {
            neighbors.add(n);
        }

        public HashSet<Node> getNeighbors() {
            return neighbors;
        }

        public boolean sameWord(Node n) {
            return word.equals(n.word);
        }
    }

    HashSet<String> dictionary;
    private HashMap<String, Node> graph;

    public WordLadder(HashSet<String> dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> findLadder(String start, String end) {
        // build graph
        buildGraph(start, end);

        // BFS to find the shortest path
        Node root = graph.get(start);
        Node target = graph.get(end);
        List<Node> path = BFS(root, target);

        // build ladder based on path
        List<String> ladder = new ArrayList<>();
        for (Node n : path)
            ladder.add(n.word);

        return ladder;
    }

    // check if two words differ by one character
    private boolean diffOneChar(String a, String b) {
        if (a.length() != b.length())
            return false;

        int differ = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                differ++;
                if (differ > 1)
                    return false;
            }
        }

        return (differ==1);
    }

    private void buildGraph(String start, String end) {

        // start and end should be in dictionary
        String[] newDict = new String[dictionary.size() + 2];
        newDict[0] = start;
        int i=1;
        for (String word : dictionary)
            newDict[i++] = word;
        newDict[i] = end;

        // build HashMap for word and its graph node
        graph = new HashMap<>();
        for (String s : newDict)
            graph.put(s, new Node(s));

        // build neighbor relationship for all words
        for (i = 0; i < newDict.length; ++i) {
            Node nodei = graph.get(newDict[i]);
            for (int j = i + 1; j < newDict.length; ++j) {
                if (diffOneChar(newDict[i], newDict[j])) {
                    Node nodej = graph.get(newDict[j]);
                    nodei.addNeighbor(nodej);
                    nodej.addNeighbor(nodei);
                }
            }
        }
    }

    // shortest path from root to target with BFS
    // Time: O(V+E)
    // Memory: O(V)
    private List<Node> BFS(Node root, Node target) {
        HashSet<Node> visited = new HashSet<>();
        HashMap<Node, Node> prev = new HashMap<>();

        Queue<Node> q = new LinkedList<Node>();

        Node current = root;
        visited.add(current);
        q.add(current);

        boolean found = false;
        while (!q.isEmpty()) {
            current = q.remove();

            // found
            if (current.sameWord(target)) {
                found = true;
                break;
            }

            HashSet<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    prev.put(n, current);
                    q.add(n);
                }
            }
        }

        if (!found)
            return null;

        // retrieve path via prev
        List<Node> path =  new ArrayList<>();
        for (Node n = current; n != null; n = prev.get(n))
            path.add(n);
        Collections.reverse(path);
        return path;
    }
}