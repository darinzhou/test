package com.comcast.algorithms;
import java.io.*;
import java.util.*;
import java.util.Stack;

public class BST2 {

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

    public static ArrayList<String> findAllCombinations(String s) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        helperFindAllCombinations(s, 0, sb, list);
        return list;
    }
    private static void helperFindAllCombinations(String s, int si, StringBuilder sb, ArrayList<String> list) {
        for (int i=si; i<s.length(); ++i) {
            sb.append(s.charAt(i));
            list.add(sb.toString());
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


    // public static int countps(String s) {
    //     if (s == null || s.length() == 0) {
    //         return 0;
    //     }

    //     return helperCountps(s, 0, s.length() - 1);
    // }
    // private static int helperCountps(String s, int i, int j) {
    //     if (i > j) {
    //         return 0;
    //     }
    //     if (i == j) {
    //         return 1;
    //     }

    //     if (s.charAt(i) == s.charAt(j)) {
    //         return helperCountps(s, i+1, j) + helperCountps(s, i, j-1) - helperCountps(s, i+1, j-1) + 1;
    //     }

    //     return helperCountps(s, i+1, j) + helperCountps(s, i, j-1) - helperCountps(s, i+1, j-1);
    // }

    // public static HashSet<String> allps(String s) {
    //     HashSet<String> set = new HashSet<>();
    //     if (s == null || s.length() == 0) {
    //         return set;
    //     }

    //     StringBuilder sb;
    //     helperAllps(s, set, 0, s.length() - 1, sb);
    //     return set;
    // }
    // private static void helperAllps(String s, HashSet<String> ps, int i, int j, StringBuilder sb) {
    //     if (i > j) {
    //         return;
    //     }

    //     if (s.charAt(i) == s.charAt(j)) {
    //         ps.add(s.substring(i, j+1));
    //     }

    //     helperAllps(s, ps, i+1, j);
    //     helperAllps(s, ps, i, j-1);
    // }

    public static TreeNode convertToCircularDoublyLinkedList(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        prev[0] = null;
        TreeNode[] head = new TreeNode[1];
        head[0] = null;

        convertToCircularDoublyLinkedList(root, prev, head);
        return head[0];
    }
    // inorder traverse
    private static void convertToCircularDoublyLinkedList(TreeNode root, TreeNode[] prev, TreeNode[] head) {
        if (root == null) {
            return;
        }

        // left
        convertToCircularDoublyLinkedList(root.left, prev, head);

        // root
        root.left = prev[0];
        if (prev[0] != null) {
            prev[0].right = root;
        } else {
            head[0] = root; // no prev, it's head
        }

        // save right
        TreeNode right = root.right;

        // circular head and root node
        head[0].left = root;
        root.right = head[0];

        // right
        prev[0] = root; // root is prev for right branch
        convertToCircularDoublyLinkedList(right, prev, head);
    }

    public static TreeNode findKthBigestNode(TreeNode root, int k) {
        int[] count = new int[1];
        count[0] = 0;
        TreeNode[] target = new TreeNode[1];
        target[0] = null;
        findKthBigestNode(root, k, count, target);
        return target[0];
    }
    public static void findKthBigestNode(TreeNode root, int k, int[] count, TreeNode[] target) {
        if (root == null) {
            return;
        }

        // right
        findKthBigestNode(root.right, k, count, target);

        // root
        count[0]++;
        if (count[0] == k) {
            target[0] =  root;
            return;
        }

        // left
        findKthBigestNode(root.left, k, count, target);
    }

    public static void main (String[] args) {
        int[] a = {1,9,5,7,21,15,17,13};

        TreeNode root = null;
        for (int i : a) {
            root = insert(root, new TreeNode(i));
        }

        TreeNode third = findKthBigestNode(root, 3);
        TreeNode cddll = convertToCircularDoublyLinkedList(root);

// 		printInOrder(root);

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

        ArrayList<String> li = findAllPalindromes("abbaalla");
        for (String s : li) {
            System.out.print(s +",");
        }

        System.out.println();

        ArrayList<String> li2 = findAllPalindromes2("abbaalla");
        for (String s : li2) {
            System.out.print(s +",");
        }

        String mp = findMaxLengthPalindrome("abbaalla");
        System.out.println();
        System.out.println(mp+ ": " + mp.length());

        System.out.println(findMaxLengthPalindromeRecursive("abbaalla"));

    }
}