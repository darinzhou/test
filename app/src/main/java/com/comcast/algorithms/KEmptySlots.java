package com.comcast.algorithms;

import java.io.*;
import java.util.*;

/*
There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.

Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.

For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.

Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.

If there isn’t such day, output -1.

Example 1:

1
2
3
4
5
Input:
flowers: [1,3,2]
k: 1
Output: 2
Explanation: In the second day, the first and the third flower have become blooming.
Example 2:

1
2
3
4
Input:
flowers: [1,2,3]
k: 1
Output: -1
 */
public class KEmptySlots {

//    public static int kEmptySlot(int[] a, int k) {
//        if (a == null || a.length == 0 || a.length < k + 1) {
//            return -1;
//        }
//
//        // <slot, blomming-day>
//        // TreeMap sorts by key
//        TreeMap<Integer, Integer> map = new TreeMap<>();
//
//        for (int i = 0; i < a.length; ++i) {
//            map.put(a[i], i);
//
//            // map contains all slots with flower blomming at day i or early
//            Set<Map.Entry<Integer, Integer>> set = map.entrySet();
//            Iterator<Map.Entry<Integer, Integer>> it = set.iterator();
//            Map.Entry<Integer, Integer> e1 = it.next();
//            while (it.hasNext()) {
//                Map.Entry<Integer, Integer> e2 = it.next();
//                // for sure: e1.getValue() <= i && e2.getValue() <= i
//                // if there is 2 neighbor slots in the map, with their distance as k,
//                // then we found day i satisfy the requirement
//                if ((Math.abs(e1.getKey() - e2.getKey()) == k + 1)) {
//                    return i + 1; // the requirement fo day is 1 based, so add 1 to the 0 base number
//                }
//                e1 = e2;
//            }
//        }
//
//        return -1;
//    }

    public static int kEmptySlot(int[] a, int k) {
        if (a == null || a.length == 0 || a.length < k + 1) {
            return -1;
        }

        // <slot>
        // TreeSet sorts by element value
        TreeSet<Integer> set = new TreeSet<>();

        for (int i = 0; i < a.length; ++i) {
            set.add(a[i]);

            // map contains all slots with flower blomming at day i or early
            Iterator<Integer> it = set.iterator();
            int e1 = it.next();
            while (it.hasNext()) {
                int e2 = it.next();
                // for sure: e1.getValue() <= i && e2.getValue() <= i
                // if there is 2 neighbor slots in the map, with their distance as k,
                // then we found day i satisfy the requirement
                if ((e2 - e1) == k + 1) {
                    return i + 1; // the requirement fo day is 1 based, so add 1 to the 0 base number
                }
                e1 = e2;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 2};
        int k = 1;
        System.out.println(kEmptySlot(a, k));
    }
}