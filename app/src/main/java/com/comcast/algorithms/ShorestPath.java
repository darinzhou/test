package com.comcast.algorithms;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ShorestPath {

    static class Point {
        int x;
        int y;
        int dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        public boolean isValid(int m, int n) {
            if (x < 0 || y < 0 || x >= m || y >= n) {
                return false;
            }
            return true;
        }

        public boolean isPathPoint(int[][] grid) {
            return grid[x][y] == 1;
        }

        @Override
        public int hashCode() {
            return x*1000+y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            Point point = (Point)o;

            if (point == null) {
                return false;
            }

            return x == point.x && y == point.y;
        }
    }

    public static List<Point> bfs(int[][] grid, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        if (grid == null) {
            return path;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m == 0 || n == 0 || !start.isValid(m, n) || !end.isValid(m, n)) {
            return path;
        }

        boolean[][] vistied = new boolean[m][n];
        HashMap<Point, Point> prev = new HashMap<>();
        Queue<Point> queue = new LinkedList<Point>();

        Point current = start;
        current.dist = 0;
        vistied[current.x][current.y] = true;
        queue.add(current);
        prev.put(current, null);

        while (!queue.isEmpty()) {
            current = queue.remove();

            if (current.x == end.x && current.y == end.y) {
                break;
            }

            Point next = new Point(current.x - 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
                prev.put(next, current);
            }

            next = new Point(current.x + 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
                prev.put(next, current);
            }

            next = new Point(current.x, current.y - 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
                prev.put(next, current);
            }

            next = new Point(current.x, current.y + 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
                prev.put(next, current);
            }
        }

        if (current.x != end.x || current.y != end.y) {
            return path;
        }

        // build path
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public static int dfs(int[][] grid, Point start, Point end) {
        if (grid == null) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m == 0 || n == 0 || !start.isValid(m, n) || !end.isValid(m, n)) {
            return -1;
        }

        boolean[][] vistied = new boolean[m][n];
        return dfs(grid, start, end, vistied);
    }

    public static int dfs(int[][] grid, Point current, Point end, boolean[][] visited) {
        if (grid == null) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m == 0 || n == 0 || !current.isValid(m, n) || !end.isValid(m, n)) {
            return -1;
        }

        if (visited[current.x][current.y] || !current.isPathPoint(grid)) {
            return -1;
        }

        if (current.x == end.x && current.y == end.y) {
            return current.dist;
        }

        visited[current.x][current.y] = true;

        Point next = new Point(current.x - 1, current.y, current.dist + 1);
        int dist = dfs(grid, next, end, visited);
        if (dist != -1) {
            return dist;
        }

        next = new Point(current.x + 1, current.y, current.dist + 1);
        dist = dfs(grid, next, end, visited);
        if (dist != -1) {
            return dist;
        }

        next = new Point(current.x, current.y - 1, current.dist + 1);
        dist = dfs(grid, next, end, visited);
        if (dist != -1) {
            return dist;
        }
        next = new Point(current.x, current.y + 1, current.dist + 1);
        dist = dfs(grid, next, end, visited);
        if (dist != -1) {
            return dist;
        }

        return -1;
    }

    public static List<Point> dfs2(int[][] grid, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        if (grid == null) {
            return path;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m == 0 || n == 0 || !start.isValid(m, n) || !end.isValid(m, n)) {
            return path;
        }

        boolean[][] vistied = new boolean[m][n];
        HashMap<Point, Point> prev = new HashMap<>();
        Stack<Point> stack = new Stack<>();

        Point current = start;
        current.dist = 0;
        vistied[current.x][current.y] = true;
        stack.push(current);
        prev.put(current, null);

        while (!stack.isEmpty()) {
            current = stack.pop();
            if (current.x == end.x && current.y == end.y) {
                break;
            }

            Point next = new Point(current.x - 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                stack.push(next);
                prev.put(next, current);
            }
            next = new Point(current.x + 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                stack.push(next);
                prev.put(next, current);
            }
            next = new Point(current.x, current.y - 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                stack.push(next);
                prev.put(next, current);
            }
            next = new Point(current.x, current.y + 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                stack.push(next);
                prev.put(next, current);
            }
        }

        if (current.x != end.x || current.y != end.y) {
            return path;
        }

        // build path
        while (current != null) {
            path.add(current);
            current = prev.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] mat = new int[][]{
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 1, 0, 0, 1}
        };

        Point source = new Point(0, 0, 0);
        Point dest = new Point(3, 4, 0);

        List<Point> ps = bfs(mat, source, dest);

        Point source1 = new Point(0, 0, 0);
        Point dest1 = new Point(3, 4, 0);
        int pi = dfs(mat, source1, dest1);

        Point source2 = new Point(0, 0, 0);
        Point dest2 = new Point(3, 4, 0);
        List<Point> p = dfs2(mat, source2, dest2);

        int a = 0;

    }
}
