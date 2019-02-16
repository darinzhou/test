package com.comcast.algorithms;

import java.util.HashMap;

/*
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the empty string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

 */
public class MinWindowString {

        class Window {
            int begin;
            int end;
            boolean includedAll;
            HashMap<Character, Integer> map0;
            HashMap<Character, Integer> map;
            int counter;

            Window(HashMap<Character, Integer> map) {
                this.map0 = new HashMap<Character, Integer>(map);
                this.map = new HashMap<Character, Integer>(map);
                counter = map.size();
            }
            Window(Window other) {
                begin = other.begin;
                end = other.end;
                includedAll = other.includedAll;
                this.map0 = new HashMap<>(other.map0);
                this.map = new HashMap<>(other.map);
                counter = other.counter;
            }

            void append(String s, int index) {
                char c = s.charAt(index);
                if (map.containsKey(c)) {
                    int count = map.get(c)+1;
                    map.put(c, count);
                    if (count == 2*map0.get(c)) {
                        counter--;
                    }
                }
                end = index+1;
                if (counter == 0) {
                    includedAll = true;
                    shrinkLeft(s);
                }
            }
            void remove(char c, int index) {
                if (map.containsKey(c)) {
                    int count = map.get(c)-1;
                    map.put(c, count);
                    if (count == 2*map0.get(c)-1){
                        counter++;
                    }
                }
                begin = index+1;
                if (counter != 0) {
                    includedAll = false;
                }
            }

            void shrinkLeft(String s) {
                while (begin < end - 1) {
                    int l = begin;
                    char lc = s.charAt(begin);
                    if (map.containsKey(lc)) {
                        int count = map.get(lc);
                        if (count == 2 * map0.get(lc)) {
                            break;
                        } else {
                            remove(lc, begin);
                        }
                    } else {
                        begin++;
                    }
                }
            }

            int length() {
                return end-begin;
            }

            boolean lessThan(Window other) {
                if (other == null) {
                    return true;
                }
                return length() < other.length();
            }

            String substring(String s) {
                return s.substring(begin, end);
            }
        }

        HashMap<Character, Integer> buildMap(String t) {
            HashMap<Character, Integer> map = new HashMap<>();
            for (int i=0; i<t.length(); ++i) {
                if (!map.containsKey(t.charAt(i))) {
                    map.put(t.charAt(i), 1);
                } else {
                    map.put(t.charAt(i), map.get(t.charAt(i))+1);
                }
            }
            return map;
        }

        public String minWindow(String s, String t) {
            if (s == null || s.length() == 0 || t == null || t.length() == 0 || s.length() < t.length()) {
                return "";
            }

            HashMap<Character, Integer> map =  buildMap(t);
            Window wnd = new Window(map);
            Window minWnd = null;

            int begin = 0;
            int end = begin+1;
            while (end <= s.length()) {
                wnd.append(s, end-1);
                if (wnd.includedAll) {
                    if (wnd.lessThan(minWnd)) {
                        minWnd = new Window(wnd);
                    }
                }

                end++;
            }

            if (minWnd == null) {
                return "";
            }
            return minWnd.substring(s);
        }
    }