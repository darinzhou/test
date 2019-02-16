package com.comcast.algorithms;

import java.io.*;
import java.util.*;

    /*
    # Given a time represented in the format "HH:MM",
    # form the next closest time by reusing the current digits.
    # There is no limit on how many times a digit can be reused.
    #
    # You may assume the given input string is always valid.
    # For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.
    #
    # Example 1:
    #
    # Input: "19:34"
    # Output: "19:39"
    # Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which
    # occurs 5 minutes later.
    # It is not 19:33, because this occurs 23 hours and 59 minutes later.
    #
    # Example 2:
    #
    # Input: "23:59"
    # Output: "22:22"
    # Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22.
    # It may be assumed that the returned time is next day's time since it is smaller than
    the input time numerically.
    */

public class NextClosestTime {

    /*
    Intuition and Algorithm

    Simulate the clock going forward by one minute. Each time it moves forward, if all the digits are
    allowed, then return the current time.

    The natural way to represent the time is as an integer t in the range 0 <= t < 24 * 60. Then the
    hours are t / 60, the minutes are t % 60, and each digit of the hours and minutes can be found by
    hours / 10, hours % 10 etc.
    */
    public static String nextClosestTime(String time) {
        if (time == null || time.length() == 0) {
            return null;
        }
        String[] parts = time.split(":");
        if (parts.length != 2) {
            return null;
        }
        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        Set<Integer> digits = new HashSet<>();
        for (int i = 0; i < time.length(); ++i) {
            char c = time.charAt(i);
            if (i != ':' && i != ' ') {
                digits.add(c - '0');
            }
        }

        int nextCloest = hour * 60 + min;

        while (true) {
            nextCloest = (nextCloest + 1) % (24 * 60);
            int nchour = nextCloest / 60;
            int ncmin = nextCloest % 60;

            int ncdigit1 = nchour / 10;
            int ncdigit2 = nchour % 10;
            int ncdigit3 = ncmin / 10;
            int ncdigit4 = ncmin % 10;

            if (ncdigit1 != 0 && !digits.contains(ncdigit1)) {
                continue;
            }

            if (ncdigit3 != 0 && !digits.contains(ncdigit3)) {
                continue;
            }

            if (!digits.contains(ncdigit2)) {
                continue;
            }

            if (!digits.contains(ncdigit4)) {
                continue;
            }

            return nchour + ":" + ncmin;
        }
    }


    public static void main(String[] args) {

        System.out.println(nextClosestTime("19:34"));
    }
}
