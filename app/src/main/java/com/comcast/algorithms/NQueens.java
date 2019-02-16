package com.comcast.algorithms;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class NQueens {
        public List<List<String>> solveNQueens(int n) {

            List<char[][]> solutions= new ArrayList<>();
            char[][] board = new char[n][n];
            for (char[] row : board) {
                Arrays.fill(row, '.');
            }

            solve(board, 0, solutions);

            List<List<String>> sss = new ArrayList<>();
            for (char[][] a : solutions) {
                List<String> ss = new ArrayList<>();
                for (char[] row : a) {
                    String s = "";
                    for (char c : row) {
                        s += c;
                    }
                    ss.add(s);
                }
                sss.add(ss);
            }
            return sss;
        }

        private void solve(char[][] board, int col, List<char[][]> solutions) {
            if (col == board[0].length) {
                solutions.add(board);
                return;
            }

            // Consider this column and try placing
            // this queen in all rows one by one
            for (int row=0; row<board.length; ++row) {
                if (isSafe(board, row, col)) {
                    // cases put a queen at (row, col)
                    board[row][col] = 'Q';
                    solve(board, col+1, solutions);

                    // cases not put a queen at (row, col)
                    board[row][col] = '.';
                }
            }
        }

        private boolean isSafe(char[][] board, int row, int col) {
            int i, j;

            // check cols in row
            // only cols less than col has been set yet, so just need to check cols from 0 to col-1
            for (j = 0; j < col; ++j) {
                if (board[row][j] == 'Q') {
                    return false;
                }
            }

            // check diagonals
            for (i = row, j = col; i >=0 && j >= 0; i--, j--) {
                if (board[i][j] == 'Q') {
                    return false;
                }
            }
            for (i = row, j = col; i < board[0].length && j >= 0; i++, j--) {
                if (board[i][j] == 'Q') {
                    return false;
                }
            }

            return true;
        }
    }