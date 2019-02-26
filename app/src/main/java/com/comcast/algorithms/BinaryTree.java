package com.comcast.algorithms;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by zzhou200 on 7/22/15.
 */
public class BinaryTree {
    private TreeNode root;

    public BinaryTree(TreeNode r) {
        root = r;
    }

    public TreeNode getRoot() {
        return root;
    }

    public ArrayList<TreeNode> preOrderTraversal() {
        ArrayList<TreeNode> result = new ArrayList<>();
        preOrderTraversal(root, result);
        return result;
    }

    public static void preOrderTraversal(TreeNode root, ArrayList<TreeNode> result) {
        if (root == null)
            return;

        result.add(root);
        preOrderTraversal(root.getLeft(), result);
        preOrderTraversal(root.getRight(), result);
    }

    public ArrayList<TreeNode> inOrderTraversal() {
        ArrayList<TreeNode> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    public static void inOrderTraversal(TreeNode root, ArrayList<TreeNode> result) {
        if (root == null)
            return;

        inOrderTraversal(root.getLeft(), result);
        result.add(root);
        inOrderTraversal(root.getRight(), result);
    }


    public ArrayList<TreeNode> postOrderTraversal() {
        ArrayList<TreeNode> result = new ArrayList<>();
        postOrderTraversal(root, result);
        return result;
    }

    public static void postOrderTraversal(TreeNode root, ArrayList<TreeNode> result) {
        if (root == null)
            return;

        postOrderTraversal(root.getLeft(), result);
        postOrderTraversal(root.getRight(), result);
        result.add(root);
    }

    public ArrayList<ArrayList<TreeNode>> levelOrderTraversal() {
        return levelOrderTraversal(root);
    }

    // BFS
    public static ArrayList<ArrayList<TreeNode>> levelOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        ArrayList<TreeNode> nodesCurrentLevel = new ArrayList<>();

        if (root == null)
            return result;

        Queue<TreeNode> q = new LinkedList<>();
        int nodeNumCurrentLevel = 0;
        int nodeNumNextLevel = 0;

        q.add(root);
        nodeNumCurrentLevel++;

        while (!q.isEmpty()) {
            TreeNode current = q.remove();
            nodeNumCurrentLevel--;
            nodesCurrentLevel.add(current);

            TreeNode left = current.getLeft();
            if (left != null) {
                q.add(left);
                nodeNumNextLevel++;
            }

            TreeNode right = current.getRight();
            if (right != null) {
                q.add(right);
                nodeNumNextLevel++;
            }

            // end of a level?
            if (nodeNumCurrentLevel == 0) {
                // add current level nodes to result
                result.add(nodesCurrentLevel);

                // create new list to store next level nodes
                nodesCurrentLevel = new ArrayList<>();

                // next level will be current level
                nodeNumCurrentLevel = nodeNumNextLevel;
                // clear next level node count
                nodeNumNextLevel = 0;
            }
        }
        return result;
    }

    /*
    Given a binary tree, print out the tree in zig zag level order (ie, from left to right, then right to left for the next level and alternate between). Output a newline after the end of each level.

         3
       /  \
      9   20
         /  \
        15    7

    For example, the zig zag level order output of the tree above is:

    3
    20 9
    15 7
     */
    public static ArrayList<ArrayList<TreeNode>> zigzagOrderTraversal(TreeNode root) {
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        ArrayList<TreeNode> nodesCurrentLevel = new ArrayList<>();

        if (root == null)
            return result;

        Queue<TreeNode> q = new LinkedList<>();
        int nodeNumCurrentLevel = 0;
        int nodeNumNextLevel = 0;

        Stack<TreeNode> reverseLevelStack = new Stack<>();
        int level = 0;

        q.add(root);
        nodeNumCurrentLevel++;

        while (!q.isEmpty()) {
            TreeNode current = q.remove();
            nodeNumCurrentLevel--;
            if (level % 2 == 0)
                nodesCurrentLevel.add(current);
            else
                reverseLevelStack.push(current);

            TreeNode left = current.getLeft();
            if (left != null) {
                q.add(left);
                nodeNumNextLevel++;
            }

            TreeNode right = current.getRight();
            if (right != null) {
                q.add(right);
                nodeNumNextLevel++;
            }

            // end of a level?
            if (nodeNumCurrentLevel == 0) {

                // reverse the level need to be reversed
                if (level % 2 == 1) {
                    while (!reverseLevelStack.isEmpty())
                        nodesCurrentLevel.add(reverseLevelStack.pop());
                    // clear the reverse stack
                    reverseLevelStack.clear();
                }

                // add to result
                result.add(nodesCurrentLevel);

                // create new list to store next level nodes
                nodesCurrentLevel = new ArrayList<>();

                // next level will be current level
                nodeNumCurrentLevel = nodeNumNextLevel;
                // clear next level node count
                nodeNumNextLevel = 0;
                // increase level
                level++;
            }
        }
        return result;
    }

    public static ArrayList<ArrayList<TreeNode>> columnwiseOrderTraversal(TreeNode root) {
        HashMap<Integer, ArrayList<TreeNode>> map = new HashMap<>();
        columnwiseOrderTraversal(root, 0, map);

        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        int cStart = -1 * map.keySet().size();
        int cEnd = map.keySet().size();
        for (int i = cStart; i <= cEnd; ++i) {
            ArrayList<TreeNode> ns = map.get(i);
            if (ns != null)
                result.add(ns);
        }

        return result;
    }

