package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.List;

public class Solution2 {

    public static String reverse(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        return reverse(s.substring(1)) + s.charAt(0);
    }

    public static String reverse2(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        char[] a = stringToCharArray(s);
        reverseStringArray(a, 0, a.length - 1);
        return charArrayToString(a);
    }

    private static void reverseStringArray(char[] a, int i, int j) {
        if (i >= j) {
            return;
        }
        char temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        reverseStringArray(a, i + 1, j - 1);
    }

    private static char[] stringToCharArray(String s) {
        char[] a = new char[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            a[i] = s.charAt(i);
        }
        return a;
    }

    private static String charArrayToString(char[] a) {
        StringBuilder sb = new StringBuilder();
        for (char c : a) {
            sb.append(c);
        }
        return sb.toString();
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
        if (head == null || head.next == null) {
            return head;
        }

        Node second = head.next;
        head.next = null;

        Node rest = reverse(second);

        second.next = head;

        return rest;
    }

    public static Node reverse2(Node head) {
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

    public static Node getKthNodeFromEnd(Node head, int k) {
        Node fast = head;
        int i = 0;
        while (i < k && fast != null) {
            i++;
            fast = fast.next;
        }

        if (fast == null) {
            return null;
        }

        Node slow = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public static void reversePrint(Node head) {
        if (head == null) {
            return;
        }
        reversePrint(head.next);
        System.out.print(head.val + " ");
    }

    public static boolean hasLoop(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    /*
    Given array of elements {1,2,3,4} whose sum is equal to given positive integer (e.g 5) .
    Find the possible subsets (e.g {4,1}, {3,2} )
    */
    public static ArrayList<int[]> findSumPair(int[] a, int sum) {
        HashSet<Integer> wanted = new HashSet<>();
        ArrayList<int[]> pairs = new ArrayList<>();

        for (int i : a) {
            if (wanted.contains(i)) {
                int[] pair = new int[2];
                pair[0] = i;
                pair[1] = sum - i;
                pairs.add(pair);
            } else {
                wanted.add(sum - i);
            }
        }

        return pairs;
    }

    public static List<String> findAllCombinations(String s) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        findAllComb(s, 0, sb, list);
        return list;
    }

    private static void findAllComb(String s, int si, StringBuilder sb, List<String> list) {
        list.add(sb.toString());

        for (int i = si; i < s.length(); ++i) {
            sb.append(s.charAt(i));
            findAllComb(s, i + 1, sb, list);
            sb.deleteCharAt(sb.length() - 1);
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

    public static boolean isBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        TreeNode[] prev = new TreeNode[1];
        return isBST(root, prev);
    }

    private static boolean isBST(TreeNode root, TreeNode[] prev) {
        if (root == null) {
            return true;
        }

        if (!isBST(root.left, prev)) {
            return false;
        }

        if (prev[0] != null && prev[0].val >= root.val) {
            return false;
        }

        prev[0] = root;
        return isBST(root.right, prev);
    }

    public static boolean isBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        Stack<TreeNode> s = new Stack<TreeNode>();

        TreeNode prev = null;
        TreeNode current = root;

        while (!s.isEmpty() || current != null) {
            while (current != null) {
                s.push(current);
                current = current.left;
            }

            TreeNode top = s.pop();
            System.out.print(top.val + ",");

            if (prev != null && prev.val >= top.val) {
                return false;
            }

            prev = top;
            current = top.right;
        }

        return true;
    }

    public static List<String> allCombinations(int[] a) {
        List<String> all = new ArrayList<>();
        if (a == null || a.length == 0) {
            return all;
        }

        StringBuilder one = new StringBuilder();
        allCombinations(a, 0, one, all);

        return all;
    }

    private static void allCombinations(int[] a, int si, StringBuilder one, List<String> all) {
        all.add(one.toString());
        for (int i = si; i < a.length; ++i) {
            one.append("" + a[i]);
            allCombinations(a, i + 1, one, all);
            one.deleteCharAt(one.length() - 1);
        }
    }

    public static List<String> allCombinations(String s) {
        List<String> all = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return all;
        }
        StringBuilder one = new StringBuilder();
        allCombinations(s, 0, one, all);
        return all;
    }

    private static void allCombinations(String s, int si, StringBuilder one, List<String> all) {
        all.add(one.toString());
        for (int i = si; i < s.length(); ++i) {
            one.append(s.charAt(i));
            allCombinations(s, i + 1, one, all);
            one.deleteCharAt(one.length() - 1);
        }
    }

    public static List<String> allCombinations(String s, int k) {
        List<String> all = new ArrayList<>();
        if (s == null || s.length() == 0 || s.length() < k) {
            return all;
        }
        StringBuilder one = new StringBuilder();
        allCombinations(s, 0, k, one, all);
        return all;
    }

    private static void allCombinations(String s, int si, int k, StringBuilder one, List<String> all) {
        if (one.length() == k) {
            all.add(one.toString());
            return;
        }

        for (int i = si; i < s.length(); ++i) {
            one.append(s.charAt(i));
            allCombinations(s, i + 1, k, one, all);
            one.deleteCharAt(one.length() - 1);
        }
    }

    public static List<String> allCombinations2DA(char[][] a) {
        List<String> all = new ArrayList<>();
        if (a == null || a.length == 0) {
            return all;
        }

        StringBuilder one = new StringBuilder();
        allCombinations(a, 0, one, all);

        return all;
    }

    private static void allCombinations(char[][] a, int si, StringBuilder one, List<String> all) {
        all.add(one.toString());

        int size = a.length * a[0].length;
        for (int n = si; n < size; ++n) {
            int i = n / a[0].length;
            int j = n % a[0].length;
            one.append(a[i][j]);
            allCombinations(a, n + 1, one, all);
            one.deleteCharAt(one.length() - 1);
        }
    }

    public static List<String> allPermutations(String s) {
        List<String> all = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return all;
        }
        StringBuilder sb = new StringBuilder(s);
        allPermutations(sb, 0, s.length() - 1, all);
        return all;
    }

