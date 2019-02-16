package com.comcast.algorithms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Created by zzhou200 on 7/24/15.
 */
public class BST {
    private TreeNode root;

    public BST(TreeNode r) {
        root = r;
    }

    public TreeNode getRoot() {
        return root;
    }

    // Time: O(n)
    // Space: O(1) but calling stack
    public static boolean isBST(TreeNode root) {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static boolean isBST(TreeNode root, int min, int max) {
        if (root == null)
            return true;

        int val = root.getData();
        if (min >= val || val >= max)
            return false;

        return isBST(root.getLeft(), min, val) && isBST(root.getRight(), val, max);
    }

    /*
        Another solution is to do an in-order traversal of the binary tree, and verify that the previous value
        (can be passed into the recursive function as reference) is less than the current value. This works because
        when you do an in-order traversal on a BST, the elements must be strictly in increasing order.

        This method also runs in O(N) time and O(1) space.
    */
    // Time: O(n)
    // Space: O(1) but calling stack
    public static boolean isBST2(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        prev[0] = null;
        return isBST2(root, prev);
    }
    // use inorder traversal with recursive
    private static boolean isBST2(TreeNode root, TreeNode[] prev) {
        if (root == null)
            return true;

        // left branch
        if (!isBST2(root.getLeft(), prev))
            return false;

        // root is current
        if (prev[0] != null) {
            if (root.getData() <= prev[0].getData())
                return false;
        }

        // right branch

        // for right branch, root is the prev at start
        prev[0] = root;
        if (!isBST2(root.getRight(), prev))
            return false;

        return true;
    }

    // use inorder traversal with stack and iterative
    // Time: O(n)
    // Space: O(n)
    public static boolean isBST3(TreeNode root) {
        if (root == null)
            return true;

        java.util.Stack<TreeNode> stack = new java.util.Stack<>();

        TreeNode prev = null;
        TreeNode current = root; // used to track nodes

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                // no left node any more

                // pop a node from stack and process it
                current = stack.pop();
                /////
                if (prev != null) {
                    if (current.getData() <= prev.getData())
                        return false;
                }

                // current will be prev
                prev = current;

                // then process the right node
                current = current.right;
            }
        }

