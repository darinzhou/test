package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 Find the 100 most frequently occurring words in a set of documents. Optimize.
 */
public class WordFrequencyCount {

    public static ArrayList<String> getMostFrequenctlyOccuredWords(String[] words, int n) {
        // create pq with comparator on map entry's value
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<Map.Entry<String, Integer>>(n, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                return lhs.getValue() - rhs.getValue();
            }
        });

        // hash map
        HashMap<String, Integer> wordMap = new HashMap<>();
        for (int i=0; i<words.length; ++i) {
            if (wordMap.containsKey(words[i]))
                wordMap.put(words[i], wordMap.get(words[i]) + 1);
            else
                wordMap.put(words[i], 1);
        }

        // add to pq
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            // if pq is full, compare the smallest item in pq and the current entry
            if (pq.size() >= n) {
                // if current entry's frequency is less than the smallest in pq, then ignore it
                Map.Entry<String, Integer> e = pq.peek();
                if (e.getValue() >= entry.getValue())
                    continue;

                // if need to add the current entry, remove the smallest item
                if (pq.size() >= n)
                    pq.remove();
            }

            // add entry to pq
            pq.add(entry);
        }

        // return result list
        ArrayList<String> wl = new ArrayList<>();
        while (!pq.isEmpty())
            wl.add(pq.remove().getKey());
        return wl;
    }
}