    private static void allPermutations(StringBuilder sb, int li, int ri, List<String> all) {
        if (li == ri) {
            all.add(sb.toString());
            return;
        }

        for (int i = li; i <= ri; i++) {
            swap(sb, li, i);
            allPermutations(sb, li + 1, ri, all);
            swap(sb, li, i);
        }
    }

    private static void swap(StringBuilder sb, int bi, int ei) {
        char temp = sb.charAt(bi);
        sb.setCharAt(bi, sb.charAt(ei));
        sb.setCharAt(ei, temp);
    }

    public static List<String> allPermutations(char[] a) {
        List<String> all = new ArrayList<>();
        if (a == null || a.length == 0) {
            return all;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : a) {
            sb.append(c);
        }
        allPermutations(sb, 0, a.length - 1, all);
        return all;
    }
    // private static void allPermutations(StringBuilder sb, int li, int ri, List<String> all) {
    //     if (li == ri) {
    //         all.add(sb.toString();
    //     }

    //     for (int i=li; i<ri; ++i) {
    //         swap(sb, li, i);
    //         allPermutations(sb, li+1, ri, all);
    //         swap(sb, li, i);
    //     }
    // }

    // not in sequence
    public static int maxSum(int[] a) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Invalid argument");
        }

        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;

        int[] cmax = new int[1];
        cmax[0] = 0;

        maxSum(a, 0, cmax, max);

        return max[0];
    }

    private static void maxSum(int[] a, int si, int[] cmax, int[] max) {
        if (cmax[0] > max[0]) {
            max[0] = cmax[0];
        }
        for (int i = si; i < a.length; ++i) {
            cmax[0] += a[i];
            maxSum(a, i + 1, cmax, max);
            cmax[0] -= a[i];
        }
    }

    // max sum by continous elements
    public static int[] maxSumByContinousElements(int[] a) {
        int[] res = new int[]{Integer.MIN_VALUE, -1, -1};
        if (a == null || a.length == 0) {
            return res;
        }
        res[1] = 0;
        res[2] = 0;
        int csum = 0;
        int cbegin = 0;
        int cend = 0;
        for (int i = 0; i < a.length; ++i) {
            csum += a[i];
            cend = i;

            if (csum > res[0]) {
                res[0] = csum;
                res[1] = cbegin;
                res[2] = cend;
            }

            if (csum < 0) {
                cbegin = i + 1;
                csum = 0;
            }
        }
        return res;
    }

    public static String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        char[] a = s.toCharArray();

        // remove ending separators
        int lastNotSepIndex = a.length - 1;
        while (isSep(a[lastNotSepIndex])) {
            lastNotSepIndex--;
        }

        // reverse words
        int wbp = 0;
        int wep = 0;
        boolean afterSep = true;
        for (int i = 0; i <= lastNotSepIndex; ++i) {
            if (isSep(a[i])) {
                if (!afterSep) {
                    wep = i - 1;
                    reverseString(a, wbp, wep);
                }
                afterSep = true;
                continue;
            }
            if (afterSep) {
                afterSep = false;
                wbp = i;
            }
        }

        // reverse last word
        reverseString(a, wbp, lastNotSepIndex);
        return new String(a);
    }

    private static boolean isSep(char c) {
        return c == ' ';
    }

    private static void reverseString(char[] a, int bi, int ei) {
        while (bi < ei) {
            char temp = a[bi];
            a[bi] = a[ei];
            a[ei] = temp;
            bi++;
            ei--;
        }
    }

    public static String reverseWordOrder(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        char[] a = s.toCharArray();
        reverseString(a, 0, a.length - 1);
        reverseWords(a);
        return new String(a);
    }

    private static void reverseWords(char[] a) {
        // remove ending separators
        int lastNotSepIndex = a.length - 1;
        while (isSep(a[lastNotSepIndex])) {
            lastNotSepIndex--;
        }

        // reverse words
        int wbp = 0;
        int wep = 0;
        boolean afterSep = true;
        for (int i = 0; i <= lastNotSepIndex; ++i) {
            if (isSep(a[i])) {
                if (!afterSep) {
                    wep = i - 1;
                    reverseString(a, wbp, wep);
                }
                afterSep = true;
                continue;
            }
            if (afterSep) {
                afterSep = false;
                wbp = i;
            }
        }

        // reverse last word
        reverseString(a, wbp, lastNotSepIndex);
    }


    class FileSystem {
        ArrayList<Drive> drives;
    }

    class Drive {
        ArrayList<Folder> folders;
        ArrayList<File> files;
    }

    interface FSEntity {
        // operations for both file and folder
        // create, delete, move, getInfo...
    }

    class File implements FSEntity {
        // file operations: read, write, seek
    }

    class Folder implements FSEntity {
        ArrayList<Folder> folders;
        ArrayList<File> files;
        // folder operations
    }


        /*
        Remove K Digits

        Given a non-negative integer num represented as a string, remove k digits from the number
        so that the new number is the smallest possible.
        Note:
        The length of num is less than 10002 and will be >= k.
        The given num does not contain any leading zero.
        Example 1:
        Input: num = "1432219", k = 3
        Output: "1219"
        Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
        Example 2:
        Input: num = "10200", k = 1
        Output: "200"
        Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
        Example 3:
        Input: num = "10", k = 2
        Output: "0"
        Explanation: Remove all the digits from the number and it is left with nothing which is 0.
        */

    public static String smallestAfterRemovedKDigits(String s, int k) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        int removed = 0;

        for (int i = s.length() - 1; i >= 0; --i) {
            stack.push(s.charAt(i));
        }

        while (!stack.isEmpty()) {
            char c = stack.pop();
            char next = stack.peek();

            if (c < next) {
                sb.append(c);
            } else {
                removed++;

                if (removed == k) {
                    break;
                }
            }
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        // remove beginning 0
        while (sb.charAt(0) == '0' && sb.length() > 2) {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    /*
            public static String removeKdigits(String s, int k) {
                if (s == null || s.length() == 0) {
                    return "0";
                }
                if (k <= 0) {
                    return "0";
                }
                int len = s.length();
                if (len <= k) {
                    return "0";
                }

                // matain smallest digit in stack
                Stack<Character> stack = new Stack<>();
                stack.push(s.charAt(0));
                int i = 1;
                while (i<len && k!=0) {
                    char top = stack.peek();
                    char c = s.charAt(i);
                    if (top >= c) {
                        stack.pop();
                        k--;
                    }
                    stack.push(c);
                    i++;
                }

                // put no processed chars in stack
                for (; i<len; ++i) {
                    stack.push(s.charAt(i));
                }

                // put all chars in stack to string builder, it is in reversed order
                StringBuilder sb = new StringBuilder();
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }

                // remove leading (now at end of string) 0
                int j = sb.length()-1;
                while (j >= 0 && sb.charAt(j) == '0') {
                    sb.deleteCharAt(j);
                    j--;
                }

                // reverse string
                i = 0;
                while (i < j) {
                    char temp = sb.charAt(j);
                    sb.setCharAt(j, sb.charAt(i));
                    sb.setCharAt(i, temp);
                    i++;
                    j--;
                }
                s = sb.toString();

                return s.length() == 0 ? "0" : s;
            }
    */
    // fibonacci with DP
    public static int fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n should not be negative");
        }
        if (n == 0 || n == 1) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 2] + dp[i - 1];
        }
        return dp[n];
    }


    /*
        facebook-interview-questions1
        of 1 vote
        13
        Answers
        Given a number, rearrange the digits of that number to make a higher number,
        among all such permutations that are greater,one of them is the smallest,
        Find the smallest greater permutation (the next Permutation).

        Examples:
        next_permutation (12) = 21
        next_permutation (315) = 351
        next_permutation (583) = 835
        next_permutation (12389) = 12398
        next_permutation (34722641) = 34724126
    */
    public static int next_ermutation(int n) {
        List<Integer> list = new ArrayList<Integer>();

        int nn = n;
        while (nn != 0) {
            int m = nn % 10;
            nn = nn / 10;
            list.add(m);
        }

        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            a[i] = list.get(i);
        }

        int[] smallest = new int[] {Integer.MAX_VALUE};
        recuisivePermute(a, n, 0, smallest);

        return smallest[0];
    }

    private static void recuisivePermute(int[] a, int n, int start, int[] smallest) {
        if (start == a.length-1) {
            int val = calucate(a);
            if (val > n) {
                if (val < smallest[0]) {
                    smallest[0] = val;
                }
            }
            return;
        }

        for (int i= start; i<a.length; ++i) {
            swap(a, start, i);
            recuisivePermute(a, n, start+1, smallest);
            swap(a, start, i);
        }

    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static int calucate(int[] a) {
        int v = 0;
        for (int i = 0; i < a.length; i++) {
            v = v * 10 + a[i];
        }

        return v;
    }

    public static int maxPermutation(int n) {
        int[] digitCounts = new int[10];
        int length = 0;

        while (n != 0) {
            int m = n % 10;
            n = n / 10;

            digitCounts[m]++;
            length++;
        }

        int val = 0;
        for (int i = 9; i >= 0 && length >= 0; --i) {
            if (digitCounts[i] > 0) {
                val += i * Math.pow(10, length - 1);

                digitCounts[i]--;
                length--;
            }
        }

        return val;
    }

    /*
    Given a string as input, return the list of all the patterns possible:

        '1' : ['A', 'B', 'C'],
        '2' : ['D', 'E'],
        '12' : ['X']
        '3' : ['P', 'Q']

        Example if input is '123', then output should be [ADP, ADQ, AEP, AEQ, BDP, BDQ, BEP, BEQ, CDP, CDQ, CEP, CEQ, XP, XQ]
     */
    public static List<String> allPatterns(String input, Map<String, List<Character>> map) {
        List<String> result = new ArrayList<>();
        if (input==null || input.isEmpty() || map == null || map.isEmpty()) {
            return result;
        }

        List<List<String>> inputSubstrings = allSubstringsCombinedAsSource(input);
        // remove substring combination not included in the map
        for (int i=0; i<inputSubstrings.size(); ++i) {
            for (String s : inputSubstrings.get(i)) {
                if (!map.containsKey(s)) {
                    inputSubstrings.remove(i);
                    i--;
                    break;
                }
            }
        }

        for (List<String> keyList : inputSubstrings) {
            int si = 0;
            StringBuilder one = new StringBuilder();
            allPatterns(map, keyList, si, one, result);
        }

        return result;
    }

    public static List<List<String>> allSubstringsCombinedAsSource(String input) {
        List<List<String>> result = new ArrayList<>();

        // iterate j, ja, jav, java
        for (int i = 0; i < input.length() - 1; i++) {
            String root = input.substring(0, i + 1);
            String leaf = input.substring(i + 1);
            List<List<String>> resultFromLeaf = allSubstringsCombinedAsSource(leaf);

            // combine root and each of substrings of leaf, and add to result
            for (List<String> strings : resultFromLeaf) {
                List<String> one = new ArrayList<>();
                one.add(root);
                one.addAll(strings);
                result.add(one);
            }
        }

        // add the whole string as one of the leaves
        List<String> one = new ArrayList<>();
        one.add(input);
        result.add(one);

        return result;
    }

    private static void allPatterns(Map<String, List<Character>> map,
                                    List<String> keyList, int si, StringBuilder one, List<String> result) {
        if (si == keyList.size()) {
            result.add(one.toString());
            return;
        }

        List<Character> letters = map.get(keyList.get(si));
        for (int i=0; i<letters.size(); ++i) {
            one.append(letters.get(i));
            allPatterns(map, keyList, si+1, one, result);
            one.deleteCharAt(one.length()-1);
        }
    }

    public static void main(String[] args) {

        Map<String, List<Character>> map = new HashMap<>();

        List<Character> l1 = Arrays.asList(new Character[] {'A', 'B', 'C'});
        List<Character> l2 = Arrays.asList(new Character[] {'D', 'E'});
        List<Character> l3 = Arrays.asList(new Character[] {'X'});
        List<Character> l4 = Arrays.asList(new Character[] {'P', 'Q'});

        map.put("1", l1);
        map.put("2", l2);
        map.put("12", l3);
        map.put("3", l4);

        List<String> aps = allPatterns("123", map);

        List<List<String>> ss = allSubstringsCombinedAsSource("java");

        int n = next_ermutation(34722641);

        int v = maxPermutation(12389);

        String s = smallestAfterRemovedKDigits("10200", 1);

        int[] res = maxSumByContinousElements(new int[]{-2, -3, 4, -1, -2, 1, 5, -3});
        System.out.println(res[0] + "," + res[1] + "," + res[2]);

        // System.out.println(fib(0));
        // System.out.println(fib(1));
        // System.out.println(fib(2));
        // System.out.println(fib(3));
        // System.out.println(fib(4));
        // System.out.println(fib(5));


        // System.out.println(removeKdigits("10",3));

        // int[] a = {1,-1,4,2,-5,6};
        // System.out.println(maxSum(a));


// 	    int[] a = {1,9,5,7,21,15,17,13};

//         TreeNode root = null;
//         for (int i : a) {
//             root = insert(root, new TreeNode(i));
//         }

// 		printInOrder(root);

// 		System.out.println(isBST(root));
// 		System.out.print(isBST2(root));

// allCombinations("abc");

        // List<String> all = allCombinations("abcd", 3);
        // for (String s:all) {
        //     System.out.println(s);
        // }

        // Node n1 = new Node(1);
        // Node n2 = new Node(2);
        // Node n3 = new Node(3);
        // Node n4 = new Node(4);
        // n1.next = n2;
        // n2.next =n3;
        // n3.next=n4;
        // n4.next = n3;
        // Node head =n1;

        // System.out.println(hasLoop(head));

        // Node current = n1;
        // while (current != null){
        //     System.out.print(current.val+",");
        //     current=current.next;
        // }

        // System.out.println();

        // Node n5 = reverse2(n1);
        // current=n5;
        // while (current != null){
        //     System.out.print(current.val+",");
        //     current=current.next;
        // }
        // System.out.println();
        // System.out.println(getKthNodeFromEnd(head, 2).val);

        // int[] a = new int[] {1,2,3,4,7,6};
        // ArrayList<int[]> pairs = findSumPair(a, 8);
        // for (int[] p : pairs) {
        //     System.out.println(p[0] + ","+ p[1]);
        // }

        // List<String> list = findAllCombinations("abc");
        // for (String s : list) {
        //     System.out.println(s);
        // }

        // System.out.println((8<<1));
    }


}