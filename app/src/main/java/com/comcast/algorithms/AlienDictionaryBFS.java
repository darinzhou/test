package com.comcast.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Queue;

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
public class AlienDictionaryBFS {

    // Time: O(CharNumber)
    public static String alienOrder(String[] words) {
        // key: char
        // value: char set of all chars with lower order than the key
        Map<Character, Set<Character>> graph = new HashMap<>();

        // key: char
        // value: number-of-chars-with-higher-order than the key
        Map<Character, Integer> inDegree = new HashMap<>();

        buildGraph(words, graph, inDegree);

        String order = bfs(graph, inDegree);

        return order.length() == graph.size() ? order : "";
    }

    // Time: O(CharNumber) + O(WordNumber * WordLength) -> O(CharNumber)
    private static void buildGraph(String[] words, Map<Character, Set<Character>> graph,
                                   Map<Character, Integer> inDegree) {
        // init graph
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.put(c, new HashSet<Character>());
                inDegree.put(c, 0);
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
                    if (!graph.get(charInFirstWord).contains(charInSecondWord)) {
                        // add to graph: charInSecondWord's order is lower tha charInFirstWord
                        graph.get(charInFirstWord).add(charInSecondWord);

                        // one more char with higher order is found: charInFirstWord
                        // number-of-chars-with-higher-order of charInSecondWord should be increased by 1
                        int ref = inDegree.get(charInSecondWord);
                        inDegree.put(charInSecondWord, ref + 1);

                        break;
                    }
                }
            }
        }
    }

    // Time: O(V+E): O(CharNumber) + O(CharNumber + CharToCharRelationship) -> O(CharNumber)
    private static String bfs(Map<Character, Set<Character>> graph, Map<Character, Integer> inDegree) {
        Queue<Character> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();

        // start point: the char which number-of-chars-with-higher-order is 0
        for (char c : graph.keySet()) {
            if (inDegree.get(c) == 0) {
                queue.offer(c);
            }
        }

        // bfs
        while (!queue.isEmpty()) {

            // current node
            char c = queue.poll();
            sb.append(c);

            // process current node's neighbors: all chars with lower order than current char
            for (char ch : graph.get(c)) {
                // as we have solved current char, number-of-chars-with-higher-order of all chars
                // with lower order shoild be decreased by 1
                int ref = inDegree.get(ch);
                inDegree.put(ch, ref-1);

                // if a char's number-of-chars-with-higher-order is zero, then it is the next one
                if (inDegree.get(ch) == 0) {
                    queue.offer(ch);
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String[] words = new String[] {"baa", "abcd", "abca", "cab", "cad"};
//        String[] words = {"caa", "aaa", "aab"};
//        List<Character> sortedChars = sortChars(words);

        String s = alienOrder(words);

        int a = 0;
    }
}