        return true;
    }

    // preorder traversal
    public static void serialize(TreeNode root, DataOutputStream dos) throws IOException {
        if (root == null)
            return;
        dos.writeInt(root.getData());
        serialize(root.left, dos);
        serialize(root.right, dos);
    }

    // preorder traversal
    public static TreeNode deserialize(DataInputStream dis) throws IOException {
        if (dis.available() <= 0)
            return null;

        int[] current = new int[1];
        current[0] = dis.readInt();
        return deserialize(Integer.MIN_VALUE, Integer.MAX_VALUE, current, dis);
    }
    private static TreeNode deserialize(int min, int max, int[] current, DataInputStream dis) throws IOException {
        // not fit
        if (current[0] <= min || current[0] >= max)
            return null;

        // current is root value, and max for left, and min for right
        int rootValue = current[0];

        // build root
        TreeNode root = new TreeNode(rootValue);

        // end of file?
        if (dis.available() <= 0)
            return root;

        // read next value from file
        current[0] = dis.readInt();

        // use the same value to fit left first, if failed then try right
        root.left = deserialize(min, rootValue, current, dis);
        root.right = deserialize(rootValue, max, current, dis);

        return root;
    }

    public static TreeNode findInorderSuccessor(TreeNode root, TreeNode node) {
        if (root == null || node == null) {
            return null;
        }

        if (node.right != null) {
            return findMostLeft(node.right);
        }

        return findFromRoot(root, node);
    }
    static TreeNode findMostLeft(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node != null) {
            node = node.left;
        }
        return node;
    }
    static TreeNode findFromRoot(TreeNode root, TreeNode node) {
        TreeNode current = root;
        TreeNode successor = null;
        while (current != null) {
            if (current.val == node.val) {
                break;
            }

            if (current.val > node.val) {
                successor = current;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return successor;
    }

    // the middle element in a sorted array is the root value of the BST
    // low part is left, and high part is right
    public static TreeNode sortedArrayToBST(int[] a, int start, int end) {
        if (start > end)
            return null;

        int mid = end + (end-start)/2;
        int rootVal = a[mid];

        TreeNode root = new TreeNode(rootVal);

        root.left = sortedArrayToBST(a, start, mid - 1);
        root.right = sortedArrayToBST(a, mid + 1, end);

        return root;
    }

    public static TreeNode sortedListToBST(Node head) {
        Node[] l = new Node[1];
        l[0] = head;
        int end = getLinkedListLength(head)-1;
        return sortedListToBST(l, 0, end);
    }
    private static int getLinkedListLength(Node head) {
        int len = 0;
        Node p = head;

        while (p != null) {
            len++;
            p = p.getNext();
        }
        return len;
    }
    private static TreeNode sortedListToBST(Node[] l, int start, int end) {
        if (start > end)
            return null;

        int mid = start + (end-start)/2;

        // build left from the head of list
        TreeNode left = sortedListToBST(l, start, mid-1);

        // now current list node in l[0] is the root
        TreeNode root = new TreeNode(l[0].getData());

        // build right from next node
        l[0] = l[0].getNext();
        TreeNode right = sortedListToBST(l, mid+1, end);

        root.setLeft(left);
        root.setRight(right);

        return root;
    }

    /*

        Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.

    For example,
    Given n = 3, your program should return all 5 unique BST's shown below.

       1         3     3      2      1
        \       /     /      / \      \
         3     2     1      1   3      2
        /     /       \                 \
       2     1         2                 3
     */

    public static int numBSTs(int n) {
        if (n < 0)
            return 0;

        // init
        int[] num = new int[n+1];
        num[0] = 1;
        // build DP map
        for (int i=1; i<=n; ++i) {
            for (int j=0; j<i; ++j) {
                num[i] += num[j] * num[i-1-j];
            }
        }
        return num[n];
    }

    public static ArrayList<TreeNode> generateBSTs(int n) {
        return generateBSTs(1, n);
    }
    private static ArrayList<TreeNode> generateBSTs(int start, int end) {
        ArrayList<TreeNode> list = new ArrayList<>();

        if (start > end) {
            list.add(null);
            return list;
        }

        for (int i=start; i<=end; ++i) {
            // i is the root
            // all possible lift trees
            ArrayList<TreeNode> lefts = generateBSTs(start, i-1);
            // all possible right trees
            ArrayList<TreeNode> rights = generateBSTs(i+1, end);

            // all possible combinations
            for (TreeNode lt : lefts) {
                for (TreeNode rt : rights) {
                    TreeNode root = new TreeNode(i);
                    root.left = lt;
                    root.right = rt;
                    list.add(root);
                }
            }
        }

        return list;
    }

        /*
    Given a binary search tree, write a function kthSmallest to find the kth smallest element in it. (1 ≤ k ≤ BST's total elements)
     */

    // inorder traversal, the kth element is the kth smallest
    public static TreeNode kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();

        TreeNode current = root;

        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode node = stack.pop();
                k--;
                if (k == 0)
                    return node;
                current = node.right;
            }
        }
        return null;
    }

    /*
    Given values of two nodes in a Binary Search Tree, write a c program to find the Lowest Common Ancestor (LCA). You may assume that both the values exist in the tree.

    We can solve this problem using BST properties. We can recursively traverse the BST from root. The main idea of the solution is,
    while traversing from top to bottom, the first node n we encounter with value between n1 and n2, i.e., n1 < n < n2 or same as one of the n1 or n2,
    is LCA of n1 and n2 (assuming that n1 < n2).

    So just recursively traverse the BST in, if node's value is greater than both n1 and n2 then our LCA lies in left side of the node,
    if it's is smaller than both n1 and n2, then LCA lies on right side. Otherwise root is LCA (assuming that both n1 and n2 are present in BST)
     */

    // Time: O(h) where h is height of tree
    // Space: O(1) with calling stack
    public static TreeNode findLCA(TreeNode root, TreeNode n1, TreeNode n2) {
        if (root == null)
            return null;
        if (n1 == null && n2 == null)
            return root;
        if (n1 == null)
            return n2;
        if (n2 == null)
            return n1;

        // If both n1 and n2 are smaller than root, then LCA lies in left
        if (root.val > n1.val && root.val > n2.val)
            return findLCA(root.left, n1, n2);

        // If both n1 and n2 are greater than root, then LCA lies in right
        if (root.val < n1.val && root.val < n2.val)
            return findLCA(root.right, n1, n2);

        // root in between n1 and n2, is the LCA
        return root;
    }

    // insert a node
    public static TreeNode insert(TreeNode root, int v) {
        if (root == null)
            return new TreeNode(v);

        if (v < root.getData())
            root.left = insert(root.left, v);
        else if (v > root.getData())
            root.right = insert(root.right, v);

        return root;
    }

    // remove a node
    public static TreeNode remove(TreeNode root, int v) {
        // no found
        if (root == null)
            return null;

        if (v < root.getData())
            root.left = remove(root.left, v);   // target is on left
        else if (v > root.getData())
            root.right = remove(root.right, v); // target is on right
        else {
            // root is the one to be removed: v == root.getData()

            if (root.left == null)
                root = root.right;          // only has right child
            else if (root.right == null)
                root = root.left;           // only has left child
            else {
                // has two children

                // find the min node in right child
                TreeNode minNode = findMin(root.right);
                // set the target's value as the min node's value
                root.val = minNode.val;
                // remove the min node in right child
                root.right = remove(root.right, minNode.val);
            }
        }

        return root;
    }

    // find the node with min value
    public static TreeNode findMin(TreeNode root) {
        if (root == null)
            return null;
        if (root.left == null)
            return root;
        return findMin(root.left);
    }

    // find the node with max value
    public static TreeNode findMax(TreeNode root) {
        if (root == null)
            return null;
        if (root.right == null)
            return root;
        return findMax(root.right);
    }

    // find a node
    public static TreeNode find(TreeNode root, int v) {
        if (root == null)
            return null;

        if (v == root.getData())
            return root;

        if (v < root.getData())
            return find(root.left, v);

        return find(root.right, v);
    }

    public static void bstSort(int[] a) {
        TreeNode root = null;
        for (int i=0; i<a.length; ++i)
            root = BST.insert(root, a[i]);
        inOrderTraversal(root, 0, a);
    }

    public static int inOrderTraversal(TreeNode root, int istart, int[] a) {
        if (root == null) {
            return istart;
        }
        istart = inOrderTraversal(root.left, istart, a);
        a[istart++] = root.val;
        return inOrderTraversal(root.right, istart, a);
    }

    /*
    Two elements of a binary search tree (BST) are swapped by mistake.
    Recover the tree without changing its structure.
    Note:

    A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?

    两个node互换。举个例子，tree inorder：1，2，3，4，5，6，7
    有可能会有1对降序：1，2，4，3，5，6，7。也有可能会有两对降序：1，6，3，4，5，2，7
    但是无论如何，第一次降序的大的和最后一次降序小的就是对调的两个。
     */
    public static boolean recoverBST(TreeNode root) {
        // find swapped nodes
        TreeNode[] prev = new TreeNode[1];
        prev[0] = null;
        TreeNode[] first = new TreeNode[1];
        first[0] = null;
        TreeNode[] second = new TreeNode[1];
        second[0] = null;
        findSwappedNodes(root, prev, first, second);
        if (first[0] == null || second[0] == null)
            return false;

        // swap swapped nodes to recover
        int tmp = first[0].val;
        first[0].val = second[0].val;
        second[0].val = tmp;
        return true;
    }
    // inOrder traversal
    // Time: O(n)
    // Space: O(1) - calling stack
    private static void findSwappedNodes(TreeNode root, TreeNode[] prev, TreeNode[] first, TreeNode[] second) {
        if (root == null)
            return;

        // find in left
        findSwappedNodes(root.left, prev, first, second);

        // check current, if current not bigger than prev, then it was swapped
        if (prev[0] != null) {
            if (root.val <= prev[0].val) {
                if (first[0] == null)
                    first[0] = prev[0]; // first is always the bigger one
                else {
                    second[0] = root;   // second is always the smaller one
                }
            }
        }

        // find in right

        // root is prev for right
        prev[0] = root;
        findSwappedNodes(root.right, prev, first, second);
    }

    /*
    Given a bst with integer values as keys how do I find the closest node to that key in a bst ?
    The BST is represented using a object of nodes (Java). Closest will be for eg 4,5,9 and
    if the key is 6 it will return 5
     */

    public static TreeNode findClosestNode(TreeNode root, int target) {
        if (root == null) {
            throw new IllegalArgumentException("BST is null");
        }

        TreeNode closest = null;
        int minDif = Integer.MAX_VALUE;

        TreeNode current = root;
        while (current != null) {
            if (current.val == target) {
                return current;
            } else if (current.val < target) {
                //check current
                int dif = target - current.val;
                if (dif < minDif) {
                    minDif = dif;
                    closest = current;
                }
                //check right before go to left
                if (current.right != null) {
                    dif = Math.abs(target - current.right.val);
                    if (dif < minDif) {
                        minDif = dif;
                        closest = current.right;
                    }
                }
                // check lefy
                current = current.left;
            } else if (current.val > target) {
                //check current
                int dif = current.val - target;
                if (dif < minDif) {
                    minDif = dif;
                    closest = current;
                }
                //check left before go to right
                if (current.left != null) {
                    dif = Math.abs(current.left.val - target);
                    if (dif < minDif) {
                        minDif = dif;
                        closest = current.left;
                    }
                }
                // check right
                current = current.right;
            }
        }

        return closest;
    }

    public static TreeNode findClosest(TreeNode root, int target) {
        TreeNode[] goal = new TreeNode[1];
        int[] min = new int[1];
        min[0] = Integer.MAX_VALUE;
        findClosest(root, target, goal, min);
        return goal[0];
    }
    public static void findClosest(TreeNode root, int target, TreeNode[] goal, int[] min) {
        if (root == null) {
            return;
        }

        if (root.val == target) {
            goal[0] = root;
            min[0] = 0;
            return;
        }

        int dif = Math.abs(root.val - target);
        if (dif < min[0]) {
            goal[0] = root;
            min[0] = dif;
        }

        if (target < root.val) {
            findClosest(root.left, target, goal, min);
        } else {
            findClosest(root.right, target, goal, min);
        }
    }

    public static TreeNode findClosest2(TreeNode root, int target) {
        int min = Integer.MAX_VALUE;
        TreeNode goal = null;

        while (root != null) {
            if (target < root.val) {
                int dif = Math.abs(target-root.val);
                if (dif < min) {
                    min = dif;
                    goal = root;
                }
                root = root.left;
            } else if (target > root.val) {
                int dif = Math.abs(target-root.val);
                if (dif < min) {
                    min = dif;
                    goal = root;
                }
                root = root.right;
            } else {
                return root;
            }
        }

        return goal;
    }

    public static ArrayList<TreeNode> findClosestKNodes(TreeNode root, int target, int k) {
        ArrayList<TreeNode> list = new ArrayList<>();
        findClosestKNodes(root, target, k, list);
        return list;
    }

    public static void findClosestKNodes(TreeNode root, int target, int k, ArrayList<TreeNode> list ) {
        if (root == null) {
            return;
        }

        findClosestKNodes(root.left, target, k, list);

        if (list.size() < k) {
            list.add(root);
        } else {
            if (Math.abs(target-list.get(0).val) > Math.abs(root.val - target)) {
                list.remove(0);
                list.add(root);
            } else {
                return;
            }
        }

        findClosestKNodes(root.right, target, k, list);

    }

