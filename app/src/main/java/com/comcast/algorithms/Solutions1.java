package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.List;

public class Solutions1 {

        static class TrieNode{
            char c;
            String word;
            TrieNode[] children =  new TrieNode[26];
            TrieNode() {}
            TrieNode(char c) {
                this.c = c;
            }
        }

        static class Trie {
            private TrieNode root;
            public Trie() {
                root = new TrieNode();
            }
            public Trie(String[] words) {
                rebuildTrie(words);
            }

            public void rebuildTrie(String[] words) {
                root = new TrieNode();
                for (String w : words) {
                    addWord(w);
                }
            }

            public boolean addWord(String word) {
                if (root == null || word == null || word.length() == 0) {
                    return false;
                }
                TrieNode node = root;
                for (int i=0; i<word.length(); ++i) {
                    char c = word.charAt(i);
                    int idx = c - 'a';
                    if (node.children[idx] == null) {
                        node.children[idx] = new TrieNode(c);
                    }
                    node = node.children[idx];
                }
                node.word = word;
                return true;
            }

            public boolean matchWord(String word) {
                if (root == null || word == null || word.length() == 0) {
                    return false;
                }
                TrieNode node = root;
                for (int i=0; i<word.length(); ++i) {
                    char c = word.charAt(i);
                    int idx = c - 'a';
                    if (node.children[idx] == null) {
                        return false;
                    }
                    node = node.children[idx];
                }
                return word.equals(node.word);
            }

            public ArrayList<String> findWords(String[] strings) {
                ArrayList<String> list = new ArrayList<String>();
                for (String s : strings) {
                    if (matchWord(s)) {
                        list.add(s);
                    }
                }
                return list;
            }
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

        public static ArrayList<TreeNode> inOrderTraversalIterative(TreeNode root) {
            if (root == null) {
                return null;
            }

            ArrayList<TreeNode> list = new ArrayList<TreeNode>();
            Stack<TreeNode> s = new Stack<>();

            TreeNode node = root;
            while (node != null) {
                s.push(node);
                node = node.left;
            }

            while (!s.isEmpty()) {
                TreeNode top = s.pop();
                list.add(top);

                TreeNode right = top.right;
                while (right != null) {
                    s.push(right);
                    right = right.left;
                }
            }

            return list;
        }

        public static boolean isBST(TreeNode root) {
            TreeNode[] prev = new TreeNode[1];
            return helperIsBST(root, prev);
        }
        private static boolean helperIsBST(TreeNode root, TreeNode[] prev) {
            if (root == null) {
                return true;
            }

            if (!helperIsBST(root.left, prev))
                return false;

            if (prev[0] != null) {
                if (root.val <= prev[0].val) {
                    return false;
                }
            }

            prev[0] = root;
            return helperIsBST(root.right, prev);
        }

        public static TreeNode closestNode(TreeNode root, int target) {
            TreeNode[] goal = new TreeNode[1];
            helperClosestNode(root, target, goal);
            return goal[0];
        }
        private static void helperClosestNode(TreeNode root, int target, TreeNode[] goal) {
            if (root == null) {
                return;
            }

            helperClosestNode(root.left, target, goal);

            if (goal[0] != null) {
                if (Math.abs(goal[0].val - target) <= Math.abs(root.val - target)) {
                    return;
                }
            }

            goal[0] = root;
            helperClosestNode(root.right, target, goal);
        }

        public static ArrayList<TreeNode> closestKnodes(TreeNode root, int target, int k) {
            ArrayList<TreeNode> klist = new ArrayList<>();
            helperClosestKnodes(root, target, k, klist);
            return klist;
        }
        private static void helperClosestKnodes(TreeNode root, int target, int k, ArrayList<TreeNode> klist) {
            if (root == null) {
                return;
            }

            helperClosestKnodes(root.left, target, k, klist);

            if (klist.size() < k) {
                klist.add(root);
            } else {
                if (Math.abs(root.val - target) < Math.abs(target-klist.get(k-1).val)) {
                    // not found yet, keep updating klist
                    klist.remove(0);
                    klist.add(root);
                } else {
                    // found all in klist
                    return;
                }
            }

            helperClosestKnodes(root.right, target, k, klist);
        }

