package com.comcast.algorithms;

import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by zzhou200 on 8/30/15.
 */
public class MinStack {
    private Stack<Integer> stack = new Stack<>();
    private PriorityQueue<Integer> pq = new PriorityQueue<>();

    public void push(int e) {
        stack.push(e);
        pq.add(e);
    }

    public int pop() {
        int e = stack.pop();
        pq.remove(e);
        return e;
    }

    public int min() {
        return pq.peek();
    }
}