//    public static TreeNode findSecondClosestNode(TreeNode root, int target) {
//        if (root == null) {
//            throw new IllegalArgumentException("BST is null");
//        }
//
//        TreeNode closest = null;
//        int minDif = Integer.MAX_VALUE;
//
//        TreeNode secondClosest = null;
//        int secondMinDif = Integer.MAX_VALUE;
//
//        TreeNode current = root;
//        while (current != null) {
//            if (current.val == target) {
//                if (current.left != null && current.right != null) {
//                    int difLeft = current.val-current.left.val;
//                    int difRight = current.right.val-current.val;
//                    if (difLeft > difRight) {
//                        return current.left;
//                    }
//                    else {
//                        return current.right;
//                    }
//                } else if (current.left != null) {
//                    if (current.val-current.left.val > )
//                }
//            } else if (current.val < target) {
//                current = current.left;
//                dif = target - current.val;
//            } else if (current.val > target) {
//                current = current.right;
//                dif = current.val - target;
//            }
//
//            int dif = Math.abs(target - current.val);
//            if (dif < minDif) {
//                // update second closest to closest
//                secondClosest = closest;
//                secondMinDif = minDif;
//                // update closest to current
//                closest = current;
//                minDif = dif;
//            } else if (dif < secondMinDif) {
//                // update second closest to current
//                secondClosest = current;
//                secondMinDif = dif;
//            }
//        }
//
//        return secondClosest;
//    }

    /*
    Kth largest node in BST

    do reverse-inorder traversal: right-root-left
     */

    public static TreeNode kthLargest(TreeNode root, int k) {
        int[] count = new int[1];
        count[0] = k;
        return kthLargest(root, k, count);
    }
    public static TreeNode kthLargest(TreeNode root, int k, int[] count) {
        if (root == null || count[0] > k)
            return null;

        // right first
        TreeNode node = kthLargest(root.right, k, count);
        // found
        if (node != null)
            return node;

        // root
        count[0] = count[0] + 1;
        // found
        if (count[0] == k)
            return root;

        // left
        return kthLargest(root.left, k, count);
    }
}