        /*
        Subsets

        Given a set of distinct integers, nums, return all possible subsets (the power set).
        Note: The solution set must not contain duplicate subsets.
        */
        public static ArrayList<ArrayList<Integer>> findAllSubsets(int[] a) {
            ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
            ArrayList<Integer> subset = new ArrayList<>();
            helperFindAllSubsets(a, 0, subset, allSubsets);
            return allSubsets;
        }
        private static void helperFindAllSubsets(int[] a, int startIndex,  ArrayList<Integer> subset, ArrayList<ArrayList<Integer>>allSubsets) {
            // add subset to allSubsets
            allSubsets.add(new ArrayList<Integer>(subset));

            for (int i=startIndex; i<a.length; ++i) {
                // find all subsets contains a[i]
                subset.add(a[i]);
                helperFindAllSubsets(a, i+1, subset, allSubsets);

                // find all subsets don't contains a[i]
                subset.remove(subset.size()-1);
            }
        }

        /*
        Subsets II

        Given a collection of integers that might contain duplicates, nums,
        return all possible subsets (the power set).
        Note: The solution set must not contain duplicate subsets.
        */
        public static ArrayList<ArrayList<Integer>> findAllSubsets2(int[] a) {
            ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
            ArrayList<Integer> subset = new ArrayList<>();

            // sort the source data
            Arrays.sort(a);

            helperFindAllSubsets2(a, 0, subset, allSubsets);
            return allSubsets;
        }
        private static void helperFindAllSubsets2(int[] a, int startIndex,  ArrayList<Integer> subset, ArrayList<ArrayList<Integer>>allSubsets) {
            // add subset to allSubsets
            allSubsets.add(new ArrayList<Integer>(subset));

            for (int i=startIndex; i<a.length; ++i) {
                // skip duplicated item
                if (i > startIndex && a[i] == a[i-1]) {
                    continue;
                }

                // find all subsets contains a[i]
                subset.add(a[i]);
                helperFindAllSubsets2(a, i+1, subset, allSubsets);

                // find all subsets don't contains a[i]
                subset.remove(subset.size()-1);
            }
        }

        /*
        Combination Sum

        Given a set of candidate numbers (C) (without duplicates) and a target number (T),
        find all unique combinations in C where the candidate numbers sums to T.

        The same repeated number may be chosen from C unlimited number of times.

        Note:
        All numbers (including target) will be positive integers.
        The solution set must not contain duplicate combinations.
        */
        public static ArrayList<ArrayList<Integer>> combinationSum(int[] a, int target) {
            ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
            ArrayList<Integer> subset = new ArrayList<>();
            helperCombinationSum(a, 0, target, subset, allSubsets);
            return allSubsets;
        }
        private static void helperCombinationSum(int[] a, int startIndex, int target, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>>allSubsets) {
            // check condition and add subset to allSubsets
            if (target == 0) {
                allSubsets.add(new ArrayList<Integer>(subset));
                return;
            }
            if (target < 0) {
                return;
            }

            for (int i=startIndex; i<a.length; ++i) {
                // find all subsets contains a[i]
                subset.add(a[i]);
                helperCombinationSum(a, i+1, target-a[i], subset, allSubsets);

                // find all subsets don't contains a[i]
                subset.remove(subset.size()-1);
            }
        }

        /*
        Combination Sum II

        Given a collection of integers that might contain duplicates, nums,
        return all possible subsets (the power set).

        Note: The solution set must not contain duplicate subsets.
        */
        public static ArrayList<ArrayList<Integer>> combinationSum2(int[] a, int target) {
            ArrayList<ArrayList<Integer>> allSubsets = new ArrayList<>();
            ArrayList<Integer> subset = new ArrayList<>();

            // sort
            Arrays.sort(a);

            helperCombinationSum2(a, 0, target-a[0], subset, allSubsets);
            return allSubsets;
        }
        private static void helperCombinationSum2(int[] a, int startIndex, int target, ArrayList<Integer> subset, ArrayList<ArrayList<Integer>>allSubsets) {
            // check condition and add subset to allSubsets
            if (target == 0) {
                allSubsets.add(new ArrayList<Integer>(subset));
                return;
            }
            if (target < 0) {
                return;
            }

            for (int i=startIndex; i<a.length; ++i) {
                // skip duplicates
                if (i > startIndex && a[i] == a[i-1]) {
                    continue;
                }

                // find all subsets contains a[i]
                subset.add(a[i]);
                helperCombinationSum2(a, i+1, target, subset, allSubsets);

                // find all subsets don't contains a[i]
                subset.remove(subset.size()-1);
            }
        }

