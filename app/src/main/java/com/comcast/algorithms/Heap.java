package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/1/15.
 */
public class Heap {
    private int n;
    public int[] a;

    public Heap(int[] a) {
        n = a.length;
        this.a = a;
        heapify();
    }

    public int size() {
        return n;
    }

    public int peek() {
        return a[0];
    }

    // 1. set the last element to the root
    // 2. decrease the array size by 1
    // 3. call minHeap to shift the root down
    public int remove() {
        int v = a[0];

        a[0] = a[n-1];
        n--;

        minheap(0);

        return v;
    }

    // 1. increase array by 1
    // 2. set the value to add to the end of array
    // 3. call shiftUp to shift it up
    public void add(int v) {
        int[] newA = new int[n+1];
        System.arraycopy(a, 0, newA, 0, n);
        newA[n] = v;
        a = newA;
        n++;

        shiftUp(n - 1);
    }

    // smaller shift-up
    protected void shiftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (a[parent] > a[i]) {
                swap(parent, i);
                i = parent;
            } else
                return;
        }
    }

    // build heap
    protected void heapify() {
        for (int i=n/2-1; i>=0; i--)
            minheap(i);
    }

    // bigger shift-down
    protected void minheap(int i) {
        int left = 2*i+1;
        int right = 2*i+2;
        int mini = i;

        if (left < n && a[left] < a[mini])
            mini = left;
        if (right < n && a[right] < a[mini])
            mini = right;

        if (mini != i) {
            swap (i, mini);
            minheap(mini);  // recursive to put bigger down
        }
    }

    private void swap(int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
