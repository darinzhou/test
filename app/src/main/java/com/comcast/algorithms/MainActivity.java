package com.comcast.algorithms;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            testBST();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        testBSTIterator();

//        testMergeSortedArrays();

//        ArrayList<TreeNode> list = BST.generateBSTs(3);

//        int sum = testNNCT();

//        testPreOrderTraversal();

//        ArrayList<int[]> result = Algorithms.permute(new int[] {1,2,3});
//
//        ArrayList<int[]> comb = Algorithms.combine(new int[]{1, 2, 3}, 3);
//
//        ArrayList<ArrayList<Integer>> combsum = Algorithms.combineSum(new int[]{2, 3, 6, 7}, 7);
//
//        ArrayList<String> ps = Algorithms.genParentheses(3);
//
//        ArrayList<String> ws1 = Algorithms.combinationsFromDigitsOnPhoneKeypad(new int[]{1, 2, 3});
//        ArrayList<String> ws2 = Algorithms.combinationsFromDigitsOnPhoneKeypad(new int[]{2,3,4});
//        ArrayList<String> ws3 = Algorithms.combinationsFromDigitsOnPhoneKeypad(new int[]{0,1,2});
//
//        ArrayList<String> ip = Algorithms.resolveIpAddress("1203456");

//        int[] r = null;
//        int m1 = 0, m2 = 0, m3 = 0, m4 = 0, m11=0;
//        try {
////            r = Algorithms.maxSubArray2(new int[] {-2,1,-3,4,-1,2,1,-5,4});
//            m1 = Algorithms.maxSubArrayProduct(new int[]{-2, 1, -3, 4, 0, 2, 1, -5, 4});
//            m11 = Algorithms.maxSubArrayProduct(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
//            m2 = Algorithms.maxSubArrayProduct(new int[]{6, -3, -10, 0, 2});
//            m3 = Algorithms.maxSubArrayProduct(new int[]{-1, -3, -10, 0, 60});
//            m4 = Algorithms.maxSubArrayProduct(new int[]{-2, -3, 0, -2, -40});
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////        ArrayList<String> res = Algorithms.permuteString("test");
//
//
//        int[] r1 = Algorithms.findMaximum3Int(new int[]{-2, 1, -3, 4, 0, 2, 1, -5, 4});
//        int[] r11 = Algorithms.findMaximum3Int(new int[]{-2, 9, -3, 4, -1, 2, 1, -5, 4});
//        int[] r2 = Algorithms.findMaximum3Int(new int[]{6, -3, -10, 0, 2});
//        int[] r3 = Algorithms.findMaximum3Int(new int[]{-1, -3, -10, 0, 60});
//        int[] r4 = Algorithms.findMaximum3Int(new int[]{-2, -3, 0, -2, -40});
//
//        ArrayList<TreeNode> ts = BST.generateBSTs(3);
//
//        LRU<Integer, String> c = new LRU<>(3);
//        c.put(0, "a");
//        c.put(1, "b");
//        c.put(2, "c");
//
//        String s = c.get(0);
//
//        c.put(3, "d");
//
//        int[] aa = new int[1];
//        aa[0]  =1;
//
//        int[] bb = aa;
//
//        aa[0] =100;
//
//        TreeNode root = new TreeNode(30);
//        TreeNode left = new TreeNode(20);
//        TreeNode right = new TreeNode(40);
//
//        root.left = left;
//        root.right = right;
//
//        TreeNode rrrr = root.right;
//        root.right = root.left;
//
//        ArrayList<ArrayList<int[]>> result = Algorithms.findForwardPaths(0,0,2,2);

//        testPreOrderTraversal();
//
//        int[] data = new int[1000];
//        for (int i=0; i<1000; ++i)
//            data[i] = i;
//
//        PriorityQueue<Integer> pq  =new PriorityQueue<>(100);
//        for (int i=99; i>=0; --i)
//            pq.add(data[i]);
//
//        for (int i=100; i<1000; ++i){
//            int m = pq.peek();
//            if (m < data[i]) {
//                pq.remove();
//                pq.add(data[i]);
//            }
//        }
//
//        int[] rrr=new int[2];
//        boolean b = Algorithms.findContinuousSubarraySum(new int[]{1, 4}, 0, rrr);
//
//        boolean b1 = Algorithms.isPalindrome("abc");
//        boolean b2 = Algorithms.isPalindrome("aba");
//        boolean b3 = Algorithms.isPalindrome("a,bbc");
//        boolean b4 = Algorithms.isPalindrome("abb,?A");
//
//        double sqr1 = Algorithms.sqrt(4);
//        double sqr2 = Algorithms.sqrt(5);
//        double sqr3 = Algorithms.sqrt(6);
//        double sqr4 = Algorithms.sqrt(9);
//
//        Algorithms.fibonacci(0);
//        System.out.print("\n");
//        Algorithms.fibonacci(1);
//        System.out.print("\n");
//        Algorithms.fibonacci(2);
//        System.out.print("\n");
//        Algorithms.fibonacci(3);
//        System.out.print("\n");
//        Algorithms.fibonacci(10);
//        System.out.print("\n");
//
//        int a, bb, c,d, ee,f;
//        try {
//             a = Algorithms.divide(10,3);
//             bb = Algorithms.divide(12,3);
//             c = Algorithms.divide(11,4);
//             d = Algorithms.divide(-10,2);
//            ee = Algorithms.divide(3,5);
//
//            f = Algorithms.divide(-10,2);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<String> rs = Algorithms.allCombinations("abc");
//
//        ArrayList<String> rss = Algorithms.allPermutations("abcd");
//
//        String lcs = Algorithms.lcs("thisisatest", "testing123testing");

