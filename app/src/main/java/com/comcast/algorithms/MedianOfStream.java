package com.comcast.algorithms;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PriorityQueue;

/*
Given that integers are read from a data stream. Find median of elements read so for in efficient way.
*/

public class MedianOfStream {

    private InputStream stream;
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public MedianOfStream(InputStream stream) {
        this.stream = stream;
    }

    public boolean hasNext() throws IOException {
        return stream != null && stream.available() > 0;
    }

    public int nextMedian() throws IOException {
        if (stream != null && stream.available() > 0) {
            int current = stream.read();//.readByte();//.readInt();

            // add to heap
            if (maxHeap.isEmpty())
                maxHeap.add(current);
            else {
                int left = maxHeap.peek();
                if (current < left)
                    maxHeap.add(current);
                else
                    minHeap.add(current);
            }

            // balance heaps
            int leftSize = maxHeap.size();
            int rightSize = minHeap.size();
            if (leftSize > rightSize + 2) {
                int moveToRight = maxHeap.remove();
                minHeap.add(moveToRight);
            } else if (rightSize > leftSize + 2) {
                int moveToLeft = minHeap.remove();
                maxHeap.add(moveToLeft);
            }
        }

        // get median
        int leftSize = maxHeap.size();
        int rightSize = minHeap.size();
        if (leftSize == 0 && rightSize == 0)
            throw new IOException("no data in stream");
        else if (leftSize == 0)
            return minHeap.peek();
        else if (rightSize == 0)
            return maxHeap.peek();

        int leftCurrent = maxHeap.peek();
        int rightCurrent = minHeap.peek();
        if (leftSize < rightSize)
            return rightCurrent;
        else if (leftSize > rightCurrent)
            return leftCurrent;
        return (leftCurrent+rightCurrent)/2;
    }

}
