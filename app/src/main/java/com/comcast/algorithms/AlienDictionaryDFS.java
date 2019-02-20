package com.comcast.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/*
Given a sorted dictionary (array of words) of an alien language, find order of characters in the language.
Examples:

Input:  words[] = {"baa", "abcd", "abca", "cab", "cad"}
Output: Order of characters is 'b', 'd', 'a', 'c'
Note that words are sorted and in the given language "baa"
comes before "abcd", therefore 'b' is before 'a' in output.
Similarly we can find other orders.

Input:  words[] = {"caa", "aaa", "aab"}
Output: Order of characters is 'c', 'a', 'b'
*/
public class AlienDictionaryDFS {

    // Time:
    public static String alienOrder(String[] words) {
        // graph: map of char -> set of chars
        // key: char
        // value: char set of all chars with lower order than the key
        Map<Character, Set<Character>> graph = new HashMap<>();

        // 1. build graph
        buildGraph(words, graph);

        // 2. dfs - recursive

        // controller for visiting status: set of visited chars
        Set<Character> visited = new HashSet<>();

        // stack save results
        // char order is built reversely
        Stack<Character> stack = new Stack<>();

        // dfs: for each char, recursively call dfs
        for (char c : graph.keySet()) {
            if (!visited.contains(c)) {
                dfs(c, graph, visited, stack);
            }
        }

        // build result

        // check validation
        if (stack.size() != graph.size()) {
            return "";
        }

        // build order
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    // Time: O(CharNumber) + O(WordNumber * WordLength) -> O(CharNumber)
    private static void buildGraph(String[] words, Map<Character, Set<Character>> graph) {
        // init graph
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.put(c, new HashSet<Character>());
            }
        }

        // build graph by comparing each 2 words
        for (int i = 1; i < words.length; i++) {
            String firstWord = words[i - 1];
            String secondWord = words[i];
            int length = Math.min(firstWord.length(), secondWord.length());

            for (int j = 0; j < length; j++) {
                char charInFirstWord = firstWord.charAt(j);
                char charInSecondWord = secondWord.charAt(j);

                // only the first not same char matters
                if (charInFirstWord != charInSecondWord) {
                    // charInSecondWord's order is lower tha charInFirstWord
                    // if relationship between charInFirstWord and charInSecondWord has not been built yet
                    // then build it
                    graph.get(charInFirstWord).add(charInSecondWord);
                    break;
                }
            }
        }
    }

    // Time:
    private static void dfs(char c, Map<Character, Set<Character>> graph,
                            Set<Character> visited, Stack<Character> stack) {

        // process it, mark as visited
        visited.add(c);

        // process current node's neighbors: all chars with lower order than current char
        for (char ch : graph.get(c)) {
            if (!visited.contains(ch)) {
                dfs(ch, graph, visited, stack);
            }
        }

        // to here, it was solve

        // push to result stack
        stack.push(c);
    }

    public static void main(String[] args) {
        String[] words = new String[]{"baa", "abcd", "abca", "cab", "cad"};
//        String[] words = {"caa", "aaa", "aab"};
//        List<Character> sortedChars = sortChars(words);

        String s = alienOrder(words);

        int a = 0;
    }
}