//        String s1 = Algorithms.rotateChars("abcd", 1);
//        String s2 = Algorithms.rotateChars("abcd", 2);
//        String s3 = Algorithms.rotateChars("abcd", 5);
//
//        String ss1 = Algorithms.rotateWords("this is a test", 1);
//        String ss2 = Algorithms.rotateWords("this is a test", 2);
//        String ss3 = Algorithms.rotateWords("this is a test", 5);
//
//        int arr[] = {13, 11, 15, 5, 6, 7};
//        Heap heap = new Heap(arr);
//
//        heap.add(8);
//        heap.add(14);
//        heap.add(10);
//        heap.add(4);
//
//        int pa = heap.peek();
//        pa  =heap.remove();
//
//        pa = heap.remove();
//
//        heap.add(5);
//        heap.add(4);
//
//        pa = heap.remove();

//      testPreOrderTraversal();

//        BigInt a = new BigInt(1, new byte[] {9, 8, 7}); //789
//        BigInt b = new BigInt(1, new byte[] {1, 3, 4}); //431
//        BigInt c = new BigInt(1, new byte[] {8, 7, 6}); //678
//
//        BigInt ab = BigInt.add(a, b);
//        BigInt ab1 = BigInt.minus(a, b);
//        BigInt ac = BigInt.minus(b, a);
//
//       BigInt p = BigInt.multiply(a, b);

//        ArrayList<Character> subset = Algorithms.randomSubset(new char[] {'a', 'b', 'c', 'd'});
//
//        boolean b1 = Algorithms.oneEditDiff("abcd", "abce");
//        boolean b2 = Algorithms.oneEditDiff("abcd", "abed");
//        boolean b3 = Algorithms.oneEditDiff("abcd", "acde");
//        boolean b4 = Algorithms.oneEditDiff("abc", "abcd");
//        boolean b5 = Algorithms.oneEditDiff("abc", "abdc");
//        boolean b6 = Algorithms.oneEditDiff("abc", "abcde");
//
//
//        String s = Algorithms.removeBduplicateA("abc bac be about");

//        ArrayList<ArrayList<String>> g = Algorithms.groupAnagrams(new String[]{"abcd", "acbd", "efd", "efgh", "fehg", "dcba"});

//        Node head = new Node(1);
//        Node current = head;
//        for (int i=2; i<10; ++i) {
//            Node n = new Node(i);
//            current.setNext(n);
//            current = current.getNext();
//        }
//        Node rn = com.comcast.algorithms.LinkedList.reverseWithRecursive(head);

//        int count = Algorithms.digitsMapCharsCount(new char[]{1,3,1,3});
//        int count2 = Algorithms.digitsMapCharsCount2("1313");

//        TreeNode r = null;
//        Random rnd = new Random();
//        int[] a = new int[] {5,2,-4,3,12,9,21,19,25};
//        for (int i=0; i<a.length; ++i)
//            r = BST.insert(r, a[i]);
//
//        BinaryTree.print(r);
//
//        TreeNode min = BST.findMin(r);
//        TreeNode max = BST.findMax(r);
//        TreeNode t = BST.find(r, 12);
//        r = BST.remove(r, 12);
//        BinaryTree.print(r);
//
//        String rws = Algorithms.reverseWordsInString("This  is a    tes.");


//        ArrayList<String> all = Algorithms.allCombinations("hello");
//
//        int[] aa = new int[] {1,2,3,4,5};
//        int[] bb = new int[] {1,2,3,4,5};

//        Algorithms.shuffleEquallyLikely(aa);

        /*
        Input: 1,0,0,0,0,0,1,0,0
        3 => true
        4 => false

        Input: 1,0,0,1,0,0,1,0,0
        1 => true
        2 => false

        input: 0
        1 => true
        2 => false

        */
//        ArrayList<Boolean> fb1  =new ArrayList<>();
//        fb1.add(true);
//        fb1.add(false);
//        fb1.add(false);
//        fb1.add(false);
//        fb1.add(false);
//        fb1.add(false);
//        fb1.add(true);
//        fb1.add(false);
//        fb1.add(false);
//        boolean cpf11 = Algorithms.canPlaceFlowers(fb1,3);
//        boolean cpf12 = Algorithms.canPlaceFlowers(fb1,4);
//
//        ArrayList<Boolean> fb2  =new ArrayList<>();
//        fb2.add(true);
//        fb2.add(false);
//        fb2.add(false);
//        fb2.add(true);
//        fb2.add(false);
//        fb2.add(false);
//        fb2.add(true);
//        fb2.add(false);
//        fb2.add(false);
//        boolean cpf21 = Algorithms.canPlaceFlowers(fb2,1);
//        boolean cpf22 = Algorithms.canPlaceFlowers(fb2,2);
//
//        ArrayList<Boolean> fb3  =new ArrayList<>();
//        fb3.add(false);
//        boolean cpf31 = Algorithms.canPlaceFlowers(fb3,1);
//        boolean cpf32 = Algorithms.canPlaceFlowers(fb3,2);

//        Child Parent IsLeft
//        15 20 true
//        19 80 true
//        17 20 false
//        16 80 false
//        80 50 false
//        50 null false
//        20 50 true