    private static void columnwiseOrderTraversal(TreeNode root, int horizontalDistanceFromRoot, HashMap<Integer, ArrayList<TreeNode>> map) {
        if (root == null)
            return;

        ArrayList<TreeNode> nodes = map.get(horizontalDistanceFromRoot);
        if (nodes == null) {
            nodes = new ArrayList<>();
            nodes.add(root);
            map.put(horizontalDistanceFromRoot, nodes);
        } else
            nodes.add(root);

        columnwiseOrderTraversal(root.left, horizontalDistanceFromRoot - 1, map);
        columnwiseOrderTraversal(root.right, horizontalDistanceFromRoot + 1, map);
    }

    public static void print(TreeNode root) {
        ArrayList<ArrayList<TreeNode>> nl = levelOrderTraversal(root);
        for (ArrayList<TreeNode> l : nl) {
            for (TreeNode n : l) {
                System.out.print(n.getData() + " ");
            }
            System.out.print("\n");
        }
    }

//    public void serialize(String fileName) throws IOException {
//        serialize(root, new FileOutputStream(fileName));
//    }
//    public void deserialize(String fileName) throws IOException {
//        root = deserialize(new FileInputStream(fileName));
//    }

    // predorder traverse
    // use special value (such as Integer.MIN_VALUE) to represent null node
    public static void serialize(TreeNode root, DataOutputStream fs) throws IOException {
        if (root == null) {
            fs.writeInt(Integer.MIN_VALUE);
            return;
        }

        fs.writeInt(root.getData());

        serialize(root.getLeft(), fs);
        serialize(root.getRight(), fs);
    }

