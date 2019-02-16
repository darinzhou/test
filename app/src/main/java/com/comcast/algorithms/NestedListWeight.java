package com.comcast.algorithms;

import java.io.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

/*
Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:
Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
 */
public class NestedListWeight {

    // This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public static int sum(List<NestedInteger> nilist) {
        return helper(nilist, 1);
    }

    public static int helper(List<NestedInteger> nilist, int depth) {
        int sum = 0;

        if (nilist == null || nilist.size() == 0) {
            return sum;
        }

        for (NestedInteger ni : nilist) {
            if (ni.isInteger()) {
                sum += ni.getInteger() * depth;
            } else {
                sum += helper(ni.getList(), depth + 1);
            }
        }

        return sum;
    }

    public static int sumIterative(List<NestedInteger> nilist) {
        int sum = 0;
        Queue<NestedInteger> qni = new LinkedList<>();
        Queue<Integer> qdepth = new LinkedList<>();

        // level 1
        for (NestedInteger ni : nilist) {
            qni.add(ni);
            qdepth.add(1);
        }

        while (!qni.isEmpty()) {
            NestedInteger ni = qni.remove();
            int deepth = qdepth.remove();

            if (ni.isInteger()) {
                sum += ni.getInteger() * deepth;
            } else {
                for (NestedInteger niItem : ni.getList()) {
                    qni.add(niItem);
                    qdepth.add(deepth + 1);
                }
            }
        }

        return sum;
    }

    public static int sumIterative2(List<NestedInteger> nilist) {
        int sum = 0;
        Queue<NestedInteger> qni = new LinkedList<>();
        int curLevelCnt = 0;
        int nextLevelCnt = 0;

        // level 1
        for (NestedInteger ni : nilist) {
            qni.add(ni);
            curLevelCnt++;
        }
        int deepth = 1;

        while (!qni.isEmpty()) {
            NestedInteger ni = qni.remove();
            curLevelCnt--;

            if (ni.isInteger()) {
                sum += ni.getInteger() * deepth;
            } else {
                for (NestedInteger niItem : ni.getList()) {
                    qni.add(niItem);
                    nextLevelCnt++;
                }
            }

            if (curLevelCnt == 0) {
                deepth++;
                curLevelCnt = nextLevelCnt;
                nextLevelCnt = 0;
            }
        }

        return sum;
    }

/*
Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Different from the previous question where weight is increasing from root to leaf, now the weight is defined from bottom up. i.e.,
the leaf level integers have weight 1, and the root level integers have the largest weight.

Example 1:
Given the list [[1,1],2,[1,1]], return 8. (four 1's at depth 1, one 2 at depth 2)

Example 2:
Given the list [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2, and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17)*/
    public static int sum2Iterative(List<NestedInteger> nilist) {
        int sum = 0;
        Queue<NestedInteger> qni = new LinkedList<>();
        int curLevelCnt = 0;
        int nextLevelCnt = 0;
        int level = 0;

        // count level

        // level 1
        for (NestedInteger ni : nilist) {
            qni.add(ni);
            curLevelCnt++;
        }
        // loop while quue is not empty
        while (!qni.isEmpty()) {
            NestedInteger ni = qni.remove();
            curLevelCnt--;

            if (!ni.isInteger()) {
                for (NestedInteger niItem : ni.getList()) {
                    qni.add(niItem);
                    nextLevelCnt++;
                }
            }

            if (curLevelCnt == 0) {
                level++;
                curLevelCnt = nextLevelCnt;
                nextLevelCnt = 0;
            }
        }

        // sum

        int levelNum = level;
        curLevelCnt = 0;
        nextLevelCnt = 0;
        level = 0;

        // level 1
        for (NestedInteger ni : nilist) {
            qni.add(ni);
            curLevelCnt++;
        }
        // loop with queue is not empty
        while (!qni.isEmpty()) {
            NestedInteger ni = qni.remove();
            curLevelCnt--;
            int deepth = levelNum - level;

            if (ni.isInteger()) {
                sum += ni.getInteger() * deepth;
            } else {
                for (NestedInteger niItem : ni.getList()) {
                    qni.add(niItem);
                    nextLevelCnt++;
                }
            }

            if (curLevelCnt == 0) {
                level++;
                curLevelCnt = nextLevelCnt;
                nextLevelCnt = 0;
            }
        }

        return sum;
    }

}