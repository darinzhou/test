package com.comcast.algorithms;

public class BinarySearch {

    public static int binarySearch(int[] a, int target) {
        if (a == null || a.length == 0) {
            return -1;
        }

        int l = 0;
        int r = a.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (a[m] == target) {
                return m;
            } else if (a[m] > target) {
                // find in left half
                r = m - 1;
            } else {
                // find in right part
                l = m + 1;
            }
        }

        return -1;
    }

    public static int binarySearchRecursive(int[] a, int l, int r, int target) {
        if (a == null || a.length == 0) {
            return -1;
        }

        if (l > r) {
            return -1;
        }

        int m = l + (r - l) / 2;
        if (a[m] == target) {
            return m;
        } else if (a[m] > target) {
            return binarySearchRecursive(a, l, m - 1, target);
        }

        return binarySearchRecursive(a, m + 1, r, target);
    }

    public static int[] binarySearchDuplicateRange(int[] a, int target) {
        if (a == null || a.length == 0) {
            return null;
        }

        int l = 0;
        int r = a.length - 1;

        // find start index
        int start = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a[m] == target) {
                start = m;
                // continue search on the left half
                r = m - 1;
            } else if (a[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        // find end index
        l = 0;
        r = a.length - 1;
        int end = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a[m] == target) {
                end = m;
                // continue search on the right half
                l = m + 1;
            } else if (a[m] > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        if (start == -1 && end == -1) {
            return null;
        } else if (start == -1) {
            return new int[]{end, end};
        } else if (end == -1) {
            return new int[]{start, start};
        }

        return new int[]{start, end};
    }

    public static int binarySearchSmallestInRotatedSortedArray(int[] a) {
        if (a == null || a.length == 0) {
            return -1;
        }

        int l = 0;
        int r = a.length - 1;

        // no rotation case, leftmost one is the smallest one
        if (a[l] < a[r]) {
            return l;
        }

        while (l <= r) {

            // we found it
            if (l == r) {
                return l;
            }

            int m = l + (r - l) / 2;

            if (a[m] < a[r]) {
                // search on the left half
                // position m may be the target, so next search must include position m
                r = m;
            } else {
                l = m + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] a2 = new int[]{0, 1, 2, 3, 3, 3, 3, 4, 5, 6, 7, 8, 9};
        int[] a3 = new int[]{7, 8, 9, 1, 2, 3, 4, 5, 6};

        int t10 = binarySearch(a1, 8);
        int t11 = binarySearch(a1, 0);
        int t12 = binarySearch(a1, 9);
        int t13 = binarySearch(a1, 90);
        int t101 = binarySearchRecursive(a1, 0, a1.length - 1, 8);
        int t102 = binarySearchRecursive(a1, 0, a1.length - 1, 0);
        int t103 = binarySearchRecursive(a1, 0, a1.length - 1, 9);
        int t104 = binarySearchRecursive(a1, 0, a1.length - 1, 90);
        int[] t20 = binarySearchDuplicateRange(a2, 3);
        int[] t21 = binarySearchDuplicateRange(a2, 0);
        int[] t22 = binarySearchDuplicateRange(a2, 9);
        int[] t23 = binarySearchDuplicateRange(a2, 6);
        int[] t24 = binarySearchDuplicateRange(a2, 60);
        int t3 = binarySearchSmallestInRotatedSortedArray(a3);
        int a = 0;
    }
}