//        ArrayList<RelationBinaryTree.Relation> rs = new ArrayList<>();
//        RelationBinaryTree.Relation r1 = new RelationBinaryTree.Relation();
//        r1._isLeft = true;
//        r1._parent = 20;
//        r1._child = 15;
//        RelationBinaryTree.Relation r2 = new RelationBinaryTree.Relation();
//        r2._isLeft = true;
//        r2._parent = 80;
//        r2._child = 19;
//        RelationBinaryTree.Relation r3 = new RelationBinaryTree.Relation();
//        r3._isLeft = false;
//        r3._parent = 20;
//        r3._child = 17;
//        RelationBinaryTree.Relation r4 = new RelationBinaryTree.Relation();
//        r4._isLeft = false;
//        r4._parent = 80;
//        r4._child = 16;
//        RelationBinaryTree.Relation r5 = new RelationBinaryTree.Relation();
//        r5._isLeft = false;
//        r5._parent = 50;
//        r5._child = 80;
//        RelationBinaryTree.Relation r6 = new RelationBinaryTree.Relation();
//        r6._isLeft = false;
//        r6._parent = null;
//        r6._child = 50;
//        RelationBinaryTree.Relation r7 = new RelationBinaryTree.Relation();
//        r7._isLeft = true;
//        r7._parent = 50;
//        r7._child = 20;
//        rs.add(r1);
//        rs.add(r2);
//        rs.add(r3);
//        rs.add(r4);
//        rs.add(r5);
//        rs.add(r6);
//        rs.add(r7);
//
//        RelationBinaryTree.Node root = RelationBinaryTree.buildTree(rs);

        /*
        Positive: {6,7,8,9,10,11,12,1,2,3,4,5}, target = 3, target = 8
Negative: {6,7,8,9,10,11,12,1,2,3,4,5}, target = 0, target = 13
Boundary: {6,7,8,9,10,11,12,1,2,3,4,5}, target = 6, target = 5
         */
//        int[] a = new int[] {6,7,8,9,10,11,12,1,2,3,4,5};
//        int i1 = Algorithms.binarySearchShiftedSortedArray(a, 3, 0, a.length - 1);
//        int i2 = Algorithms.binarySearchShiftedSortedArray(a, 8, 0, a.length-1);
//        int i3 = Algorithms.binarySearchShiftedSortedArray(a, 0, 0, a.length-1);
//        int i4 = Algorithms.binarySearchShiftedSortedArray(a, 13, 0, a.length-1);
//        int i5 = Algorithms.binarySearchShiftedSortedArray(a, 6, 0, a.length-1);
//        int i6 = Algorithms.binarySearchShiftedSortedArray(a, 5, 0, a.length-1);
//
//        boolean in1 = Algorithms.isNumber("-12");
//        boolean in2 = Algorithms.isNumber("+12");
//        boolean in3 = Algorithms.isNumber(".12");
//        boolean in4 = Algorithms.isNumber("12.");
//        boolean in5 = Algorithms.isNumber("1.2");
//        boolean in6 = Algorithms.isNumber("124567");
//        boolean in7 = Algorithms.isNumber("1a2");

//        final ClientServerTest.Server server = new ClientServerTest.Server();
//        server.start();
//
//        for (int i=0; i<20;++i) {
//            final String clientName = "client_" + i;
//            ClientServerTest.Client client = new ClientServerTest.Client(clientName);
//            client.sendJob(server);
//        }

//        Triangle t = new Triangle();
//        t.printAllTriangleSides(new int[]{10, 21, 22, 100, 101, 200, 300});
//
//        int[] num = new int[] {4,1,2,3,4,5,6,5,4,3,4,4,4,4,4,4,4};
//        int maxlp = Algorithms.maxLengthPalindrome(num, 0, num.length-1);

//        testTurnDownTree();

//        int n = Algorithms.findCommonChars(new String[]{"aghkafgkltz",
//                "dfghazko",
//                "qzwemnaarkf" });
//
//        ArrayList<String> l =  new ArrayList<>();
//        Algorithms.pp(new char[]{'a', 'b', 'c'}, 0, l);
//
//        ArrayList<int[]> ll = Algorithms.combine(new int[]{1, 2, 3}, 3);
//
//        String[] ss = new String[]{"the", "quick", "brown", "fox", "quick"};
//        List<String> ssl = Arrays.asList(ss);
//        WordDistanceFinder finder = new WordDistanceFinder(ssl);
//        int d1 = finder.distance("fox","the");
//        int d2 = finder.distance("quick", "fox");
//
//        testFCA();

//        int n = Algorithms.maxSubArrayProduct(new int[] {0, 2, 5, 0});
//        int n1 = Algorithms.maxSubArrayProduct(new int[] {-2, -3, -6});
//        int n2 = Algorithms.maxSubArrayProduct(new int[] {2, -3, -2, -2, 0,-40,-5});
//
//        int[] rg1 = Algorithms.findRange(new int[]{0, 2, 3, 3, 3, 10, 10}, 3);// should return (2,4).
//        int[] rg2 = Algorithms.findRange(new int[]{0, 2, 3, 3, 3, 10, 10}, 6);// should return (-1,-1).
//        int[] rg3 = Algorithms.findRange(new int[]{0, 2, 3, 3, 3, 3,10, 10}, 3);// should return (2,4).
//        int[] rg4 = Algorithms.findRange(new int[]{0 ,2, 3 ,3, 3 ,10, 10}, 10);// should return (-1,-1).
//        int[] rg5 = Algorithms.findRange(new int[]{0 ,2, 3 ,3, 3 ,10, 10}, 0);// should return (-1,-1).
//        int[] rg6 = Algorithms.findRange(new int[]{0 ,0,2, 3 ,3, 3 ,10, 10}, 0);// should return (-1,-1).
//
//        ArrayList<String> pr = Algorithms.wordWrap(new String[]{"aaa", "bb", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "cc", "ddddd"}, 5);
//
//        double p = Algorithms.pow(2, 0);
//        double p1 = Algorithms.pow(2, 1);
//        double p2 = Algorithms.pow(2,2);
//        double p3 = Algorithms.pow(2,3);
//        double p4 = Algorithms.pow(2,4);
//        double p5 = Algorithms.pow(2,-3);
//
//        boolean[][] m = new boolean[][]{
//                {false, true, true, false},
//                {true, false, true, true},
//                {false, false, false, false},
//                {true, true, true, false}};
//
//        int infl = Algorithms.getInfluencer(m);
//
//        PointsOnPlane pop = new PointsOnPlane();
//        pop.addPoint(new Point(0,1));
//        pop.addPoint(new Point(0, 2));
//        pop.addPoint(new Point(0,3));
//        pop.addPoint(new Point(0,4));
//        pop.addPoint(new Point(0,5));
//        pop.addPoint(new Point(0,6));
//
//        Collection<Point> nps = pop.findNearest(new Point(0,0), 3);

        /*
            * ['c', 'f', 'j', 'p', 'v'], 'a' => 'c'
    * ['c', 'f', 'j', 'p', 'v'], 'c' => 'f'
    * ['c', 'f', 'j', 'p', 'v'], 'k' => 'p'
    * ['c', 'f', 'j', 'p', 'v'], 'z' => 'c' // The wrap around case
    * ['c', 'f', 'k'], 'f' => 'k'
    * ['c', 'f', 'k'], 'c' => 'f'
    * ['c', 'f', 'k'], 'd' => 'f'

         */

