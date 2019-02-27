package com.comcast.algorithms;

import android.support.annotation.NonNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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


    /*
        Given two input arrays, return true if the words array is sorted according to the ordering array
        Input:
        words = ['cc', 'cb', 'bb', 'ac']
        ordering = ['c', 'b', 'a']
        Output: True

        Input:
        words = ['cc', 'cb', 'bb', 'ac']
        ordering = ['b', 'c', 'a']
        Output: False
     */
    public static boolean isSorted(List<String> words, List<Character> order) {
        if (words == null || words.size() < 2 || order == null || order.size() == 0) {
            return true;
        }

        // build ap for order
        HashMap<Character, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < order.size(); ++i) {
            orderMap.put(order.get(i), i);
        }

        // build relationship between chars in words
//        HashMap<Character, HashSet<Character>> map = new HashMap<>();
        for (int i = 1; i < words.size(); ++i) {
            String w1 = words.get(i - 1);
            String w2 = words.get(i);
            int l = Math.min(w1.length(), w2.length());
            for (int j = 0; j < l; ++j) {
                char c1 = w1.charAt(j);
                char c2 = w2.charAt(j);

                // only the first different char matters
                if (c1 != c2) {
                    // check is c1 less than c2 for early false
                    if (orderMap.get(c1) >= orderMap.get(c2)) {
                        return false;
                    }
//
//                    if (map.containsKey(c1)) {
//                        HashSet<Character> cl = map.get(c1);
//                        cl.add(c2);
//                    } else {
//                        HashSet<Character> cl = new HashSet<>();
//                        cl.add(c2);
//                        map.put(c1, cl);
//                    }

                    break;
                }
            }
        }

