package com.comcast.algorithms;

/**
 * Design a class to implement the following interface
 */
 interface List<T> {
    public void add(T o);//add to the last

    public T get(int index); //get the index object

    public int size();//return the size

    public boolean remove(T o);//remove the first o and return true; if not exist, return false.
}

public class MyList<T> implements List<T> {
    class Node {
        T val;
        Node next;
        public Node(T v) {
            val = v;
            next = null;
        }
    }

    private Node head;
    private int count;

    @Override
    public void add(T o) {
        Node nn = new Node(o);

        if (head == null) {
            head = nn;
        } else {
            Node current = head;
            while (current.next != null)
                current = current.next;
            current.next = nn;
        }
        count++;
    }

    @Override
    public T get(int index) {
        if (index >= count)
            throw new IndexOutOfBoundsException("index out of bounds");
        Node current = head;
        for (int i=0; i<index; ++i)
            current = current.next;
        return current.val;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean remove(T o) {
        if (size() == 0)
            return false;

        Node prev = null;
        Node current = head;
        while (current != null) {
            if (current.val.equals(o)) {
                if (prev == null) {
                    head = head.next;
                } else {
                    prev.next = current.next;
                }
                count--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }
}
