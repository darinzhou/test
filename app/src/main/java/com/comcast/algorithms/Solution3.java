package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.List;

public class Solution3 {

        /*
        I. Contain Duplicate

        Given an array of integers, find if the array contains any duplicates.
        Your function should return true if any value appears at least twice in the array,
        and it should return false if every element is distinct.
        */
        public static boolean hasDuplicates(int[] a) {
            if (a == null || a.length == 0) {
                return false;
            }
            HashSet<Integer> set = new HashSet<>();
            for (int i=0; i<a.length; ++i) {
                if (set.contains(a[i])) {
                    return true;
                }
                set.add(a[i]);
            }
            return false;
        }

        /*
        II. Contain Nearby Duplicate (within k index difference)
        Given an array of integers and an integer k, return true if and only if there are
        two distinct indices i and j in the array such that nums[i] = nums[j] and the difference
        between i and j is at most k.
        */
        public static boolean hasDuplicates(int[] a, int k) {
            if (a == null || a.length == 0) {
                return false;
            }
            HashMap<Integer, Integer> map = new HashMap<>(); // <val, index>
            for (int i=0; i<a.length; ++i) {
                if (map.containsKey(a[i])) {
                    int lastIndex = map.get(a[i]);
                    if (i - lastIndex <= k) {
                        return true;
                    }
                }
                map.put(a[i], i);
            }
            return false;
        }

    /*
    III. Contain Nearby Almost Duplicate (within k index difference, within t value difference)
    Given an array of integers, find out whether there are two distinct indices i and j in the array
    such that the difference between nums[i] and nums[j] is at most t and the difference
    between i and j is at most k.
    */

        // brute force: O(k*n)
        public static boolean hasDuplicates(int[] a, int k, int t) {
            if (a == null || a.length == 0) {
                return false;
            }
            for (int i=0; i<a.length; ++i) {
                for (int j = 1; j<=k; ++j) {
                    if (Math.abs(a[i+j] - a[i]) <= t) {
                        return true;
                    }
                }
            }
            return false;
        }

        // using TreeSet: O(log(k)*n)
        public static boolean hasDuplicates2(int[] a, int k, int t) {
            if (a == null || a.length == 0) {
                return false;
            }
            TreeSet<Integer> set = new TreeSet<>();
            for (int i=0; i<a.length; ++i) {
                if ((set.floor(a[i]) != null && a[i] - set.floor(a[i]) <= t) ||
                        (set.ceiling(a[i]) != null && set.ceiling(a[i]) - a[i] <= t)) {
                    return true;
                } else {
                    set.add(a[i]);
                    if (i >= k) {
                        set.remove(a[i-k]);
                    }
                }
            }
            return false;
        }

        // combinations in a 2D array, each combination only contains one char from one row/col
        public static List<String> arraysCombinations(int[][] a) {
            List<String> all = new ArrayList<>();
            if (a == null || a.length == 0) {
                return all;
            }
            StringBuilder one = new StringBuilder();
            findCombinations(a, 0, one, all);
            return all;
        }
        private static void findCombinations(int[][] a, int rowIndex, StringBuilder one, List<String> all) {
            if (rowIndex == a.length) {
                all.add(one.toString());
                return;
            }

            // for each col in row
            for (int j=0; j<a[rowIndex].length; ++j) {
                one.append(a[rowIndex][j]);
                findCombinations(a, rowIndex+1, one, all);
                one.deleteCharAt(one.length()-1);
            }
        }

        public static List<String> listsCombinations(List<String> ss) {
            List<String> all = new ArrayList<>();
            if (ss == null || ss.size() == 0) {
                return all;
            }
            StringBuilder one = new StringBuilder();
            findCombinations(ss, 0, one, all);
            return all;
        }
        private static void findCombinations(List<String> ss, int stringIndex, StringBuilder one, List<String> all) {
            if (stringIndex == ss.size()) {
                all.add(one.toString());
                return;
            }

            // for each col in row
            for (int j=0; j<ss.get(stringIndex).length(); ++j) {
                one.append(ss.get(stringIndex).charAt(j));
                findCombinations(ss, stringIndex+1, one, all);
                one.deleteCharAt(one.length()-1);
            }
        }

