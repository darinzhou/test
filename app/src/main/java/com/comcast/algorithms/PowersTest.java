package com.comcast.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PowersTest {

    // Provide an implementation of the following interface:
    public interface Powers extends Iterator<Long> {
    /* Returns the next integer a in the arithmetic sequence of integers where
    * a = m^n, m > 1 n > 1, and m and n are both integers
    * Thus, the first few outputs will be 4, 8, 9, 16, 25, 27, 32, 36, etc.
    */

        public Long next();

        /* Resets the sequence to the beginning, such that the next call to next()
        * will return 4.
        */
        public void reset();
    }

    public static class PowersClass implements Powers {
        private PriorityQueue<Long> pq  =new PriorityQueue<>();
        private HashMap<Long, Integer> mMap = new HashMap<>();
        private HashMap<Long, Integer> nMap = new HashMap<>();

        public PowersClass() {
            add(2, 2);
        }

        public boolean add(int m, int n) {
            long a = (long)Math.pow(m, n);

            if (mMap.containsKey(a))
                return false;

            mMap.put(a, m);
            nMap.put(a, n);
            pq.add(a);
            return true;
        }

        @Override
        public boolean hasNext() {
            return true;
        }
        @Override
        public void remove() {

        }

        /*
        1) Use a minheap
        2) Upon reset (or instantiation), make sure heap is cleared with 4 on the top with {2,2} for m^n as metadata.
        3) When next is called, pop the min and add 8 {2,3} and 9 {3,2}
        4) Repeat for {m,n+1} when you pop {m,n}. If a {m,2} is popped, then add {m+1,2}
        5) For any existing values, push {m,n+2} instead
        6) Rinse and repeat for each next().
         */

        @Override
        public Long next() {
            long a = pq.remove();
            int m = mMap.get(a);
            int n = nMap.get(a);

            mMap.remove(a);
            nMap.remove(a);

            // add next 2^n
            if (m == 2)
                add(2, n+1);

            // add other, consider duplicated values
            if (n == 2) {
                if (!add(m + 1, 2))
                    add(m + 2, 2);
            } else {
                if (!add(m, n + 1))
                    add(m, n + 2);
            }

            return a;
        }

        @Override
        public void reset() {
            pq.clear();
            mMap.clear();
            nMap.clear();
            add(2,2);
        }
    }
}
