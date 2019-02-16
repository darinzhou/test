package com.comcast.algorithms;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by zzhou200 on 8/18/15.
 */
public class LRUCache<T> {
    public class Element implements Comparable<Element> {
        private String key;
        private T value;
        private Date timestamp;

        public Element() {
        }

        public Element(String key, T value, Date timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }

        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }

        public T getValue() {
            return value;
        }
        public void setValue(T value) {
            this.value = value;
        }

        public Date getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(Element another) {
            return timestamp.compareTo(another.timestamp);
        }
    }

    private static final int SIZE = 5;
    private Map<String, Element> map = new HashMap<>();
    private PriorityQueue<Element> pq = new PriorityQueue<>(SIZE);

    // insert to pq and hash map
    private void insert(Element e) {
        System.out.println("Received Element: " + e.getValue());
        // update timestamp
        e.setTimestamp(new Date());
        pq.offer(e); //add
        map.put(e.getKey(), e);
    }

    // remove the least used element from pq and hashmap
    private Element remove() {
        Element leastUsed = pq.poll();      //remove
        if (leastUsed != null) {
            map.remove(leastUsed.getKey());     // remove from hash map
            System.out.println("Removing least used element:" + leastUsed.getValue());
            System.out.println("This element was last used:" + leastUsed.getTimestamp());
        }
        return leastUsed;
    }

    // remove the specified element, update its timestamp and re-insert it
    private void update(String key) {
        //update priority queue with most recent access.
        //Internal datastructure on PriorityQueue is Heap and it is partially sorted.
        //This means, any update on elements means to delete them and add them again.

        Element element = map.get(key);
        pq.remove(element);
        // update timestamp
//        element.setTimestamp(new Date());
        insert(element);
    }

    // remove the specified element with new content, update its timestamp and re-insert it
    private void update(String key, T value) {
        //update priority queue with most recent access.
        //Internal datastructure on PriorityQueue is Heap and it is partially sorted.
        //This means, any update on elements means to delete them and add them again.

        // get element
        Element element = map.get(key);
        // remove from pq
        pq.remove(element);
        // update value
        element.setValue(value);
        // update timestamp
//        element.setTimestamp(new Date());
        //re-insert
        insert(element);
    }

    // get element value with key
    public T get(String key) {
        Element e = map.get(key);
        T value = null;
        if (e != null) {
            value = e.getValue();
            System.out.println("Updating " + key + " with current timestamp.");
            update(key);
        }
        return value;
    }

    // put value with key
    public void put(String key, T value) {
        System.out.println("Before put opertaion, map size:" + map.size());
        if (map.containsKey(key)) {
            System.out.println("Cache hit on key:" + key + ", nothing to insert!");
            update(key, value);
        } else {
            if (pq.size() >= SIZE) {
                remove(); // remove last used from Priority Queue
            }
            System.out.println("Element not present in Cache: " + key);
            Element e = new Element(key, value, new Date());
            insert(e);
        }
        System.out.println("After put operation, following stats are generated:");
        System.out.println("Least used element:" + pq.peek().getValue() + ", last used at:" + pq.peek().getTimestamp());
        System.out.println("map size:" + map.size());
    }
}
