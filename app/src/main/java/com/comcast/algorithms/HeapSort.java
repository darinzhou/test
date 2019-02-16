package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/1/15.
 */
public class HeapSort {

    // heap sort

    // 1. heapify to build max heap, now max element is the first one (root)
    // 2. swap the last element and the first(root), now the max element is the last
    // 3. decrease the heap size by 1
    // 4. call maxHeap to shift the new max to root
    // 5. loop till all elements are processed
    public static void sort(int[] a) {
        heapify(a);

        int n = a.length;
        for (int i = a.length-1; i > 0; i--) {
            swap(a, i, 0);
            n--;
            maxheap(a, 0, n);
        }
    }

    // build heap
    // Time: O(n)
    public static void heapify(int[] a) {
        int n  = a.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            maxheap(a, i, n);
    }

    // swap largest element in heap
    // shift up the largest element to root
    // Time: O(logn) - O(h)
    public static void maxheap(int[] a, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int maxi = i;

        if (left < n && a[left] > a[maxi])
            maxi = left;
        if (right < n && a[right] > a[maxi])
            maxi = right;

        if (maxi != i) {
            swap(a, i, maxi);
            maxheap(a, maxi, n);
        }
    }

    // swap ywo elements in an array
    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}