        /**
         * A tournament tree is a binary tree
         * where the parent is the minimum of the two children.
         * Given a tournament tree find the second minimum value in the tree.
         * A node in the tree will always have 2 or 0 children.
         * Also all leaves will have distinct and unique values.
         * 2
         * / \
         * 2 3
         * / \ | \
         * 4 2 5 3
         *
         * In this given tree the answer is 3.
         */
        static class Node {
            Integer value;
            Node left, right;
            Node(Integer value, Node left, Node right) {
                this.value = value;
                this.left = left;
                this.right = right;
            }
        }
        public static Integer secondMin(Node root) {
            if (root == null) {
                return null;
            }
            int[] buf = new int[2];
            buf[0] = Integer.MAX_VALUE; // min
            buf[1] = Integer.MAX_VALUE; // second min
            secondMin(root, buf);
            return buf[1];
        }
        private static void secondMin(Node root, int[] buf) {
            if (root == null) {
                return;
            }

            if (root.value < buf[0]) {
                buf[0] = root.value; // update min
                if (root.left != null) {
                    // find larger child and update second min
                    if (root.left.value == root.value) {
                        if (root.right.value < buf[1]) {
                            buf[1] = root.right.value;
                        }
                    } else {
                        if (root.left.value < buf[1]) {
                            buf[1] = root.left.value;
                        }
                    }
                }
            } else if (root.value < buf[1] && root.value != buf[0]) {
                buf[1] = root.value; // update second min
            }

            // System.out.print(buf[0] + "," + buf[1] + ",");

            secondMin(root.left, buf);
            secondMin(root.right, buf);
        }

        interface NestedInteger {
            /** @return true if this NestedInteger holds a single integer, rather than a nested list */
            boolean isInteger();

            /** @return the single integer that this NestedInteger holds, if it holds a single integer
             * Return null if this NestedInteger holds a nested list */
            Integer getInteger();