//        char[] a = new char[]{'c', 'f', 'j', 'p', 'v'};
//        char[] b = new char[]{'c', 'f', 'k'};
//
//        char sc0 = Algorithms.findSmallestLargerThanTarget(a, 'a');
//        char sc1 = Algorithms.findSmallestLargerThanTarget(a, 'c');
//        char sc2 = Algorithms.findSmallestLargerThanTarget(a, 'k');
//        char sc3 = Algorithms.findSmallestLargerThanTarget(a, 'z');
//        char sc4 = Algorithms.findSmallestLargerThanTarget(b, 'f');
//        char sc5 = Algorithms.findSmallestLargerThanTarget(b, 'c');
//        char sc6 = Algorithms.findSmallestLargerThanTarget(b, 'd');
//
//        HashSet<String> sss = Algorithms.findRepeatingSubstrng("ABCABCA", 2);
//        HashSet<String> sss2 = Algorithms.findRepeatingSubstrng("ABCACBABC", 3);
//
//        int[] arr1 = { 4, 8};
//        int[] arr2 = { 1, 6, 10 };
//        int md = Algorithms.findSmallestDifference(arr1, arr2);
//
//        String rs = Algorithms.reverseRecursive("abcdefg");

        /*
            find_depth('(00)') -> 0
    find_depth('((00)0)') -> 1
    find_depth('((00)(00))') -> 1
    find_depth('((00)(0(00)))') -> 2
    find_depth('((00)(0(0(00))))') -> 3
    find_depth('x') -> -1
    find_depth('0') -> -1
    find_depth('()') -> -1
    find_depth('(0)') -> -1
    find_depth('(00)x') -> -1
    find_depth('(0p)') -> -1
         */

//        int depth = BinaryTree.findSpecialBinaryTreeDepth("(00)");
//        int depth1 = BinaryTree.findSpecialBinaryTreeDepth("((00)0)");
//        int depth2 = BinaryTree.findSpecialBinaryTreeDepth("((00)(00))");
//        int depth3 = BinaryTree.findSpecialBinaryTreeDepth("((00)(0(00)))");
//        int depth4 = BinaryTree.findSpecialBinaryTreeDepth("((00)(0(0(00))))");
//
//        int depth5 = BinaryTree.findSpecialBinaryTreeDepth("((000)(0(0(00))))");
//        int depth6 = BinaryTree.findSpecialBinaryTreeDepth("((00))(0(0(00))))");
//        int depth7 = BinaryTree.findSpecialBinaryTreeDepth("((0p)(0(0(00))))");
//
//        HashSet<String> pattern = new HashSet<>();
//        pattern.add("W1");
//        pattern.add("W2");
//        pattern.add("W3");
//        String sss = Algorithms.findShortestString(new String[] {"abc", "W1", "ddd", "dk", "W2", "df", "W3", "kjhhhhhhhhhhhhhhh", "W2", "W1", "d", "d"}, pattern);
//
//        boolean b1 = Algorithms.isNumber2("-0675");
//        boolean b2 = Algorithms.isNumber2("0b011");
//        boolean b22 = Algorithms.isNumber2("0b675");
//        boolean b3 = Algorithms.isNumber2("-0xa675");
//        boolean b4 = Algorithms.isNumber2("675a");
//        boolean b5 = Algorithms.isNumber2("+0x6p75");
//        boolean b6 = Algorithms.isNumber2("-06.75");
//        boolean b7 = Algorithms.isNumber2("6.75");
//        boolean b8 = Algorithms.isNumber2("6.7.5");
//        boolean b9 = Algorithms.isNumber2("f675");
//
//        String ps = Algorithms.infix2postfix("(A+B)*(C-D)");
//        String ps1 = Algorithms.infix2postfix("A+B*C-D+E/F");

