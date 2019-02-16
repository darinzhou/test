package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class GroupShiftedStrings {

    /*
    Group Shifted Strings

    Given a string, we can "shift" each of its letter to its successive letter,
    for example: "abc" -> "bcd". We can keep "shifting" which forms the sequence:

    "abc" -> "bcd" -> ... -> "xyz"
    Given a list of strings which contains only lowercase alphabets, group all strings
    that belong to the same shifting sequence.

    For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
    A solution is:

    [
      ["abc","bcd","xyz"],
      ["az","ba"],
      ["acef"],
      ["a","z"]
    ]
    */

    public static List<List<String>> groupShiftedStrings(List<String> ss) {
        if (ss == null || ss.size() == 0) {
            return null;
        }

        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : ss) {
            String key = getPattern(s);
            if (map.containsKey(key)) {
                map.get(key).add(s);
            } else {
                List<String> l = new ArrayList<>();
                l.add(s);
                map.put(key, l);
            }
        }

        return new ArrayList<List<String>>(map.values());
    }

    public static String getPattern(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("0");

        for (int i = 1; i < s.length(); ++i) {
            int diff = s.charAt(i) - s.charAt(i - 1);
            if (diff < 0) {
                diff += 26;
            }
            sb.append(String.valueOf(diff));
        }

        return sb.toString();
    }


    public static void main(String[] args) {
        String[] ss = {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"};
        List<List<String>> ll = groupShiftedStrings(Arrays.asList(ss));
        for (List<String> l : ll) {
            for (String s : l) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}