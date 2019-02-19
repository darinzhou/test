package com.comcast.algorithms;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public static final String TAG = "TestFCA";

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
/*
    public void testAddOne() throws Exception {
        int[] a = {1, 4, 2, 1};
        int[] b = {1, 4, 2, 9};
        int[] c = {1, 4, 9, 9};
        int[] d = {1, 9, 2, 9};
        int[] e = {9, 9, 9, 9};

        int[] ra = Algorithms.addOne(a);
        Log.d(TAG, Arrays.toString(a) + " -> " + Arrays.toString(ra));

        int[] rb = Algorithms.addOne(b);
        Log.d(TAG, Arrays.toString(b) + " -> " + Arrays.toString(rb));
        int[] rc = Algorithms.addOne(c);
        Log.d(TAG, Arrays.toString(b) + " -> " + Arrays.toString(rc));
        int[] rd = Algorithms.addOne(d);
        Log.d(TAG, Arrays.toString(d) + " -> " + Arrays.toString(rd));
        int[] re = Algorithms.addOne(e);
        Log.d(TAG, Arrays.toString(e) + " -> " + Arrays.toString(re));

        assertTrue(true);
    }

    public void testMaxDifInArray() throws Exception {
        int[] a = {9, 8, 1, 2, 3, 0};
        int[] b = {9, 8, 1, 2, 3, 0};

        Algorithms.MaxDif maxd1 = Algorithms.getMaxDifInArray(a);
        Log.d(TAG, Arrays.toString(b) + " - getMaxDifInArray -> " + maxd1);

        Algorithms.MaxDif maxd2 = Algorithms.getMaxDifInArrayForward(b);
        Log.d(TAG, Arrays.toString(b) + " - getMaxDifInArrayForward -> " + maxd2);

        assertTrue(true);
    }

    public void testCalcAllProductsExceptAtIndex() throws Exception {
        int[] c = {1, 4, 9, 9};
        String array = Arrays.toString(c);

        int[] apei1 = Algorithms.calcAllProductsExceptAtIndex(c);
        Log.d(TAG, array + " - calcAllProductsExceptAtIndex -> " + Arrays.toString(apei1));

        int[] apei2 = Algorithms.calcAllProductsExceptAtIndexWithDivision(c);
        Log.d(TAG, array + " - calcAllProductsExceptAtIndexWithDivision -> " + Arrays.toString(apei2));

        Algorithms.calcAllProductsExceptAtIndexWithRecursive(c, 1, 0);
        Log.d(TAG, array + " - calcAllProductsExceptAtIndexWithRecursive -> " + Arrays.toString(c));

        assertTrue(true);
    }

    public void testCalcHighestProductOf3() throws Exception {
        int[] aa = {-1, 9, -5, -6, 4, 4, 2, 3};
        String array = Arrays.toString(aa);

        int h1 = Algorithms.calcHighestProductOf3(aa);
        Log.d(TAG, array + " - calcHighestProductOf3 -> " + h1);
        int h2 = Algorithms.calcHighestProductOf3WithSort(aa);
        Log.d(TAG, array + " - calcHighestProductOf3 -> " + h2);

        assertTrue(true);
    }

    public void testCalcChangePossibilities() throws Exception {
        int[] a = {1, 2, 3};
        String array = Arrays.toString(a);
        int n = 6;

        int np = Algorithms.calcChangePossibilities(n, a);
        Log.d(TAG, n + " with " + array + " - calcChangePossibilities -> " + np);

        HashMap<Integer, Integer> usedDenominations = new HashMap<Integer, Integer>();
        ArrayList<HashMap<Integer, Integer>> changePossibilities = new ArrayList<HashMap<Integer, Integer>>();
        Algorithms.calcChangePossibilities(n, a, usedDenominations, changePossibilities);
        Log.d(TAG, n + " with " + array + " - calcChangePossibilitiesRecursive -> ");

        assertTrue(true);
    }

    public void testRotate() throws Exception {
        char[] a = {'1', '2', '3', '4', '5', '6'};
        String array = Arrays.toString(a);
        int order = 3;

        Algorithms.rotate(a, order);
        Log.d(TAG, array + " - rotate with order of " + order + " -> " + Arrays.toString(a));

        assertTrue(true);
    }

    public void testReverseWord() throws Exception {
        String s = "this is a test";

        String ss = Algorithms.reverseWordsInString(s);
        Log.d(TAG, s + " - reverseWordsInSentence -> " + ss);

        String sss = Algorithms.reverseWordOrder(s);
        Log.d(TAG, s + " - reverseWordOrder -> " + sss);

        assertTrue(true);
    }

    public void testEvaluateRPN() throws Exception {
        String[] expr = {"4", "13", "5", "/", "+"};

        int v = Algorithms.evaluateRPN(expr);
        Log.d(TAG, Arrays.toString(expr) + " - evaluateRPN -> " + v);

        assertTrue(true);
    }

    public void testIsIsomorphic() throws Exception {
        String s1 = "fofofo";
        String s2 = "gdgdgd";
        String s3 = "gfgdgd";

        boolean b1 = Algorithms.isIsomorphic(s1, s2);
        Log.d(TAG, s1 + ", " + s2 + " - isIsomorphic -> " + b1);
        boolean b2 = Algorithms.isIsomorphic(s1, s3);
        Log.d(TAG, s1 + ", " + s3 + " - isIsomorphic -> " + b2);

        assertTrue(true);
    }

    public void testWordLadder() throws Exception {
        // dict = ["hot","dot","dog","lot","log"] result 5;
        HashSet<String> dict = new HashSet<String>();
        dict.add("hot");
        dict.add("dot");
        dict.add("dog");
        dict.add("lot");
        dict.add("log");
        String start = "hit";
        String end = "cog";

//        dict.add("stone");
//        dict.add("store");
//        dict.add("shore");
//        dict.add("chore");
//        dict.add("choke");
//        dict.add("choky");
//        dict.add("cooky");
//        dict.add("cooey");
//        dict.add("coney");
//        dict.add("money");
//        String start = "stone";
//        String end = "money";

        LinkedList<Graph.Node> worderLadder = Graph.wordLadder(dict, start, end);
        Log.d(TAG, start + "->" + end + " - wordLadder -> " + worderLadder.size());

        HashMap<Graph.Node, LinkedList<Graph.Node>> dijkstra = Graph.shorestPathWithDijkstra(dict, start, end);
        Log.d(TAG, start + "->" + end + " - shorestPathWithDijkstra -> " + dijkstra.size());

        assertTrue(true);
    }

    public void testKthSmallestInSortedArrays() throws Exception {
        int[] a = {1, 5, 9, 12, 15};
        int[] b = {2, 3, 7, 8, 13, 20, 30, 44};
        int k1 = 2;
        int k2 = 5;
        int k3 = 8;

        int e1 = Algorithms.kthSmallestInSortedArrays(a, 0, 4, b, 0, 4, k1);
        Log.d(TAG, Arrays.toString(a) + " + " + Arrays.toString(b) + " (" + k1 + ") - kthSmallestInSortedArrays -> " + e1);

        int e2 = Algorithms.kthSmallestInSortedArrays(a, 0, 4, b, 0, 4, k2);
        Log.d(TAG, Arrays.toString(a) + " + " + Arrays.toString(b) + " (" + k2 + ") - kthSmallestInSortedArrays -> " + e2);

        int e3 = Algorithms.kthSmallestInSortedArrays(a, 0, 4, b, 0, 4, k3);
        Log.d(TAG, Arrays.toString(a) + " + " + Arrays.toString(b) + " (" + k3 + ") - kthSmallestInSortedArrays -> " + e3);

        int m = Algorithms.medianInSortedArrays(a, b);
        Log.d(TAG, Arrays.toString(a) + " + " + Arrays.toString(b) + " - medianInSortedArrays -> " + m);

        assertTrue(true);
    }

    public void testIsWildcardMatch() throws Exception {
        String s = "abcdeg";
        String p1 = "?b*eg";
        String p2 = "*b?d*";
        String p3 = "*bd*";
        String p4 = "ab*e";
        String p5 = "ab*";
        String p6 = "abcdeg*";
        String p7 = "abcdeg?";

        boolean b1 = Algorithms.isWildcardMatch(s, p1);
        boolean b2 = Algorithms.isWildcardMatch(s, p2);
        boolean b3 = Algorithms.isWildcardMatch(s, p3);
        boolean b4 = Algorithms.isWildcardMatch(s, p4);
        boolean b5 = Algorithms.isWildcardMatch(s, p5);
        boolean b6 = Algorithms.isWildcardMatch(s, p6);
        boolean b7 = Algorithms.isWildcardMatch(s, p7);

        assertTrue(true);
    }

    public void testMergeIntervals() throws Exception {

        Algorithms.Interval i1 = new Algorithms.Interval(1, 3);
        Algorithms.Interval i2 = new Algorithms.Interval(2, 6);
        Algorithms.Interval i3 = new Algorithms.Interval(8, 10);
        Algorithms.Interval i4 = new Algorithms.Interval(15, 18);

        ArrayList<Algorithms.Interval> intervals = new ArrayList<>();
        intervals.add(i1);
        intervals.add(i3);
        intervals.add(i2);
        intervals.add(i4);

        ArrayList<Algorithms.Interval> mergedIntervals = Algorithms.mergeIntervals(intervals);

        Algorithms.Interval ii1 = new Algorithms.Interval(1, 2);
        Algorithms.Interval ii2 = new Algorithms.Interval(3, 5);
        Algorithms.Interval ii3 = new Algorithms.Interval(6, 7);
        Algorithms.Interval ii4 = new Algorithms.Interval(8, 10);
        Algorithms.Interval ii5 = new Algorithms.Interval(12, 16);
        Algorithms.Interval ni = new Algorithms.Interval(4, 9);

        ArrayList<Algorithms.Interval> intervals2 = new ArrayList<>();
        intervals2.add(ii1);
        intervals2.add(ii2);
        intervals2.add(ii3);
        intervals2.add(ii4);
        intervals2.add(ii5);
        ArrayList<Algorithms.Interval> insertedIntervals = Algorithms.insertInterval(intervals2, ni);

        assertTrue(true);
    }

    public void testTwoSum() throws Exception {
        int[] a = {1, 5, 3, 9, 11, 4, 2};
        int sum = 7;

        int[] result = Algorithms.twoSum(a, sum);

        Arrays.sort(a);
        int[] result2 = Algorithms.twoSumInSortedArray(a, sum);

        assertTrue(true);
    }

    public void testThreeSum() throws Exception {
        int[] a = {-1, 0, 1, 2, -1, -4, -3};
        int sum = 0;

        int[] res = Algorithms.threeSum(a, sum);

        ArrayList<int[]> result = Algorithms.threeSumInSortedArray(a, sum);

        ArrayList<int[]> result1 = Algorithms.fourSumInSortedArray(a, sum);

        assertTrue(true);
    }

    public void testAtoi() throws Exception {
        String s1 = "-9123";
        String s2 = "9123";
        String s3 = "y9123";
        String s4 = "91y23";

        int i1 = Algorithms.atoi(s1);
        int i2 = Algorithms.atoi(s2);
        try {
            int i3 = Algorithms.atoi(s3);
        } catch (Exception e) {
            String m = e.getMessage();
        }
        try {
            int i4 = Algorithms.atoi(s4);
        } catch (Exception e) {
            String m = e.getMessage();
        }

        assertTrue(true);
    }

    public void testMergeTwoSortedArrays() throws Exception {
        int[] a = {1, 3, 6,8,9};
        int[] b = {1, 2, 3,5,7,10};

        int[] c = Algorithms.mergeTwoSortedArrays(a, b);

        assertTrue(true);
    }

    public void testValidBrackets() throws Exception {
        String s1 = "()[]{}";
        String s2 = "([)]{}";
        String s3 = "{()[]{}}";
        String s4 = "{()[]{})";
        String s5 = "{()[]{}}]";
        String s6 = "{{()[]{}}";

        boolean b1 = Algorithms.validBrackets(s1);
        boolean b2 = Algorithms.validBrackets(s2);
        boolean b3 = Algorithms.validBrackets(s3);
        boolean b4 = Algorithms.validBrackets(s4);
        boolean b5 = Algorithms.validBrackets(s5);
        boolean b6 = Algorithms.validBrackets(s6);

        assertTrue(true);
    }

    public void testLongestValidParenttheses() throws Exception {
        String s1 = ")(()";
        String s2 = "(()()())";
        String s3 = ")()())()()()(()()()()())";

        int nn1 = Algorithms.longestValidParentheses(s1);
        int nn2 = Algorithms.longestValidParentheses(s2);
        int nn3 = Algorithms.longestValidParentheses(s3);

        assertTrue(true);
    }

    public void testDSbasedOnNode() throws Exception {

        Stack s = new Stack();
        Node n = new Node(1);
        s.push(n);
        Node n1 = s.pop();

        if (n.equals(n1)) {
            Log.d(TAG, "equals");
        }

        if (n == n1) {
            Log.d(TAG, "the same");
        }

        n1.setData(-1000);

        com.comcast.algorithms.LinkedList ll = new com.comcast.algorithms.LinkedList();

        try {
            ll.remove();
        } catch (Exception e) {
            String m = e.getMessage();
        }

        for (int i=0; i<4; ++i)
            ll.add(i);

        ll.reverse();

//        Node rn = com.comcast.algorithms.LinkedList.reverseWithRecursive(ll.get(0), null);

        try {
            ll.add(-100, -1);
        } catch (Exception e) {
            String m = e.getMessage();
        }

        ll.add(-1, 2);
        ll.add(-2, 10);

        ll.remove();
        ll.remove(0);
        ll.remove(2);

        Queue q = new Queue();
        q.enqueue(new Node(1));
        q.enqueue(new Node(2));
        q.enqueue(new Node(3));
        q.enqueue(new Node(4));

        int size = q.size();

        Node node1 = q.dequeue();
        size = q.size();

        Node node2 = q.dequeue();
        size = q.size();

        assertTrue(true);
    }

    public void testFileIO() throws Exception {

        File externalStorageDir = Environment.getExternalStorageDirectory();
        File myFile = new File(externalStorageDir , "test.dat");

        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(myFile, true);
            dos = new DataOutputStream(fos);

            dos.writeInt(300);
            dos.writeInt(Integer.MIN_VALUE);
            dos.writeInt((501));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null)
                dos.close();
            if (fos != null)
                fos.close();
        }

        FileInputStream fis = null;
        DataInputStream dis = null;

        int[] data = new int[1024];
        try {
            fis = new FileInputStream(myFile);
            dis = new DataInputStream(fis);

            int i = 0;
            while (dis.available() > 0) {
                data[i++] = dis.readInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dis != null)
                dis.close();
            if (fis != null)
                fis.close();
        }

        for (int i=0; i<10; ++i) {
            if (data[i] == Integer.MIN_VALUE)
                Log.d(TAG, "---------------minimun integer------------");
        }

        assertTrue(true);
    }

    */

