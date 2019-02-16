package com.comcast.algorithms;

public class Celebrity {

    public static int findCelebrity(int[][] m) {
        if (m == null || m.length == 0 || m.length != m[0].length) {
            return -1;
        }

        int i = 0;
        int j = m.length - 1;
        while (i < j) {
            if (knows(m, i, j)) {
                i++;
            } else {
                j--;
            }
        }

        // i the potential celebrity
        // if i knows anyone or any other person don't know i, then i is not celebrity
        for (int k = 0; k < m.length; ++k) {
            if ((k != i) && (knows(m, i, k) || !knows(m, k, i))) {
                return -1;
            }
        }

        return i;
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
