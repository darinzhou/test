package com.comcast.algorithms;

/**
 * Maximum XOR of Two Numbers in an Array
 Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 231.

 Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.

 Could you do this in O(n) runtime?

 Example:

 Input: [3, 10, 5, 25, 2, 8]

 Output: 28

 Explanation: The maximum result is 5 ^ 25 = 28.
 */

public class MaxXorOfTwoNumbersInArray {

    class Node {
        Node[] children = new Node[2];
    }

    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Init Trie.
        Node root = new Node();
        for (int num : nums) {
            Node curNode = root;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >>> i) & 1;
                if (curNode.children[curBit] == null) {
                    curNode.children[curBit] = new Node();
                }
                curNode = curNode.children[curBit];
            }
        }

        // find max XOR
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            Node curNode = root;
            int curSum = 0;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >>> i) & 1;

                // if the opposite bit (curBit ^ 1) exists in Trie (that is,
                // curNode.children[curBit ^ 1] != null), then current bit XOR it will
                // generate value of 1 at this bit-position
                // So, the value from this bit will contribute to a potential max sum
                if (curNode.children[curBit ^ 1] != null) {
                    curSum += (1 << i);
                    curNode = curNode.children[curBit ^ 1];
                } else {
                    curNode = curNode.children[curBit];
                }
            }
            max = Math.max(curSum, max);
        }
        return max;
    }

}