//    public void testTree() throws Exception {
////        TreeNode root = new TreeNode(4);
////        TreeNode left = new TreeNode(2);
////        TreeNode right = new TreeNode(5);
////
////        TreeNode leftLeft = new TreeNode(1);
////        TreeNode leftRight = new TreeNode(3);
//
////        TreeNode rightLeft = new TreeNode(6);
////        TreeNode rightRight = new TreeNode(7);
////
////        TreeNode leftLeftLeft = new TreeNode(8);
//
///*
//                      5
//                     / \
//                    4   8
//                   /   / \
//                  11  13  4
//                 /  \    / \
//                7    2  5   1
//
// */
//        TreeNode root = new TreeNode(5);
//        TreeNode left = new TreeNode(4);
//        TreeNode right = new TreeNode(8);
//
//        TreeNode leftLeft = new TreeNode(11);
//
//        TreeNode rightLeft = new TreeNode(13);
//        TreeNode rightRight = new TreeNode(4);
//
//        TreeNode leftLeftLeft = new TreeNode(7);
//        TreeNode leftLeftRight = new TreeNode(2);
//
//        TreeNode rightRightLeft = new TreeNode(5);
//        TreeNode rightRightRight = new TreeNode(1);
//
//        root.setLeft(left);
//        root.setRight(right);
//
//        left.setLeft(leftLeft);
//
//        right.setLeft(rightLeft);
//        right.setRight(rightRight);
//
//        leftLeft.setLeft(leftLeftLeft);
//        leftLeft.setRight(leftLeftRight);
//
//        rightRight.setLeft(rightRightLeft);
//        rightRight.setRight(rightRightRight);
//
////        ArrayList<TreeNode> result = new ArrayList<>();
////        BinaryTree.preOrderTraversal(root, result);
////
////        BinaryTree bt = new BinaryTree(root);
////        ArrayList<TreeNode> r = bt.inOrderTraversal();
////
////        ArrayList<ArrayList<TreeNode>> rr = bt.levelOrderTraversal();
////
////        boolean b1 = bt.isBST();
////        boolean b2 = bt.isBST2();
////
////        TreeNode newTree = null;
////        try {
////            File externalStorageDir = Environment.getExternalStorageDirectory();
////            File myFile = new File(externalStorageDir, "tree.dat");
////
////            FileOutputStream fos = new FileOutputStream(myFile, true);
////            BinaryTree.serialize(root, fos);
////
////            fos.flush();
////            fos.close();
////
////            FileInputStream fis = new FileInputStream(myFile);
////            newTree = BinaryTree.deserialize(fis);
////
////        } catch (Exception e) {
////            String m = e.getMessage();
////        }
////
////        BinaryTree.flattenAsLinkedList(root);
//
////        ArrayList<ArrayList<TreeNode>> rrr = BinaryTree.pathSumBFS(root, 22);
//
////        BinaryTree.print(root);
////
////        ArrayList<ArrayList<TreeNode>> rrr1 = BinaryTree.pathSumDFS(root, 22);
//
//        int[] inorder = {4, 2, 5,  1,  6, 7, 3, 8};
//        int[] postorder = {4, 5, 2,  6, 7, 8, 3,  1};
//
//        TreeNode t = BinaryTree.buildTree(inorder, postorder);
//        BinaryTree.print(t);
//
//        assertTrue(true);
//    }

    public void testBST() throws Exception {
/*
            _30_
           /    \
          20    40
         /     /  \
        10    35  50
       / \
      9  12
 */

        TreeNode root = new TreeNode(30);
        TreeNode left = new TreeNode(20);
        TreeNode right = new TreeNode(40);

        TreeNode leftLeft = new TreeNode(10);

        TreeNode rightLeft = new TreeNode(35);
        TreeNode rightRight = new TreeNode(50);

        TreeNode leftLeftLeft = new TreeNode(9);
        TreeNode leftLeftRight = new TreeNode(12);

        root.setLeft(left);
        root.setRight(right);

        left.setLeft(leftLeft);

        right.setLeft(rightLeft);
        right.setRight(rightRight);

        leftLeft.setLeft(leftLeftLeft);
        leftLeft.setRight(leftLeftRight);

//        BinaryTree.print(root);

//        boolean b1 = BST.isBST(root);
//        boolean b2 = BST.isBST2(root);
//        boolean b3 = BST.isBST3(root);

        TreeNode newTree = null;
        try {
            File externalStorageDir = Environment.getExternalStorageDirectory();
            File myFile = new File(externalStorageDir, "tree.dat");
            myFile.delete();

            FileOutputStream fos = new FileOutputStream(myFile, true);
            BST.serialize(root, new DataOutputStream(fos));

            fos.flush();
            fos.close();

            FileInputStream fis = new FileInputStream(myFile);
            newTree = BST.deserialize(new DataInputStream(fis));

            BinaryTree.print(newTree);

        } catch (Exception e) {
            String m = e.getMessage();
        }

        boolean bb11 = BST.isBST(newTree);
        boolean bb12 = BST.isBST2(newTree);
        boolean bb13 = BST.isBST3(newTree);

        int a[] = {1, 2, 3, 4, 5, 6, 7, 8};
        TreeNode t1 = BST.sortedArrayToBST(a, 0, 7);
        BinaryTree.print(t1);

        boolean bb1 = BST.isBST(t1);
        boolean bb2 = BST.isBST2(t1);
        boolean bb3 = BST.isBST3(t1);

        Node l = new Node(1);
        Node ll = l;
        for (int i = 2; i < 9; ++i) {
            Node n = new Node(i);
            l.setNext(n);
            l = l.getNext();
        }
        TreeNode t2 = BST.sortedListToBST(ll);
//        BinaryTree.print(t2);
        boolean bbb1 = BST.isBST(t2);
        boolean bbb2 = BST.isBST2(t2);
        boolean bbb3 = BST.isBST3(t2);

        int d1 = BinaryTree.minDepth(root);
        int d2 = BinaryTree.minDepth(t1);

        int dd1 = BinaryTree.minDepthBFS(root);
        int dd2 = BinaryTree.minDepthBFS(t1);

        int m1 = BinaryTree.maxPathSumLeaf2Leaf(root);
        int m2 = BinaryTree.maxPathSumLeaf2Leaf(t1);

        int mm1 = BinaryTree.maxPathSum(root);
        int mm2 = BinaryTree.maxPathSum(t1);

        assertTrue(true);
    }


    public void testPrintOddEvenInDifThreads() {

        class Printer {
            private volatile boolean isOddJustPrinted = false;

            public synchronized void printOdd(int n) {
                if (isOddJustPrinted) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(Thread.currentThread().getName() + ":" + n);

                isOddJustPrinted = true;
                notify();
            }

            public synchronized void printEven(int n) {
                if (!isOddJustPrinted) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.print(Thread.currentThread().getName() + ":" + n);

                isOddJustPrinted = false;
                notify();
            }
        }

        class OddThread extends Thread {
            private Printer printer;
            private int max;

            public OddThread(Printer printer, int max) {
                this.printer = printer;
                this.max = max;
            }

            @Override
            public void run() {
                for (int i = 2; i <= max; ++i) {
                    printer.printOdd(i);
                }
            }
        }

        class EvenThread extends Thread {
            private Printer printer;
            private int max;

            public EvenThread(Printer printer, int max) {
                this.printer = printer;
                this.max = max;
            }

            @Override
            public void run() {
                for (int i = 2; i <= max; ++i) {
                    printer.printEven(i);
                }
            }
        }

        Printer printer = new Printer();
        int max = 10;
        OddThread oddThread = new OddThread(printer, max);
        EvenThread evenThread = new EvenThread(printer, max);
        oddThread.start();
        evenThread.start();
    }

}