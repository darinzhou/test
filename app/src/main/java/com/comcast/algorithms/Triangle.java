package com.comcast.algorithms;

import android.util.Log;

import java.util.Arrays;

interface ITriangle {

    /**
     * Three segments of lengths A, B, C form a triangle iff
     *
     *      A + B > C
     *      B + C > A
     *      A + C > B
     *
     * e.g.
     *  6, 4, 5 can form a triangle
     * 10, 2, 7 can't
     *
     * Given a list of segments lengths algorithm should find at least one triplet of segments that form a triangle (if any).
     *
     * Method should return an array of either:
     * - 3 elements: segments that form a triangle (i.e. satisfy the condition above)
     * - empty array if there are no such segments
     */
    int[] getTriangleSides(int[] segments);
}

public class Triangle implements ITriangle {

    private boolean isTriangle(int a, int b, int c) {
        return (a + b > c) && (b + c > a) && (a + c > b);
    }

    public int[] getTriangleSides(int[] segments) {
        int[] t = null;

        // sorts all segments: O(nlogn)
        Arrays.sort(segments);

        // check every 3 segemnt combinations: O(n)
        for (int i = 0; i < segments.length - 2; ++i) {
            if (isTriangle(segments[i], segments[i + 1], segments[i + 2])) {
                t = new int[3];
                t[0] = segments[i];
                t[1] = segments[i + 1];
                t[2] = segments[i + 2];
                break;
            }
        }

        return t;
    }

    // Time: O(n*n*logn)
    public void printAllTriangleSides(int[] segments) {
        Arrays.sort(segments);

        int count = 0;
        for (int i = 0; i < segments.length; ++i) {
            for (int j = i + 1; j < segments.length; ++j) {
                int l = j + 1;
                int r = segments.length - 1;

                int rightMostValid = -1;
                while (l <= r) {
                    int mid = l + (r - l) / 2;
                    if (isTriangle(segments[i], segments[j], segments[mid]) &&
                            (mid ==segments.length-1 || !isTriangle(segments[i], segments[j], segments[mid + 1]))) {
                        rightMostValid = mid;
                        break;
                    } else if (isTriangle(segments[i], segments[j], segments[mid - 1]) && !isTriangle(segments[i], segments[j], segments[mid])) {
                        rightMostValid = mid - 1;
                        break;
                    } else if (isTriangle(segments[i], segments[j], segments[mid]) && isTriangle(segments[i], segments[j], segments[mid + 1])) {
                        // move to right
                        l = mid + 2;
                    } else {
                        // move to left
                        r = mid - 1;
                    }
                }

                for (int k = j + 1; k <= rightMostValid; ++k) {
                    count++;
                    Log.i("Triangle", segments[i] + ", " + segments[j] + ", " + segments[k]);
                }
            }
        }

        Log.i("Triangle", "Total: " + count);
    }
}
