package com.comcast.algorithms;

public class PatternMatcher {

    public static boolean match(String word, String pattern) {

        String[] pps = pattern.split("\\*");
        for (int i=0; i<pps.length; ++i) {
            int idx = word.indexOf(pps[i]);
            if (idx == -1) {
                return false;
            }

            word = word.substring(idx + pps[i].length());
        }

        return true;
    }

    public static void main(String[] args) {
        boolean m = match("this a test", "i*a*es");
        boolean m2 = match("this a test", "*a*es");
        boolean m3 = match("this a test", "i*am*es");
        boolean m4 = match("this a test", "i*am*es");
    }
}