//        int[][] a2 = Algorithms.printArrayInCol(new int[] {1, 2, 3, 4, 5, 6, 7}, 3);
//
//        PowersTest.PowersClass pc  = new PowersTest.PowersClass();
//        long[] lv  =new long[10];
//        for (int i=0; i<10; ++i)
//            lv[i] = pc.next();
//
//        ArrayList<String> wl = WordFrequencyCount.getMostFrequenctlyOccuredWords2(new String[]{"abc", "def", "abc", "fgh", "hhh", "aaa", "aaa", "bbb", "ccc", "fgh"}, 3);
//
//        boolean b1 = RegexTest.isIpAddress("123.123.123.123");
//        boolean b12 = RegexTest.isIpAddress("266.123.123.123");
//        boolean b13 = RegexTest.isIpAddress("255.255.255.255");
//        boolean b14 = RegexTest.isIpAddress("0.0.0.0");
//        boolean b15 = RegexTest.isIpAddress("1.1.1.1");
//        boolean b16 = RegexTest.isIpAddress("a.b.c.d");
//        boolean b17 = RegexTest.isIpAddress("1.2.3.4.5");
//
//        Node node1 = new Node(1);
//        Node node2 = new Node(12);
//        Node node3 = new Node(23);
//        Node node4 = new Node(34);
//        Node node5 = new Node(55);
//        Node node6 = new Node(60);
//        Node node7 = new Node(70);
//        Node node8 = new Node(80);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        node5.next = node6;
//        node6.next = node7;
//        node7.next = node8;
//
//        Node node21 = new Node(11);
//        Node node22 = new Node(22);
//        Node node23 = new Node(33);
//        Node node24 = new Node(40);
//        Node node25 = new Node(59);
//        Node node26 = new Node(90);
//        Node node27 = new Node(92);
//        Node node28 = new Node(98);
//        node21.next = node22;
//        node22.next = node23;
//        node23.next = node24;
//        node24.next = node25;
//        node25.next = node26;
//        node26.next = node27;
//        node27.next = node28;
//
//        Node mh = LinkedList.merge(node1, node21);

//
//        Node nh = LinkedList.swapAdjacentNodes(node1);

//        int bp = Algorithms.findBallancePoint(new int[] {1, 2, 3, 4, 3, 2, 1});
//        int bp2 = Algorithms.findBallancePoint(new int[] {1, 2, 3, 4, 3, 2, 1, 5});

//        int bp1 = Algorithms.findShorstestDistanceBetween2Words(new String[]{"this", "is", "a", "test", "to", "test", "this"}, "this", "test");
//        int bp2 = Algorithms.findShorstestDistanceBetween2Words2(new String[]{"this", "is", "a", "test", "to", "test", "this"}, "this", "test");
//
//        boolean win = Algorithms.canIWin(15, 100);

//        HashSet<String> dna = Algorithms.findDNA("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",10);
//
//        //[1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9]
//
//        IntervalTest.Interval i1 = new IntervalTest.Interval(1,2);
//        IntervalTest.Interval i2 = new IntervalTest.Interval(3,5);
//        IntervalTest.Interval i3 = new IntervalTest.Interval(6,7);
//        IntervalTest.Interval i4 = new IntervalTest.Interval(8,10);
//        IntervalTest.Interval i5 = new IntervalTest.Interval(12,16);
//        IntervalTest.Interval i6 = new IntervalTest.Interval(4,9);
//        ArrayList<IntervalTest.Interval> il = new ArrayList<>();
//        il.add(i1);
//        il.add(i2);
//        il.add(i3);
//        il.add(i4);
//        il.add(i5);
//
//        ArrayList<IntervalTest.Interval> nil = IntervalTest.insertInterval(il, i6);

//        HashSet<String> dict = new HashSet<>();
//        dict.add("hit");
//        dict.add("hot");
//        dict.add("dot");
//        dict.add("dog");
//        dict.add("cog");
//        WordLadder wl  = new WordLadder(dict);
//        List<String> l = wl.findLadder("hit", "cog");
//
//
//        int sqrt12 = Algorithms.integerSqrt(4);
//        int sqrt13 = Algorithms.integerSqrt(5);
//        int sqrt14 = Algorithms.integerSqrt(6);
//        int sqrt15 = Algorithms.integerSqrt(7);
//        int sqrt16 = Algorithms.integerSqrt(8);
//        int sqrt17 = Algorithms.integerSqrt(9);
//        int sqrt121 = Algorithms.integerSqrt(4);
//        int sqrt122 = Algorithms.integerSqrt(5);
//        int sqrt123 = Algorithms.integerSqrt(6);
//        int sqrt124 = Algorithms.integerSqrt(7);
//        int sqrt125 = Algorithms.integerSqrt(8);
//        int sqrt126 = Algorithms.integerSqrt(9);
//
//
//        List a = new ArrayList<>();
//        for (int i=0; i<=10; i+=2)
//            a.add(i);
//        List b = new ArrayList<>();
//        for (int i=1; i<10; i+=2)
//            b.add(i);
//
//        Iterator it = IteratorUtil.mergeKSortedIterators(new Iterator[]{a.iterator(), b.iterator()}).iterator();
//        for (int i=0; i<20; ++i)
//            if (it.hasNext())
//            Log.d("TTT","---->" + it.next());

//        testLRUCache();
//
//        int sn1 = Algorithms.getClimbWays(4);
//        int sn5 = Algorithms.getClimbWays(5);

//        H2OTest2.test();
//
//        String rs = Algorithms.replaceAll("123888123999912300000123", "123", "abc");
//
//        List<Integer> pf = Algorithms.findPrimeFactors(2);
//        List<Integer> pf1 = Algorithms.findPrimeFactors(24);
//        List<Integer> pf2 = Algorithms.findPrimeFactors(605);


//        String lss = Algorithms.findLongestSubstringWithoutRepeatingChars("GEEKSFORGEEKS");
//
//        int n = Algorithms.firstMissingPositive(new int[] {3,4,-1,1});

        String S = "rabbbit";
        String T = "rabbit";
        int c = Algorithms.countDistinctSubString(S, T);


        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;

        Node nl = LinkedList.removeNthNodeFromEnd(node1, 3);

