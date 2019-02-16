package com.comcast.algorithms;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zzhou200 on 8/30/15.
 */
public class ConcurrentReadWriteBuffer<T> {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();
    private List<T> list = new ArrayList<T>();

    public T get(int i) {
        readLock.lock();
        try {
            return list.get(i);
        } finally {
            readLock.unlock();
        }
    }

    public void set(int i, T data) {
        writeLock.lock();
        try {
            list.add(i, data);
        } finally {
            writeLock.unlock();
        }
    }
}
