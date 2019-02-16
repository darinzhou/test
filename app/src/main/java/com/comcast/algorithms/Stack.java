package com.comcast.algorithms;

/**
 * Created by zzhou200 on 7/21/15.
 */
public class Stack {
    private Node top;
    private int count;

    public Stack() {
        top = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count==0;
    }

    public void push(Node node) {
        if (node == null)
            return;
        node.setNext(top);
        top = node;
        count++;
    }

    public Node pop() {
        if (top == null)
            return null;
        Node temp = top;
        top = top.getNext();
        count--;
        return temp;
    }

    public Node peek() {
        if (top == null)
            return null;
        return top;
    }
}