        public static ArrayList<String> findAllCombinations(String s) {
            ArrayList<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            helperFindAllCombinations(s, 0, sb, list);
            return list;
        }
        private static void helperFindAllCombinations(String s, int si, StringBuilder sb, ArrayList<String> list) {
            list.add(sb.toString());

            for (int i=si; i<s.length(); ++i) {
                sb.append(s.charAt(i));
                helperFindAllCombinations(s, i+1, sb, list);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        public static ArrayList<String> findAllCombinations(String s, int combLen) {
            ArrayList<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            helperFindAllCombinations(s, combLen, 0, sb, list);
            return list;
        }
        private static void helperFindAllCombinations(String s, int cl, int si, StringBuilder sb, ArrayList<String> list) {
            for (int i=si; i<s.length(); ++i) {
                sb.append(s.charAt(i));
                if (sb.length() < cl) {
                    helperFindAllCombinations(s, cl, i+1, sb, list);
                } else {
                    list.add(sb.toString());
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        public static boolean isPalindrom(String s) {
            if (s == null) {
                return false;
            }
            return helperIsPalindrom(s, 0, s.length()-1);
        }
        private static boolean helperIsPalindrom(String s, int i, int j) {
            if (i >= j) {
                return true;
            }

            if (s.charAt(i) == s.charAt(j)) {
                return helperIsPalindrom(s, i+1, j-1);
            }
            return false;
        }

        public static boolean isPalindrom2(String s) {
            if (s == null) {
                return false;
            }
            int i = 0;
            int j = s.length() - 1;
            while (i<j) {
                if (s.charAt(i) != s.charAt(j)) {
                    return false;
                }
                i++;
                j--;
            }
            return true;
        }

        // brute force, Time: O(n^3), Space: O(1)
        public static ArrayList<String> findAllPalindromes(String str) {
            ArrayList<String> list = new ArrayList<>();
            for(int i=0;i<=str.length();i++) {
                for(int j=i;j<str.length();j++) {
                    if(isPalindrom2(str.substring(i,j+1))) {
                        list.add(str.substring(i,j+1));
                    }
                }
            }
            return list;
        }

        /* Each char in the string can be considered as the middle of a palindrome with odd length,
         *  while every two adjancent chars can be considered as the middle of palindrome with even length.
         *  Expand from middle of string to find all palindromes.
         *  Time O(n^2), Space: O(1)
         */
        public static  ArrayList<String> findAllPalindromes2(String s) {
            ArrayList<String> list = new ArrayList<>();
            if (s == null || s.length() == 0) {
                return list;
            }
            for (int i=0; i<s.length(); ++i) {
                // find all odd length palindromes by expanding from the middle s[i]
                expand(s, i, i, list) ;

                // find all even length palindromes by expanding from the middle s[i]s[i+1]
                expand(s, i, i+1, list);
            }

            return list;
        }
        private static void expand(String s, int l, int r, ArrayList<String> list) {
            int len = s.length();

            // loop while keep as palindroms by expanding from middle to left and right
            while (l >= 0 && r <len && s.charAt(l) == s.charAt(r)) {
                // save palindrome found
                list.add(s.substring(l, r+1));
                // expanding
                l--;  // <---- middle
                r++;  //       middle ---->
            }
        }

        /* Each char in the string can be considered as the middle of a palindrome with odd length,
         *  while every two adjancent chars can be considered as the middle of palindrome with even length.
         *  Expand from middle of string to find all palindromes.
         *  Time O(n^2), Space: O(1)
         */
        public static String findMaxLengthPalindrome(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            String maxp = "";
            int maxl = -1;
            String curp = "";
            int curl = 0;

            for (int i=0; i<s.length(); ++i) {
                // find all odd length palindromes by expanding from the middle s[i]
                curp = expand(s, i, i) ;
                curl = curp.length();
                // update max
                if (curl > maxl) {
                    maxp = curp;
                    maxl = curl;
                }

                // find all even length palindromes by expanding from the middle s[i]s[i+1]
                curp = expand(s, i, i+1) ;
                curl = curp.length();
                // update max
                if (curl > maxl) {
                    maxp = curp;
                    maxl = curl;
                }
            }

            return maxp;
        }
        private static String expand(String s, int l, int r) {
            int len = s.length();

            // loop while keep as palindroms by expanding from middle to left and right
            while (l >= 0 && r <len && s.charAt(l) == s.charAt(r)) {
                // expanding
                l--;  // <---- middle
                r++;  //       middle ---->
            }

            return s.substring(l+1, r);
        }

        public static int findMaxLengthPalindromeRecursive(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }
            return helperFindMaxLengthPalindromeRecursive(s, 0, s.length()-1);
        }
        private static int helperFindMaxLengthPalindromeRecursive(String s, int i, int j) {
            if (i>j) {
                return 0;
            }
            if (s.charAt(i) == s.charAt(j)) {
                return helperFindMaxLengthPalindromeRecursive(s, i+1, j-1) + 2;
            }
            return Math.max(helperFindMaxLengthPalindromeRecursive(s, i+1, j),
                    helperFindMaxLengthPalindromeRecursive(s, i, j-1));
        }


        // Find the second largest element in a Binary Search Tree
        public static TreeNode find2ndLargestNode(TreeNode root) {
            ArrayList<TreeNode> list = new ArrayList<>();
            inOrdertraversal(root, list, 2);
            return list.size() == 2 ? list.get(0) : null;
        }
        private static void inOrdertraversal(TreeNode root, ArrayList<TreeNode> list, int maxSize) {
            if (root == null) {
                return;
            }

            inOrdertraversal(root.left, list, 2);

            if (list.size() < maxSize) {
                list.add(root);
            } else {
                list.remove(0);
                list.add(root);
            }

            inOrdertraversal(root.right, list, 2);
        }

        // Describe a routine which returns the set of integers in {1..100} divisible without remainder by 3 but not by 9.
        public static void devidedBy3Not9() {
            int a[] = new int[100];
            for (int i=0; i<100; ++i) {
                a[i] = i+1;
            }

            for (int i=2; i<a.length; i+=3) {
                if (a[i]%9 != 0) {
                    System.out.print(a[i] + " ");
                }
            }
        }

        public static HashSet<Integer> findInfluencer(int[][] a) {
            HashSet<Integer> influencers = new HashSet<>();
            for (int i=0; i<a.length;++i) {
                influencers.add(i);
            }
            for (int i=0; i<a.length; ++i) {
                for (int j=0; j<a[0].length; ++j) {
                    if (influencers.contains(i) && i!=j && (a[i][j] == 0 || (inRange(j, i, a) &&  a[j][i] == 1))) {
                        influencers.remove(i);
                        break;
                    }
                }
            }
            return influencers;
        }
        private static boolean inRange(int i, int j, int[][] a) {
            return 0 <= i && i < a.length && 0 <= j && j < a[0].length;
        }

        public static String justify(String text, int maxWidth) {
            if (text == null || text.length() == 0) {
                return text;
            }

            // formlize
            text = text.trim();
            if (text.length() > maxWidth) {
                // text = moveLastWordToNextLineTilInRange(text, maxWidth);
            }

            // text formalized
            String[] words = text.split(" ");
            int nonSpaceCharNum = 0;
            for (String w : words) {
                nonSpaceCharNum +=w.length();
            }
            int totalSpaceNeeded = maxWidth - nonSpaceCharNum;
            int averageSpaceNumBetweenWords = totalSpaceNeeded/(words.length-1);
            int extraSpaceNum = totalSpaceNeeded - averageSpaceNumBetweenWords*(words.length-1);

            StringBuilder sb = new StringBuilder(words[0]);
            for (int i=1; i<words.length; ++i) {
                if (i <= extraSpaceNum) {
                    sb.append(" ");
                }
                for (int j=0; j<averageSpaceNumBetweenWords; ++j) {
                    sb.append(" ");
                }
                sb.append(words[i]);
            }
            return sb.toString();
        }

        public static int sqrt(int n) {
            if (n == 0 || n == 1) {
                return n;
            }
            int x = 1;
            int xx = 1;
            while (xx < n) {
                x++;
                xx = x * x;
            }

            if (xx == n) {
                return x;
            }
            return x-1;
        }

        public static double sqrt(double n) {
            double sqrt = n/2;
            double t = 0;
            do {
                t = sqrt;
                sqrt = (t + (n/t))/2;
            } while (t - sqrt != 0);
            return sqrt;
        }

        public static double pow(double n, int p) {
            if (n == 1 || p == 0) {
                return 1;
            }
            boolean isNegtive = p < 0;
            if (isNegtive) {
                p *= -1;
            }

            double v = n;
            for (int i=1; i<p; ++i) {
                v *= n;
            }

            if (isNegtive) {
                v = 1.0/v;
            }

            return v;
        }

        public static boolean isNumber(String s) {
            if (s == null || s.length() == 0) {
                return false;
            }

            if (!isValidFirstChar(s)) {
                return false;
            }
            int dotCount = 0;
            for (int i = 1; i<s.length(); ++i) {
                boolean checkDot = isDot(s.charAt(i));
                if (!isDigit(s.charAt(i)) && !checkDot) {
                    return false;
                }
                if (checkDot) {
                    dotCount++;
                    if (dotCount > 1) {
                        return false;
                    }
                }
            }
            return true;
        }
        private static boolean isDigit(char c) {
            return '0' <= c && c <= '9';
        }
        private static boolean isSign(char c) {
            return c=='+' || c == '-';
        }
        private static boolean isDot(char c) {
            return c=='.';
        }
        private static boolean isValidFirstChar(String s) {
            char first = s.charAt(0);
            if (isSign(first)) {
                if (s.length() < 2) {
                    return false;
                }
                char second = s.charAt(1);
                return isDigit(second);
            }
            return isDigit(first);
        }

        static class BlockingQueue<T> {
            private Queue<T> queue = new LinkedList<>();
            private int capacity = 16;

            public BlockingQueue() {
            }

            public BlockingQueue(int cap) {
                capacity = cap;
            }

            public int size() {
                return queue.size();
            }

            protected boolean isFull() {
                return size() == capacity;
            }
            protected boolean isEmpty() {
                return size() == 0;
            }

            public synchronized void put(T item) throws InterruptedException {
                while (isFull()) {
                    wait();
                }
                boolean empty = isEmpty();
                queue.add(item);
                if (empty) {
                    notifyAll();
                }
            }

            public synchronized T get() throws InterruptedException {
                while (isEmpty()) {
                    wait();
                }
                boolean full = isFull();
                T data = queue.remove();
                if (full) {
                    notifyAll();
                }
                return data;
            }
        }

        static class BSTIterator {
            private Stack<TreeNode> stack;

            public BSTIterator(TreeNode root) {
                stack = new Stack<>();
                pushNodeAndLeftChainInStack(root);
            }

            private void pushNodeAndLeftChainInStack(TreeNode node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public TreeNode next() {
                TreeNode node = stack.pop();

                // update stack for next smallest value on top
                if (node.right != null) {
                    pushNodeAndLeftChainInStack(node.right);
                }

                return node;
            }
        }

        public static void printFib(int n) {
            for (int i=0; i<n; ++i) {
                System.out.print(fib(i)+",");
            }
        }
        public static int fib(int n) {
            if (n < 2) {
                return n;
            }
            return fib(n-1) + fib(n-2);
        }

        static class Node {
            public int val;
            public Node next;

            public Node(int v) {
                val = v;
                next = null;
            }
        }

        public static Node reverse(Node head) {
            if (head == null) {
                return head;
            }

            Node prev = null;
            Node current = head;
            while (current != null) {
                Node next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
            return prev;
        }

        public static Node reverseRecursive(Node current, Node prev) {
            if (current == null) {
                return prev;
            }

            // reverse all nodes starting at current.next
            Node rest = reverseRecursive(current.next, current);

            // reverse current and prev
            current.next = prev;

            return rest;
        }

        public static void main (String[] args) {
            Node n1 = new Node(1);
            Node n2 = new Node(2);
            Node n3 = new Node(3);
            Node n4 = new Node(4);
            n1.next = n2;
            n2.next =n3;
            n3.next=n4;
            Node head =n1;

            Node current = n1;
            while (current != null){
                System.out.print(current.val+",");
                current=current.next;
            }

            System.out.println();

            Node n5 = reverse(n1);
            current=n5;
            while (current != null){
                System.out.print(current.val+",");
                current=current.next;
            }

            System.out.println();

            Node n6 = reverseRecursive(head, null);
            current=n6;
            while (current != null){
                System.out.print(current.val+",");
                current=current.next;
            }

            // printFib(20);

            // final BlockingQueue<Integer> bq = new BlockingQueue<>();
            // Thread t1 = new Thread (new Runnable() {
            //     public void run() {
            //         for (int i=0; i<600; ++i) {
            //             try {
            //             bq.put(i);
            //             } catch(Exception e) {}
            //         }
            //     }
            // });
            // Thread t2 = new Thread(new Runnable() {
            //     public void run() {
            //         for (int i=0; i<600; ++i) {
            //             try {
            //             int data = bq.get();
            //             System.out.print(data+",");
            //             } catch(Exception e) {}
            //         }
            //     }
            // });

            // t1.start();
            // t2.start();

            // Trie trie = new Trie(new String[] {"this", "is", "test"});
            // System.out.println(trie.matchWord("test"));
            // System.out.println(trie.matchWord("is"));
            // System.out.println(trie.matchWord("this"));
            // System.out.println(trie.matchWord("push"));

            // ArrayList<String> ss = trie.findWords(new String[] {"this", "a", "test"});
            //     for (String s : ss) {
            //         System.out.print(s +",");
            //     }

            // String t1 = "This is a test.";
            // String t2 = justify(t1, 16);
            // System.out.println(t1.length() + ":" + t1);
            // System.out.println(t2.length() + ":" + t2);

            //System.out.println(isNumber(".123"));
            //System.out.println(isNumber("-.123"));
            //System.out.println(isNumber("1.123"));
            //System.out.println(isNumber("+2.123"));
            //System.out.println(isNumber("+2.12.3"));
            //System.out.println(isNumber("+7123."));
            //System.out.println(isNumber("-07123."));

            // System.out.println(pow(11.1, 2));
            // System.out.println(pow(4.0, 3));

// 		int[] a = {15,9,5,7,21,13,17,1};

// 		TreeNode root = null;
// 		for (int i : a) {
// 		    root = insert(root, new TreeNode(i));
// 		}

//         BSTIterator bi = new BSTIterator(root);
//         while (bi.hasNext()) {
//             System.out.print(bi.next().val + ",");
//         }

// // 		printInOrder(root);
// //         System.out.println();

// //         System.out.println(find2ndLargestNode(root).val);

//         devidedBy3Not9();

//         int[][] ia = new int[][]{
//             {0,0,0,0,0,0,0,0,1,0},
//             {1,1,1,0,1,1,1,1,1,1},
//             {0,0,0,0,0,0,0,0,1,0},
//             {1,1,1,1,1,1,1,1,1,1},
//             {0,0,0,0,0,0,0,0,1,0}

//         };
// 		HashSet<Integer> set = findInfluencer(ia);
// 		for (int i : set) {
// 		    System.out.print(i + " ");
// 		}

// 		TreeNode n = search(root, new TreeNode(7));
// 		if (n != null) {
// 		    n.print();
// 		}

            // ArrayList<TreeNode> l = inOrderTraversalIterative(root);
            // for (TreeNode n : l) {
            //     System.out.print(n.val +",");
            // }

            // System.out.print(closestNode(root,8).val);

            // ArrayList<TreeNode> kl = closestKnodes(root, 21, 3);
            //  for (TreeNode n : kl) {
            //     System.out.print(n.val +",");
            // }

            // int c = countps("aaa");
            // System.out.println("--> "+ c);

            // HashSet<String> set = allps("abbaeae");
            // for (String s : set) {
            //     System.out.print(s +",");
            // }
            // ArrayList<String> list = findAllCombinations("abcd", 2);
            // for (String s : list) {
            //     System.out.print(s +",");
            // }

            // System.out.println(isPalindrom2(""));
            // System.out.println(isPalindrom2("a"));
            // System.out.println(isPalindrom2("ab"));
            // System.out.println(isPalindrom2("abba"));

            // ArrayList<String> li = findAllPalindromes("abbaalla");
            // for (String s : li) {
            //     System.out.print(s +",");
            // }

            //  System.out.println();

            // ArrayList<String> li2 = findAllPalindromes2("abbaalla");
            // for (String s : li2) {
            //     System.out.print(s +",");
            // }

            // String mp = findMaxLengthPalindrome("abbaalla");
            //  System.out.println();
            //  System.out.println(mp+ ": " + mp.length());

            //  System.out.println(findMaxLengthPalindromeRecursive("abbaalla"));

        }
    }