package com.comcast.algorithms;

/**
 * Created by zzhou200 on 7/21/15.
 */
public class Queue {
    private Node head;
    private Node tail;
    private int count;

    public Queue() {
        head = null;
        tail = null;
        count = 0;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count==0;
    }

    public void enqueue(Node node) {
        if (head == null) {
            head = node;
            tail = head;
        } else {
            tail.setNext(node);
            tail = node;
        }
        count++;
    }

    public Node dequeue() {
        if (head == null)
            return null;
        Node temp = head;
        head = head.getNext();
        count--;
        return temp;
    }
}