            /** @return the nested list that this NestedInteger holds, if it holds a nested list
             * Return null if this NestedInteger holds a single integer */
            List<NestedInteger> getList();
        }
        /*
        Given a nested list of integers, return the sum of all integers in the list weighted
        by their depth.

        Each element is either an integer, or a list -- whose elements may also be integers
        or other lists.

        Example 1:
        Given the list [[1,1],2,[1,1]], return 10. (four 1's at depth 2, one 2 at depth 1)
        */
        public static int nestedIntegerSum(List<NestedInteger> nestedList) {
            return nestedIntegerSumHelper(nestedList, 1);
        }
        private static int nestedIntegerSumHelper(List<NestedInteger> nestedList, int depth) {
            if (nestedList == null || nestedList.size() == 0) {
                return 0;
            }
            int sum = 0;
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger()) {
                    sum += ni.getInteger() * depth;
                } else {
                    sum += nestedIntegerSumHelper(ni.getList(), depth+1);
                }
            }
            return sum;
        }

        /*
        Given a nested list of integers, return the sum of all integers in the list weighted by their depth.

        Each element is either an integer, or a list -- whose elements may also be integers or other lists.

        Different from the previous question where weight is increasing from root to leaf, now the weight is
        defined from bottom up. i.e., the leaf level integers have weight 1, and the root level integers
        have the largest weight.

        Example 1:
        Given the list [[1,1],2,[1,1]], return 8. (four 1's at depth 1, one 2 at depth 2)

        Example 2:
        Given the list [1,[4,[6]]], return 17. (one 1 at depth 3, one 4 at depth 2,
        and one 6 at depth 1; 1*3 + 4*2 + 6*1 = 17)
        */
        public static int nestedIntegerSumInvert(List<NestedInteger> nestedList) {
            if (nestedList == null || nestedList.size() == 0) {
                return 0;
            }

            // compute height

            Queue<NestedInteger> q = new LinkedList<>();
            int height = 0;
            int curLevelCount = 0;
            int nextLevelCount = 0;
            for (NestedInteger ni : nestedList) {
                q.add(ni);
                curLevelCount++;
            }

            while (!q.isEmpty()) {
                NestedInteger current = q.remove();
                curLevelCount--;

                if (!current.isInteger()) {
                    for (NestedInteger ni : current.getList()) {
                        q.add(ni);
                        nextLevelCount++;
                    }
                }

                if (curLevelCount == 0) { // end of current level
                    height++;
                    curLevelCount = nextLevelCount;
                    nextLevelCount = 0;
                }
            }

            // compute sum

            q.clear();
            int sum = 0;
            int weight = height;

            curLevelCount = 0;
            nextLevelCount = 0;
            for (NestedInteger ni : nestedList) {
                q.add(ni);
                curLevelCount++;
            }

            while (!q.isEmpty()) {
                NestedInteger current = q.remove();
                curLevelCount--;

                if (!current.isInteger()) {
                    for (NestedInteger ni : current.getList()) {
                        q.add(ni);
                        nextLevelCount++;
                    }
                } else {
                    sum += current.getInteger() * weight;
                }

                if (curLevelCount == 0) { // end of current level
                    weight--;
                    curLevelCount = nextLevelCount;
                    nextLevelCount = 0;
                }
            }

            return sum;
        }


        static class TreeNode {
            public int val;
            public TreeNode left;
            public TreeNode right;

            public TreeNode(int v) {
                val = v;
                left = null;
                right = null;
            }

            public void print() {
                System.out.println(val);
            }
        }

        public static TreeNode insert(TreeNode root, TreeNode node) {
            if (root == null) {
                return node;
            }
            if (node == null) {
                return root;
            }

            if (node.val < root.val) {
                root.left = insert(root.left, node);
            } else {
                root.right = insert(root.right, node);
            }

            return root;
        }

        public static void printInOrder(TreeNode root) {
            if (root == null) {
                return;
            }
            printInOrder(root.left);
            System.out.print(root.val + ",");
            printInOrder(root.right);
        }

        public static TreeNode search(TreeNode root, TreeNode target) {
            if (root == null || target == null) {
                return null;
            }

            if (target.val < root.val) {
                return search(root.left, target);
            } else if (target.val == root.val) {
                return root;
            }
            return search(root.right, target);
        }

        /*
        Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.

        Example:
        Given binary tree

                   1
                 / \
                2   3
               / \
              4   5
        Returns [4, 5, 3], [2], [1].
        */
        public static List<List<Integer>> findleaves(TreeNode root) {
            List<List<Integer>> list = new ArrayList<>();
            List<Integer> leaves = new ArrayList<>();

            while (root != null) {
                root = findleavesHelper(root, leaves, list);
            }
            return list;
        }
        public static TreeNode findleavesHelper(TreeNode root, List<Integer> leaves, List<List<Integer>> list) {

            if (root == null) {
                list.add(leaves);
                return null;
            }

            // find leave node, put in list and assign it to null to remove it
            if (root.left == null && root.right == null) {
                leaves.add(root.val);
                return null;
            }

            root.left = findleavesHelper(root.left, leaves, list);
            root.right = findleavesHelper(root.right, leaves, list);
            return root;
        }

        /*
        Give an array that has only the values 1, 2 or 3, sort the  array in place.
        */
        public static void sortOneTwoThree(int[] a) {
            if (a == null || a.length <= 1) {
                return;
            }
            int count1 = 0;
            int count2 = 0;
            // int count3 = 0;
            int i = 0;
            for (i=0; i<a.length; ++i) {
                if (a[i] == 1) {
                    count1++;
                } else if (a[i] == 2) {
                    count2++;
                }
                // else {
                //     count3++;
                // }
            }

            for (i = 0; i<count1; ++i) {
                a[i] = 1;
            }
            for (; i<count1+count2; ++i) {
                a[i] = 2;
            }
            for (; i<a.length/*count1+coun2+count3*/; ++i) {
                a[i] = 3;
            }
        }

        public static void sortOneTwoThree2(int[] a) {
            if (a == null || a.length <= 1) {
                return;
            }

            int i = 0;
            int j = a.length-1;
            while (i<j) {
                while (a[i] == 1) i++;
                while (a[j] != 1) j--;

                if (i < j) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    i++;
                    j--;
                }
            }

            i = 0;
            j = a.length-1;
            while (i<j) {
                while (a[i] != 3) i++;
                while (a[j] != 2) j--;

                if (i < j) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    i++;
                    j--;
                }
            }

        }

        // permute array
        public static List<int[]> permute(int[] a) {
            List<int[]> all = new ArrayList<>();
            if (a == null || a.length == 0) {
                return all;
            }
            permuteHelper(a, 0, all);
            return all;
        }
        private static void permuteHelper(int[] a, int si, List<int[]> all) {
            if (si == a.length) {
                all.add(a.clone());
                return;
            }
            for (int i = si; i<a.length; ++i) {
                swap(a, si, i);
                permuteHelper(a, si+1, all);
                swap(a, si, i);
            }
        }
        private static void swap(int[] a, int i, int j) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }

        // combinations array
        public static List<List<Integer>> combinations(int[] a) {
            List<List<Integer>> all = new ArrayList<>();
            if (a == null || a.length == 0) {
                return all;
            }
            List<Integer> one = new ArrayList<>();
            combinationsHelper(a, 0, one, all);
            return all;
        }
        private static void combinationsHelper(int[] a, int si, List<Integer> one, List<List<Integer>> all) {
            all.add(new ArrayList<Integer>(one));

            for (int i = si; i<a.length; ++i) {
                one.add(a[i]);
                combinationsHelper(a, i+1, one, all);
                one.remove(one.size()-1);
            }
        }

        // phone keypad combinations: conbinations in 2d array, elements from each row
        public static List<List<Integer>> comb2DA(int[][] a) {
            List<List<Integer>> all = new ArrayList<>();
            if (a == null || a.length == 0) {
                return all;
            }
            List<Integer> one = new ArrayList<>();
            comb2DAHelper(a, 0, one, all);
            return all;
        }
        private static void comb2DAHelper(int[][] a, int rowIndex, List<Integer> one, List<List<Integer>> all) {
            if (rowIndex == a.length) {
                all.add(new ArrayList<Integer>(one));
                return;
            }

            for (int i = 0; i<a[rowIndex].length; ++i) {
                one.add(a[rowIndex][i]);
                comb2DAHelper(a, rowIndex+1, one, all);
                one.remove(one.size()-1);
            }
        }


        public static void printList(List<String> l) {
            for (String s : l) {
                System.out.print(s + ", ");
            }
        }

        public static void deadLock() {
            final Object lock1 = new Object();
            final Object lock2 = new Object();

            Thread t1 =  new Thread(new Runnable() {
                public void run() {
                    synchronized (lock1) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        synchronized (lock2) {
                            System.out.println("Thread 1 moving on...");
                        }
                    }
                }
            });

            Thread t2 =  new Thread(new Runnable() {
                public void run() {
                    synchronized (lock2) {
                        synchronized (lock1) {
                            System.out.println("Thread 2 moving on...");
                        }
                    }
                }
            });

            t1.start();
            t2.start();
        }


        public static void mergeSort(int[] a) {
            if (a == null || a.length <= 1) {
                return;
            }

            int m = a.length/2;
            int[] a1 = new int[m];
            int[] a2 = new int[a.length-m];
            System.arraycopy(a, 0, a1, 0, m);
            System.arraycopy(a, m, a2, 0, a.length-m);

            mergeSort(a1);
            mergeSort(a2);

            merge(a, a1, a2);
        }
        private static void merge(int[] a, int[] a1, int[] a2) {
            int i = 0;
            int j = 0;
            int k = 0;

            while (i < a1.length && j < a2.length) {
                if (a1[i] < a2[j]) {
                    a[k] = a1[i];
                    i++;
                } else {
                    a[k] = a2[j];
                    j++;
                }
                k++;
            }

            for (; i<a1.length; ++i) {
                a[k] = a1[i];
                k++;
            }
            for (; j<a2.length; ++j) {
                a[k] = a2[j];
                k++;
            }

        }

        public static void qsort(int[] a, int l, int r) {
            if (a == null || a.length <= 1 || l >= r) {
                return;
            }
            System.out.print(l + "," + r + " ");
            int p = partition(a, l, r);
            qsort(a, l, p);
            qsort(a, p+1, r);
        }
        private static int partition(int[] a, int l, int r) {
            int p = l;
            while (l < r) {
                while (a[l] < a[p]) l++;
                while (a[r] > a[p]) r--;

                if (l < r) {
                    // swap a[l] and a[r]
                    int t = a[l];
                    a[l] = a[r];
                    a[r] = t;
                }
            }
            return r;
        }

        public static void main (String[] args) {

            int[] aa= new int[]{1,5,6,2,3,11,13 ,9,7};
            qsort(aa,0,aa.length-1);
            for (int i : aa) {
                System.out.print(i + ", ");
            }

            // deadLock();

            // int[] aa= new int[]{1,2,3};
            // List<int[]> l = permute(aa);
            //     for (int[]  a : l) {
            //         for (int i : a) {
            //             System.out.print(i + ", ");
            //         }
            //         System.out.println();
            //     }

            // int[][] aa= new int[][]{{1,2}, {3, 4}};
            // List<List<Integer>> l = comb2DA(aa);
            //     for (List<Integer> a : l) {
            //         for (int i : a) {
            //             System.out.print(i + ", ");
            //         }
            //         System.out.println();
            //     }



            // int[] a= new int[]{1,3,2,2,1,1,3,2,3,3,1,2};
            // sortOneTwoThree2(a);
            //for (int i : a) {
            //         System.out.print(i + ", ");
            //     }
            // TreeNode n1 = new TreeNode(1);
            // TreeNode n2 = new TreeNode(2);
            // TreeNode n3 = new TreeNode(3);
            // TreeNode n4 = new TreeNode(4);
            // TreeNode n5 = new TreeNode(5);

            // n2.left = n4;
            // n2.right = n5;
            // n1.left =n2;
            // n1.right = n3;

            // List<List<Integer>> l = findleaves(n1);
            // for (List<Integer> li : l) {
            //    for (int i : li) {
            //             System.out.print(i + ", ");
            //         }
            //         System.out.println();
            // }

            // Node n31 = new Node(4, null, null);
            // Node n32 = new Node(2, null, null);
            // Node n33 = new Node(5, null, null);
            // Node n34= new Node(3, null, null);
            // Node n21 = new Node(2, n31, n32);
            // Node n22 = new Node(3, n33, n34);
            // Node n11 = new Node(2, n21, n22);

            //  System.out.print(secondMin(n11));

// 	    int[][] a = new int[][] {{1,2}, {3, 4}};

// 	    List<String> all = arraysCombinations(a);
// 		printList(all);

// 		List<String> ss = new ArrayList<>();
// 		ss.add("ab");
// 		ss.add("cde");
// 	    List<String> all = listsCombinations(ss);
// 		printList(all);


        }
    }