package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/9/15.
 */
/**
 * Given a nested list of integers, returns the sum of all integers in the list weighted by their depth
 * For example, given the list {{1,1},2,{1,1}} the function should return 10 (four 1's at depth 2, one 2 at depth 1)
 * Given the list {1,{4,{6}}} the function should return 27 (one 1 at depth 1, one 4 at depth 2, and one 6 at depth 3)
 */

import java.util.List;
import java.util.Stack;

public class NestedInteger {

    public int depthSum(List<INestedInteger> input) {

        if (input == null || input.size() == 0)
            return 0;

        return depthSumHelper(input, 1, 0);
    }

    private int depthSumHelper(List<INestedInteger> input, int level, int sum) {

        if (input == null || input.size() == 0)
            return sum;

        for (INestedInteger ni : input) {
            if (ni.isInteger())
                sum += ni.getInteger() * level;
            else {
                List<INestedInteger> l = ni.getList();
                sum += depthSumHelper(l, level+1, sum);
            }
        }

        return sum;
    }

    /**
     * Given a nested list of integers, returns the sum of all integers in the list weighted by their reversed depth.
     * For example, given the list {{1,1},2,{1,1}} the deepest level is 2. Thus the function should return 8
     * (four 1's with weight 1, one 2 with weight 2)
     * Given the list {1,{4,{6}}} the function should return 17 (one 1 with weight 3, one 4 with weight 2, and one 6 with weight 1)
     */
    public int depthSumReverse(List<INestedInteger> input) {
        if (input == null || input.size() == 0)
            return 0;
        int maxDepth = findDepth(input, 1);
        return depthSumReverseHelper(input, maxDepth, 1, 0);
    }

    public int depthSumReverseHelper(List<INestedInteger> input, int maxDepth, int level, int sum) {
        if (input == null || input.size() == 0)
            return sum;

        for (INestedInteger ni : input) {
            if (ni.isInteger())
                sum += (maxDepth-level+1) * ni.getInteger();
            else
                sum += depthSumReverseHelper(ni.getList(), maxDepth, level+1, sum);
        }

        return sum;
    }

    public int findDepth(List<INestedInteger> input, int level) {
        if (input == null || input.size() == 0)
            return level;

        int maxLevel = level;
        for (INestedInteger ni : input) {
            if (!ni.isInteger())
                maxLevel = Math.max(maxLevel, findDepth(ni.getList(), level + 1));
        }
        return maxLevel;
    }

    // level traversal

    public void printLevel(List<INestedInteger> input) {
        for (INestedInteger ni : input) {
            if (ni.isInteger())
                System.out.print(ni.getInteger());
            else
                printLevel(ni.getList());
            System.out.println();
        }
    }
}

/**
 * This is the interface that represents nested lists.
 * You should not implement it, or speculate about its implementation.
 */
interface INestedInteger
{
    /** @return true if this NestedInteger holds a single integer, rather than a nested list */
    boolean isInteger();

    /** @return the single integer that this NestedInteger holds, if it holds a single integer
     * Return null if this NestedInteger holds a nested list */
    Integer getInteger();

    /** @return the nested list that this NestedInteger holds, if it holds a nested list
     * Return null if this NestedInteger holds a single integer */
    List<INestedInteger> getList();
}
