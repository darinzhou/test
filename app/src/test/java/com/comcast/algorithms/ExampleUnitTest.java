package com.comcast.algorithms;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testPrintOddEvenInDifThreads() {

        class Printer {
            private volatile boolean isOddJustPrinted = false;

            public synchronized void printOdd(int n) {
                if (isOddJustPrinted) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + ":" + n);

                isOddJustPrinted = true;
                notify();
            }

            public synchronized void printEven(int n) {
                if (!isOddJustPrinted) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + ":" + n);

                isOddJustPrinted = false;
                notify();
            }
        }

        class OddThread extends Thread {
            private Printer printer;
            private int max;

            public OddThread(Printer printer, int max, String name) {
                this.printer = printer;
                this.max = max;
                setName(name);
            }

            @Override
            public void run() {
                for (int i = 1; i <= max; i = i + 2) {
                    printer.printOdd(i);
                }
            }
        }

        class EvenThread extends Thread {
            private Printer printer;
            private int max;

            public EvenThread(Printer printer, int max, String name) {
                this.printer = printer;
                this.max = max;
                setName(name);
            }

            @Override
            public void run() {
                for (int i = 2; i <= max; i = i + 2) {
                    printer.printEven(i);
                }
            }
        }

        Printer printer = new Printer();
        int max = 10;
        OddThread oddThread = new OddThread(printer, max, "Odd");
        EvenThread evenThread = new EvenThread(printer, max, "Even");
        oddThread.start();
        evenThread.start();
    }

    @Test
    public void test_pattern() {
        String sp = "abc";
        String[] ss = new String[]{"cdf", "too", "hgfdt", "paa"};
        String p = getPattern(sp);
        for (String s : ss) {
            if (matchPattern(sp, p, s)) {
                System.out.println(s);
            }
        }

        String sp1 = "acc";
        String[] ss2 = new String[]{"cdf", "too", "hgfdt", "paa"};
        p = getPattern(sp1);
        for (String s : ss) {
            if (matchPattern(sp1, p, s)) {
                System.out.println(s);
            }
        }
    }

    public String getPattern(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int code = 0;

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);

            int cc;
            if (map.containsKey(c)) {
                cc = map.get(c);
            } else {
                cc = code++;
                map.put(c, cc);
            }

            sb.append("" + cc);
        }

        return sb.toString();
    }

    public boolean matchPattern(String s1, String pattern1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        String pattern2 = getPattern(s2);
        return pattern1.equals(pattern2);
    }

    @Test
    public void test_findSum3() {

    }

    public boolean findSum3(int[] a, int target) {
        Arrays.sort(a);

        for (int i = 0; i < a.length; ++i) {
            int l = i + 1;
            int r = a.length - 1;

            while (l < r) {
                int sum = a[i] + a[l] + a[r];
                if (sum == target) {
                    return true;
                } else if (sum < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return false;
    }

    public void findKthLargest(BST2.TreeNode root, int k) {
        int[] count = new int[1];
        BST2.TreeNode[] node = new BST2.TreeNode[1];
        findKthLargest(root, k, count, node);
    }

    public void findKthLargest(BST2.TreeNode root, int k, int[] count, BST2.TreeNode[] node) {
        if (root == null || count[0] >= k) {
            return;
        }

        findKthLargest(root.right, k, count, node);

        count[0]++;

        if (count[0] == k) {
            node[0] = root;
            return;
        }

        findKthLargest(root.left, k, count, node);

    }

    @Test
    public void test_word_compound() {
        String dictionary[] = {"mobile","samsung","sam","sung",
                "man","mango","icecream","and",
                "go","i","like","ice","cream"};
        String[] cw = compoundWords(dictionary);
        int a = cw.length;
    }
    public static String[] compoundWords(String[] data) {
        ArrayList<String> list = new ArrayList<>();
        for (String word : data) {
            if (isCompound(data, word)) {
                list.add(word);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    public static boolean isCompound(String[] data, String word) {
        return isCompound(data, word, false);
    }

    public static boolean isCompound(String[] data, String word, boolean separated) {
        if (data == null || word == null || word.trim().isEmpty()) {
            return false;
        }
        for (String str : data) {
            if (str.equals(word) && separated) {
                return true;
            }
            if (word.startsWith(str)) {
                String subword = word.substring(str.length());
                if (isCompound(data, subword, true)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void test_shortestExpression() {
        String exp = shortestExpression(26, 3);
        int a = 0;
    }
    public String shortestExpression(int target, int n) {
        class Op {
            public int valAfter;
            public String expression;
            public String cOp;
            public Op(int v, String exp, String c) {
                valAfter = v;
                expression = exp;
                cOp = c;
            }
        }

        HashSet<String> visited = new HashSet<>();
        LinkedList<Op> q = new LinkedList<>();

        q.add(new Op(n, ""+ n, "*"));
        visited.add("" + n);

        while (!q.isEmpty()) {
            Op curOp = q.remove();
            if (curOp.valAfter == target) {
                return curOp.expression;
            }

            // try +
            String nOp = "+";
            int val = curOp.valAfter + n;
            String visitedKey = curOp.valAfter + nOp + n;
            String expression = curOp.expression + nOp + n;
            if (!visited.contains(visitedKey)) {
                q.add(new Op(val, expression, nOp));
                visited.add(visitedKey);
            }

            // try -
            nOp = "-";
             val = curOp.valAfter - n;
             visitedKey = curOp.valAfter + nOp + n;
             expression = curOp.expression + nOp + n;
            if (!visited.contains(visitedKey)) {
                q.add(new Op(val, expression, nOp));
                visited.add(visitedKey);
            }

            // try *
            nOp = "*";
            if (curOp.cOp.equals("+")) {
                val = (curOp.valAfter - n) + n*n;
                visitedKey = (curOp.valAfter - n) + curOp.cOp + n*n;
            } else if (curOp.cOp.equals("-")) {
                val = (curOp.valAfter + n) - n*n;
                visitedKey = (curOp.valAfter + n) + curOp.cOp + n*n;
            } else {
                val = curOp.valAfter * n;
                visitedKey = curOp.valAfter + nOp + n;
            }
            expression = curOp.expression + nOp + n;
            if (!visited.contains(visitedKey)) {
                q.add(new Op(val, expression, nOp));
                visited.add(visitedKey);
            }

            // try /
            nOp = "/";
            if (curOp.cOp.equals("+")) {
                val = (curOp.valAfter - n) + 1;
                visitedKey = (curOp.valAfter + n) + curOp.cOp + 1;
            } else if (curOp.cOp.equals("-")) {
                val = (curOp.valAfter + n) - 1;
                visitedKey = (curOp.valAfter + n) + curOp.cOp + 1;
            } else {
                val = curOp.valAfter / n;
                visitedKey = curOp.valAfter + nOp + n;
            }
            visitedKey = curOp.valAfter + nOp + n;
            expression = curOp.expression + nOp + n;
            if (!visited.contains(visitedKey)) {
                q.add(new Op(val, expression, nOp));
                visited.add(visitedKey);
            }
        }

        return null;
    }

    
}