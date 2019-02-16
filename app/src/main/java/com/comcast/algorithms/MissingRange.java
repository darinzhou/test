package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class MissingRange {

    /*
    Given a sorted integer array where the range of elements are in the inclusive
    range [lower, upper], return its missing ranges.

    For example, given [0, 1, 3, 50, 75], lower = 0 and upper = 99,
    return ["2", "4->49", "51->74", "76->99"].
    */
    public static List<String> missingRanges(int[] a, int l, int u) {
        if (a == null || a.length == 0 || l >= u) {
            return null;
        }
        List<String> res = new ArrayList<>();

        int begin = l - 1;
        for (int i = 0; i <= a.length; ++i) {

            int end = (i == a.length) ? u + 1 : a[i];

            // System.out.print(begin + " " + end);

            if (begin + 1 < end) {
                res.add(getRange(begin + 1, end - 1));

                // System.out.print(" --> " + getRange(begin+1, end-1));
            }

            // System.out.println();

            begin = end;
        }

        return res;
    }

    public static String getRange(int begin, int end) {
        return (begin == end) ? ("" + begin) : (begin + "->" + end);
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 3, 50, 75};
        List<String> l = missingRanges(a, 0, 99);
        for (String s : l) {
            System.out.print(s + " ");
        }
    }
}