//        TreeNode bst = BST.sortedListToBST(node1);

//        Node rk = LinkedList.reverseKGroup(node1, 5);

//        Node nl = LinkedList.deleteDuplicated(node1);
//
//        ArrayList<ArrayList<String>> sl = Algorithms.palindromePartitions("aab");

//        Node rp = LinkedList.reversePartially(node1, 2, 4);
//
//        ArrayList<String> cl = Algorithms.letterCombinations("1234");
//        ArrayList<String> cl1 = Algorithms.combinationsFromDigitsOnPhoneKeypad(new int[]{1, 2, 3, 4});

//        Node p = LinkedList.partition(node1, 3);

        /*
        [
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
         */
//        ArrayList<Integer> r0 = new ArrayList<>();
//        ArrayList<Integer> r1 = new ArrayList<>();
//        ArrayList<Integer> r2 = new ArrayList<>();
//        ArrayList<Integer> r3 = new ArrayList<>();
//        r0.add(2);
//        r1.add(3);
//        r1.add(4);
//        r2.add(6);
//        r2.add(5);
//        r2.add(7);
//        r3.add(1);
//        r3.add(2);
//        r3.add(8);
//        r3.add(3);
//        ArrayList<ArrayList<Integer>> t = new ArrayList<>();
//        t.add(r0);
//        t.add(r1);
//        t.add(r2);
//        t.add(r3);
//
//        int ms = Algorithms.minPathSumTriangle(t);

//        String s0 = Algorithms.countAndSay(0);
//        String s1 = Algorithms.countAndSay(1);
//        String s2 = Algorithms.countAndSay(2);
//        String s3 = Algorithms.countAndSay(3);
//        String s4 = Algorithms.countAndSay(4);

//        String prefix = Algorithms.longestCommonPrefix(new String[] {
//                "abcd1 hjsadhsb",
//                "abcd 2hjsadhsb",
//                "abcd  hjsadhsb",
//                "abcd 4hjsadhsb"
//        });

//        ArrayList<ArrayList<Integer>> ss  =Algorithms.subSets(new int[]{1,2,2,2}) ;
//
//        ArrayList<String> cs = Algorithms.allCombinations("123");
//
//        int rt = Algorithms.trappingRainWater(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});

//        boolean ip = Algorithms.isPalindromeNumber(1234321);

//        ArrayList<ArrayList<Integer>> comb = Algorithms.combinations(4,2);
//        ArrayList<int[]> comb1 = Algorithms.combine(new int[]{1, 2, 3, 4}, 2);
//
//        ArrayList<ArrayList<Integer>> comb2 = Algorithms.combineSum(new int[]{2, 3, 6, 7}, 7);
//
//        int[][] m = Algorithms.spiralMatrix(4);

//        int[][] g = new int[][] {
//                {1,2,3},
//                {4,8,2},
//                {1,5,3}
//        };
//
//        int mp = Algorithms.minPathSumDP(g);
//
//        String s = "catsanddog";
//        Set<String> dict = new HashSet<>();
//        dict.add("cat");
//        dict.add("cats");
//        dict.add("and");
//        dict.add("sand");
//        dict.add("dog");
//
//        ArrayList<ArrayList<String>> r = Algorithms.wordBreak(s, dict);

//        int[] a = new int[] {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
//        Algorithms.sortColors(a);
//
//        ArrayList<int[]> pt  =Algorithms.pascalTriangle(6);
//        int[] pr = Algorithms.pascalTriangleRow(6);
//
//        ArrayList<Integer> gc = Algorithms.grayCode(3);
//
//        int[] aa = new int[]{1,1,1,2,2,3,3,3,3};
//        int l = Algorithms.removeDuplicates2(aa);
//
//        int n1 = BST.numBSTs(1);
//        int n2 = BST.numBSTs(2);
//        int n3 = BST.numBSTs(3);
//        int n4 = BST.numBSTs(4);
//        int n5 = BST.numBSTs(5);
//
//        ArrayList<TreeNode> ts4 = BST.generateBSTs(4);
//        ArrayList<TreeNode> ts5 = BST.generateBSTs(5);

//        int n = Algorithms.singleNumber2(new int[] {1,2,3,2,3,1,7,3,2,1});
//        testLRUCache();

//        int n1 = Algorithms.minEditDistance("cat", "car");
//        int n2 = Algorithms.minEditDistance("cart", "car");
//        int n3 = Algorithms.minEditDistance("cat", "dog");
//
//        int n = 10000;
//        int[] a = new int[n];
//        for (int i=0; i<n; ++i)
//            a[i] = i;
//        int[] aa = Algorithms.maxNumbers(a, 100);

//        double s1     =Algorithms.sqrt(192);
//        double s2  =Algorithms.sqrt(192, 2);
//        double s3  =Algorithms.sqrt(192, 3);
//        double s4  =Algorithms.sqrt(192, 4);
//
//int h = Algorithms.hashCode("this is a test");

//        int n = 9;
//        int[] a = new int[n];
//        for (int i=0; i<n; ++i)
//            a[i] = i;
//        Algorithms.rotateArray3(a, 3);

//        byte[] a = new byte[] {1,2,3,4,5,6,7,8,9};
//            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(a));
//
//        try {
//            MedianOfStream mos = new MedianOfStream(dis);
//            while (mos.hasNext()) {
//                int m = mos.nextMedian();
//                System.out.println(m);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        int[] a =new int[] {1,2,3};
//        ArrayList<int[]> cc = Algorithms.combine(a, 2);

