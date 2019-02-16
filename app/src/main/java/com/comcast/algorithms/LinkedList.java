package com.comcast.algorithms;

/**
 * Created by zzhou200 on 7/21/15.
 */

public class LinkedList {
    public Node head;
    public int count;

    public LinkedList() {
        head = null;
        count = 0;
    }
    public LinkedList(int v) {
        head = new Node(v);
        count = 1;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count==0;
    }

    // get the node at index=count will return null
    public Node get(int index) throws Exception {
        if (index < 0 || index > count)
            throw new Exception("invalid index");

        if (index == count)
            return null;

        Node current = head;
        for (int i=0; i<index; ++i)
            current = current.getNext();

        return current;
    }

//    public Node getLast() {
//        if (isEmpty())
//            return null;
//
//        Node prev = null;
//        Node current = head;
//        while (current != null) {
//            prev = current;
//            current = current.getNext();
//        }
//
//        return prev;
//    }

//    public void add(int v) {
//        Node newNode = new Node(v);
//
//        try {
//            Node last = get(count - 1);
//            if (last == null) {
//                head = newNode;
//                count = 1;
//                return;
//            }
//
//            if (last != null) {
//                last.setNext(newNode);
//                count++;
//            }
//        } catch (Exception e) {
//        }
//    }

    // inserts at the end in this list
    public void add(int v) {
        try {
            add(v, count);
        } catch (Exception e) {

        }
    }

    // inserts the specified element at the specified position in this list
    public void add(int v, int index) throws Exception {
        if (index < 0)
            throw new Exception("invalid index");
        if (index > count)
            index = count;

        Node newNode = new Node(v);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
            count++;
            return;
        }

        Node prev = get(index - 1);
        Node node = get(index);

