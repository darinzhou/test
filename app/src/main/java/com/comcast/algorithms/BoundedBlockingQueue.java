package com.comcast.algorithms;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Created by zzhou200 on 8/13/15.
 */
public class BoundedBlockingQueue<E> {
    private Queue<E> q = new LinkedList<E>();
    private int capacity;
    private int count;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        count = 0;
    }

    public synchronized void add(E e) throws InterruptedException {
        while (count == capacity)
            wait();

        int oldCount = count;
        q.add(e);
        count++;

        if (oldCount == 0)
            notifyAll();
    }

    public synchronized E remove() throws InterruptedException {
        while (count == 0)
            wait();

        int oldCount = count;
        E e = q.remove();
        count--;

        if (oldCount == capacity)
            notifyAll();

        return e;
    }

    public E peek() {
        if (count == 0)
            return null;
        synchronized(this) {
            return q.peek();
        }
    }
}
