package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.List;

public class Solutions4 {


        public static int binarySearch(int[] a, int n, int l, int r) {
            if (l <= r) {
                int m = l + (r-l)/2;
                if (a[m] == n) {
                    return m;
                } else if (a[m] > n) {
                    return binarySearch(a, n, l, m-1);
                } else {
                    return binarySearch(a, n, m+1, r);
                }
            }
            return -1;
        }

        public static int findFirst(int[] a, int n) {

            int l = 0;
            int r = a.length - 1;
            while (l <= r) {
                int m = l + (r-l)/2;
                if (a[m] == n && (m == 0 || a[m-1] < n)) {
                    return m;
                } else if (a[m] >= n) {
                    r = m-1;
                } else {
                    l = m+1;
                }
            }

            return -1;
        }

        public static int binarySearchFindFirst(int[] a, int n, int l, int r) {
            if (l <= r) {
                int m = l + (r-l)/2;
                if (a[m] == n && (m == 0 || a[m-1] < n)) {
                    return m;
                } else if (a[m] >= n) {
                    return binarySearchFindFirst(a, n, l, m-1);
                } else {
                    return binarySearchFindFirst(a, n, m+1, r);
                }
            }
            return -1;
        }

        public static int binarySearchFindLast(int[] a, int n, int l, int r) {
            if (l <= r) {
                int m = l + (r-l);
                if (a[m] == n && (m == a.length-1 || a[m+1] > n)) {
                    return m;
                } else if (a[m] <= n) {
                    return binarySearchFindLast(a, n, m+1, r);
                } else {
                    return binarySearchFindLast(a, n, l, m-1);
                }
            }
            return -1;
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

        public static String serializeBST(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            serializeBST(root, sb);
            return sb.toString();
        }
        private static void serializeBST(TreeNode root, StringBuilder sb) {
            if (root==null) {
                sb.append("#,");
                return;
            }
            sb.append(root.val + ",");
            serializeBST(root.left, sb);
            serializeBST(root.right, sb);
        }

        public static TreeNode deserializeBST(String s) {
            if (s == null || s.length() == 0) {
                return null;
            }
            String[] ss = s.split(",");
            int[] index = new int[1];
            index[0] = 0;
            return deserializeBST(ss, index);
        }
        private static TreeNode deserializeBST(String[] ss, int[] index) {
            if (index[0] >= ss.length) {
                return null;
            }

            String s = ss[index[0]++];
            if (s.equals("#")) {
                return null;
            }
            TreeNode root = new TreeNode(Integer.valueOf(s));

            root.left = deserializeBST(ss, index);
            root.right = deserializeBST(ss, index);
            return root;
        }

        public static TreeNode findLcaBST(TreeNode root, TreeNode n1, TreeNode n2) {
            if (root == null) {
                return null;
            }
            if (n1 == null && n2 == null) {
                return root;
            }
            if (n1 == null) {
                return n2;
            }
            if (n2 == null) {
                return n1;
            }

            if (root.val > n1.val && root.val > n2.val) {
                return findLcaBST(root.left, n1, n1);
            } else if (root.val < n1.val && root.val < n2.val) {
                return findLcaBST(root.right, n1, n1);
            }

            return root;
        }

        public static List<TreeNode> shorestPathBST(TreeNode root, TreeNode n1, TreeNode n2) {
            TreeNode lca = findLcaBST(root, n1, n2);
            if (lca == null) {
                return null;
            }

            List<TreeNode> path =  new ArrayList<>();
            TreeNode lessNode = (n1.val < n2.val) ? n1 : n2;
            TreeNode largerNode = (n1.val > n2.val) ? n1 : n2;

            // find left part of path, save in a stack
            Stack<TreeNode> lpStack = new Stack<>();
            TreeNode current = lca;
            while (current != null) {
                lpStack.push(current);
                if (current.val == lessNode.val) {
                    break;
                } else if (current.val > lessNode.val) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            // build lleft part of path from stack to List
            while (!lpStack.isEmpty()) {
                path.add(lpStack.pop());
            }
            // remove lca, which will be add as right part
            path.remove(path.size()-1);

            // find right part of path
            current = lca;
            while (current != null) {
                path.add(current);
                if (current.val == largerNode.val) {
                    break;
                } else if (current.val > largerNode.val) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            return path;
        }

        public static TreeNode bstToDLL(TreeNode root) {
            if (root == null) {
                return null;
            }

            TreeNode[] head = new TreeNode[1];
            head[0] = null;
            TreeNode[] prev = new TreeNode[1];
            prev[0] = null;
            bstToDLL(root, head, prev);
            return head[0];
        }
        private static void bstToDLL(TreeNode root, TreeNode[] head, TreeNode[] prev) {
            if (root == null) {
                return;
            }

            bstToDLL(root.left, head, prev);

            if (head[0] == null && root.left == null && root.right == null) {
                head[0] = root;
            }

            if (prev[0] != null) {
                prev[0].right = root;
            }
            root.left = prev[0];

            prev[0] = root;
            bstToDLL(root.right, head, prev);
        }

        public static List<TreeNode> findKthSmallest(TreeNode root, int k) {
            List<TreeNode> list = new ArrayList<>();
            if (root == null) {
                return list;
            }
            findKthSmallestHelper(root, k, list);
            return list;
        }
        private static void findKthSmallestHelper(TreeNode root, int k, List<TreeNode> list) {
            if (root == null) {
                return;
            }

            findKthSmallestHelper(root.left, k, list);

            if (list.size() < k) {
                list.add(root);
                findKthSmallestHelper(root.right, k, list);
            }
        }

        public static List<TreeNode> findKthLargest(TreeNode root, int k) {
            List<TreeNode> list = new ArrayList<>();
            if (root == null) {
                return list;
            }
            findKthLargestHelper(root, k, list);
            return list;
        }
        private static void findKthLargestHelper(TreeNode root, int k, List<TreeNode> list) {
            if (root == null) {
                return;
            }

            findKthLargestHelper(root.left, k, list);

            if (list.size() < k) {
                list.add(root);
            } else {
                list.remove(0);
                list.add(root);
            }

            findKthLargestHelper(root.right, k, list);
        }

        public static List<TreeNode> longestPath(TreeNode root) {
            List<TreeNode> lp = new ArrayList<>();
            if (root == null) {
                return lp;
            }

            List<TreeNode> one = new ArrayList<>();
            List<List<TreeNode>> all = new ArrayList<>();

            longestPathHelper(root, one, all, lp);

            for (List<TreeNode> l : all) {
                for (TreeNode n : l) {
                    System.out.print(n.val + " ");
                }
                System.out.println();
            }
            return lp;
        }
        private static void longestPathHelper(TreeNode root, List<TreeNode> one, List<List<TreeNode>> all, List<TreeNode> lp) {
            if (root == null) {
                return;
            }

            one.add(root);

            if (root.left == null && root.right == null) {
                all.add(one);
                if (one.size() > lp.size()) {
                    lp.clear();
                    lp.addAll(one);
                }
                return;
            }

            // List<TreeNode> temp = new ArrayList<>(one);
            longestPathHelper(root.left, new ArrayList<>(one), all, lp);
            // temp = new ArrayList<TreeNode>(one);
            longestPathHelper(root.right, new ArrayList<>(one), all, lp);

        }

        public static TreeNode nthNode(TreeNode root, int n) {
            if (root == null) {
                return null;
            }

            int[] count = new int[1];
            count[0] = 0;
            TreeNode[] node = new TreeNode[1];
            node[0] = null;
            nthNodeHelper(root, n, count, node);

            return node[0];
        }
        private static void nthNodeHelper(TreeNode root, int n, int[] count, TreeNode[] node) {
            if (root == null || node[0] != null) {
                return;
            }
            nthNodeHelper(root.left, n, count, node);

            count[0]++;
            if (count[0] == n) {
                node[0] = root;
                return;
            }

            nthNodeHelper(root.right, n, count, node);

        }

        // public static List<Integer> mergeBST(TreeNode r1, TreeNode r2) {
        //     List<String> list = new ArrayList<>();
        //     if (r1 == null && r2 == null) {
        //         return list;
        //     }

        //     mergeBST(r1, r2, list);
        // }
        // private static void mergeBST(TreeNode r1, TreeNode r2, List<Integer> list) {
        //     if (r1 == null && r2 == null) {
        //         return;
        //     }
        //     mergeBST(r1, r2, list);
        //     if (r1 == null) {
        //         list.add(r1.)
        //     }
        // }

        static class Node {
            int data;
            Node next;

            public Node(int dat) {
                data = dat;
                next = null;
            }
        }

        public static void printList(Node head) {
            while(head != null) {
                System.out.print(head.data+" ");
                head = head.next;
            }
            System.out.println();
        }

        public static boolean contains(Node head, int data) {
            while (head != null) {
                if (head.data == data) {
                    return true;
                }
                head = head.next;
            }
            return false;
        }
        public static Node add(Node head, int data) {
            Node node = new Node(data);
            node.next = head;
            return node;
        }

        public static Node getIntesect(Node l1, Node l2) {
            Node head = null;
            while (l1 != null) {
                if (contains(l2, l1.data)) {
                    head = add(head, l1.data);
                }
                l1 = l1.next;
            }
            return head;
        }

        public static Node getUnion(Node l1, Node l2) {
            Node head = null;
            Node head1 = l1;
            while (l1 != null) {
                head = add(head, l1.data);
                l1 = l1.next;
            }

            while (l2 != null) {
                if (!contains(head1, l2.data)) {
                    head = add(head, l2.data);
                }
                l2 = l2.next;
            }
            return head;
        }

        public static Node printReverse(Node head) {
            if (head == null) {
                return head;
            }
            if (head.next == null) {
                System.out.print(head.data + " ");
                return head;
            }

            Node second = head.next;
            head.next = null;
            Node rest = printReverse(second);
            second.next = head;

            System.out.print(head.data + " ");

            return rest;
        }

        // dp
        public static int factorialDP(int n) {
            // if (n < 0) {
            //     throw IllegalArgumentException("n should be larger than 0");
            // }
            int[] dp = new int[n+1];
            dp[0] = 1;
            for (int i=1; i<=n; ++i) {
                dp[i] = dp[i-1] * i;
            }
            return dp[n];
        }

        public static int maxSum(int[] a) {
            if (a == null || a.length == 0) {
                return 0;
            }
            int maxSum = Integer.MIN_VALUE;
            int sum = 0;
            for (int i=0; i<a.length; ++i) {
                sum += a[i];
                if (sum > maxSum) {
                    maxSum = sum;
                } else {
                    sum -= a[i];
                }
            }
            return maxSum;
        }

        static class SumRectangle {

            int[][] matrix;
            int rowNum;
            int colNum;
            int[][] dp = new int[rowNum][colNum]; // sum (0,0) - (i,j)

            public SumRectangle(int[][] m) {
                if (m == null || m.length == 0 || m[0].length == 0) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                rowNum = m.length;
                colNum = m[0].length;
                this.matrix = new int[rowNum][colNum];
                for (int i = 0; i<rowNum; ++i) {
                    matrix[i] = m[i].clone();
                }

                dp = new int[rowNum][colNum]; // sum (0,0) - (i,j)
                for (int[] row : dp) {
                    Arrays.fill(row, Integer.MIN_VALUE);
                }

                dp[0][0] = matrix[0][0];
                for (int i=1; i<rowNum; ++i) {
                    dp[i][0] = dp[i-1][0] + matrix[i][0];
                }
                for (int i=1; i<rowNum; ++i) {
                    dp[0][i] = dp[0][i-1] + matrix[0][i];
                }
            }

            public int sumRectangle(int i1, int j1, int i2, int j2) {
                if (i1 < 0 || i1 >= rowNum || j1 < 0 || j1 >= colNum ||
                        i2 < 0 || i2 >= rowNum || j2 < 0 || j2 >= colNum) {
                    throw new IllegalArgumentException("invalid arguments");
                }

                return sumRectangle(i2, j2) - sumRectangle(i2, j1) - sumRectangle(i1, j2) +
                        sumRectangle(i1,j1);
            }

            public int sumRectangle(int i, int j) {
                if (dp[i][j] == Integer.MIN_VALUE) {
                    dp[i][j] = sumRectangle(0,0,i,j);
                }
                return dp[i][j];
            }
        }

        // O(n)/O(r)
        public static void rotateArray(int[] a, int r) {
            if (a == null || a.length == 0) {
                return;
            }

            int n = a.length;
            r = r % n;
            if (r == 0) {
                return;
            }

            int[] ra = new int[r];
            System.arraycopy(a, n-r, ra, 0, r);
            System.arraycopy(a, 0, a, r, n-r);
            System.arraycopy(ra, 0, a, 0, r);
        }

        public static PriorityQueue<Integer> leastSetSum(int[] a, int k) {
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            if (a == null || a.length == 0) {
                return pq;
            }

            int sum = 0;
            for (int i=0; i<a.length; ++i) {
                sum += a[i];
                if (sum < k) {
                    pq.add(a[i]);
                } else {
                    int smallest = pq.peek();
                    if (smallest < a[i]) {
                        pq.remove();
                        sum -= smallest;
                        pq.add(a[i]);

                        // maintain pq
                        smallest = pq.peek();
                        while (sum - smallest >= k) {
                            pq.remove();
                            sum -= smallest;
                            smallest = pq.peek();
                        }
                    } else {
                        sum -= a[i];
                    }
                }
            }
            return pq;
        }

        public static String balanceParanthesis(String s) {
            if (s == null || s.length() == 0) {
                return s;
            }

            Stack<Character> stack = new Stack<>();
            Stack<Integer> leftPS = new Stack<>();

            Set<Integer> unpaired = new HashSet<>();
            for (int i = 0; i< s.length(); ++i) {
                char c = s.charAt(i);
                if (c != ')') {
                    stack.push(c);
                    if (c == '(') {
                        leftPS.push(i);
                    }
                } else {
                    boolean paired = false;
                    while (!stack.isEmpty()) {
                        char top = stack.pop();
                        if (top == '(') {
                            leftPS.pop();
                            paired = true;
                            break;
                        }
                    }
                    if (!paired) {
                        unpaired.add(i);
                    }
                }
            }

            while (!stack.isEmpty()) {
                char top = stack.pop();
                if (top == '(') {
                    unpaired.add(leftPS.pop());
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i=0; i<s.length(); ++i) {
                if (!unpaired.contains(i)) {
                    sb.append(s.charAt(i));
                }
            }

            return sb.toString();
        }

        // Given an array of integers, now we want to erase all 0's (can be other value),
        // and we want the result array condensed, meaning no empty cell in the array.
        public static int[] condenseArray(int[] a) {
            if (a == null || a.length == 0) {
                return a;
            }
            int i = 0;
            int j = a.length-1;
            while (i < j) {
                while (a[i] != 0) {
                    i++;
                }
                while (a[j] == 0) {
                    j--;
                }

                if (i < j) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }

            // j is the last no-zero element, so the new array's length is j+1
            return Arrays.copyOf(a, j+1);
        }


        static class Job {
            public int start;
            public int end;
            public int cost;
            public Job(int s, int e, int c) {
                start = s;
                end = e;
                cost = c;
            }
        }
        public static List<Job> maxCostSubset(Job[] jobs) {
            List<Job> maxSubset = new ArrayList<>();
            if (jobs == null || jobs.length == 0) {
                return maxSubset;
            }
            int[] maxCost = new int[1];
            maxCost[0] = 0;
            List<Job> curSubset = new ArrayList<>();
            int[] curCost = new int[1];
            curCost[0] = 0;
            maxCostSubsetHelper(jobs, 0, curCost, curSubset, maxCost, maxSubset);
            return maxSubset;
        }
        private static void maxCostSubsetHelper(Job[] jobs, int si, int[] curCost, List<Job> curSubset,
                                                int[] maxCost, List<Job> maxSubset) {

            if (curCost[0] > maxCost[0]) {
                maxCost[0] = curCost[0];
                maxSubset = new ArrayList<Job>(curSubset);
            }

            for (int i=si; i<jobs.length; ++i) {
                if (!overlap(jobs[i], curSubset)) {

                    curSubset.add(jobs[i]);
                    curCost[0] += jobs[i].cost;

                    maxCostSubsetHelper(jobs, i+1, curCost, curSubset, maxCost, maxSubset);

                    curSubset.remove(curSubset.size()-1);
                    curCost[0] -= jobs[i].cost;
                }
            }
        }
        private static boolean overlap(Job job, List<Job> jobs) {
            for (Job j : jobs) {
                if (overlap(job, j)) {
                    return true;
                }
            }
            return false;
        }
        private static boolean overlap(Job j1, Job j2) {
            return (j1.start < j2.end || j2.start < j1.end);
        }

        public static boolean isPrime(int n) {
            if (n == 0) {
                return false;
            }
            if (n >= 1 && n <= 3) {
                return true;
            }
            if (n % 2 == 0) {
                return false;
            }
            if (n == 5) {
                return true;
            }

            int m = n/2;
            for (int i=3; i<=m; i+=2) {
                if (n % i == 0) {
                    return false;
                }
            }

            return true;
        }

        public static void printPrime(String s) {
            if (s == null || s.length() == 0) {
                return;
            }

            int i = 0;
            while (i < s.length()) {
                if (!Character.isDigit(s.charAt(i))) {
                    i++;
                    continue;
                }

                List<Integer> nums = new ArrayList<>();
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    char c = s.charAt(i++);
                    nums.add(c - '0');
                }

                for (int j=0; j<nums.size(); ++j) {
                    int n = nums.get(j);
                    if (isPrime(n)) {
                        System.out.print(n + " ");
                    }
                    int sum = n;
                    for (int k=j+1; k<nums.size(); ++k) {
                        int m = nums.get(k);
                        sum = sum*10+m;
                        if (isPrime(sum)) {
                            System.out.print(sum + " ");
                        }
                    }
                }
            }
        }

        public static void main (String[] args) {
            // printPrime("abc2134kd31");


            // Job[] jobs = new Job[5];
            // jobs[0] = new Job(1,3,4);
            // jobs[1] = new Job(2,3,5);
            // jobs[2] = new Job(4,5,4);
            // jobs[3] = new Job(7,9,7);
            // jobs[4] = new Job(3,6,14);
            // List<Job> mset = maxCostSubset(jobs);
            // System.out.println(mset.size());
            // for (Job j : mset) {
            //       System.out.println(j.start + "," + j.end +","+j.cost);
            //     }

            // int[] a = {0,1,0,2,0,3,0,4,0,5,7,0,8,0,0};
            // a = condenseArray(a);
            //     for (int i : a) {
            //       System.out.print(i + " ");
            //     }

            // int[] a = {-2,-3,1,2,3,4,5,6};
            // PriorityQueue<Integer> pq = leastSetSum(a,6);
            // Iterator<Integer> it = pq.iterator();
            //     while (it.hasNext()) {
            //         System.out.print(it.next() + " ");
            //     }

            // int[] a = {1,-2,5,3,-6,4,-2};
            // System.out.println(maxSum(a));

            //  System.out.println(factorialDP(4));

            int[] a = {20,10,5,15,30,25,35};

            TreeNode root = null;
            for (int i : a) {
                root = insert(root, new TreeNode(i));
            }

            System.out.println(nthNode(root,1).val);
            System.out.println(nthNode(root,2).val);
            System.out.println(nthNode(root,3).val);

//   System.out.print(root.val + " " + root.left.val + " " + root.right.val);
            // 	List<TreeNode> p = longestPath(root);
            // for (TreeNode n : p) {
            //     System.out.print(n.val + " ");
            // }

            //   List<TreeNode> p = findKthSmallest(root, 3);
            // for (TreeNode n : p) {
            //     System.out.print(n.val + " ");
            // }
            //  System.out.println();
            // p = findKthLargest(root, 3);
            // for (TreeNode n : p) {
            //     System.out.print(n.val + " ");
            // }
            //     TreeNode dll = bstToDLL(root);
            //     while (dll != null) {
            //         System.out.print(dll.val + " ");
            //         dll = dll.right;
            //     }

            // System.out.println(root.val + " " + root.left.val + " " + root.right.val);

            // List<TreeNode> p = shorestPathBST(root, new TreeNode(15), new TreeNode(25));
            // for (TreeNode n : p) {
            //     System.out.print(n.val + " ");
            // }

            // Node l = new Node(9);
            // l = add(l,7);
            // l = add(l,8);
            // l = add(l,5);
            // l = add(l,3);
            // l = add(l,2);
            // l = add(l,1);

            // Node ll = l;
            // printList(ll);
            // printReverse(l);

            // Node l2 = new Node(8);
            // l2 = add(l2,6);
            // l2 = add(l2,5);
            // l2 = add(l2,4);
            // l2 = add(l2,2);
            // l2 = add(l2,10);

            //     printList(l);
            //     printList(l2);

            //     Node ll = l;
            //     Node ll1 = l2;
            //     Node li = getIntesect(ll, ll1);
            //     printList(li);

            //     ll = l;
            //     ll1 = l2;
            //     Node lu = getUnion(ll, ll1);
            //     printList(lu);


            // int[] a = {1,2,2,2,2,2,2,3,3,5,6,7,8,8,8,9};
// 		System.out.println(binarySearchFindFirst(a, 3, 0, a.length-1));
// 		System.out.println(findFirst(a,2));

            // int[] a = {1,9,5,7,21,15,17,13};

            // TreeNode root = null;
            // for (int i : a) {
            //     root = insert(root, new TreeNode(i));
            // }

            // printInOrder(root);
            // System.out.println();


            // String s = serializeBST(root);
            // System.out.println(s);

            // TreeNode r = deserializeBST(s);
            // printInOrder(r);
            // System.out.println();

        }
    }