//        // verify map with orderMap
//        for (Character c1 : map.keySet()) {
//            int ic1 = orderMap.get(c1);
//            for (Character c2 : map.get(c1)) {
//                int ic2 = orderMap.get(c2);
//                if (ic1 >= ic2) {
//                    return false;
//                }
//            }
//        }

        return true;
    }

    /*
    Define a class 'Space' which has a member string variable that indicates if the space is a "tree",
    a "house" or an empty space and another member variable that will store the 'space neighbors'
    (left, right, up and down only)

    Given a 'Grid' (list) of Spaces write the code for the findAll(start) method to find all the
    trees and houses given a 'Space' as start point

    Example, Grid of 'Spaces':

    T 0 0 H 0
    0 0 0 0 0
    H H T H 0

    Where Ts are trees and Hs are houses
     */

    static class Space {
        char type;
        int x;
        int y;
        String id;
        Space left;
        Space right;
        Space up;
        Space down;

        Space(int x, int y, char type) {
            this.x = x;
            this.y = y;
            this.type = type;
            id = x + "_" + y;
        }
    }

    public static List<Space> findAll(char[][] grid, Space start) {

        int m = grid.length;
        int n = grid[0].length;

        // build grap
        List<Space> graph = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] != '0') {
                    createSpace(grid, i, j, graph);
                }
            }
        }

        // bfs

        // result
        List<Space> result = new ArrayList<>();

        // find start
        for (Space s : graph) {
            if (start.x == s.x && start.y == s.y) {
                start = s;
                break;
            }
        }

        HashSet<String> visited = new HashSet<>();
        Queue<Space> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            Space space = queue.poll();
            visited.add(space.id);
            if (space.type == 'H' || space.type == 'T') {
                result.add(space);
            }

            Space ts = space.left;
            if (ts != null && visited.contains(ts.id)) {
                queue.offer(ts);
            }
            ts = space.up;
            if (ts != null && visited.contains(ts.id)) {
                queue.offer(ts);
            }
            ts = space.right;
            if (ts != null && visited.contains(ts.id)) {
                queue.offer(ts);
            }
            ts = space.down;
            if (ts != null && visited.contains(ts.id)) {
                queue.offer(ts);
            }

        }

        return result;
    }

    public static Space createSpace(char[][] grid, int i, int j, List<Space> graph) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return null;
        }

        Space space = new Space(i, j, grid[i][j]);
        space.left = createSpace(grid, i - 1, j, graph);
        space.right = createSpace(grid, i + 1, j, graph);
        space.up = createSpace(grid, i, j - 1, graph);
        space.down = createSpace(grid, i, j + 1, graph);
        graph.add(space);
        grid[i][j] = 0;

        return space;
    }

    public static int findIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++i) {
                if (grid[i][j] == 1) {
                    search(grid, i, j);
                    count++;
                }
            }
        }

        return count;
    }

    private static void search(int[][] grid, int i, int j) {
        if (i <= 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }

        if (grid[i][j] != 1) {
            return;
        }

        search(grid, i - 1, j);
        search(grid, i + 1, j);
        search(grid, i, j - 1);
        search(grid, i, j + 1);
    }

    // 0 - not checked, 1 - queen
    public static boolean nQueen(int[][] board, int n) {
        // start with 0 queen
        return check(board, n, 0);
    }

    // n - target, k - placed queen number
    private static boolean check(int[][] board, int n, int k) {
        if (n == k) {
            // succeed!
            return true;
        }

        // check each point
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                // check the first empty point
                if (board[i][j] == 0) {
                    // safe to place a queen here?
                    if (isSafe(board, i, j)) {
                        // it's safe to place a queen at (i,j)
                        board[i][j] = 1;

                        // recursively checking for placing more queens
                        if (check(board, n, k + 1)) {
                            // succeed!
                            return true;
                        }

                        // failed at (i, j), backtracking
                        board[i][j] = 0;
                    }
                }
            }
        }

        // failed!
        return false;
    }

    private static boolean isSafe(int[][] board, int ii, int jj) {
        int m = board.length;

        for (int i = 0; i < m; ++i) {
            if (i != ii && board[i][jj] == 1) {
                return false;
            }
        }
        for (int i = 0; i < m; ++i) {
            if (i != jj && board[ii][i] == 1) {
                return false;
            }
        }

        int i = ii;
        int j = jj;
        while (i > 0 && j > 0) {
            i--;
            j--;
            if (board[i][j] == 1) {
                return false;
            }
        }
        i = ii;
        j = jj;
        while (i < m - 1 && j > 0) {
            i++;
            j--;
            if (board[i][j] == 1) {
                return false;
            }
        }
        i = ii;
        j = jj;
        while (i < m - 1 && j < m - 1) {
            i++;
            j++;
            if (board[i][j] == 1) {
                return false;
            }
        }
        i = ii;
        j = jj;
        while (i > 0 && j < m - 1) {
            i--;
            j++;
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    public static void merge2SortedArraysToOne(int[] a, int al, int[] b) {
        if (a == null || b == null || al == 0 || b.length == 0 || a.length < al + b.length) {
            return;
        }

        // move all a elements to end of a
        int t = a.length - 1;
        for (int i = al - 1; i >= 0; --i) {
            a[t--] = a[i];
        }

        int i = a.length - al; // start of a elements
        int j = 0; // start of b elements
        int k = 0; // start of merged elements in a
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                a[k++] = a[i++];
            } else {
                a[k++] = b[j++];
            }
        }
        while (i < a.length) {
            a[k++] = a[i++];
        }
        while (j < b.length) {
            a[k++] = b[j++];
        }
    }

    /*
        Given an aray with ['a1', 'a2', .....'aN', 'b1', 'b2', ....'bN', 'c1', 'c2', .....'cN'],
        stagger the subarrays so it becomes ['a1', 'b1', 'c1', 'a2', 'b2', 'c2', ...'aN', 'bN', 'cN'].

        The optimal solution requires linear-time sorting and a constant space complexity.
     */
    public static void staggerArray(String[] a) {

        int n = a.length / 3;

        for (int i = 0; i < 3 * n; i += 3) {
            int k = i / 3;
            if (k * n < 3 * n) {
                swap(a, i, k * n);
            }
            if (n + k < 3 * n) {
                swap(a, i + 1, n + k);
            }
            if (2 * n + k < 3 * n) {
                swap(a, i + 2, 2 * n + k);
            }
        }
    }

    private static void swap(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /*
Given a non-empty string s, you may delete at most k characters. Judge whether you can make it a palindrome.
 */
    public static boolean makePalindromeByDeleting(String s, int k) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        int i = 0;
        int j = s.length() - 1;
        return makePalindromeByDeleting(s, k, i, j, 0);
    }

    // time complexity: O(n)
    private static boolean makePalindromeByDeleting(String s, int k, int left, int right, int deleted) {

        if (deleted > k) {
            return false;
        }

        if (left >= right) {
            return true;
        }

        if (s.charAt(left) == s.charAt(right)) {
            return makePalindromeByDeleting(s, k, left + 1, right - 1, deleted);
        }

        // delete left
        if (makePalindromeByDeleting(s, k, left + 1, right, deleted + 1)) {
            return true;
        }
        // delete right
        if (makePalindromeByDeleting(s, k, left, right - 1, deleted + 1)) {
            return true;
        }
        // delete both
        if (makePalindromeByDeleting(s, k, left + 1, right - 1, deleted + 2)) {
            return true;
        }

        return false;
    }

    /*
    Robot walked from the upper left to the lower right, can only go down and to the right,
    the number of each grid is height,
    If the next cell height is higher than the current, we must pay the difference cost,
    otherwise no cost,
    Find the minimum cost to reach the lower right corner,
    Follow up 1, print the minimum cost path;
     */
    static class Cell implements Comparable<Cell> {
        int x;
        int y;
        int cost;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.cost = 0;
        }

        public Cell(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        public boolean isValid(int m, int n) {
            return (x >= 0 && x < m && y >= 0 && y < n);
        }

        public static String getTag(int x, int y) {
            return x + "-" + y;
        }

        @Override
        public String toString() {
            return getTag(x, y);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(toString());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            Cell other = (Cell) obj;
            if (other == null) {
                return false;
            }

            return other.x == x && other.y == y;
        }


        @Override
        public int compareTo(@NonNull Cell cell) {
            return cost - cell.cost;
        }
    }

    public static List<Cell> minPath(int[][] grid, int x1, int y1, int x2, int y2) {
        List<Cell> path = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return path;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (x1 < 0 || x1 >= m || y1 < 0 || y1 >= n || x2 < 0 || x2 >= m || y2 < 0 || y2 >= n) {
            return path;
        }

        // build graph
        HashMap<String, Cell> graph = new HashMap<>();
        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
                Cell cell = new Cell(i, j, Integer.MAX_VALUE);
                graph.put(cell.toString(), cell);
            }
        }

        // bfs
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        HashMap<Cell, Cell> prev = new HashMap<>();

        Cell current = graph.get(Cell.getTag(x1, y1));
        current.cost = grid[x1][y1];
        pq.add(current);
        prev.put(current, null);

        while (!pq.isEmpty()) {
            current = pq.remove();
            if (current.x == x2 && current.y == y2) {
                break;
            }

            // right
            Cell right = graph.get(Cell.getTag(current.x + 1, current.y));
            if (right!=null && right.isValid(m, n)) {
                int newCost = current.cost + grid[right.x][right.y];
                if (newCost < right.cost) {
                    // found new path with lower cost
                    right.cost = newCost;
                    pq.add(right);
                    prev.put(right, current);
                }
            }

            // down
            Cell down = graph.get(Cell.getTag(current.x, current.y + 1));
            if (down!=null && down.isValid(m, n)) {
                int newCost = current.cost + grid[down.x][down.y];
                if (newCost < down.cost) {
                    // found new path with lower cost
                    down.cost = newCost;
                    pq.add(down);
                    prev.put(down, current);
                }
            }

            // righ-down
            // down
            Cell rightDown = graph.get(Cell.getTag(current.x + 1, current.y + 1));
            if (rightDown!=null && rightDown.isValid(m, n)) {
                int newCost = current.cost + grid[rightDown.x][rightDown.y];
                if (newCost < rightDown.cost) {
                    // found new path with lower cost
                    rightDown.cost = newCost;
                    pq.add(rightDown);
                    prev.put(rightDown, current);
                }
            }
        }

        // not found
        if (current.x != x2 || current.y != y2) {
            return path;
        }

        // build path in reverse order
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }

        // reverse list to get the path
        Collections.reverse(path);


        return path;
    }

    public static void main(String[] args) {

        int cost[][] = {
                {1, 2, 3},
                {4, 8, 2},
                {1, 5, 3}
        };

        List<Cell> p = minPath(cost, 0,0, 2,2);

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
