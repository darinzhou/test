package com.comcast.algorithms;

import java.util.PriorityQueue;

/**
 * You have n infinitely large sorted streams, and you have to get an output stream which returns the combined stream..
 *
 Create an result[] of size n*k.
 Create Min-Heap of type HeapN­ode.( HeapN­ode– Every Node will store the data and the list no from which it belongs).
 Now take one element from each of the K list and cre­ate HeapN­ode object and insert into min-Heap.
 Extract the min­i­mum Node from the min-Heap, insert the data into result array.
 The extracted node will also contain the list to which it belongs, insert the next ele­ment from that list into min-Heap.
 If any point of time any list gets over, insert +∞ into min-Heap.
 Keep repeat­ing until all the K list gets over.
 */
public class MergeSortedArrays {
    static class SortedArrayElement implements Comparable<SortedArrayElement> {
        private int[] array;
        private int currentIndex;

        public SortedArrayElement(int[] a, int ci) {
            array = a;
            currentIndex = ci;
        }

        public int[] getArray() {
            return array;
        }

        public int getLength() {
            return array.length;
        }

        public int getCurrentIndex() {
            return currentIndex;
        }

        public int getCurrentValue() {
            return array[currentIndex];
        }

        @Override
        public int compareTo(SortedArrayElement another) {
            return getCurrentValue() - another.getCurrentValue();
        }
    }

    // k - number of arrays, n - average size of arrays
    // Time: O(nklogk)
    // Space: O(k)
    public static int[] merge(int[][] arrays) {
        // min heap to save current elements of all arrays
        PriorityQueue<SortedArrayElement> minHeap = new PriorityQueue<>();

        // put first elements in min heap and count size of merged array
        int total = 0;
        for (int i=0; i<arrays.length; ++i) {
            minHeap.add(new SortedArrayElement(arrays[i], 0));
            total += arrays[i].length;
        }

        // create merged array
        int[] a = new int[total];

        // merge
        int ai = 0;
        while (!minHeap.isEmpty()) {
            // get the smallest one from min heap
            SortedArrayElement least = minHeap.poll();
            a[ai++] = least.getCurrentValue();

            // add next element from the array just extracted to min heap if there is any
            if (least.getCurrentIndex() < least.getLength()-1)
                minHeap.add(new SortedArrayElement(least.getArray(), least.getCurrentIndex()+1));
        }

        return a;
    }
}
