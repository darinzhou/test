package com.comcast.algorithms;

/**
 * Created by zzhou200 on 7/21/15.
 */
public class Node {
    public int val;
    public Node next;

    public Node(int v) {
        val = v;
        next = null;
    }

    public void setData(int v) {
        val = v;
    }
    public int getData() {
        return val;
    }

    public void setNext(Node n) {
        next = n;
    }
    public Node getNext() {
        return next;
    }
}
