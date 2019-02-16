package com.comcast.algorithms;

/* This class will be given a list of words (such as might be tokenized
 * from a paragraph of text), and will provide a method that takes two
 * words and returns the shortest distance (in words) between those two
 * words in the provided text.
 * Example:
 *   WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
 *   assert(finder.distance("fox","the") == 3);
 *   assert(finder.distance("quick", "fox") == 1);
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordDistanceFinder {

    private List<String> sentence;
    private HashMap<String, Integer> map;

    public WordDistanceFinder(List<String> sentence) {
        this.sentence = sentence;
        map = new HashMap<>();
    }

    public int distance(String w1, String w2) {
        if (sentence == null || sentence.size() == 0 || w1 == null || w2 == null || w1.length() == 0 || w2.length() == 0)
            return -1;
        if (w1.equals(w2))
            return 0;

        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i< sentence.size(); ++i) {
            if (sentence.get(i).equals(w1))
                map.put(w1, i);
            else if (sentence.get(i).equals(w2))
                map.put(w2, i);

            if (map.size() == 2) {
                minDistance = Math.min(minDistance, Math.abs(map.get(w1) - map.get(w2)));
            }
        }

        return minDistance;
    }
}