//        String[] one = new String[] {"Red", "Green", "Blue"};
//        String[] two = new String[] {"Large", "Medium", "Small"};
//        String[] three= new String[] {"giant", "monster"};
//        String[][] arrays = new String[3][];
//        arrays[0] = one;
//        arrays[1] = two;
//        arrays[2] = three;
//
//        ArrayList<ArrayList<String>> res = Algorithms.combinationsOfArrays(arrays);

//        ArrayList<String> res = Algorithms.combinationsFromDigitsOnPhoneKeypad(new int[]{0,1,2,3,4});

//        int n = 1000000;
//        int[] a = new int[n];
//        Random rnd = new Random();
//        for (int i=0; i<n; ++i) {
//            a[i] = rnd.nextInt((int) Math.pow(2, 20) - 1);
//        }
//        int dn = Algorithms.findOneNotInArray(a);

//        String[] ss = Algorithms.splitString("This     \"is  a   test.");

//        int si = Algorithms.findSingleInteger(new int[]{1, 1, 2, 2, 3, 4, 3});

//        QueueUsingStacks<Integer> qs = new QueueUsingStacks<>();
//        qs.enqueue(1);
//        qs.enqueue(2);
//        qs.enqueue(3);
//
//        int o1 = qs.dequeue(); //1
//        int o2 = qs.dequeue(); //2
//
//        qs.enqueue(4);
//        int o3 = qs.dequeue(); //3
//        qs.enqueue(5);
//        int o4 = qs.dequeue(); //4
//        qs.enqueue(6);
//
//        int o5 = qs.dequeue(); //5
//        int o6 = qs.dequeue(); //6
//
//        qs.enqueue(7);
//        int o7 = qs.dequeue(); //7
//        qs.enqueue(8);
//        qs.enqueue(9);
//        int o8 = qs.dequeue(); //8
//        int o9 = qs.dequeue(); //9
//
//        int o = qs.dequeue(); //exception

//        ArrayList<int[]> r = Algorithms.findRange(new int[]{1, 1, 2, 2, 3, 4, 3, -1,3,-2,7,-3,9}, 2, 7);
//
//        String s = Algorithms.findMissingRange(new int[]{3, 5, 23, 24, 60});
//
//        String res = Algorithms.removeChars("This is a test", "it");

//        String[] ss = {
//                "abcd efg",
//                "hij klm",
//                "123 45"
//        };
//        String es = Algorithms.serialize(ss);
//        String[] sa = Algorithms.deserialize(es);

