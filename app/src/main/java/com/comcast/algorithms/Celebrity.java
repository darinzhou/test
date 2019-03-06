package com.comcast.algorithms;

import java.util.Stack;

public class Celebrity {

    // return the celebrity's index or -1 if not celebrity
    public static int findCelebrity(int[][] m) {
        if (m == null || m.length == 0 || m.length != m[0].length) {
            return -1;
        }

        Stack<Integer> stack = new Stack<>();
        for (int i=0; i<m.length; ++i) {
            stack.push(i);
        }

        while (stack.size() > 1) {
            int i = stack.pop();
            int j = stack.pop();

            if (knows(m, i, j)) {
                // i knows j
                // i is not celerity, out of stack
                // j is possible, push into stack again
                stack.push(j);
            } else {
                // i doesn't know j
                // j is not celerity, out of stack
                // i is possible, push into stack again
                stack.push(i);
            }
        }

        // check the left one in stack
        int p  =stack.pop();

        // p the potential celebrity
        // if p knows anyone or any other person don't know p, then p is not celebrity
        for (int i = 0; i < m.length; ++i) {
            if ((p != i) && (knows(m, p, i) || !knows(m, i, p))) {
                return -1;
            }
        }

        return p;
    }

    private static boolean knows(int[][] m, int i, int j) {
        return m[i][j] == 1;
    }


    public static void main(String[] args) {

        //if i knows j then Matrix[i][j] = 1, else 0
        int[][] m = new int[][]{
                {0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0}
        };

        System.out.println(findCelebrity(m));
    }
}
