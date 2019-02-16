package com.comcast.algorithms;

import java.util.*;
import java.util.List;

public class PhoneKeyPadCombinations {
    public List<String> letterCombinations(String digits) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");
        map.put(9, "wxyz");
        map.put(0, "");

        StringBuilder one = new StringBuilder();
        List<String> all = new ArrayList<>();
        helper(map, digits, 0, one, all);

        return all;
    }

    public void helper(HashMap<Integer, String> map, String digits, int si, StringBuilder one, List<String> all) {
        if (si == digits.length()) {
            all.add(one.toString());
            return;
        }

        char c = digits.charAt(si);
        int digit = c - '0';
        String letters = map.get(digit);
        for (int i = 0; i < letters.length(); ++i) {
            one.append(letters.charAt(i));
            helper(map, digits, si + 1, one, all);
            one.deleteCharAt(one.length() - 1);
        }
    }
}