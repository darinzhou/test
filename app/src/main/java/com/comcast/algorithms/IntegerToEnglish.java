package com.comcast.algorithms;

import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class IntegerToEnglish {
    public static String integerToEnglish(int n) {
        Queue<Integer> digitsQueue = buildDigitsQueue(n);
        Stack<String> englishStack = buildEnglishStack(digitsQueue);
        return buildEnglishWords(englishStack);
    }

    private static Queue<Integer> buildDigitsQueue(int n) {
        Queue<Integer> stack = new LinkedList<>();
        while (n != 0) {
            stack.add(n % 10);
            n = n / 10;
        }
        return stack;
    }

    private static Stack<String> buildEnglishStack(Queue<Integer> digitsQueue) {

        HashMap<Integer, String> tensMap = new HashMap<>();
        tensMap.put(0, "");
        tensMap.put(1, "one");
        tensMap.put(11, "eleven");
        //...
        tensMap.put(99, "ninety-nine");

        Stack<String> stack = new Stack<>();

        while (!digitsQueue.isEmpty()) {
            int d1 = digitsQueue.remove();
            int d10 = 0;
            int d100 = 0;
            if (!digitsQueue.isEmpty()) {
                d10 = digitsQueue.remove();
            }
            if (!digitsQueue.isEmpty()) {
                d100 = digitsQueue.remove();
            }

            int tens = d10 * 10 + d1;

            String english = "";
            if (d100 == 0) {
                english = tensMap.get(tens);
            } else {
                english = tensMap.get(d100) + " hundred";

                if (tens != 0) {
                    english += " and " + tensMap.get(tens);
                }
            }

            stack.push(english);
        }

        return stack;
    }

    private static String buildEnglishWords(Stack<String> stack) {
        HashMap<Integer, String> unitsMap = new HashMap<>();
        unitsMap.put(0, "");
        unitsMap.put(1, "thousand,");
        unitsMap.put(2, "million,");
        unitsMap.put(3, "billion,");

        StringBuilder english = new StringBuilder();
        while (!stack.isEmpty()) {
            String words = stack.pop();
            if (!words.isEmpty()) {
                String unit = unitsMap.get(stack.size());
                english.append(words + " " + unit + " ");
            }
        }

        while (english.charAt(english.length()-1) == ' ' ||
                english.charAt(english.length()-1) == ',') {
            english.deleteCharAt(english.length()-1);
        }

        return english.toString();
    }

    public static void main(String[] args) {
        System.out.println(integerToEnglish(1));
        System.out.println(integerToEnglish(11));
        System.out.println(integerToEnglish(101));
        System.out.println(integerToEnglish(111));
        System.out.println(integerToEnglish(1000));
        System.out.println(integerToEnglish(1001));
        System.out.println(integerToEnglish(1111));
        System.out.println(integerToEnglish(11111));
        System.out.println(integerToEnglish(111111));
        System.out.println(integerToEnglish(1111111));
    }
}
