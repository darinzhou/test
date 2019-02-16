package com.comcast.algorithms;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zzhou200 on 7/29/15.
 */
public class LRU<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public LRU(int cacheSize) {
        super(cacheSize, 1.0f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return size() > cacheSize;
//        return super.removeEldestEntry(eldest);
    }

}
