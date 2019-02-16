package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.Stack;

public class Solution5 {

    public static int evaluate(String s) {

        Stack<Character> ops = new Stack<>();
        Stack<Integer> vals = new Stack<>();

        int i = 0;
        int len = s.length();
        while (i < len) {
            char c = s.charAt(i++);
            if (c == '(') {
                ops.push(c);
            } else if (isNumber(c)) {
                vals.push(c - '0');
            } else if (isLowLevelOp(c)) {
                if (!ops.isEmpty()) {
                    char op = ops.peek();
                    if (isOperator(op)) {
                        op = ops.pop();
                        int v2 = vals.pop();
                        int v1 = vals.pop();
                        int v = operate(v1, op, v2);
                        vals.push(v);
                    }
                }
                ops.push(c);
            } else if (isHighLevelOp(c)) {
                if (!ops.isEmpty()) {
                    char op = ops.peek();
                    if (isHighLevelOp(op)) {
                        op = ops.pop();
                        int v2 = vals.pop();
                        int v1 = vals.pop();
                        int v = operate(v1, op, v2);
                        vals.push(v);
                    }
                }
                ops.push(c);
            } else if (c == ')') {
                char op = ops.pop();
                while (isOperator(op)) {
                    int v2 = vals.pop();
                    int v1 = vals.pop();
                    int v = operate(v1, op, v2);
                    vals.push(v);
                    op = ops.pop();
                }
            }
        }

        while (!ops.isEmpty()) {
            char op = ops.pop();
            int v2 = vals.pop();
            int v1 = vals.pop();
            int v = operate(v1, op, v2);
            vals.push(v);
        }

        return vals.pop();
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isLowLevelOp(char c) {
        return c == '+' || c == '-';
    }

    private static boolean isHighLevelOp(char c) {
        return c == '*' || c == '/';
    }

    private static boolean isOperator(char c) {
        return isLowLevelOp(c) || isHighLevelOp(c);
    }

    private static int operate(int v1, char op, int v2) {
        if (op == '+') {
            return v1 + v2;
        } else if (op == '-') {
            return v1 - v2;
        } else if (op == '*') {
            return v1 * v2;
        } else /*if (op == '/')*/ {
            return v1 / v2;
        }
    }

    static class Semphore {

        int count = 1;

        Semphore() {
        }

        Semphore(int count) {
            this.count = count;
        }

        synchronized void acquire() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            count--;
            notifyAll();
        }

        synchronized void release() throws InterruptedException {
            while (count > 0) {
                wait();
            }
            count++;
            notifyAll();
        }
    }

    static class Data2 {
        int data;
        Semphore semp = new Semphore(2);

        void put(int dat) throws InterruptedException {
            semp.acquire();
            data = dat;
        }

        int get() throws InterruptedException {
            semp.release();
            return data;
        }
    }

    static class Data {
        int data;
        boolean ready = false;

        synchronized void put(int dat) throws InterruptedException {
            while (ready) {
                wait();
            }
            data = dat;
            ready = true;
            notifyAll();
        }

        synchronized int get() throws InterruptedException {
            while (!ready) {
                wait();
            }
            int dat = data;
            ready = false;
            notifyAll();
            return dat;
        }
    }

    static Data2 data = new Data2();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    try {
                        data.put(i);
                    } catch (Exception e) {
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; ++i) {
                    try {
                        System.out.println(data.get());
                    } catch (Exception e) {
                    }
                }
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
        }


        // String[] ss = {"abc", "def", "ghi", "jkl", "mno"};
        // int i = Arrays.binarySearch(ss, "jkl");
        // System.out.println(i);
    }
}