        newNode.setNext(node);
        prev.setNext(newNode);
        count++;
    }

    public void remove() throws Exception {
        remove(count-1);
    }

    public void remove(int index) throws Exception {
        if (index < 0 || index >= count)
            throw new Exception("invilad index");

        // find node and the one prev to it
        Node prev = null;
        Node node = head;
        for (int i = 0; i < index; ++i) {
            prev = node;
            node = node.getNext();
        }

        // remove
        if (prev == null)
            head = head.getNext();
        else
            prev.setNext(node.getNext());

        count--;
    }

    public static Node reverse(Node head) {
        Node prev = null;
        Node current = head;

        while (current != null) {
            Node next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    public static Node reverseWithRecursive(Node head) {
        if (head == null || head.getNext() == null)
            return head;

        // get second node
        Node second = head.next;

        // reverse head - set head's next to null
        head.next = null;

        // reverse all nodes starting at second
        Node rest = reverseWithRecursive(second);

        // reverse head and second
        second.next = head;

        // return rest
        return rest;
    }

    public static Node reverseWithRecursive2(Node current, Node prev) {
        if (current == null)
            return prev;

        // reverse all nodes starting at next
        Node rest = reverseWithRecursive2(current.next, current);
        // reverse current and prev
        current.next = prev;

        // return rest
        return rest;
    }

    public void reverse() {
        head = reverse(head);
    }

    /*
    Given a node from a cyclic linked list which has been sorted, write a function to insert a value
    into the list such that it remains a cyclic sorted list. The given node can be any single node in the list.
     */

    public static Node insertToSortedCircularLinkedList(Node node, int x) {
        Node nn = new Node(x);

        if (node == null) {
            nn.next = nn;
            return nn;
        }

        Node prev = null;
        Node current = node;

        // assume list is in non-descending order
        do {
            prev = current;
            current = current.next;

            if (x >= prev.val && x <= current.val)
                break;
            else if (prev.val > current.val && (x > prev.val || x < current.val))
                break;
        } while (current != node);

        prev.next = nn;
        nn.next = current;
        return nn;
    }

    /*
    Given a linked list of numbers. Swap every 2 adjacent links
     */

    public static Node swapAdjacentNodes(Node head) {
        if (head == null)
            return null;
        Node newHead = head.next;
        if (newHead == null)
            return head;

        Node prev = null;
        Node current = head;

        while (current != null) {
            Node next = current.next;
            if (next == null)
                break;
            Node nextNext = next.next;

            if (prev != null)
                prev.next = next;
            next.next = current;

            current.next = nextNext;

            prev = current;
            current = nextNext;
        }

        return newHead;
    }

    /*
    merge two sorted lists to one
     */

    public static Node merge(Node h1, Node h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;

        Node h = null;
        Node current = null;
        while (h1 != null && h2 != null) {
            int lessVal = 0;
            if (h1.val < h2.val) {
                lessVal = h1.val;
                h1 = h1.next;
            } else {
                lessVal = h2.val;
                h2 = h2.next;
            }

            Node n = new Node(lessVal);
            if (h == null)
                h = n;
            else
                current.next = n;
            current = n;
        }

        if (h1 != null)
            current.next = h1;
        else if (h2 != null)
            current.next = h2;

        return h;
    }

    /*
    Given a linked list, write a function to reverse every k nodes (where k is an input to the function).

    Example:
    Inputs:  1->2->3->4->5->6->7->8->NULL and k = 3
    Output:  3->2->1->6->5->4->8->7->NULL.

    Inputs:   1->2->3->4->5->6->7->8->NULL and k = 5
    Output:  5->4->3->2->1->8->7->6->NULL.
     */

    public static Node reverseKGroup(Node head, int k) {
        Node prev = null;
        Node current = head;
        Node next = null;

        // reverse the first group
        int count = 0;
        while (current != null && count < k) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }

        // after reversing the first group
        // 1. head is the last node of the first (reversed) group
        // 2. next is the head of next group
        // 3. prev is the head of the whole reversed list

        // recursively reverse other groups
        if (next != null)
            head.next = reverseKGroup(next, k);

        // return the new head
        return prev;
    }

    /*
    Remove Duplicates from Sorted List II
    Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

    For example,
    Given 1->2->3->3->4->4->5, return 1->2->5.
    Given 1->1->1->2->3, return 2->3.
     */

    public static Node deleteDuplicated(Node head) {
        Node current = head;
        Node next = null;

        while (current != null) {
            next = current.next;
            while (next != null && current.val == next.val)
                next = next.next;

            current.next = next;
            current = current.next;
        }

        return head;
    }

    /*
    Reverse a linked list from position m to n. Do it in-place and in one-pass.
    For example:
    Given 1->2->3->4->5->NULL, m = 2 and n = 4,
    return 1->4->3->2->5->NULL.
     */

    public static Node reversePartially(Node head, int start, int end) {
        if (head == null)
            return null;

        Node prev = null;
        Node current = head;

        int count = 1;
        while (current != null && count < start) {
            prev = current;
            current = current.next;
            count++;
        }

        if (current == null)
            return head;

        // prev is the node before the part to be reversed
        // current is the node start to reverse

        Node endNodeOfReversedPart = current;

        Node prev1 = current;
        current = current.next;

        Node next = null;
        while (current != null && count < end) {
            next = current.next;
            current.next = prev1;
            prev1 = current;
            current = next;
            count++;
        }

        // prev1 is the start of reversed part
        // current is the first node after the reversed part

        endNodeOfReversedPart.next = current;
        prev.next = prev1;

        return head;
    }

    /*
    Partition List
    Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
    You should preserve the original relative order of the nodes in each of the two partitions.

    For example,
    Given 1->4->3->2->5->2 and x = 3,
    return 1->2->2->4->3->5.
     */

    public static Node partition(Node head, int x) {
        if (head == null || head.next == null)
            return head;

        Node prev = null;
        Node current = head;
        Node prevFirstGreater = null;
        Node firstGreater = null;

        while (current != null) {
            if (firstGreater == null && current.val >= x) {
                prevFirstGreater = prev;
                firstGreater = current;

                prev = current;
                current = current.next;
            } else  if (firstGreater != null && current.val < x) {
                // backup
                Node n = current.next;

                // move current before firstGreater
                if (prevFirstGreater == null)
                    head = current; // new head
                else
                    prevFirstGreater.next = current;
                prev.next = current.next;
                current.next = firstGreater;
                prevFirstGreater = current;

                // update current, prev is unchanged
                current = n;

            } else {
                prev = current;
                current = current.next;
            }
        }

        return head;
    }

    /*
    Remove Nth Node From End of List
    Given a linked list, remove the nth node from the end of list and return its head.
    For example,
       Given linked list: 1->2->3->4->5, and n = 2.

       After removing the second node from the end, the linked list becomes 1->2->3->5.

    Note:
    Given n will always be valid.
    Try to do this in one pass.
     */

    public static Node removeNthNodeFromEnd(Node head, int n) {
        if (head == null)
            return null;

        Node slow = head;
        Node fast = head;
        int forward = 0;
        while (fast != null && forward <= n) {
            fast = fast.next;
            forward++;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        if (slow.next != null)
            slow.next = slow.next.next;

        return head;
    }
}