    public static TreeNode deserialize(DataInputStream fs) throws IOException {
        if (fs.available() <= 0)
            return null;

        int token = fs.readInt();

        // is leaf?
        if (token == Integer.MIN_VALUE)
            return null;

        TreeNode root = new TreeNode(token);
        root.left = deserialize(fs);
        root.right = deserialize(fs);

        return root;
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("null,");
            return;
        }
        sb.append(root.val + ",");
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    public static TreeNode deserialize(String data) {
        String[] ss = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(ss));
        return deserialize(queue);
    }

    static TreeNode deserialize(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }

        String s = queue.remove();
        if (s.equals("null")) { // empty leaf
            return null;
        }

        TreeNode root = new TreeNode(Integer.valueOf(s));
        root.left = deserialize(queue);
        root.right = deserialize(queue);
        return root;
    }

    // rebuild binary with both inorder and post order traversal arrays
    // assume each node value is different from other nodes
    public static TreeNode buildTree(int[] inorder, int[] postorder) throws Exception {
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    public static TreeNode buildTree(int[] inorder, int istart, int iend, int[] postorder, int pstart, int pend) throws Exception {
        if (istart > iend || pstart > pend)
            return null;

        if (iend - istart != pend - pstart)
            throw new Exception("inorder and postorder arrays are not for the same tree.");

        // the last element in postorder array is root
        int rootValue = postorder[pend];

        // find the root in inorder array
        // if there are 2 nodes with the same value, this step will fail
        int rootIndexInorder = 0;
        for (rootIndexInorder = istart; rootIndexInorder <= iend; ++rootIndexInorder) {
            if (rootValue == inorder[rootIndexInorder])
                break;
        }
        if (rootIndexInorder > iend)
            throw new Exception("inorder and postorder arrays are not for the same tree.");

        // build root
        TreeNode root = new TreeNode(rootValue);

        // in inorder array, all elements whose index less than root's index construct the left subtree,
        // and the right subtree is constructed by all other elements on the other side
        // [left](root)[right]
        //
        // in postorder array, the first leftSize elements construct the left subtree,
        // and the rightSize elements after them construct the right subtree
        // [left][right](root)

        int leftSize = rootIndexInorder - istart;
//        int rightSize = iend - rootIndexInorder;

        // build left
        int leftInorderStart = istart;
        int leftInorderEnd = rootIndexInorder - 1;
        int leftPostorderStart = pstart;
        int leftPostorderEnd = pstart + leftSize - 1;
        TreeNode left = buildTree(inorder, leftInorderStart, leftInorderEnd, postorder, leftPostorderStart, leftPostorderEnd);

        // build right
        int rightInorderStart = rootIndexInorder + 1;
        int rightInorderEnd = iend;
        int rightPostorderStart = leftPostorderEnd + 1;
        int rightPostorderEnd = pend - 1;
        TreeNode right = buildTree(inorder, rightInorderStart, rightInorderEnd, postorder, rightPostorderStart, rightPostorderEnd);

        root.setLeft(left);
        root.setRight(right);
        return root;
    }

    // like preorder traversal
    // Time: O(n)
    // Memory: O(1) - but need calling stack
    public static TreeNode flattenAsLinkedListWithRecursive(TreeNode root) {
        if (root == null)
            return root;

        // backup right
        TreeNode r = root.right;

        // flatten left branch and move to right
        if (root.left != null) {
            root.right = root.left;
            root.left = null;
            // flatten the new right (original left) and set its last node as root
            root = flattenAsLinkedListWithRecursive(root.right);
        }

        // root either be the orignal root (if without left) or the last node of flattened left

        // flatten right branch and add to flattened left branch
        if (r != null) {
            root.right = r;
            // flatten right and set its last node as root
            root = flattenAsLinkedListWithRecursive(root.right);
        }

        // return last node saved in root
        return root;
    }

    // like preorder traversal
    // Time: O(n)
    // Memory : O(n)
    public static void flattenAsLinkedListWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        TreeNode current = root;
        while (current != null) {
            if (current.getLeft() != null) {
                // push all right nodes in left to stack
                if (current.getRight() != null)
                    stack.push(current.getRight());
                // set left as right
                current.setRight(current.getLeft());
                // trim left
                current.setLeft(null);
            }

            // if current has no right then pop right node from stack
            if (current.getRight() == null && !stack.isEmpty())
                current.setRight(stack.pop());

            // process right node
            current = current.getRight();
        }
    }

    // Time: O(n)
    // Memory: O(1)
    public static void flattenAsLinkedList(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            if (current.getLeft() != null) {
                if (current.getRight() != null) {
                    // find the right most node RM in left branch
                    TreeNode next = current.getLeft();
                    while (next.getRight() != null)
                        next = next.getRight();
                    // move current's right as RM's right
                    next.setRight(current.getRight());
                }
                // move left as right
                current.setRight(current.getLeft());
                current.setLeft(null);
            }
            current = current.getRight();
        }
    }

    /*
        Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

        For example, given the below binary tree and sum = 22,

                      5
                     / \
                    4   8
                   /   / \
                  11  13  4
                 /  \    / \
                7    2  5   1
        the method returns the following:

        [
           [5,4,11,2],
           [5,8,4,5]
        ]
     */

    // BFS with queue
    // Time: O(n)
    // Space: O(n)
    public static ArrayList<ArrayList<TreeNode>> pathSumBFS(TreeNode root, int sum) {
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();

        if (root == null)
            return result;

        HashMap<TreeNode, TreeNode> prev = new HashMap<>();
        Queue<TreeNode> qNodes = new LinkedList<>();
        Queue<Integer> qValues = new LinkedList<>();

        qNodes.add(root);
        qValues.add(root.getData());
        prev.put(root, null);

        while (!qNodes.isEmpty()) {
            TreeNode current = qNodes.remove();
            int sumVal = qValues.remove();

            if (current.getLeft() == null && current.getRight() == null && sumVal == sum) {
                // retrieve path
                ArrayList<TreeNode> path = new ArrayList<>();
                for (TreeNode node = current; node != null; node = prev.get(node))
                    path.add(node);
                Collections.reverse(path);
                result.add(path);
            }

            TreeNode left = current.getLeft();
            if (left != null) {
                qNodes.add(left);
                qValues.add(sumVal + left.getData());
                prev.put(left, current);
            }

            TreeNode right = current.getRight();
            if (right != null) {
                qNodes.add(right);
                qValues.add(sumVal + right.getData());
                prev.put(right, current);
            }
        }

        return result;
    }

    // DFS recursive
    // Time: O(n)
    // Space: O(n) for calling stack
    public static ArrayList<ArrayList<TreeNode>> pathSumDFSRecursive(TreeNode root, int sum) {
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        if (root == null)
            return result;

        ArrayList<TreeNode> path = new ArrayList<>();
        path.add(root);
        pathSumDFSRecursive(root, sum - root.getData(), result, path);

        return result;
    }

    private static void pathSumDFSRecursive(TreeNode root, int remain, ArrayList<ArrayList<TreeNode>> result, ArrayList<TreeNode> path) {
        if (root == null)
            return;

        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();

        // found a valid path
        if (left == null && right == null && remain == 0) {
            ArrayList<TreeNode> temp = new ArrayList<>();
            temp.addAll(path);
            result.add(temp);
            return;
        }

        // left branch
        if (left != null) {
            path.add(left);
            pathSumDFSRecursive(left, remain - left.getData(), result, path);
            path.remove(path.size() - 1);
        }

        // right branch
        if (right != null) {
            path.add(right);
            pathSumDFSRecursive(right, remain - right.getData(), result, path);
            path.remove(path.size() - 1);
        }
    }

    // DFS recursive
    // Time: O(n)
    // Memory: O(1)
    public static int minDepth(TreeNode root) {
        if (root == null)
            return 0;

        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();

        if (left == null && right == null)
            return 1;

        return 1 + Math.min(minDepth(left), minDepth(right));
    }

    // BFS with queue
    // Time: O(n) worst case
    // Memory: O(n)
    public static int minDepthBFS(TreeNode root) {
        if (root == null)
            return 0;

        int depth = 0;
        Queue<TreeNode> q = new LinkedList<>();
        int nodeNumCurrentLevel = 0;
        int nodeNumNextLevel = 0;

        q.add(root);
        nodeNumCurrentLevel++;

        while (!q.isEmpty()) {
            TreeNode current = q.remove();
            nodeNumCurrentLevel--;

            TreeNode left = current.getLeft();
            if (left != null) {
                q.add(left);
                nodeNumNextLevel++;
            }
            TreeNode right = current.getRight();
            if (right != null) {
                q.add(right);
                nodeNumNextLevel++;
            }

            if (left == null && right == null)
                return 1 + depth;

            if (nodeNumCurrentLevel == 0) {
                depth++;
                nodeNumCurrentLevel = nodeNumNextLevel;
                nodeNumNextLevel = 0;
            }
        }

        return depth;
    }

    // DFS
    // Time: O(n)
    public static int getTreeHeight(TreeNode root) {
        if (root == null)
            return 0;

        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();

        if (left == null && right == null)
            return 1;

        return 1 + Math.max(getTreeHeight(left), getTreeHeight(right));
    }

    // Given a binary tree in which each node element contains a number.
    // Find the maximum possible sum from one leaf node to another.

    public static int maxPathSumLeaf2Leaf(TreeNode root) {
        int[] maxValue = new int[1];
        maxValue[0] = Integer.MIN_VALUE;
        calcMaxPathSumLeaf2Leaf(root, maxValue);
        return maxValue[0];
    }

    private static int calcMaxPathSumLeaf2Leaf(TreeNode root, int[] maxValue) {
        if (root == null)
            return 0;

        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();
        int rootValue = root.getData();

        int maxLeft = calcMaxPathSumLeaf2Leaf(left, maxValue);
        int maxRight = calcMaxPathSumLeaf2Leaf(right, maxValue);

        // new maxValue will be the max number of (maxLeft + rootValue + maxRight) and original maxValue
        maxValue[0] = Math.max(maxValue[0], maxLeft + rootValue + maxRight);

        // Return the maximum root to leaf path sum
        return maxLeft + maxRight + rootValue;
    }

    /*
            Given a binary tree, find the maximum path sum. The path may start and end at any node in the tree.
            For example, given the below binary tree

                   1
                  / \
                 2   3

            the result is 6.
     */
    public static int maxPathSum(TreeNode root) {
        int[] maxValue = new int[1];
        maxValue[0] = Integer.MIN_VALUE;
        calcMaxPathSum(root, maxValue);
        return maxValue[0];
    }

    private static int calcMaxPathSum(TreeNode root, int[] maxValue) {
        if (root == null)
            return 0;

        TreeNode left = root.getLeft();
        TreeNode right = root.getRight();
        int rootValue = root.getData();

        int maxLeft = calcMaxPathSum(left, maxValue);
        int maxRight = calcMaxPathSum(right, maxValue);

        // returnValue will be the max number of rootValue, (maxLeft + rootValue) and (rootValue + maxRight)
        int returnValue = Math.max(rootValue, Math.max(maxLeft + rootValue, rootValue + maxRight));

        // new maxValue will be the max number of returnValue, (maxLeft + rootValue + maxRight) and original maxValue
        maxValue[0] = Math.max(maxValue[0], Math.max(returnValue, maxLeft + rootValue + maxRight));

        return returnValue;
    }

    // Time: O(n)
    public static boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        int height = getHeight(root);
        return (height != -1);
    }

    private static int getHeight(TreeNode root) {
        if (root == null)
            return 0;

        int leftHeight = getHeight(root.getLeft());
        if (leftHeight == -1)
            return -1;

        int rightHeight = getHeight(root.getRight());
        if (rightHeight == -1)
            return -1;

        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;

        // it's balanced for now and return the height
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Given two binary trees check if they are mirror image of each other.
    public static boolean isMirror(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null)
            return true;

        if (r1 == null || r2 == null)
            return false;

        if (r1.val != r2.val)
            return false;

        if (!isMirror(r1.left, r2.right))
            return false;

        return isMirror(r1.right, r2.left);
    }


    // Time: O(n)
    public static boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isSymmetric(root.getLeft(), root.getRight());
    }

    private static boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;

        // both left ad right are not null

        if (left.getData() != right.getData())
            return false;

        if (!isSymmetric(left.getLeft(), right.getRight()))
            return false;

        if (!isSymmetric(left.getRight(), right.getLeft()))
            return false;

        return true;
    }

    /*
    Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes
    you can see ordered from top to bottom. For example, given the following binary tree,

       1            <---
     /   \
    2     3         <---
     \
      5             <---
    You can see [1, 3, 5].
     */
    // Time: O(n)
    // Space: O(n)
    public static ArrayList<Integer> rightSideView(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null)
            return list;

        Queue<TreeNode> q = new LinkedList<>();
        int nodeNumCurrentLevel = 0;
        int nodeNumNextLevel = 0;

        q.add(root);
        nodeNumCurrentLevel++;

        while (!q.isEmpty()) {
            TreeNode current = q.remove();
            nodeNumCurrentLevel--;

            TreeNode left = current.getLeft();
            if (left != null) {
                q.add(left);
                nodeNumNextLevel++;
            }

            TreeNode right = current.getRight();
            if (right != null) {
                q.add(right);
                nodeNumNextLevel++;
            }

            if (nodeNumCurrentLevel == 0) {
                list.add(current.getData());
                nodeNumCurrentLevel = nodeNumNextLevel;
                nodeNumNextLevel = 0;
            }
        }

        return list;
    }

    /*
            Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
            Find the total sum of all root-to-leaf numbers.

        For example,

            1
           / \
          2   3
        The root-to-leaf path 1->2 represents the number 12.
        The root-to-leaf path 1->3 represents the number 13.
        Return the sum = 12 + 13 = 25.
     */

    public static int allRoot2LeafPathSum(TreeNode root) {
        if (root == null)
            return 0;
        return dfsCountRoot2LeafPathSum(root, 0, 0);
    }

    // return the sum of all branches of the current node
    private static int dfsCountRoot2LeafPathSum(TreeNode node, int numRoot2Node, int sum) {
        if (node == null)
            return sum;

        int v = node.getData();

        // update root to node num
        numRoot2Node = numRoot2Node * 10 + v;

        TreeNode left = node.getLeft();
        TreeNode right = node.getRight();

        // it's leaf
        if (left == null && right == null) {
            sum += numRoot2Node;
            return sum;
        }

        // upadte sum
        sum = dfsCountRoot2LeafPathSum(left, numRoot2Node, sum) + dfsCountRoot2LeafPathSum(right, numRoot2Node, sum);
        return sum;
    }

    /*
    Given a complete binary tree, count the number of nodes.

    Steps to solve this problem:
    1) get the height of left-most part
    2) get the height of right-most part
    3) when they are equal, the # of nodes = 2^h -1
    4) when they are not equal, recursively get # of nodes from left&right sub-trees
     */
    public static int nodeNumOfCompleteBinaryTree(TreeNode root) {
        if (root == null)
            return 0;

        int hl = getLeftHeight(root) + 1; // include root
        int hr = getRightHeight(root) + 1; // include root

        // if it's perfect tree
        if (hl == hr)
            return (2 << (hl - 1)) - 1;

        return 1 + nodeNumOfCompleteBinaryTree(root.getLeft()) + nodeNumOfCompleteBinaryTree(root.getRight());
    }

    private static int getLeftHeight(TreeNode node) {
        if (node == null)
            return 0;
        int height = 0;
        while (node.getLeft() != null) {
            height++;
            node = node.getLeft();
        }
        return height;
    }

    private static int getRightHeight(TreeNode node) {
        if (node == null)
            return 0;
        int height = 0;
        while (node.getRight() != null) {
            height++;
            node = node.getRight();
        }
        return height;
    }

    // recursive preOrder
    public static void invert(TreeNode root) {
        if (root == null)
            return;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invert(root.left);
        invert(root.right);
    }

    // BFS with queue
    public static TreeNode invert2(TreeNode root) {
        if (root == null)
            return null;

        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode current = q.remove();

            if (current.left != null)
                q.add(current.left);
            if (current.right != null)
                q.add(current.right);

            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
        }

        return root;
    }

    public static void postOrderTraversal(TreeNode root) {
        if (root == null)
            return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode current = root;

        while (!stack.empty()) {
            TreeNode temp = stack.peek();

            // temp has left, and none of its children is current
            if (temp.left != null && ((temp.right != current) && (temp.left != current)))
                stack.push(temp.left);
            else if (temp.right != null && temp.right != current) // has right and its right is current
                stack.push(temp.right);
            else {
                // temp is a leaf or it's children have been processed
                TreeNode node = stack.pop();

                System.out.print(node.val + "\t");

                // update current
                current = node;
            }
        }
        System.out.print("\n");
    }

    /*
    Given a binary tree (not a binary search tree) and two values say n1 and n2, write a program to find the least common ancestor.

    Following is definition of LCA from Wikipedia:
    Let T be a rooted tree. The lowest common ancestor between two nodes n1 and n2 is defined as the lowest node in T that has
    both n1 and n2 as descendants (where we allow a node to be a descendant of itself).

    The LCA of n1 and n2 in T is the shared ancestor of n1 and n2 that is located farthest from the root.
     */

    // Time: O(n)
    // Space: O(1)  need calling stack
    public static TreeNode findLCA(TreeNode root, TreeNode n1, TreeNode n2) {
        if (root == null)
            return null;
        if (n1 == null && n2 == null)
            return root;
        if (n1 == null)
            return n2;
        if (n2 == null)
            return n1;

        // same node
        if (root.val == n1.val || root.val == n2.val)
            return root;

        TreeNode leftLCA = findLCA(root.left, n1, n2);
        TreeNode rigtLCA = findLCA(root.right, n1, n2);

        if (leftLCA != null && rigtLCA != null)
            return root;

        return (leftLCA != null) ? leftLCA : rigtLCA;
    }

    /*
        Print all nodes at distance k from a given node
        Given a binary tree, a target node in the binary tree, and an integer value k, print all
        the nodes that are at distance k from the given target node. No parent pointers are available.
     */

    // Returns distance of root from target node, it returns -1 if target
    // node is not present in tree rooted with root.
    public static int findKDistanceNodes(TreeNode root, TreeNode target, int k, ArrayList<TreeNode> result) {
        // Base Case 1: If tree is empty, return -1
        if (root == null)
            return -1;

        // If target is same as root.  Use the downward function
        // to print all nodes at distance k in subtree rooted with
        // target or root
        if (root == target) {
            findKDistanceNodesBelowTarget(target, k, result);
            return 0;
        }

        /*
            For every ancestor, we find its distance from target node, let the distance be d, now we
            go to other subtree (if target was found in left subtree, then we go to right subtree and
            vice versa) of the ancestor and find all nodes at k-d distance from the ancestor.
         */

        // recursive call to find qualified nodes in left
        // dl is Distance of root's left child from target
        int dl = findKDistanceNodes(root.left, target, k, result);
        // target if on root's left subtree
        if (dl != -1) {
            // dl+1 is the distance of root from target
            // if root's distance from target is k, add it to result
            if (dl + 1 == k) {
                result.add(root);
                return 0;
            }

            // Else go to right subtree and find all k-dl-2 distant nodes
            // Note that the right child is 2 edges away from left child
            // If a node's distance from target is k, then its distance from the root's right child is k-dl-2
            // find nodes below root's right child and distace from root's right child is k-dl-2
            findKDistanceNodesBelowTarget(root.right, k - dl - 2, result);

            // return the distance of root from target
            return 1 + dl;
        }

        // recursive call to find qualified nodes in right
        // rl is Distance of root's right child from target
        int rl = findKDistanceNodes(root.right, target, k, result);
        // target is on root's right subtree
        if (rl != -1) {
            // rl+1 is the distance of root from target
            // if root's distance from target is k, add it to result
            if (rl + 1 == k) {
                result.add(root);
                return 0;
            }

            // Else go to left subtree and find all k-dl-2 distant nodes
            // Note that the left child is 2 edges away from right child
            // If a node's distance from target is k, then its distance from the root's left child is k-dl-2
            // find nodes below root's left child and distace from root's left child is k-dl-2
            findKDistanceNodesBelowTarget(root.left, k - rl - 2, result);

            // return the distance of root from target
            return 1 + rl;
        }

        // If target was neither present in left nor in right subtree
        return -1;
    }

    private static void findKDistanceNodesBelowTarget(TreeNode root, int k, ArrayList<TreeNode> result) {
        // no found
        if (root == null || k < 0)
            return;

        // found
        if (k == 0) {
            result.add(root);
            return;
        }

        // try to find in left and right
        findKDistanceNodesBelowTarget(root.left, k - 1, result);
        findKDistanceNodesBelowTarget(root.right, k - 1, result);
    }

    /*
        Take a binary tree and rearrange left and right pointers to make a circular doubly linked list.

            Or

        Convert a BST to a sorted circular doubly-linked list in-place.
     */

    // return the head of circular doubly linked list
    public static TreeNode toCircularDoublyLinkedList(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        prev[0] = null;
        TreeNode[] head = new TreeNode[1];
        head[0] = null;
        toCircularDoublyLinkedList(root, prev, head);
        return head[0];
    }

    // recursive inorder traversal
    // Time: O(n)
    // Space: O(1) but calling stack
    private static void toCircularDoublyLinkedList(TreeNode root, TreeNode[] prev, TreeNode[] head) {
        if (root == null)
            return;

        // convert the left subtree
        toCircularDoublyLinkedList(root.left, prev, head);

        // after left sub tree processed, root is the current node
        root.left = prev[0]; // current node's left points to prev node
        if (prev[0] != null)
            prev[0].right = root;   // prev node's right points to current node
        else
            head[0] = root;         // if prev is null, then current node is head
        // as prev[0] is passed in as null in the first call,
        // this make sure head[0] is not null

        // save right
        TreeNode right = root.right;

        // make list as circular
        head[0].left = root;
        root.right = head[0];

        // convert right subtree
        // root is the prev for right subtree
        prev[0] = root;
        toCircularDoublyLinkedList(right, prev, head);
    }

    /*
        Take a binary tree and rearrange left and right pointers to make a doubly linked list.

            Or

        Convert a BST to a sorted doubly-linked list in-place.
     */
    // return the head of circular doubly linked list
    public static TreeNode toDoublyLinkedList(TreeNode root) {
        TreeNode[] prev = new TreeNode[1];
        prev[0] = null;
        TreeNode[] head = new TreeNode[1];
        head[0] = null;
        toDoublyLinkedList(root, prev, head);

        return head[0];
    }

    // recursive inorder traversal
    // Time: O(n)
    // Space: O(1) but calling stack
    private static void toDoublyLinkedList(TreeNode root, TreeNode[] prev, TreeNode[] head) {
        if (root == null)
            return;

        // convert the left subtree
        toDoublyLinkedList(root.left, prev, head);

        // after left sub tree processed, root is the current node
        root.left = prev[0]; // current node's left points to prev node
        if (prev[0] != null)
            prev[0].right = root;   // prev node's right points to current node
        else
            head[0] = root;         // if prev is null, then current node is head

        // convert right subtree
        // root is the prev for right subtree
        prev[0] = root;
        toDoublyLinkedList(root.right, prev, head);
    }

    /*
    Given a binary tree, print all root-to-leaf paths
     */

    // preOrder
    // Time: O(n)
    public static ArrayList<ArrayList<TreeNode>> allRootToLeafPaths(TreeNode root) {
        ArrayList<TreeNode> path = new ArrayList<>();
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        path.add(root);
        allRootToLeafPaths(root, path, result);
        return result;
    }

    private static void allRootToLeafPaths(TreeNode root, ArrayList<TreeNode> path, ArrayList<ArrayList<TreeNode>> result) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            result.add(new ArrayList<TreeNode>(path));
            return;
        }

        path.add(root.left);
        allRootToLeafPaths(root.left, path, result);
        path.remove(path.size() - 1);

        path.add(root.right);
        allRootToLeafPaths(root.right, path, result);
        path.remove(path.size() - 1);
    }

    /*
    given a tree, each node contains a digit. Calculate the sum of all numbers formed by paths from root to leaf.
     */

    // preOrder
    // Time: O(n)
    public static ArrayList<Integer> allSumsOfRoot2LeafPaths(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        allSumsOfRoot2LeafPaths(root, 0, result);
        return result;
    }

    private static void allSumsOfRoot2LeafPaths(TreeNode root, int sum, ArrayList<Integer> result) {
        if (root == null)
            return;

        sum += root.val;

        if (root.left == null && root.right == null) {
            result.add(sum);
            return;
        }

        allSumsOfRoot2LeafPaths(root.left, sum, result);
        allSumsOfRoot2LeafPaths(root.right, sum, result);
    }

    /*
            Given a binary tree where all the right nodes are leaf nodes, flip it upside down and turn it into a tree with left leaf nodes.

            Keep in mind: ALL RIGHT NODES IN ORIGINAL TREE ARE LEAF NODE.


         * for example, turn these:
         *
         *        1                 1
         *       / \               / \
         *      2   3            2   3
         *     / \
         *    4   5
         *   / \
         *  6   7
         *
         * into these:
         *
         *        1               1
         *       /               /
         *      2---3           2---3
         *     /
         *    4---5
         *   /
         *  6---7
         *
         * where 6 is the new root node for the left tree, and 2 for the right tree.
         * oriented correctly:
         *
         *     6                   2
         *    / \                 / \
         *   7   4              3   1
         *        / \
         *       5   2
         *            / \
         *          3   1
         */

    public static TreeNode turndownTree(TreeNode root) {
        if (root == null)
            return root;

        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();

        // build map
        TreeNode p = null;
        TreeNode c = root;
        while (c != null) {
            parentMap.put(c, p);
            p = c;
            c = c.left;
        }

        // now the last p is the root of new tree
        root = p;

        // rebuild tree
        c = p;
        while ((p = parentMap.get(c)) != null) {
            c.left = p.right;
            c.right = p;
            c = p;
        }

        return root;
    }

    /*
    Consider this string representation for binary trees. Each node is of the form (lr), where l represents the left child and r represents the right child. If l is the character 0, then there is no left child. Similarly, if r is the character 0, then there is no right child. Otherwise, the child can be a node of the form (lr), and the representation continues recursively.
    For example: (00) is a tree that consists of one node. ((00)0) is a two-node tree in which the root has a left child, and the left child is a leaf. And ((00)(00)) is a three-node tree, with a root, a left and a right child.

    Write a function that takes as input such a string, and returns -1 if the string is malformed, and the depth of the tree if the string is well-formed.

    For instance:

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

    public static int findSpecialBinaryTreeDepth(String s) {
        if (s == null || s.length() == 0)
            return -1;
        int l = s.length();

        Stack<Character> stack = new Stack<>();
        int maxStackSize = 0;
        int zeroInSameLevel = 0;

        for (int i = 0; i < l; ++i) {
            char ch = s.charAt(i);

            if (ch == '(') {
                stack.push(ch);
                zeroInSameLevel = 0;
            } else if (ch == '0') {
                zeroInSameLevel++;
                if (zeroInSameLevel > 2)
                    return -1;
            } else if (ch == ')') {
                if (stack.isEmpty())
                    return -1;
                stack.pop();
                maxStackSize = Math.max(maxStackSize, stack.size());
                zeroInSameLevel = 0;
            } else
                return -1;
        }

        if (!stack.isEmpty())
            return -1;

        return maxStackSize;
    }

    /*
    is the same tree
     */

    public static boolean isTheSameTree(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null)
            return true;
        if (r1 == null || r2 == null)
            return false;

        if (r1.val != r2.val)
            return false;

        return isTheSameTree(r1.left, r2.left) && isTheSameTree(r2.right, r2.right);
    }

    /*
        Given a Binary Tree, write a function to check whether the given Binary Tree is Complete Binary Tree or not.

        A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled,
        and all nodes are as far left as possible.
     */

    public static boolean isCompleteTree(TreeNode root) {
        if (root == null)
            return true;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        boolean foundNotFullFilledNode = false;
        while (!q.isEmpty()) {
            TreeNode current = q.remove();

            if (current.left != null) {
                if (foundNotFullFilledNode)
                    return false;
            } else
                foundNotFullFilledNode = true;

            if (current.right != null) {
                if (foundNotFullFilledNode)
                    return false;
            } else
                foundNotFullFilledNode = true;
        }

        return false;
    }

    /*
    Populating Next Right Pointers in Each Node
    Given a binary tree
    struct TreeLinkNode {
      TreeLinkNode *left;
      TreeLinkNode *right;
      TreeLinkNode *next;
    }
    Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.  Initially, all next pointers are set to NULL.
    You may only use constant extra space.
    You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
    For example,
    Given the following perfect binary tree,
             1
           /  \
          2    3
         / \  / \
        4  5  6  7
    After calling your function, the tree should look like:
             1 -> NULL
           /  \
          2 -> 3 -> NULL
         / \  / \
        4->5->6->7 -> NULL
     */
    static class TreeLinkNode {
        TreeLinkNode left;
        TreeLinkNode right;
        TreeLinkNode next;
    }

    public static void connectTreeLinkNode(TreeLinkNode root) {
        if (root == null)
            return;

        if (root.left != null)
            root.left.next = root.right;
        if (root.right != null) {
            if (root.next != null)
                root.right.next = root.next.left;
        }

        connectTreeLinkNode(root.left);
        connectTreeLinkNode(root.right);
    }

    /*
    Populating Next Right Pointers in Each Node II
    Follow up for problem "Populating Next Right Pointers in Each Node".
    What if the given tree could be any binary tree? Would your previous solution still work?
    Note:
    You may only use constant extra space.
    For example,
    Given the following binary tree,
             1
           /  \
          2    3
         / \    \
        4   5    7
    After calling your function, the tree should look like:
             1 -> NULL
           /  \
          2 -> 3 -> NULL
         / \    \
        4-> 5 -> 7 -> NULL
     */

    public static void connectTreeLinkNode2(TreeLinkNode root) {
        if (root == null)
            return;

        // find the first non null node in root.next chain
        TreeLinkNode next = root.next;
        while (next != null) {
            if (next.left != null) {
                next = next.left;
                break;
            }
            if (next.right != null) {
                next = next.right;
                break;
            }

            // not found in this next, try next's next
            next = next.next;
        }

        if (root.right != null)
            root.right.next = next;
        if (root.left != null)
            root.left.next = (root.right != null) ? root.right : next;

        connectTreeLinkNode(root.right);
        connectTreeLinkNode(root.left);
    }

    public static ArrayList<ArrayList<TreeNode>> rootToLeafPath(TreeNode root) {
        ArrayList<ArrayList<TreeNode>> result = new ArrayList<>();
        ArrayList<TreeNode> path = new ArrayList<>();
        path.add(root);
        rootToLeafPath(root, path, result);
        return result;
    }

    public static void rootToLeafPath(TreeNode root, ArrayList<TreeNode> path, ArrayList<ArrayList<TreeNode>> result) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            result.add(new ArrayList<TreeNode>(path));
            return;
        }

        path.add(root.left);
        rootToLeafPath(root.left, path, result);
        path.remove(path.size() - 1);

        path.add(root.right);
        rootToLeafPath(root.right, path, result);
        path.remove(path.size() - 1);
    }

    public static List<TreeNode> findPath(TreeNode root, TreeNode node) {
        if (root == null || node == null) {
            return null;
        }

        List<TreeNode> path = new ArrayList<>();
        findPathHelper(root, node, path);

        return path;
    }

    public static boolean findPathHelper(TreeNode root, TreeNode node, List<TreeNode> path) {
        if (root == null) {
            return false;
        }

        path.add(root);
        if (root.val == node.val) {
            return true;
        }

        if (findPathHelper(root.left, node, path)) {
            return true;
        }
        if (findPathHelper(root.right, node, path)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

    // Given a Binary Tree, find All The Nodes Which are X distance from the Given Node
    public static List<TreeNode> findNodesDistanceAtX(TreeNode root, TreeNode node, int x) {
        if (root == null || node == null || x < 0) {
            return null;
        }

        List<TreeNode> nodes = new ArrayList<>();

        // 1. find path from root to node
        List<TreeNode> path = new ArrayList<>();
        findPathHelper(root, node, path);

        // 2. find nodes below each node in the path
        // skip what has been visied in last loop
        HashSet<TreeNode> visited = new HashSet<>();
        int istart = Math.max(path.size() - 1, 0);
        int iend = Math.max(path.size() - 1 - x, 0);
        for (int i = istart; i >= iend; --i) {
            TreeNode tn = path.get(i);
            findNodesBelowNodeDistanceAtX(tn, x--, nodes, visited);
            visited.add(tn);
        }

        return nodes;
    }

    public static void findNodesBelowNodeDistanceAtX(TreeNode node, int x, List<TreeNode> nodes, HashSet<TreeNode> visited) {
        if (node == null) {
            return;
        }

        if (x == 0) {
            nodes.add(node);
            return;
        }

        if (!visited.contains(node.left)) {
            findNodesBelowNodeDistanceAtX(node.left, x - 1, nodes, visited);
        }
        if (!visited.contains(node.right)) {
            findNodesBelowNodeDistanceAtX(node.right, x - 1, nodes, visited);
        }
    }

    public static int maxPathSum(Solution2.TreeNode root) {
        int[] maxSum = new int[1];
        maxSum[0] = Integer.MIN_VALUE;
        maxPathSum(root, maxSum);
        return maxSum[0];
    }

    // return max sum of all paths ending at root
    public static int maxPathSum(Solution2.TreeNode root, int[] maxSum) {
        if (root == null) {
            return 0;
        }

        int leftMaxSum = maxPathSum(root.left, maxSum);
        int rightMaxSum = maxPathSum(root.right, maxSum);
        int maxChild = Math.max(leftMaxSum, rightMaxSum);

        // the max sum of all paths ending at root is the max number between:
        // nodeVal, nodeVal+maxChild
        int ret = Math.max(root.val, root.val + maxChild);

        // the max sum of all paths passing root is the max number between:
        // the max sum of all paths ending at root (ret), and leftMaxSum + nodeVal + rightMaxSum
        int curMax = Math.max(ret, leftMaxSum + root.val + rightMaxSum);

        // max sum up to now
        maxSum[0] = Math.max(curMax, maxSum[0]);

        // return max sum of all paths ending at root
        return ret;
    }

}