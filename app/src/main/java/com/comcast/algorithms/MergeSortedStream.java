package com.comcast.algorithms;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 Given an interface called IntStream with methods 'bool hasNext()' and 'int next()', implement the
 function 'IntStream merge(IntStream[] streams)' where each input IntStream produces strictly
 increasing, possibly infinite number of, integers, and the resultant IntStream also produces strictly
 increasing integers by merging the input streams. The interviewer also provides a simple test harness
 that prints the first 5000 integers from that function.
 */
public class MergeSortedStream {
    public interface IntStream {
         boolean hasNext();
         int next();
    }

    public class MergedIntStream implements IntStream {
        class ISElement implements Comparable<ISElement> {
            private IntStream stream;
            private int current;

            public ISElement(IntStream stream) {
                this.stream = stream;
                current = stream.next();
            }

            public IntStream getStream() {
                return stream;
            }

            public boolean hasNext() {
                return stream.hasNext();
            }

            public int current() {
                return current;
            }

            @Override
            public int compareTo(ISElement another) {
                return current - another.current;
            }
        }

        // pq as minheap
        private PriorityQueue<ISElement> pq = new PriorityQueue<>();

        public MergedIntStream(IntStream[] streams) {
            // all stream's first element in queue
            for (IntStream stream : streams) {
                pq.add(new ISElement(stream));
            }
        }

        @Override
        public boolean hasNext() {
            return !pq.isEmpty();
        }

        @Override
        public int next() {
            if (pq.isEmpty())
                throw new NoSuchElementException("no next available");

            // merge and return next
            // get the smallest one from min heap
            ISElement least = pq.remove();
            int current = least.current();

            // add next element from the array just extracted to min heap if there is any
            if (least.hasNext())
                pq.add(new ISElement(least.getStream()));

            return current;
        }
    }

    public IntStream merge(IntStream[] streams) {
        return new MergedIntStream(streams);
    }
}
