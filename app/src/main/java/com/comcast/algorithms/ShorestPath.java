package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


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
    }

    public static int bfs(int[][] grid, Point start, Point end) {
        if (grid == null) {
            return -1;
        }
        int m = grid.length;
        int n = grid[0].length;
        if (m == 0 || n == 0 || !start.isValid(m, n) || !end.isValid(m, n)) {
            return -1;
        }

        boolean[][] vistied = new boolean[m][n];
        Queue<Point> queue = new LinkedList<Point>();

        start.dist = 0;
        vistied[start.x][start.y] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.remove();

            if (current.x == end.x && current.y == end.y) {
                return current.dist;
            }

            Point next = new Point(current.x - 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
            }

            next = new Point(current.x + 1, current.y, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
            }

            next = new Point(current.x, current.y - 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
            }

            next = new Point(current.x, current.y + 1, current.dist + 1);
            if (next.isValid(m, n) && !vistied[next.x][next.y] && next.isPathPoint(grid)) {
                vistied[next.x][next.y] = true;
                queue.add(next);
            }
        }

        return -1;
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

        int p = dfs(mat, source, dest);

        int a = 0;

    }
}
