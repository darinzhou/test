package com.comcast.algorithms;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Created by zzhou200 on 9/6/15.
 */
public class QueueUsingStacks<T> {
    Stack<T> in = new Stack<>();
    Stack<T> out = new Stack<>();

    public void enqueue(T o) {
        in.push(o);
    }

    public T dequeue() {
        if (out.isEmpty()) {
            if (in.isEmpty())
                throw new NoSuchElementException("Queue is empty");
            while (!in.isEmpty())
                out.push(in.pop());
        }
        return out.pop();
    }

}
