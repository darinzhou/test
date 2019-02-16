package com.comcast.algorithms;

import java.util.List;
import java.util.ArrayList;

/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;

/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;

class HashTable {

    class Pair {
        public String key;
        public Object val;
        public Pair(String k, Object v) {
            key = k;
            val = v;
        }
    }

    class Bucket {
        private List<Pair> pairs = new ArrayList<>();

        private Pair getPair(String key) {
            for (Pair p : pairs) {
                if (p.key.equals(key)) {
                    return p;
                }
            }
            return null;
        }

        public boolean containsKey(String key) {
            return getPair(key) != null;
        }

        public Object get(String key) {
            Pair p = getPair(key);
            if (p == null) {
                return null;
            }
            return p.val;
        }

        public void add(String key, Object val) {
            Pair p = getPair(key);
            if (p == null) {
                pairs.add(new Pair(key, val));
                return;
            }
            p.val = val;
        }

        public Object remove(String key) {
            Pair p = getPair(key);
            if (p == null) {
                return null;
            }
            pairs.remove(p);
            return p.val;
        }
    }

    private Bucket[] buckets;
    private int capacity = 1024;
    private int count = 0;

    public HashTable() {
        buckets = new Bucket[capacity];
    }
    public HashTable(int cap) {
        capacity = cap;
        buckets = new Bucket[capacity];
    }

    private int hash(String key) {
        return key.hashCode() % capacity;
    }
    private Bucket getBucket(String key) {
        return buckets[hash(key)];
    }

    public boolean containsKey(String key) {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            return false;
        }
        return bucket.containsKey(key);
    }

    public Object get(String key) {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            return null;
        }
        return bucket.get(key);
    }

    public void put(String key, Object val) {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            bucket = new Bucket();
            buckets[hash(key)] = bucket;
        }

        if (!bucket.containsKey(key)) {
            count++;
        }
        bucket.add(key, val);
    }

    public Object remove(String key) {
        Bucket bucket = getBucket(key);
        if (bucket == null) {
            return null;
        }

        Object val = bucket.remove(key);
        if (val != null) {
            count--;
        }

        return val;
    }

    public int size() {
        return count;
    }


    public static void main (String[] args) {
        HashTable ht = new HashTable();

        ht.put("test", 123);
        ht.put("abc", 456);
        ht.put("def", 789);

        System.out.println(ht.get("test"));
        System.out.println(ht.get("abc"));
        System.out.println(ht.get("def"));
        System.out.println("------->" + ht.size());

        ht.remove("abc");
        System.out.println(ht.get("test"));
        System.out.println(ht.get("abc"));
        System.out.println(ht.get("def"));
        System.out.println("------->" + ht.size());
    }
}