//        testBSTClosest();

        int[][] m = {
                {1,2,3,4},
                {8,7,6,5},
                {10,9,11,12}
        };

        ArrayList<Integer> lis = Algorithms.findLIS(m);

        int[] a = new int[] { 10, 22, 9, 33, 21, 50, 41, 60, 80 };

        BST.bstSort(a);

        return;


    }

    public void testBSTClosest() {
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

        TreeNode closest = BST.findClosestNode(root, 21);
        TreeNode secondClosest = BST.findClosestNode(root, 21);

        return;
    }



    public void testLRUCache() {
        LRUCache<String> cache = new LRUCache<>();
        cache.put("a","a");cache.put("b","b");
        cache.put("c","c");cache.put("d","d");
        cache.put("e","e");cache.put("b","b");
        cache.put("d","d");cache.put("b","b");
        cache.put("c","c");cache.put("b","b");
        cache.put("a","a");cache.put("f","f");
        cache.get("a");
    }

    public void testFCA() {
    /*
                 A
                /  \
               B   C
              /  \   \
             D   E   M
            / \
           G   F
    */

        TestFCA.Node root = new TestFCA.Node(1);
        TestFCA.Node left = new TestFCA.Node(2);
        TestFCA.Node right = new TestFCA.Node(3);

        TestFCA.Node leftLeft = new TestFCA.Node(4);
        TestFCA.Node leftRight = new TestFCA.Node(5);

        TestFCA.Node leftLeftLeft = new TestFCA.Node(7);
        TestFCA.Node leftLeftRight = new TestFCA.Node(6);

        TestFCA.Node rightRight = new TestFCA.Node(10);

        root.parent = null;
        root.left = left;
        root.right = right;

        left.parent = root;
        left.left = leftLeft;
        left.right = leftRight;

        right.parent = root;
        right.left = null;
        right.right = rightRight;

        leftLeft.parent = left;
        leftLeft.left = leftLeftLeft;
        leftLeft.right = leftLeftRight;

        leftRight.parent = left;
        leftRight.left = null;
        leftRight.right = null;

        rightRight.parent = right;
        rightRight.left = null;
        rightRight.right = null;

        leftLeftLeft.parent = leftLeft;
        leftLeftLeft.left = null;
        leftLeftLeft.right = null;

        leftLeftRight.parent = leftLeft;
        leftLeftRight.left = null;
        leftLeftRight.right = null;

        TestFCA.FirstCommonAncestorClass fcm = new TestFCA.FirstCommonAncestorClass();
        TestFCA.Node tn = fcm.commonAncestor(leftRight, leftLeftLeft);
        TestFCA.Node tn1 = fcm.commonAncestor(rightRight, leftLeftLeft);
        TestFCA.Node tn3 = fcm.commonAncestor(right, left);

        return;
    }

    public void testTurnDownTree() {
    /*
                 *
         *        1                 1
         *       / \               / \
         *      2   3            2   3
         *     / \
         *    4   5
         *   / \
         *  6   7

         */

        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);

        TreeNode leftLeft = new TreeNode(4);
        TreeNode leftRight = new TreeNode(5);

        TreeNode leftLeftLeft = new TreeNode(6);
        TreeNode leftLeftRight = new TreeNode(7);

        root.setLeft(left);
        root.setRight(right);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        leftLeft.setLeft(leftLeftLeft);
        leftLeft.setRight(leftLeftRight);

        TreeNode tr = BinaryTree.turndownTree(root);

        return;
    }

    public void testPreOrderTraversal() {
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

//        ArrayList<TreeNode> result = new ArrayList<>();
//        BinaryTree.findKDistanceNodes(root, left, 2, result);

//        TreeNode dll = BinaryTree.toDoublyLinkedList(root);


//        TreeNode n1 = BinaryTree.findLCA(root, root, root);
//        TreeNode n2 = BinaryTree.findLCA(root, rightLeft, rightRight);
//        TreeNode n3 = BinaryTree.findLCA(root, left, leftLeftRight);
//        TreeNode n4 = BinaryTree.findLCA(root, leftLeftLeft, rightRight);

//        TreeNode n21 = BST.findLCA(root, root, root);
//        TreeNode n22 = BST.findLCA(root, rightLeft, rightRight);
//        TreeNode n23 = BST.findLCA(root, left, leftLeftRight);
//        TreeNode n24 = BST.findLCA(root, leftLeftLeft, rightRight);
//
//        TreeNode l = BinaryTree.flattenAsLinkedListWithRecursive(root);
//
//        BinaryTree.postOrderTraversal(root);

//        BinaryTree.invert(root);

//        ArrayList<ArrayList<TreeNode>> r = BinaryTree.allRootToLeafPaths(root);

//        ArrayList<Integer> r = BinaryTree.allSumsOfRoot2LeafPaths(root);

        ArrayList<ArrayList<TreeNode>> rr = BinaryTree.columnwiseOrderTraversal(root);

        return;
    }
    public int testNNCT() {
       /*
         1
        / \
       2   5
      / \
     3   4

 */
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(5);

        TreeNode leftLeft = new TreeNode(3);
        TreeNode leftRight = new TreeNode(4);

        TreeNode rightRight = new TreeNode(6);

        root.setLeft(left);
        root.setRight(right);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        right.setRight(rightRight);

        int h = BinaryTree.getTreeHeight(root);

//        TreeNode ir = BinaryTree.invert(root);
//        TreeNode ir2 = BinaryTree.invert2(root);

        BinaryTree.postOrderTraversal(root);

        return BinaryTree.nodeNumOfCompleteBinaryTree(root);
    }

    public int testP2LSum() {
       /*
         1
        / \
       2   5
      / \   \
     3   4   6

 */
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(5);

        TreeNode leftLeft = new TreeNode(3);
        TreeNode leftRight = new TreeNode(4);

        TreeNode rightRight = new TreeNode(6);

        root.setLeft(left);
        root.setRight(right);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        right.setRight(rightRight);

        return BinaryTree.allRoot2LeafPathSum(root);
    }

    public void testMergeSortedArrays() {
        int[] arr1 = { 1, 3, 5, 7 };
        int[] arr2 = { 2, 4, 6, 8 };
        int[] arr3 = { 0, 9, 10, 11 };

        int[] result = MergeSortedArrays.merge(new int[][] { arr1, arr2, arr3 });
        System.out.println(Arrays.toString(result));
    }

    public void testBSTIterator() {
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

        ArrayList<Integer> rv = BinaryTree.rightSideView(root);

        BSTIterator bsti = new BSTIterator(root);
        while (bsti.hasNext())
            Log.d("TestFCA", "--> " + bsti.next());

    }

    public void testFlatten() {
/*
         1
        / \
       2   5
      / \   \
     3   4   6

 */
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(5);

        TreeNode leftLeft = new TreeNode(3);
        TreeNode leftRight = new TreeNode(4);

        TreeNode rightRight = new TreeNode(6);

        root.setLeft(left);
        root.setRight(right);

        left.setLeft(leftLeft);
        left.setRight(leftRight);

        right.setRight(rightRight);

        BinaryTree.flattenAsLinkedListWithRecursive(root);

        BinaryTree.print(root);

    }

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
        TreeNode newTree1 = null;
        try {
            File externalStorageDir = Environment.getExternalStorageDirectory();
            File myFile = new File(externalStorageDir, "tree.dat");
            myFile.delete();

            FileOutputStream fos = new FileOutputStream(myFile, true);
//            BST.serialize(root, new DataOutputStream(fos));

            fos.flush();
            fos.close();

            FileInputStream fis = new FileInputStream(myFile);
//            newTree = BST.deserialize(new DataInputStream(fis));

//            BinaryTree.print(newTree);

//            myFile = new File(externalStorageDir, "tree.dat");
            myFile.delete();

            fos = new FileOutputStream(myFile, true);
            BinaryTree.serialize(root, new DataOutputStream(fos));

            fos.flush();
            fos.close();

            fis = new FileInputStream(myFile);
            DataInputStream dis = new DataInputStream(fis);
            while (fis.available() > 0) {
                int token = dis.readInt();
                Log.d("TestFCA", "<< " + token + " ");
            }
            fis.close();

            fis = new FileInputStream(myFile);
            newTree1 = BinaryTree.deserialize(new DataInputStream(fis));

            BinaryTree.print(newTree1);

        } catch (Exception e) {
            String m = e.getMessage();
        }

        boolean bb11 = BST.isBST(newTree);
        boolean bb12 = BST.isBST2(newTree);
        boolean bb13 = BST.isBST3(newTree);

        int a[] = {1,2,3,4,5,6,7,8};
        TreeNode t1 = BST.sortedArrayToBST(a, 0, 7);
        BinaryTree.print(t1);

        boolean bb1 = BST.isBST(t1);
        boolean bb2 = BST.isBST2(t1);
        boolean bb3 = BST.isBST3(t1);

        Node l = new Node(1);
        Node ll = l;
        for (int i=2; i<9; ++i) {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
