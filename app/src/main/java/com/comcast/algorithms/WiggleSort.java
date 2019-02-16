package com.comcast.algorithms;

import java.io.*;

/*

Wiggle Sort

Given an unsorted array nums, reorder it in-place such that
nums[0] <= nums[1] >= nums[2] <= nums[3]....

For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].
*/

public class WiggleSort {

    public static void wiggleSort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }

        boolean asc = true;
        for (int i = 0; i < a.length - 1; ++i) {
            swap(asc, a, i, i + 1);
            asc = !asc;
        }
    }

    private static void swap(boolean asc, int[] a, int i, int j) {
        if ((asc && a[i] > a[j]) || (!asc && a[i] < a[j])) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{3, 5, 2, 1, 6, 4};
        wiggleSort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
    }
}