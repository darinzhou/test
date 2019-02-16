package com.comcast.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 Implement a Java Iterator for a nested HashMap in which an element can be any of
 1) an Integer or,
 2) another nested HashMap or,
 3) an empty HashMap
 */
public class NestedHashMapIteratorTest {

    public interface NestedHashMap extends Map {
        public boolean isInteger();
        public Integer getInteger();
        public NestedHashMap getHashMap();
    }

    public class NestedHashMapIterator implements Iterator {
        private NestedHashMap nhm;
        private NestedHashMapIterator it;
        private NestedHashMapIterator pIt;
        private boolean currentIsFinal;

        public NestedHashMapIterator(NestedHashMap nhm, NestedHashMapIterator pIt) {
            this.pIt = pIt;

            if (nhm.getHashMap() != null)
                it = (NestedHashMapIterator)nhm.entrySet().iterator();
            else {
                it = null;
                this.nhm = nhm;
                currentIsFinal = true;
            }
        }

        public boolean hasNext() {
            if (currentIsFinal)
                return true;

            if (it == null && pIt == null)
                return false;

            if (it != null)
                return it.hasNext();

            return pIt.hasNext();
        }

        public NestedHashMap next() {
            if (currentIsFinal) {
                currentIsFinal = false;
                return nhm;
            }

            nhm = it.next();
            if (nhm == null)
                it = pIt;
            else {
                NestedHashMap nextNhm = (NestedHashMap) nhm.getHashMap();
                if (nextNhm != null) {
                    it = new NestedHashMapIterator(nextNhm, it);
                } else
                    return nextNhm;
            }

            if (it != null && it.hasNext())
                return it.next();

            return null;
        }

        @Override
        public void remove() {

        }
    }

    public class NestedHashMapIterator2 implements Iterator {

        private Stack<Iterator> stack;
        private NestedHashMap nextMap;
        private Iterator it;

        public NestedHashMapIterator2(NestedHashMap nhm) {
            stack = new Stack<>();

            while (nhm != null && !nhm.isEmpty()) {
                it = nhm.entrySet().iterator();
                nextMap = (NestedHashMap) ((Map.Entry) (it.next())).getValue();
                if (nextMap == null || nextMap.isEmpty() || nextMap.isInteger())
                    break;
                stack.push(it);
                nhm = nextMap;
            }

        }

        @Override
        public boolean hasNext() {
            if (nextMap != null && nextMap.isInteger())
                return true;

            return false;
        }

        @Override
        public Object next() {
            Integer result = nextMap.getInteger();

            nextMap = null;

            if (it == null || !it.hasNext())
                if (!stack.isEmpty())
                    it = stack.pop();

            if (it!=null && it.hasNext()) {
                NestedHashMap nhm = (NestedHashMap) ((Map.Entry) it.next()).getValue();
                while (nhm != null && !nhm.isEmpty()) {
                    it = nhm.entrySet().iterator();
                    nextMap = (NestedHashMap) ((Map.Entry) it.next()).getValue();
                    if (nextMap == null || nextMap.isInteger() || nextMap.isEmpty())
                        break;
                    stack.push(it);
                    nhm = nextMap;
                }
            }

            return result;
        }

        @Override
        public void remove() {

        }
    }


}
