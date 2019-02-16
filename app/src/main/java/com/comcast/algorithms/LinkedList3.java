package com.comcast.algorithms;

/**
 * Created by jianxin on 3/21/2018.
 */

/*package whatever //do not write package name here */

import java.io.*;

class LinkedList3 {

    static class Node {
        int data;
        Node next;

        public Node(int dat) {
            data = dat;
            next = null;
        }
    }

    Node head;

    public void appendAtEnd(int dat) {
        Node newNode = new Node(dat);

        if (head == null) {
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    public void appendAtHead(int dat) {
        Node newNode = new Node(dat);
        newNode.next = head;
        head = newNode;
    }

    public void print() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    public void print2(Node h) {
        if (h == null) {
            return;
        }

        System.out.print(h.data + " ");

        print2(h.next);
    }

    public Node mergeSort(Node h) {
        if (h == null || h.next == null) {
            return h;
        }

        Node middle = findMiddle(h);
        Node h2 = middle.next;
        middle.next = null;

        Node left = mergeSort(h);
        Node right = mergeSort(h2);

        Node sortedHead = merge(left, right);
        return sortedHead;
    }

    Node findMiddle(Node h) {
        if (h == null || h.next == null) {
            return h;
        }

        Node fast = h.next;
        Node slow = h;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
        }

        return slow;
    }

    Node merge(Node left, Node right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        Node leftCur = left;
        Node rightCur = right;

        Node h;
        if (leftCur.data > rightCur.data) {
            h = rightCur;
            rightCur = rightCur.next;
        } else {
            h = leftCur;
            leftCur = leftCur.next;
        }

        Node current = h;
        while (leftCur != null && rightCur != null) {
            if (leftCur.data > rightCur.data) {
                current.next = rightCur;
                rightCur = rightCur.next;
            } else {
                current.next = leftCur;
                leftCur = leftCur.next;
            }
            current = current.next;
        }

        if (rightCur != null) {
            current.next = rightCur;
        } else {
            current.next = leftCur;
        }

        return h;
    }


    public static void main (String[] args) {
        LinkedList3 ll = new LinkedList3();
        ll.appendAtHead(2);
        ll.appendAtHead(9);
        ll.appendAtHead(5);
        ll.appendAtHead(4);
        ll.appendAtEnd(1);
        ll.appendAtEnd(8);
        ll.appendAtEnd(5);
        ll.appendAtEnd(6);
        ll.appendAtEnd(7);
        ll.print();
        System.out.println();


        Node mergeSorted = ll.mergeSort(ll.head);
        ll.print2(mergeSorted);

    }
}