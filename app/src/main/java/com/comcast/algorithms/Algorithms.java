package com.comcast.algorithms;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by zzhou200 on 7/16/15.
 */
public class Algorithms {
    public static final String TAG = "Algorithms";

    // Given an array a contains all digits 0-9 a   = [1, 4, 2, 1] # which represents 1421
    // Add one to the number and return the array return a = [1, 4, 2, 2] # which represents 1422

    public static int[] addOne(int[] a) {
        if (a == null || a.length == 0)
            return a;

        int carry = 1;
        for (int i = a.length - 1; i >= 0; i--) {
            int sum = a[i] + carry;
            a[i] = sum % 10;
            carry = sum / 10;
        }

        if (carry == 1) {
            int[] b = new int[a.length + 1];
            b[0] = 1;
            System.arraycopy(a, 0, b, 1, a.length);
            return b;
        }

        return a;
    }

    public static int[] addOne2(int[] a) {
        int l = a.length;
        int v = 0;
        for (int i = 0; i < l; ++i) {
            //System.out.print(a[i]);
            v += a[i] * Math.pow(10, l - i - 1);
        }

        v++;
        if (v >= Math.pow(10, l))
            l++;

        int[] b = new int[l];
        for (int i = 0; i < l; ++i) {
            int base = (int) Math.pow(10, l - i - 1);
            b[i] = v / base;
            v = v % base;
        }

        return b;
    }

    public static class MaxDif {
        int minValIndex;
        int maxValIndex;
        int maxDif;

        public MaxDif(int minValIndex, int maxValIndex, int maxDif) {
            this.minValIndex = minValIndex;
            this.maxValIndex = maxValIndex;
            this.maxDif = maxDif;
        }

        public int getMinValIndex() {
            return minValIndex;
        }

        public void setMinValIndex(int minValIndex) {
            this.minValIndex = minValIndex;
        }

        public int getMaxValIndex() {
            return maxValIndex;
        }

        public void setMaxValIndex(int maxValIndex) {
            this.maxValIndex = maxValIndex;
        }

        public int getMaxDif() {
            return maxDif;
        }

        public void setMaxDif(int maxDif) {
            this.maxDif = maxDif;
        }

    }

    public static MaxDif getMaxDifInArray(int[] a) throws Exception {
        if (a == null || a.length < 2)
            throw new Exception("array should not be null or less than 2 elements");

        int minValIndex = 0;
        int maxValIndex = 0;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] < a[minValIndex])
                minValIndex = i;
            if (a[i] > a[maxValIndex])
                maxValIndex = i;
        }

        return new MaxDif(minValIndex, maxValIndex, a[maxValIndex] - a[minValIndex]);
    }

    public static MaxDif getMaxDifInArrayForward(int[] a) throws Exception {
        if (a == null || a.length < 2)
            throw new Exception("array should not be null or less than 2 elements");

        int minValIndexTest = 0;
        int minValIndex = minValIndexTest;
        int maxValIndex = 1;
        int maxDif = a[maxValIndex] - a[minValIndex];
        for (int i = 1; i < a.length; ++i) {
            int maxDifTmp = a[i] - a[minValIndexTest];
            if (maxDifTmp > maxDif) {
                maxDif = maxDifTmp;
                maxValIndex = i;
                minValIndex = minValIndexTest;
            }
            if (a[i] < a[minValIndexTest])
                minValIndexTest = i;
        }

        return new MaxDif(minValIndex, maxValIndex, maxDif);
    }

    // You have an array of integers, and for each index you want to find the product of every integer except the integer at that index.

    // Time: O(n)
    // Memory: O(n)
    public static int[] calcAllProductsExceptAtIndex(int[] a) throws Exception {
        if (a == null || a.length == 0)
            throw new Exception("array should not be null or empty");

        int[] allProductsExceptAtIndex = new int[a.length];

        // calc all products before index
        int product = 1;
        for (int i = 0; i < a.length; ++i) {
            allProductsExceptAtIndex[i] = product;
            product *= a[i];
        }

        // calc all products after index, and multiply with products before index
        product = 1;
        for (int i = a.length - 1; i >= 0; --i) {
            allProductsExceptAtIndex[i] *= product;
            product *= a[i];
        }

        return allProductsExceptAtIndex;
    }

    // Time: O(n)
    // Memory: O(1)
    public static int[] calcAllProductsExceptAtIndexWithDivision(int a[]) throws Exception {
        if (a == null || a.length == 0)
            throw new Exception("array should not be null or empty");

        int[] allProductsExceptAtIndex = new int[a.length];

        // check 0 in elements
        int zeroCount = 0;
        for (int v : a) {
            if (v == 0)
                zeroCount++;
        }

        // if there are more than 2 zeros, every product is zero
        if (zeroCount >= 2) {
            for (int i = 0; i < a.length; ++i)
                allProductsExceptAtIndex[i] = 0;
            return allProductsExceptAtIndex;
        }

        // calc all products but skip zero element
        int product = 1;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] != 0)
                product *= a[i];
        }

        // calc all products except at index by product/a[i], don't forget zero
        for (int i = 0; i < a.length; ++i) {
            if (a[i] != 0)
                allProductsExceptAtIndex[i] = product / a[i];
            else
                allProductsExceptAtIndex[i] = product;
        }

        return allProductsExceptAtIndex;
    }

    // Time: O(n)
    // Memory: O(n) for recursive stack
    // call from index 0: calcAllProductsExceptAtIndexWithRecursive(a, 1, 0);
    // return the productAfterIndex for index-1
    public static int calcAllProductsExceptAtIndexWithRecursive(int[] a, int productBeforeIndex, int index) throws Exception {
        if (a == null || a.length == 0)
            throw new Exception("array should not be null or empty");

        int productAfterIndex = 1;
        if (index < a.length) {
            // calc the product after index+1 with recursive
            // for index+1, the new productBeforeIndex is productBeforeIndex*a[index]
            // the recursive method return the productAfterIndex for index, which is index+1-1
            productAfterIndex = calcAllProductsExceptAtIndexWithRecursive(a, productBeforeIndex * a[index], index + 1);

            int valueAtIndex = a[index];

            // the AllProductExceptAtIndex of index is productBeforeIndex*productAfterIndex
            // store it in a[index]
            a[index] = productBeforeIndex * productAfterIndex;

            // for index-1, the new productAfterIndex is productAfterIndex*a[index]
            productAfterIndex = productAfterIndex * valueAtIndex;
        }

        // return the productAfterIndex for index+1
        return productAfterIndex;
    }

    // Given an array_of_ints, find the highest_product you can get from three of the integers.

    private static int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));

    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));

    }

    //
    // Time: O(n)
    // Memory: O(1)
    public static int calcHighestProductOf3(int[] a) throws Exception {
        if (a == null || a.length < 3)
            throw new Exception("array should not be null or less than 3 elements");

        int highestProductOf3 = a[0] * a[1] * a[2];
        int highestProductOf2 = a[0] * a[1];
        int lowestProductOf2 = a[0] * a[1];
        int highest = max(a[0], a[1], a[2]);
        int lowest = min(a[0], a[1], a[2]);

        for (int i = 2; i < a.length; ++i) {

            // do we have a new highest product of 3?
            // it's either the current highest,
            // or the current times the highest product of two
            // or the current times the lowest product of two
            highestProductOf3 = max(highestProductOf3, a[i] * highestProductOf2, a[i] * lowestProductOf2);

            highestProductOf2 = max(highestProductOf2, a[i] * highest, a[i] * lowest);
            lowestProductOf2 = min(lowestProductOf2, a[i] * highest, a[i] * lowest);
            highest = Math.max(highest, a[i]);
            lowest = Math.min(lowest, a[i]);
        }

        return highestProductOf3;
    }

    // Time: O(nlogn)
    // Memory: O(1)
    public static int calcHighestProductOf3WithSort(int[] a) throws Exception {
        if (a == null || a.length < 3)
            throw new Exception("array should not be null or less than 3 elements");

        Arrays.sort(a);

        int potentialProduct1 = a[a.length - 1] * a[a.length - 2] * a[a.length - 3];
        int potentialProduct2 = a[a.length - 1] * a[0] * a[1];

        return Math.max(potentialProduct1, potentialProduct2);
    }

    // computes the number of ways to make amount of money with coins of the available denominations.

    // Time: O(m*n)
    // Memory: O(m)
    public static int calcChangePossibilities(int amountLeft, int[] denominationsLeft) {
        if (amountLeft == 0)
            return 1;
        if (amountLeft < 0)
            return 0;
        if (denominationsLeft.length == 0)
            return 0;

        Log.d("TestFCA", String.format("checking ways to make %1$d with %2$s", amountLeft, Arrays.toString(denominationsLeft)));

        int currentCoin = denominationsLeft[0];
        int[] restOfCoins = new int[denominationsLeft.length - 1];
        System.arraycopy(denominationsLeft, 1, restOfCoins, 0, denominationsLeft.length - 1);

        // see how many possibilities we can get
        // for each number of times to use current_coin
        int numPossibilities = 0;
        while (amountLeft >= 0) {
            numPossibilities += calcChangePossibilities(amountLeft, restOfCoins);
            amountLeft -= currentCoin;
            Log.d("TestFCA", " " + currentCoin);
        }

        return numPossibilities;
    }

    // Time: O(m*n)
    // Memory: O(m)
    public static void calcChangePossibilities(int amountLeft, int[] denominations, HashMap<Integer, Integer> currentCombination, ArrayList<HashMap<Integer, Integer>> changePossibilities) {

        if (amountLeft == 0) {
            if (!changePossibilities.contains(currentCombination))
                changePossibilities.add((HashMap<Integer, Integer>) currentCombination.clone());
            return;
        }

        if (amountLeft < 0)
            return;

        for (int denom : denominations) {
            if (currentCombination.get(denom) == null)
                currentCombination.put(denom, 0);

            if (amountLeft - denom < 0)
                continue;

            // try current denominate
            currentCombination.put(denom, currentCombination.get(denom) + 1);
            calcChangePossibilities(amountLeft - denom, denominations, currentCombination, changePossibilities);

            // recover from last try to prepare for next denominate
            currentCombination.put(denom, currentCombination.get(denom) - 1);
        }
    }

    // assume array is ascending sorted
    // time: O(logn)
    // memory: O(1)
    public static int binarySearch(int[] a, int target) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("array should not be null or empty");

        int left = 0;
        int right = a.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == a[mid])
                return mid;

            if (target < a[mid])
                right = mid - 1;
            else
                left = mid + 1;
        }
        return -1;
    }

    // assume array is ascending sorted
    // time: O(logn)
    // memory: O(logn) - calling stack
    public static int binarySearchRecursive(int[] a, int target, int left, int right) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("array should not be null or empty");

        if (left <= right) {
            int mid = left + (right - left) / 2;
            if (target == a[mid])
                return mid;

            if (target < a[mid])
                right = mid - 1;
            else
                left = mid + 1;

            return binarySearchRecursive(a, target, left, right);
        }

        return -1;
    }

    /*
    Find a given element in sorted array.

    arr= [1, 2, 3, 4, 5, 6]

    follow up: If the sorted array is shifted left by unknown number, modify existing binary search to find a element in modified array

    arr = [4, 5, 6, 1, 2, 3]
     */

    // Time: O(logn)
    // Space: O(1) - but calling stack
    public static int binarySearchShiftedSortedArray(int[] a, int target, int left, int right) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("array should not be null or empty");

        if (left <= right) {
            int mid = left + (right - left) / 2;
            if (a[mid] == target)
                return mid;

            // left part was shifted, right part keeps sorted
            if (a[left] > a[mid]) {

                if (a[mid] < target && target <= a[right]) // target is on right part, do normal binary search on right part
                    return binarySearchRecursive(a, target, mid + 1, right);
                else // target is on left part, do shifted search on left part
                    return binarySearchShiftedSortedArray(a, target, left, mid - 1);
            }

            // left part keeps sorted, right part was shifted sorted
            if (a[mid] > a[right]) {

                if (a[left] <= target && target < a[mid]) // target is on left part, do normal binary search on left part
                    return binarySearchRecursive(a, target, left, mid - 1);
                else // target is on right part, do shifted search on right part
                    return binarySearchShiftedSortedArray(a, target, mid + 1, right);
            }
        }

        return -1;
    }

    public static int binarySearchShiftedArray(int[] a, int target, int left, int right) {
        if (a == null || a.length == 0) {
            return -1;
        }

        if (left <= right) {
            int mid = left + (right-left)/2;
            if (a[mid] == target) {
                return mid;
            }

            if (a[left] < a[mid]) { // left is not shifted
                if (a[left] <= target && target < a[mid]) { //target is in left
                    return binarySearch(a, target, left, mid-1);
                } else { // target is in right, which is shifted
                    return binarySearchShiftedArray(a, target, mid+1, right);
                }

            } else { // right is not shifted
                if (a[mid] < target && target <= a[right]) {
                    return binarySearch(a, target, mid+1, right);
                } else {
                    return binarySearchShiftedArray(a, target, left, mid-1);
                }
            }
        }

        return -1;
    }

    public static int binarySearch(int[] a, int target, int left, int right) {
        if (a == null || a.length == 0) {
            return -1;
        }

        if (left <= right) {
            int mid = left + (right-left)/2;
            if (a[mid] == target) {
                return mid;
            }

            if (target < a[mid]) {
                return binarySearch(a, target, left, mid-1);
            }

            return binarySearch(a, target, mid+1, right);
        }

        return -1;
    }

     public static int binarySearch2(int[] a, int target) {
        if (a == null || a.length == 0) {
            return -1;
        }

        int left = 0;
        int right = a.length-1;
        while (left <= right) {
            int mid = left + (right-left)/2;
            if (a[mid] == target) {
                return mid;
            }

            if (target < a[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /*
    Given a sorted array with duplicates and a number, find the range in the
    form of (startIndex, endIndex) of that number. For example,

    find_range({0 2 3 3 3 10 10}, 3) should return (2,4).
    find_range({0 2 3 3 3 10 10}, 6) should return (-1,-1).
    The array and the number of duplicates can be large.
     */
    // Time: O(logn + K)
    // Space: O(1)
    public static int[] findDuplicatedRange(int[] a, int target) {
        int index = binarySearch(a, target);
        if (index == -1) {
            return null;
        }

        int left = index;
        int right = index;
        while (a[left] == target) {
            left--;
        }
        while (a[right] == target) {
            right++;
        }

        return new int[] {left+1, right-1};
    }

    public static int[] findRange(int[] a, int v) {
        // find low
        int l = 0;
        int r = a.length-1;
        int low = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((mid - 1 < 0 || a[mid - 1] != v) && a[mid] == v) {
                low = mid;
                break;
            } else if (a[mid] >= v) // check left (smaller part)
                r = mid - 1;
            else
                l = mid + 1;
        }

        // find high
        l = 0;
        r = a.length-1;
        int high = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (a[mid] == v && (mid + 1 >= a.length || a[mid + 1] != v)) {
                high = mid;
                break;
            } else if (a[mid] <= v) // check right (bigger part)
                l = mid + 1;
            else
                r = mid - 1;
        }

        return new int[]{low, high};
    }

    /*
    * Return the smallest character that is strictly larger than the search character,
    * If no such character exists, return the smallest character in the array
    * @param sortedStr : sorted list of letters, sorted in ascending order.
    * @param c : character for which we are searching.
    * Given the following inputs we expect the corresponding output:
    * ['c', 'f', 'j', 'p', 'v'], 'a' => 'c'
    * ['c', 'f', 'j', 'p', 'v'], 'c' => 'f'
    * ['c', 'f', 'j', 'p', 'v'], 'k' => 'p'
    * ['c', 'f', 'j', 'p', 'v'], 'z' => 'c' // The wrap around case
    * ['c', 'f', 'k'], 'f' => 'k'
    * ['c', 'f', 'k'], 'c' => 'f'
    * ['c', 'f', 'k'], 'd' => 'f'
    */

    public static char findSmallestLargerThanTarget(char[] a, char target) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("invalid arry");

        int l = 0;
        int r = a.length - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if ((mid - 1 < 0 || a[mid - 1] <= target) && target < a[mid])
                return a[mid];
            else if (a[mid] <= target)
                l = mid + 1;
            else
                r = mid - 1;
        }

        return a[0];
    }

    /*
    You have two arrays of integers, where the integers do not repeat and the two arrays have no common integers.

    Let x be any integer in the first array, y any integer in the second. Find min(Abs(x-y)). That is, find the
    smallest difference between any of the integers in the two arrays.

    Assumptions: Assume both arrays are sorted in ascending order.
     */

    // Time: O(m+n)
    public static int findSmallestDifference(int[] a, int[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0)
            throw new IllegalArgumentException("invalid arrays");

        int i = 0;
        int j = 0;
        int minDif = Math.abs(a[0] - b[0]);
        while (i < a.length && j < b.length) {
            minDif = Math.min(minDif, Math.abs(a[i] - b[j]));
            if (a[i] < b[j])
                i++;    // get a bigger one from a
            else
                j++;    // get bigger one from b
        }

        return minDif;
    }

    /*
    There are 2 sorted sets.Find the common elements of those sets
    e.g.
    A={1,2,3,4,5,6}
    B={5,6,7,8,9}
    o/p C={5,6}

    Complexity should ne 0(n+m) where n and m is the size of the first and second set respectively

    Which data structure should be used to store the output
     */

    public static HashSet<Integer> findCommonElements(int[] a, int[] b) {
        HashSet<Integer> ces = new HashSet<>();

        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                i++;
            else if (b[j] < a[i])
                j++;
            else {
                ces.add(a[i]);
                i++;
                j++;
            }
        }
        return ces;
    }

    // Time: O(n)
    // Memory: O(1)
    public static void reverse(char[] a, int left, int right) {
        if (a == null || a.length <= 1 || left < 0 || right >= a.length || right < 0)
            return;

        while (left < right) {
            char temp = a[left];
            a[left] = a[right];
            a[right] = temp;
            left++;
            right--;
        }
    }

    // Time: O(n)
    // Memory: O(n)
    public static String reverse(String s, int left, int right) {
        if (s == null || s.length() <= 1 || left < 0 || right >= s.length() || left >= right)
            return s;

        char[] a = s.toCharArray();
        while (left < right) {
            char temp = a[left];
            a[left] = a[right];
            a[right] = temp;
            left++;
            right--;
        }

        return new String(a);
    }

    /*
    reverse string recursively
     */
    public static String reverseRecursive(String s) {
        if (s == null || s.length() <= 1)
            return s;
        return reverseRecursive(s.substring(1)) + s.charAt(0);
    }

    /*
        Rotate an array of n elements to the right by k steps
        1. Divide the array two parts: 1,2,3,4 and 5, 6
        2. reverse first part: 4,3,2,1,5,6
        3. reverse second part: 4,3,2,1,6,5
        4. reverse the whole array: 5,6,1,2,3,4
    */
    // Time: O(n)
    // Memory: O(1)
    public static void rotate(char[] a, int order) {
        if (a == null || a.length <= 1 || order < 0)
            return;

        // as rotate with order of a.length means no change
        order = order % a.length;

        // length of first part
        int p = a.length - order;

        // reverse first part
        reverse(a, 0, p - 1);
        // reverse second part
        reverse(a, p, a.length - 1);
        // reverse whole array
        reverse(a, 0, a.length - 1);
    }

    // reverse each word in string, e.g. "abc def" -> "cba fed"

    // assume words are only separated by spaces
    private static boolean isSeparator(char c) {
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'))
//        if (c == ' ')
            return true;
        return false;
    }

    // Time: O(n^2)
    // Memory: O(n)
    public static String reverseWordsInString(String s) {
        if (s == null || s.length() <= 1)
            return s;

        char[] a = s.toCharArray();
        int wordStart = 0;
        int wordEnd = 0;
        for (int i = 0; i < a.length; ++i) {
            if (isSeparator(a[i])) {
                wordEnd = i - 1;
                reverse(a, wordStart, wordEnd);

                wordStart = i + 1;
            }
        }
        // reverse last word
        reverse(a, wordStart, a.length - 1);

        return new String(a);
    }

    // reverse words' order in string, e.g. "abc def" -> "def abc"
    // 1. reverse the whole string
    // 2. reverse each word in string
    // Time: O(n^2)
    // Memory: O(n)
    public static String reverseWordOrder(String s) {
        if (s == null || s.length() <= 1)
            return s;

        // reverse the whole string
        String ss = reverse(s, 0, s.length() - 1);
        // reverse each word
        return reverseWordsInString(ss);
    }

    /*
    Evaluate the value of an arithmetic expression in Reverse Polish Notation. Valid operators are +, -, *, /.
    Each operand may be an integer or another expression. For example:
            ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
            ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
     */

    // Time: O(n)
    // Memory: O(n)
    public static int evaluateRPN(String[] expr) throws Exception {
        String operators = "+-*/";
        Stack<Integer> stack = new Stack<Integer>();

        for (String token : expr) {
            int operator = operators.indexOf(token);

            // number
            if (operator == -1) {
                try {
                    int operand = Integer.valueOf(token);
                    stack.push(operand);
                } catch (Exception e) {
                    throw new Exception("Invalid expression. " + e.getMessage());
                }
                continue;
            }

            // operator

            int operand1, operand2;
            try {
                operand2 = stack.pop();
                operand1 = stack.pop();
            } catch (Exception e) {
                throw new Exception("Invalid expression. " + e.getMessage());
            }

            switch (operator) {
                case 0:
                    stack.push(operand1 + operand2);
                    break;
                case 1:
                    stack.push(operand1 - operand2);
                    break;
                case 2:
                    stack.push(operand1 * operand2);
                    break;
                case 3:
                    if (operand2 == 0)
                        throw new Exception("Divided by zero.");
                    stack.push(operand1 / operand2);
                    break;
            }
        }

        try {
            int val = stack.pop();
            if (!stack.isEmpty())
                throw new Exception("Invalid expression. ");
            return val;
        } catch (Exception e) {
            throw new Exception("Invalid expression. " + e.getMessage());
        }
    }

    /*
    Convert and infix expression to postfix expression
     */

    public static String infix2postfix(String s) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            int opp = checkOperator(ch);

            if (opp == 0)   // operand
                postfix += ch;
            else {          //operator
                if (stack.isEmpty() || ch == '(')
                    stack.push(ch);
                else {
                    if (ch == ')') {
                        while (!stack.isEmpty()) {
                            char op = stack.pop();
                            if (op == '(')
                                break;
                            postfix += op;
                        }
                    } else {
                        while (!stack.isEmpty()) {
                            char opInStack = stack.peek();
                            int oppInStack = checkOperator(opInStack);
                            if (opp < oppInStack) {     // pop higher operator
                                stack.pop();
                                postfix += opInStack;
                            } else
                                break;
                        }
                        stack.push(ch);
                    }
                }
            }
        }

        while (!stack.isEmpty())
            postfix += stack.pop();

        return postfix;
    }

    private static int checkOperator(char ch) {
        if (ch == '(' || ch == ')')
            return 1;
        else if (ch == '+' || ch == '-')
            return 2;
        else if (ch == '*' || ch == '/')
            return 3;
        return 0;
    }

    /*
    Given two strings s and t, determine if they are isomorphic. Two strings are isomorphic if the
    characters in s can be replaced to get t.

    For example,"egg" and "add" are isomorphic, "foo" and "bar" are not.
    */

    /*
        Hash <char, firstseenindex> for each string.

        The encoding of first seenindices should match.

        E.g. Foo and app both encode to 011
        Abcd and hole both encode to 0123

        Hate and hell do not match
        As encodings are 0123 and 0122
     */

    private static int getFirstSeenIndex(String s, int index, HashMap<Character, Integer> pattern) {
        Character ch = s.charAt(index);
        if (!pattern.containsKey(ch)) {
            pattern.put(ch, index);
            return index;
        }
        return pattern.get(ch);
    }

    // Time: O(n)
    // Memory: O(n)
    public static boolean isIsomorphic(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;

        int len = s1.length();
        int len2 = s2.length();
        if (len != len2)
            return false;
        if (len < 2 && len2 < 2)
            return true;

        HashMap<Character, Integer> pattern1 = new HashMap<>();
        HashMap<Character, Integer> pattern2 = new HashMap<>();

        for (int i = 0; i < len; ++i) {
            int firstSeenIndex1 = getFirstSeenIndex(s1, i, pattern1);
            int firstSeenIndex2 = getFirstSeenIndex(s2, i, pattern2);

            if (firstSeenIndex1 != firstSeenIndex2)
                return false;
        }

        return true;
    }

    /*
    There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.
    The overall run time complexity should be O(log (m+n)).
    */

    // Time: O(logn)
    // Memory: O(n) - recursive stack
    public static int medianInSortedArrays(int[] a, int[] b) {
        if ((a.length + b.length) % 2 == 0) {
            int m1 = kthSmallestInSortedArrays(a, 0, a.length - 1, b, 0, b.length - 1, (a.length + b.length) / 2);
            int m2 = kthSmallestInSortedArrays(a, 0, a.length - 1, b, 0, b.length - 1, (a.length + b.length) / 2 + 1);
            return (m1 + m2) / 2;
        }

        return kthSmallestInSortedArrays(a, 0, a.length - 1, b, 0, b.length - 1, (a.length + b.length) / 2 + 1);
    }

    // Time: O(logn)
    // Memory: O(n) - recursive stack
    public static int kthSmallestInSortedArrays(int[] a, int la, int ra, int[] b, int lb, int rb, int k) {
        int aLen = ra - la + 1;
        int bLen = rb - lb + 1;

        int aMid = aLen * (k - 1) / (aLen + bLen);    // choose a middle position for a
        int bMid = k - aMid - 1;

        // adjust as array index
        int aMidIdx = aMid + la;
        int bMidIdx = bMid + lb;

        int av1 = ((aMidIdx == 0) ? Integer.MIN_VALUE : a[aMidIdx - 1]);
        int av = ((aMidIdx == aLen) ? Integer.MAX_VALUE : a[aMidIdx]);

        int bv1 = ((bMidIdx == 0) ? Integer.MIN_VALUE : b[bMidIdx - 1]);
        int bv = ((bMidIdx == bLen) ? Integer.MAX_VALUE : b[bMidIdx]);

        if (bv1 < av && av < bv)    // if a[aMidIdx] is between b[bMidIdx-1] and b[bMidIdx]
            return av;
        else if (av1 < bv && bv < av)   // if b[bMidIdx] is between a[aMidIdx-1] and a[aMidIdx]
            return bv;

        if (a[aMidIdx] < b[bMidIdx]) {
            // adjust k
            k = k - aMid - 1;
            // exclude A[aMidIdx] and below portion
            la = aMidIdx + 1;
            // exclude B[bMidIdx] and above portion
            rb = bMidIdx - 1;
        } else {
            // adjust k
            k = k - bMid - 1;
            // exclude A[aMidIdx] and above portion
            ra = aMidIdx - 1;
            // exclude B[bMidIdx] and below portion
            lb = bMidIdx + 1;
        }

        return kthSmallestInSortedArrays(a, la, ra, b, lb, rb, k);
    }

    // Implement wildcard pattern matching with support for '?' and '*'.

    public static boolean isWildcardMatch(String s, String p) {

        int pi = 0;
        int si = 0;
        int sLen = s.length();
        int pLen = p.length();
        while (pi < pLen) {
            if (si > sLen - 1) {
                if (pi == pLen - 1 && p.charAt(pi) == '*')
                    return true;
                return false;
            }

            if (p.charAt(pi) == '?') {
                pi++;
                si++;
            } else if (p.charAt(pi) == '*') {
                if (pi == pLen - 1)
                    return true;

                pi++;
                char charAfterStar = p.charAt(pi);
                for (; si < sLen; si++)
                    if (s.charAt(si) == charAfterStar)
                        break;
                if (s.charAt(si) != charAfterStar)
                    return false;

                pi++;
                si++;
            } else {
                if (p.charAt(pi) != s.charAt(si))
                    return false;
                pi++;
                si++;
            }
        }

        return (si == sLen);
    }

    //Given an array of integers, find two numbers such that they add up to a specific target number.

    // if the array is not sorted
    // return the first group found, if want to return all results, use a ArrayList to save all found groups
    // Time: O(n)
    // Memory: O(n)
    public static int[] twoSum(int[] a, int sum) {
        int[] result = null;

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < a.length; ++i) {
            int other = sum - a[i];
            if (map.containsKey(other)) {
                // found
                result = new int[2];

                int index = map.get(other);
                if (i < index) {
                    result[0] = i;
                    result[1] = index;
                } else {
                    result[0] = index;
                    result[1] = i;
                }
                break;
            }

            if (!map.containsKey(a[i]))
                map.put(a[i], i);
        }

        return result;
    }

    // if the array is not sorted
    // return the first group found, if want to return all resuts, use a ArrayList to save all found groups
    // Time: O(n^2)
    // Memory: O(n)
    public static int[] threeSum(int[] a, int sum) {
        int[] result = null;

        for (int ii = 0; ii < a.length; ++ii) {
            HashMap<Integer, Integer> map = new HashMap<>();

            for (int i = ii + 1; i < a.length; ++i) {
                int other = sum - a[i] - a[ii];
                if (map.containsKey(other)) {
                    // found
                    result = new int[3];

                    int index = map.get(other);
                    if (i < index) {
                        result[0] = ii;
                        result[1] = i;
                        result[2] = index;
                    } else {
                        result[0] = ii;
                        result[1] = index;
                        result[2] = i;
                    }
                    return result;
                }

                if (!map.containsKey(i))
                    map.put(a[i], i);
            }
        }
        return result;
    }

    // if the array is sorted
    // return the first group found, if want to return all resuts, use a ArrayList to save all found groups
    // Time: O(n)
    // Memory: O(n)
    public static int[] twoSumInSortedArray(int[] a, int sum) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int x = a[i] + a[j];
            if (x == sum)
                // found
                return new int[]{i, j};
            else if (x < sum)
                i++;
            else
                j--;
        }
        return null;
    }

    // if the array is sorted
    // Time: O(n^2)
    public static ArrayList<int[]> threeSumInSortedArray(int[] a, int sum) {
        ArrayList<int[]> result = new ArrayList<>();

        if (a.length < 3)
            return result;

        HashMap<Integer, Boolean> used = new HashMap<>();

        for (int ii = 0; ii < a.length; ++ii) {
            int i = ii + 1;
            int j = a.length - 1;
            int target = sum - a[ii];
            while (i < j) {
                int x = a[i] + a[j];
                if (x == target) {
                    // found
                    int[] triple = new int[3];
                    triple[0] = a[ii];
                    triple[1] = a[i];
                    triple[2] = a[j];
                    // avoid duplicates
                    int hash = triple[0] * 10 * 10 + triple[1] * 10 + triple[2];
                    if (!used.containsKey(hash)) {
                        used.put(hash, true);
                        result.add(triple);
                    }
                    break;
                } else if (x < target)
                    i++;
                else
                    j--;
            }
        }
        return result;
    }

    // if the array is sorted
    // Time: O(n^3+nlogn)
    public static ArrayList<int[]> fourSumInSortedArray(int[] a, int sum) {
        ArrayList<int[]> result = new ArrayList<>();

        if (a.length < 4)
            return result;

        HashMap<Integer, Boolean> used = new HashMap<>();

        for (int kk = 0; kk < a.length; ++kk) {
            for (int ii = kk + 1; ii < a.length; ++ii) {
                int i = ii + 1;
                int j = a.length - 1;
                int target = sum - a[ii] - a[kk];
                while (i < j) {
                    int x = a[i] + a[j];
                    if (x == target) {
                        // found
                        int[] qua = new int[4];
                        qua[0] = a[kk];
                        qua[1] = a[ii];
                        qua[2] = a[i];
                        qua[3] = a[j];
                        // avoid duplicates
                        int hash = qua[0] * 10 * 10 * 10 + qua[1] * 10 * 10 + qua[2] * 10 + qua[3];
                        if (!used.containsKey(hash)) {
                            used.put(hash, true);
                            result.add(qua);
                        }
                        break;
                    } else if (x < target)
                        i++;
                    else
                        j--;
                }
            }
        }
        return result;
    }

        /*
    Design and implement a TwoSum class. It should support the following operations: add and find.

            add - Add the number to an internal data structure.
    find - Find if there exists any pair of numbers which sum is equal to the value.
    */

    public class TwoSum {
        HashMap<Integer, Integer> map = new HashMap<>();

        public void add(int i) {
            Integer count = map.get(i);
            if (count == null)
                map.put(i, 1);
            else
                map.put(i, count + 1);
        }

        public boolean find(int sum) {
            for (int i : map.keySet()) {
                int other = sum - i;
                if (map.containsKey(other)) {
                    if (other == i && map.get(other) < 2)
                        continue;
                    return true;
                }
            }

            return false;
        }
    }

    public static int atoi(String s) throws Exception {
        if (s == null || s.length() == 0)
            throw new Exception("invalid string");

        boolean isNegative = false;
        int firstNumIndex = 0;
        char firstChar = s.charAt(0);
        if (firstChar == '+')
            firstNumIndex = 1;
        else if (firstChar == '-') {
            firstNumIndex = 1;
            isNegative = true;
        }

        double result = 0;
        int len = s.length();
        for (int i = firstNumIndex; i < len; ++i) {
            char currentChar = s.charAt(i);
            if (currentChar < '0' || currentChar > '9')
                throw new Exception("invalid string");

            result = result * 10 + s.charAt(i) - '0';
        }

        if (isNegative)
            result *= -1;

        if (result >= Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if (result <= Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int) result;
    }

    public static int[] mergeTwoSortedArrays(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];

        int ai = 0;
        int bi = 0;
        int ci = 0;

        while (ai < a.length && bi < b.length) {
            if (a[ai] < b[bi])
                c[ci++] = a[ai++];
            else
                c[ci++] = b[bi++];
        }

        // if b is at end and a is not, copy rest of a
        if (bi >= b.length)
            for (; ai < a.length; ai++)
                c[ci++] = a[ai];

        // if a is at end and b is not, copy rest of b
        if (ai >= a.length)
            for (; bi < b.length; bi++)
                c[ci++] = b[bi];

        return c;
    }

    /*
    Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

    The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
    */

    public static boolean validBrackets(String s) throws Exception {
        if (s == null || s.length() == 0)
            throw new Exception("invalid string");

        HashMap<Character, Character> brackets = new HashMap<>();
        brackets.put('{', '}');
        brackets.put('[', ']');
        brackets.put('(', ')');

        Stack<Character> stack = new Stack<>();

        // in stack
        for (int i = 0; i < s.length(); ++i) {
            char cc = s.charAt(i);
            if (brackets.keySet().contains(cc))
                stack.push(cc);
            else if (brackets.values().contains(cc)) {
                // to match, the top in stack must be the key of the current char
                if (stack.isEmpty())
                    return false;
                char top = stack.pop();
                if (brackets.get(top) != cc)
                    return false;
            }
        }

        // is stack is empty, tehn all matched
        return stack.isEmpty();
    }

    /*
    Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

    For "(()", the longest valid parentheses substring is "()", which has length = 2.
    Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.

    public static int longestValidParentheses1(String s) {
        Stack<int[]> stack = new Stack<int[]>();
        int result = 0;

        for(int i=0; i<=s.length()-1; i++){
            char c = s.charAt(i);
            if(c=='('){
                int[] a = {i,0};
                stack.push(a);
            }else{
                if(stack.empty()||stack.peek()[1]==1){
                    int[] a = {i,1};
                    stack.push(a);
                }else{
                    stack.pop();
                    int currentLen=0;
                    if(stack.empty()){
                        currentLen = i+1;
                    }else{
                        currentLen = i-stack.peek()[0];
                    }
                    result = Math.max(result, currentLen);
                }
            }
        }

        return result;
    }
    */
    public static int longestValidParentheses(String s) throws Exception {
        if (s == null || s.length() == 0)
            throw new Exception("invalid string");

        Stack<Character> stack = new Stack<>();

        int count = 0;
        int maxCount = 0;
        for (int i = 0; i < s.length(); ++i) {
            char cc = s.charAt(i);
            if (cc == '(') {
                stack.push(cc);
            } else if (cc == ')') {
                if (stack.isEmpty()) {
                    maxCount = Math.max(maxCount, count);
                    count = 0;
                } else {
                    stack.pop();
                    count += 2;

                    // count the last valid group
                    if (i == s.length() - 1)
                        maxCount = Math.max(maxCount, count);
                }
            }
        }

        return maxCount;
    }

    public static int strStr(String s, String subString) {
        if (s == null || s.length() == 0)
            return -1;
        if (subString == null || subString.length() == 0)
            return 0;

        int sl = s.length();
        int ssl = subString.length();

        for (int i = 0; i < sl; ++i) {
            if (i + ssl > sl)
                return -1;

            int j = 0;
            for (j = 0; j < ssl; ++j)
                if (s.charAt(i + j) != subString.charAt(j))
                    break;

            if (j == subString.length())
                return i;
        }
        return -1;
    }

    public static String replaceAll(String s, String source, String target) {
        if (s == null || s.length() == 0 || source == null || source.length() == 0 || target == null || target.length() == 0)
            return s;

        int slen = source.length();
        StringBuffer sb = new StringBuffer();
        int start = 0;
        int end = s.indexOf(source);
        while (end != -1) {
            sb.append(s.substring(start, end));
            sb.append(target);
            start = end + slen;
            end = s.indexOf(source, start);
        }
        // add last part
        sb.append(s.substring(start));

        return sb.toString();
    }

    // Generating Permutations of a Set of Elements
    // We know that there are n! permutations of n elements

    public static ArrayList<int[]> permute(int[] a) {
        ArrayList<int[]> result = new ArrayList<>();
        permute(a, 0, result);
        return result;
    }

    // this function generates the permutations of the array from element start to end of array
    // Time: T(n)=n*T(n-1) + O(n)
    //       O(n!)
    private static void permute(int[] a, int start, ArrayList<int[]> result) {
        // if we are at the end of the array, we have one permutation
        if (start == a.length) {
            result.add(a.clone());  // must clone a and add to result
            return;
        }

        for (int i = start; i < a.length; ++i) {
            // swap a[i] and a[current]
            int temp = a[i];
            a[i] = a[start];
            a[start] = temp;

            // recursive from start+1
            permute(a, start + 1, result);

            // swap back
            // swap a[i] and a[current]
            temp = a[i];
            a[i] = a[start];
            a[start] = temp;
        }
    }

    // generate combinations of a set of n elements taken k (n C K)

    public static ArrayList<int[]> combine(int[] a, int k) {
        ArrayList<int[]> result = new ArrayList<>();
        int[] c = new int[k];
//        combine(a, c, k, 0, 0, result);
        combinationUtil(a, 0, k, c, 0, result);
        return result;
    }

    /*
    a[]  ---> Input Array
    ai   ---> index of current element in a[]
    k    ---> Size of a combination to be printed
    ci   ---> Current index in data[]
    c[]  ---> Temporary array to store current combination
    */
    private static void combinationUtil(int[] a, int ai, int k, int[] c, int ci, ArrayList<int[]> result) {
        // Current combination is ready, put in result
        if (ci == k) {
            result.add(c.clone());
            return;
        }

        // When no more elements are there to put in data[]
        if (ai >= a.length)
            return;

        // current is included, put next at next location
        c[ci] = a[ai];
        combinationUtil(a, ai+1, k, c, ci+1, result);

        // current is excluded, replace it with next (Note that
        // ai+1 is passed, but index is not changed)
        combinationUtil(a, ai + 1, k, c, ci, result);
    }

    /* a[]      ---> Input Array
       c[]      ---> Temporary array to store current combination
       k        ---> Size of a combination to be printed
       start    ---> Staring index in a[]
       ci       ---> Current index in c[]

        ci here counts through positions in combinations
        if ci > k, then a combination is done
     */
    private static void combine(int[] a, int[] c, int k, int start, int ci, ArrayList<int[]> result) {
        // we got a combination
        if (ci == k) {
            result.add(c.clone());
            return;
        }

        int n = a.length;
        // replace index with all possible elements. The condition
        // "n-i >= k-ci" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i < n/* && n-i >= k-ci*/; i++) {
            c[ci] = a[i];
            combine(a, c, k, i + 1, ci + 1, result);
        }

    }

    /*
        Given 3 array of Strings.

        Array one = {Red, Green, Blue}
        Array two = {Large, Medium, Small}
        Array three={giant, monster}

        Print out combinations of all three array.

        public void combination(String[][] arrays) {}
     */
    public static ArrayList<ArrayList<String>> combinationsOfArrays(String[][] arrays) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> comb = new ArrayList<>();
        combinationsOfArrays(arrays, 0, comb, result);
        return result;
    }

    private static void combinationsOfArrays(String[][] arrays, int index, ArrayList<String> comb, ArrayList<ArrayList<String>> result) {
        // found a combination
        if (index == arrays.length) {
            result.add(new ArrayList<String>(comb));
            return;
        }

        for (int i = 0; i < arrays[index].length; i++) {
            comb.add(arrays[index][i]);
            combinationsOfArrays(arrays, index + 1, comb, result);
            comb.remove(comb.size() - 1);
        }
    }

        /*
    Combinations
    Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
    For example,
    If n = 4 and k = 2, a solution is:
    [
      [2,4],
      [3,4],
      [2,3],
      [1,2],
      [1,3],
      [1,4],
    ]
     */

    public static ArrayList<ArrayList<Integer>> combinations(int n, int k) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> comb = new ArrayList<>();
        combinations(n, k, 1, 0, comb, result);
        return result;
    }

    private static void combinations(int n, int k, int start, int count, ArrayList<Integer> comb, ArrayList<ArrayList<Integer>> result) {
        if (count == k) {
            result.add(new ArrayList<Integer>(comb));
            return;
        }
        for (int i = start; i <= n; ++i) {
            comb.add(i);
            combinations(n, k, i + 1, count + 1, comb, result);
            comb.remove(comb.size() - 1);
        }
    }

    /*
        Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers
        sums to T. The same repeated number may be chosen from C unlimited number of times.

        Note: All numbers (including target) will be positive integers. Elements in a combination (a1, a2, ... , ak) must be in
        non-descending order. (ie, a1 <= a2 <= ... <= ak). The solution set must not contain duplicate combinations.

        For example, given candidate set 2,3,6,7 and target 7, A solution set is:

        [7]
        [2, 2, 3]
     */

    public static ArrayList<ArrayList<Integer>> combineSum(int[] a, int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> c = new ArrayList<>();
        combineSum(a, 0, sum, c, result);
        return result;
    }

    private static void combineSum(int[] a, int start, int remainingValue, ArrayList<Integer> c, ArrayList<ArrayList<Integer>> result) {
        if (remainingValue == 0) {
            ArrayList<Integer> temp = new ArrayList<Integer>(c);
            result.add(temp);
            return;
        }

        int n = a.length;
        for (int i = start; i < n; i++) {
            if (remainingValue < a[i])
                return;

            c.add(a[i]);
            combineSum(a, i, remainingValue - a[i], c, result);
            c.remove(c.size() - 1);
        }
    }

    /*
    Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

    For example, given n = 3, a solution set is:

    "((()))", "(()())", "(())()", "()(())", "()()()"
     */

    public static ArrayList<String> genParentheses(int n) {
        ArrayList<String> result = new ArrayList<>();
        genParentheses(n, n, "", result);
        return result;
    }

    // DFS like
    /*
    left and right represents the remaining number of ( and ) that need to be added.
    When left > right, there are more ")" placed than "(". Such cases are wrong and the method stops.
    */
    private static void genParentheses(int left, int right, String newString, ArrayList<String> result) {
        if (left > right || left < 0 || right < 0)
            return;

        // one right combination was found
        if (left == 0 && right == 0) {
            result.add(newString);
            return;
        }

        // can add more (
        if (left > 0)
            genParentheses(left - 1, right, newString + "(", result);

        // can add more )
        if (right > 0)
            genParentheses(left, right - 1, newString + ")", result);
    }

    /*
        Given a keypad as shown in diagram, and a n digit number, list all words which are possible by pressing these numbers.
        For example if input number is 234, possible words which can be formed are (Alphabetical order):
        adg adh adi aeg aeh aei afg afh afi bdg bdh bdi beg beh bei bfg bfh bfi cdg cdh cdi ceg ceh cei cfg cfh cfi
     */
    public static ArrayList<String> combinationsFromDigitsOnPhoneKeypad(
            int[] digits) {
        // get valid digits
        int count = 0;
        for (int i=0; i<digits.length; ++i)
            if (digits[i] >= 2 && digits[i] < 10)
                count++;

        // build string array
        String[] ss = new String[count];
        int ii=0;
        for (int i=0; i<digits.length; ++i) {
            switch (digits[i]) {
                case 2:
                    ss[ii++] = "abc";
                    break;
                case 3:
                    ss[ii++] = "def";
                    break;
                case 4:
                    ss[ii++] = "ghi";
                    break;
                case 5:
                    ss[ii++] = "jkl";
                    break;
                case 6:
                    ss[ii++] = "mno";
                    break;
                case 7:
                    ss[ii++] = "pqrs";
                    break;
                case 8:
                    ss[ii++] = "tuv";
                    break;
                case 9:
                    ss[ii++] = "wxyz";
                    break;
            }
        }

        // recursive to get result
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Character> comb = new ArrayList<>();
        combinationsOfStrings(ss, 0, comb, result);

        return result;
    }

    private static void combinationsOfStrings(
            String[] ss, int index,
            ArrayList<Character> comb,
            ArrayList<String> result) {
        if (index == ss.length) {
            String s = "";
            for (char c : comb)
                s += c;

            result.add(s);
            return;
        }

        for (int i=0; i<ss[index].length(); ++i) {
            comb.add(ss[index].charAt(i));
            combinationsOfStrings(ss, index + 1, comb, result);
            comb.remove(comb.size() - 1);
        }
    }


    private static final HashMap<Integer, String> keypadMap = new HashMap<Integer, String>();
    public static ArrayList<String> combinationsFromDigitsOnPhoneKeypad2(int[] digits) {
        // build the map
        keypadMap.put(0, "");
        keypadMap.put(1, "");
        keypadMap.put(2, "abc");
        keypadMap.put(3, "def");
        keypadMap.put(4, "ghi");
        keypadMap.put(5, "jkl");
        keypadMap.put(6, "mno");
        keypadMap.put(7, "pqrs");
        keypadMap.put(8, "tuv");
        keypadMap.put(9, "wxyz");

        ArrayList<String> result = new ArrayList<>();
        ArrayList<Character> comb = new ArrayList<>();
        combinationsFromDigitsOnPhoneKeypad(digits, 0, comb, result);
        return result;
    }

    // A recursive function to print all possible words that can be obtained
    // by input number[] of size n.  The output words are one by one stored
    // in output[]
    // Time: O(m^n) where m is the average length of letters for a key, n is the number of digits
    private static void combinationsFromDigitsOnPhoneKeypad(int[] digits, int currentIndex, ArrayList<Character> comb, ArrayList<String> result) {
        // a combination is ready
        if (currentIndex == digits.length) {
            // convert comb to String
            String s = "";
            for (Character c : comb)
                s += c;
            result.add(s);
            return;
        }

        int currentDigit = digits[currentIndex];
        String currentLetters = keypadMap.get(currentDigit);
        int currentLetterCount = currentLetters.length();

        if (currentLetterCount == 0) {
            combinationsFromDigitsOnPhoneKeypad(digits, currentIndex + 1, comb, result);
        } else {
            // Try all possible characters for current digit in digits[]
            // and recur for remaining digits
            for (int i = 0; i < currentLetterCount; ++i) {
                comb.add(currentLetters.charAt(i));
                combinationsFromDigitsOnPhoneKeypad(digits, currentIndex + 1, comb, result);
                comb.remove(comb.size() - 1);
            }
        }
    }

    /*
        Given a string containing only digits, restore it by returning all possible valid IP address combinations.
        For example: Given "25525511135", return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
     */

    public static ArrayList<String> resolveIpAddress(String s) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> ipAddress = new ArrayList<>();
        resolveIpAddress(s, 0, ipAddress, result);

        // format ip addresses

        ArrayList<String> finalResult = new ArrayList<String>();

        for (ArrayList<String> l : result) {
            StringBuilder sb = new StringBuilder();
            for (String str : l) {
                sb.append(str + ".");
            }
            sb.setLength(sb.length() - 1);
            finalResult.add(sb.toString());
        }

        return finalResult;
    }

    private static void resolveIpAddress(String s, int start, ArrayList<String> ipAddress, ArrayList<ArrayList<String>> result) {
        // if already get 4 numbers, but s is not consumed, return
        if (ipAddress.size() >= 4 && start != s.length())
            return;

        // make sure ipAddress's size + remaining string's length >=4
        if ((ipAddress.size() + s.length() - start + 1) < 4)
            return;

        // ipAddress's size is 4 and no remaining part that is not consumed.
        if (ipAddress.size() == 4 && start == s.length()) {
            ArrayList<String> temp = new ArrayList<String>(ipAddress);
            result.add(temp);
            return;
        }

        // each field has 3 digits in max
        for (int i = 1; i <= 3; i++) {
            // make sure the index is within the boundary
            if (start + i > s.length())
                break;

            // current field
            String field = s.substring(start, start + i);

            // handle case like 001. i.e., if length > 1 and first char is 0, ignore the case.
            if (i > 1 && s.charAt(start) == '0')
                break;

            // make sure each number <= 255
            if (Integer.valueOf(field) > 255)
                break;

            ipAddress.add(field);
            resolveIpAddress(s, start + i, ipAddress, result);
            ipAddress.remove(ipAddress.size() - 1);
        }
    }

    /*
    In computer science, the maximum subarray problem is the task of finding the contiguous subarray
    within a one-dimensional array of numbers (containing at least one positive number) which has
    the largest sum.

    For example, for the sequence of values −2, 1, −3, 4, −1, 2, 1, −5, 4; the contiguous subarray with
    the largest sum is 4, −1, 2, 1, with sum 6.

        def max_subarray(A):
            max_ending_here = max_so_far = A[0]
            for x in A[1:]:
                max_ending_here = max(x, max_ending_here + x)
                max_so_far = max(max_so_far, max_ending_here)
            return max_so_far
     */

    // Time: O(n)
    public static int maxSubArray(int[] a) throws Exception {
        if (a == null || a.length == 0)
            throw new Exception("invalid array");

        int maxSum = a[0];
        int maxSumCurrent = a[0];
        for (int i = 1; i < a.length; ++i) {
            maxSumCurrent = Math.max(a[i], maxSumCurrent + a[i]);
            maxSum = Math.max(maxSum, maxSumCurrent);
        }

        return maxSum;
    }

    // Time: O(n)
    // ret[0] - max sub array sum
    // ret[1] - start index of the subarray
    // ret[2] - end index of the subarray
    public static int[] maxSubArray2(int[] a) throws Exception {
        if (a == null || a.length == 0)
            throw new Exception("invalid array");

        int maxCurrent = a[0];
        int startCurrent = 0;
        int endCurrent = 0;

        int maxSum = maxCurrent;
        int start = startCurrent;
        int end = endCurrent;

        for (int i = 1; i < a.length; ++i) {
            //update maxCurrent
            if (maxCurrent + a[i] > a[i]) {
                maxCurrent = maxCurrent + a[i];
                endCurrent = i;
            } else {
                maxCurrent = a[i];
                startCurrent = i;
                endCurrent = i;
            }

            // update maxSum
            if (maxCurrent > maxSum) {
                maxSum = maxCurrent;
                start = startCurrent;
                end = endCurrent;
            }
        }

        int[] ret = new int[3];
        ret[0] = maxSum;
        ret[1] = start;
        ret[2] = end;
        return ret;
    }

    /*
    Given an array that contains both positive and negative integers, find the product of
    the maximum product subarray. Expected Time complexity is O(n) and only O(1) extra space can be used.
     */

    public static int maxSubArrayProduct(int[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("invalid array");

        int maxCurrent = a[0];
        int minCurrent = a[0];
        int maxProduct = maxCurrent;

        for (int i = 1; i < a.length; ++i) {
            // special case
            if (a[i] == 0) {
                maxCurrent = 1;
                minCurrent = 1;
                continue;
            }

            // update maxCurrent and minCurrent
            // maxCurrent and minCurrent can be any of maxCurrent*a[i], minCurrent*a[i] or a[i]
            int maxi = maxCurrent * a[i];
            int mini = minCurrent * a[i];
            maxCurrent = Math.max(maxi, Math.max(a[i], mini));
            minCurrent = Math.min(maxi, Math.min(a[i], mini));

            // update maxProduct
            if (maxCurrent > maxProduct)
                maxProduct = maxCurrent;
        }

        return maxProduct;
    }

    /*
    Generate a list of all permutation of a string
     */

    public static ArrayList<String> permuteString(String s) {
        ArrayList<String> result = new ArrayList<>();
        if (s == null || s.length() == 0)
            return result;
        if (s.length() == 1) {
            result.add(s);
            return result;
        }

        for (int i = 0; i < s.length(); ++i) {
            char first = s.charAt(i);
            String other = s.substring(0, i) + s.substring(i + 1);
            ArrayList<String> pl = permuteString(other);
            for (int j = 0; j < pl.size(); ++j)
                result.add(first + pl.get(j));
        }
        return result;
    }

    /*
    Find maximum 3 integers in an array
     */

    public static int[] findMaximum3Int(int[] a) {
        int[] r = new int[3];
        r[0] = Integer.MIN_VALUE;
        r[1] = Integer.MIN_VALUE;
        r[2] = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] > r[2]) {
                r[0] = r[1];
                r[1] = r[2];
                r[2] = a[i];
            } else if (a[i] > r[1]) {
                r[0] = r[1];
                r[1] = a[i];
            } else if (a[i] > r[0])
                r[0] = a[i];
        }
        return r;
    }

    /*
    Unique paths
    Given a grid of size m by n, write an algorithm that computes all paths from 0,0 to m,n such that
    you can always step horizontally or vertically but cannot reverse.
     */

    // Time: O(mn)
    // Space: O(1) with calling stack
    public static ArrayList<ArrayList<int[]>> findForwardPaths(int[][] g) {
        ArrayList<ArrayList<int[]>> result = new ArrayList<>();
        if (g == null)
            return result;

        ArrayList<int[]> path = new ArrayList<>();
        path.add(new int[]{0, 0});
        findForwardPaths(0, 0, g.length, g[0].length, path, result);

        return result;
    }

    private static void findForwardPaths(int x, int y, int m, int n, ArrayList<int[]> path, ArrayList<ArrayList<int[]>> result) {
        // passed
        if (x > m || y > n)
            return;

        // found a path
        if (x == m && y == n) {
            ArrayList<int[]> p = new ArrayList<>();
            p.addAll(path);
            result.add(p);
            return;
        }

        path.add(new int[]{x + 1, y});
        findForwardPaths(x + 1, y, m, n, path, result);
        path.remove(path.size() - 1);

        path.add(new int[]{x, y + 1});
        findForwardPaths(x, y + 1, m, n, path, result);
        path.remove(path.size() - 1);
    }

    /*
    Now consider if some obstacles are added to the grids. How many unique paths would there be?

    An obstacle and empty space is marked as 1 and 0 respectively in the grid.
     */

    public static ArrayList<ArrayList<int[]>> uniquePaths(int[][] g) {
        ArrayList<ArrayList<int[]>> result = new ArrayList<>();
        if (g == null)
            return result;

        ArrayList<int[]> path = new ArrayList<>();
        path.add(new int[]{0, 0});
        uniquePaths(g, 0, 0, path, result);

        return result;
    }

    private static void uniquePaths(int[][] g, int x, int y, ArrayList<int[]> path, ArrayList<ArrayList<int[]>> result) {
        if (x >= g.length || y >= g[0].length || g[x][y] == 1)
            return;

        // foun a path
        if (x == g.length - 1 && y == g[0].length) {
            result.add(new ArrayList<int[]>(path));
            return;
        }

        path.add(new int[]{x + 1, y});
        uniquePaths(g, x + 1, y, path, result);
        path.remove(path.size() - 1);

        path.add(new int[]{x, y + 1});
        uniquePaths(g, x, y + 1, path, result);
        path.remove(path.size() - 1);
    }

    /*
    Given an unsorted array of nonnegative integers, find a continuous subarray which adds to a given number.
     */

    // Time: O(n)
    // Space: O(1)
    public static boolean findContinuousSubarraySum(int[] a, int sum, int[] result) {
        int start = 0;
        int end = -1;
        int sumCurrent = a[0];

        // Add elements one by one to sumCurrent and if the curr_sum exceeds the
        // sum, then remove starting element
        for (int i = 1; i <= a.length; ++i) {
            // If sumCurrent exceeds the sum, then remove the starting elements
            while (sumCurrent > sum && start < i - 1) {
                sumCurrent -= a[start];
                start++;
            }

            // found
            if (sumCurrent == sum) {
                end = i;
                result[0] = start;
                result[1] = end;
                return true;
            }

            if (i < a.length)
                sumCurrent += a[i];
        }

        return false;
    }

    /*
    Write a function that can tell if a string is a palindrome regardless of punctuation or capitalization
     */
    public static boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;

        while (i < j) {
            char ci = Character.toLowerCase(s.charAt(i));
            if (ci < 'a' || ci > 'z') {
                i++;
                continue;
            }
            char cj = Character.toLowerCase(s.charAt(j));
            if (cj < 'a' || cj > 'z') {
                j--;
                continue;
            }

            if (ci != cj)
                return false;

            i++;
            j--;
        }

        return true;
    }

    /*
     Check if two strings are Palindrome
     */

    public static boolean isPalindrome(String s1, String s2) {
        if (s1 == null && s2 == null)
            return true;

        int l1 = s1.length();
        int l2 = s2.length();
        if (l1 == 0 && l2 == 0)
            return true;
        if (l1 != l2)
            return false;

        int i = 0;
        int j = l1 - 1;
        while (i < l1 && j >= 0) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(j);
            if (c1 != c2)
                return false;
            i++;
            j--;
        }

        return true;
    }

    /*
     * Returns a^b, as the standard mathematical exponentiation function

        public double pow(double a, int b) {}

        Interviewer looking for log(n) solution, right on first attempt.
     */

    public static double pow(double a, int b) {
        if (a == 0)
            return 0;
        if (a == 1 || b == 0)
            return 1;

        if (b < 0)
            return 1.0 / pow(a, -b);

        if (b % 2 == 0) {
            double p = pow(a, b / 2);
            return p * p;
        } else {
            double p = pow(a, (b - 1) / 2);
            return a * p * p;
        }
    }

    /*
    as we see n's integer sqrt is less than n/2, so we can use binary to search between 1 to n/2 for the integer sqrt

    Time: O(logn)
     */
    public static int integerSqrt(int n) {
        if (n == 0 || n == 1)
            return n;

        int l = 2;
        int r = n / 2;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            float m = (float) n / mid;  //mid*mid;
            if (m == mid)   //(m == n)
                return mid;
            else if (m > mid) //(m < n)
                l = l + 1;
            else
                r = r - 1;
        }
        return -1;
    }

    /*
    as 1+3+5+...+(2n-1) = n * (1+(2n-1))/2 = n * n = n^2
    so, if 1+2+3+5+...+(2n-1) == m
    then, n is m's sqrt

    Time: O(n)
     */
    public static int integerSqrt2(int n) {
        if (n == 0 || n == 1)
            return n;

        int sum = 0;
        for (int i = 1; i <= n / 2; ++i) {
            sum += 2 * i - 1;
            if (sum == n)
                return i; // i is the sqrt
            else if (sum > n)
                return -1;  // no integer sqrt
        }
        return -1;
    }

    public static double sqrt(double v) {
        double xn = v;
        double xn1 = v;
        do {
            xn1 = 0.5 * (xn + v / xn);
            xn = 0.5 * (xn1 + v / xn1);
        } while (xn - xn1 != 0);
        return xn;
    }

    // p is precision for x*x and v
    public static double sqrt(double v, int p) {
        double pl = Math.pow(10, -(p+1));

        double xn = v;
        do {
            xn = 0.5 * (xn + v / xn);
        } while (Math.abs(v - xn * xn) > pl);

        return xn;
    }

    public static int hashCode(String s) {
        int h1 = s.hashCode();

        int h = 0;
        for (int i=0; i<s.length(); ++i)
            h = h * 31 + s.charAt(i);

        int i = h % 10;

        return h;
    }

    public static void fibonacci(int n) {
        if (n <= 0) {
            System.out.print("invalid number");
            return;
        }
        if (n == 1) {
            System.out.print(0);
            return;
        }
        if (n == 2) {
            System.out.print(0 + " " + 1);
            return;
        }

        int n1 = 0;
        int n2 = 1;
        System.out.print(0 + " " + 1);
        for (int i = 0; i < n - 2; ++i) {
            int n3 = n1 + n2;
            System.out.print(" " + n3);

            n1 = n2;
            n2 = n3;
        }
    }

    // implement int division
    public static int divide(int dividend, int divisor) throws Exception {
        if (divisor == 0)
            throw new Exception("divided by zero");

        if (dividend == 0)
            return 0;

        if (dividend == divisor)
            return 1;

        boolean negative = false;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0))
            negative = true;

        if (dividend < 0)
            dividend *= -1;
        if (divisor < 0)
            divisor *= -1;

        if (dividend < divisor)
            return 0;

        int quotient = 0;
        while (dividend >= divisor) {
            dividend -= divisor;
            quotient++;
        }

        if (negative)
            quotient *= -1;

        // remain = dividend-divisor;

        return quotient;
    }

    /*
    Implement the integral part logn base 2 with bit manipulations
     */

    public static int log2(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("argument cannot be less than 0");

        int lg = 0;
        while (n > 0) {
            n >>= 1;
            lg++;
        }
        return lg - 1;
    }

    /*
    Write a function to determine if a string is a number without using any built-in function.
     */

    public static boolean isNumber(String num) {
        if (num == null || num.length() == 0)
            throw new IllegalArgumentException("String is null or empty");

        char[] a = num.toCharArray();

        // first char must be signs or digits
        if (a[0] != '-' && a[0] != '+' && (a[0] < '0' || '9' < a[0]))
            return false;

        int dotCount = 0;
        for (int i = 1; i < a.length; ++i) {
            // dot
            if (a[i] == '.' && i == a.length - 1) // dot cannot be the last char
                return false;
            else if (a[i] == '.') {
                dotCount++;
                if (dotCount > 1)   // at most one dot
                    return false;
            } else if (a[i] < '0' || '9' < a[i]) // digits
                return false;
        }

        return true;
    }

    /*
    Find and store all the valid numbers in an array that are in the string including negative, positive, hexadecimal, octal, binary?

    For example string "abcd 0xa 11.12 123" has values 10, 11.12 , 123.

    I would rephrase the question: find all words(separated from other words through tabs or spaces) in the string that can be expressed
    in the form of decimal number. After the words are obtained store their value in the array.
     */

    public static boolean isNumber2(String s) {
        if (s == null || s.length() == 0)
            return false;

        s = s.toLowerCase();
        int l = s.length();

        int firstNoSignIndex = 0;

        // sign
        if (s.charAt(0) == '+' || s.charAt(0) == '-')
            firstNoSignIndex++;

        // base
        int base = 10;
        if (s.charAt(firstNoSignIndex) == '0') {
            firstNoSignIndex++;
            char ch = s.charAt(firstNoSignIndex);
            if (ch == 'x') {
                base = 16;
                firstNoSignIndex++;
            } else if (ch == 'b') {
                base = 2;
                firstNoSignIndex++;
            } else
                base = 8;
        }

        // no real digits?
        if (firstNoSignIndex >= l)
            return false;

        // digits and dot
        int dotCount = 0;
        for (int i = firstNoSignIndex; i < l; ++i) {
            char ch = s.charAt(i);

            // dot
            if (ch == '.') {
                if (i == firstNoSignIndex || i == l - 1)
                    return false;
                else {
                    dotCount++;
                    if (dotCount > 1)
                        return false;
                }
            } else {
                switch (base) {
                    case 2:
                        if (ch != '0' && ch != '1')
                            return false;
                        break;
                    case 8:
                        if (ch < '0' || ch > '7')
                            return false;
                        break;
                    case 10:
                        if (ch < '0' || ch > '9')
                            return false;
                        break;
                    case 16:
                        if ((ch < '0' || ch > '9') && (ch < 'a' || ch > 'f'))
                            return false;
                        break;
                }
            }
        }

        return true;
    }

    /*
    All combinations of a set
     */

    // Time: O(2^n)
    // Space: O(1)
    public static ArrayList<String> allCombinations(String s) {
        ArrayList<String> result = new ArrayList<>();

        if (s == null)
            return result;

        int length = s.length();
        if (length == 0)
            return result;

        for (int i = 0; i < length; ++i) {
            char current = s.charAt(i);
            int resultSize = result.size();
            for (int j = 0; j < resultSize; ++j) {
                String newCombination = current + result.get(j);
                result.add(newCombination);
            }

            result.add(Character.toString(current));
        }

        return result;
    }

    /*
    All permutations of a set
     */
    //Time: O(n!)
    //Space: O(1)
    public static ArrayList<String> allPermutations(String s) {
        ArrayList<String> result = new ArrayList<String>();

        if (s == null)
            return result;

        int length = s.length();
        if (length == 0)
            return result;

        if (length == 1) {
            result.add(s);
            return result;
        }

        for (int i = 0; i < length; i++) {
            char first = s.charAt(i);
            String other = s.substring(0, i) + s.substring(i + 1);

            ArrayList<String> innerPermutations = allPermutations(other);

            for (int j = 0; j < innerPermutations.size(); j++)
                result.add(first + innerPermutations.get(j));
        }

        return result;
    }

    /*
    Longest common subsequence
     */

    // Time: O(2^n)
    // Space: O(n)
    public static String lcs(String a, String b) {
        if (a == null || b == null)
            return "";

        int aLen = a.length();
        int bLen = b.length();

        if (aLen == 0 || bLen == 0)
            return "";

        String aSub = a.substring(0, aLen - 1);
        String bSub = b.substring(0, bLen - 1);

        // the last char is the same
        if (a.charAt(aLen - 1) == b.charAt(bLen - 1))
            return lcs(aSub, bSub) + a.charAt(aLen - 1);

        // if the last char is not the same, return the longest lcs exclude the last one
        String lcs1 = lcs(a, bSub);
        String lcs2 = lcs(aSub, b);
        return (lcs1.length() > lcs2.length()) ? lcs1 : lcs2;
    }

    public static String rotateChars(String s, int n) {
        if (s == null)
            return null;

        int len = s.length();
        if (s.length() <= 1 || n <= 0)
            return new String(s);

        n = n % len;
        if (n == 0)
            return new String(s);

        char[] a = new char[len];
        for (int i = 0; i < n; ++i)
            a[i] = s.charAt(len - n + i);
        for (int i = n; i < len; ++i)
            a[i] = s.charAt(i - n);

        return new String(a);
    }

    public static String rotateWords(String s, int n) {
        if (s == null)
            return null;

        String[] words = s.split(" ");
        int count = words.length;

        if (count <= 1 || n <= 0)
            return new String(s);

        n = n % count;
        if (n == 0)
            return new String(s);

        String ns = "";
        int i = 0;
        for (i = count - n; i < count; ++i)
            ns += words[i] + " ";
        for (i = 0; i < count - n - 1; ++i)
            ns += words[i] + " ";

        // last word
        ns += words[i];

        return ns;
    }

    /*
     Generate a subset of a known list of objects. This subset   should consider all elements randomly. Consider time complexity.
     */
    // Time: O(n)
    public static ArrayList<Character> randomSubset(char[] s) {
        int n = s.length;
        int maxv = (int) Math.pow(2, n);

        Random rnd = new Random();
        int r = rnd.nextInt(maxv);

        ArrayList<Character> subset = new ArrayList<>();
        int mask = 1;
        for (int i = 0; i < n; ++i) {
            int bit = (r >> i) & mask;
            if (bit == 1)
                subset.add(s[i]);
        }

        return subset;
    }

    /*
    Phone round question Given 2 strings, write a function to   check if the two words are
    one edit away from each other. i.e one word can be formed from another by
    inserting/ deleting / replacing one character Valid Examples : car , cas; car, card.
    Invalid Examples : car, caert
     */

    public static boolean oneEditDiff(String w1, String w2) {
        if (w1 == null || w2 == null)
            return false;

        int l1 = w1.length();
        int l2 = w2.length();

        if (Math.abs(l1 - l2) > 1)
            return false;

        int i1 = 0;
        int i2 = 0;
        int dif = 0;
        while (i1 < l1 && i2 < l2) {
            if (w1.charAt(i1) == w2.charAt(i2)) {
                i1++;
                i2++;
                continue;
            }

            dif++;

            if (dif > 1)
                return false;

            if (l1 > l2) {
                i1++;
            } else if (l1 < l2) {
                i2++;
            } else if (l1 == l2) {
                i1++;
                i2++;
            }
        }

        return true;
    }

    /*
    given a string, remove all 'b's and duplicate all 'a's
     */

    public static String removeBduplicateA(String s) {
        ArrayList<Character> ns = new ArrayList<>();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == 'b')
                continue;
            else if (s.charAt(i) == 'a') {
                ns.add('a');
                ns.add('a');
                continue;
            }

            ns.add(s.charAt(i));
        }

        StringBuilder sb = new StringBuilder();
        for (char c : ns) {
            sb.append(c);
        }

        return sb.toString();
    }

    /*
    Given a list of string, group those that are anagrams
     */

    public static ArrayList<ArrayList<String>> groupAnagrams(String[] words) {
        HashMap<HashMap<Character, Integer>, ArrayList<String>> mainMap = new HashMap<>();
        for (String w : words) {
            HashMap<Character, Integer> wMap = buildMap(w);
            ArrayList<String> wl = mainMap.get(wMap);
            if (wl == null) {
                wl = new ArrayList<>();
                wl.add(w);
                mainMap.put(wMap, wl);
            } else
                wl.add(w);
        }

        return new ArrayList<ArrayList<String>>(mainMap.values());
    }

    private static HashMap<Character, Integer> buildMap(String word) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            Integer count = map.get(c);
            if (count == null)
                map.put(c, 1);
            else
                map.put(c, count + 1);
        }
        return map;
    }

    /*
    1--a 2--b ... 26--z given a digit , return the count of the possible output
    eg. '1313' --- 4 acac mac mm acm '101' --1 ja cannot discard any digit .
     */
    public static int digitsMapCharsCount(char[] digits) {
        return digitsMapCharsCount(digits, digits.length);
    }

    private static int digitsMapCharsCount(char[] digits, int n) {
        if (n < 2)
            return 1;

        int count = 0;

        if (digits[n - 1] > 0)
            count += digitsMapCharsCount(digits, n - 1);

        if (n >= 2) {
            if (digits[n - 2] < 2 || (digits[n - 2] == 2 && digits[n - 1] < 7))
                count += digitsMapCharsCount(digits, n - 2);
        }

        return count;
    }

    // input as String
    public static int digitsMapCharsCount2(String s) {
        char[] digits = s.toCharArray();
        return digitsMapCharsCount2(digits, digits.length);
    }

    private static int digitsMapCharsCount2(char[] digits, int n) {
        if (n < 2)
            return 1;

        int count = 0;

        if (digits[n - 1] > '0')
            count += digitsMapCharsCount2(digits, n - 1);

        if (n >= 2) {
            if (digits[n - 2] < '2' || (digits[n - 2] == '2' && digits[n - 1] < '7'))
                count += digitsMapCharsCount2(digits, n - 2);
        }

        return count;
    }

    /*
    Give a set of objects and a function. Pass two objects to   that function and it can tell you
    whether one object points to another one. Find one object that is pointed by all other objects
     */
//    public static Object findObjectPointedByAll(Object[] objs) {
//        HashMap<Object, Boolean> failedObjs = new HashMap<>();
//        for (int i=0; i<objs.length; ++i) {
//            if (failedObjs.keySet().contains(objs[i]))
//                continue;
//
//            int j = 0;
//            for (j=i-1; j>=0;--j) {
//                if (!isPointedBy(objs[j], objs[i]))
//                    failedObjs.put(objs[j], true);
//                if (!isPointedBy(objs[i], objs[j])) {
//                    failedObjs.put(objs[i], true);
//                    break;
//                }
//            }
//            if (j >= 0)
//                continue;
//
//            for (j=i+1; j<objs.length;++j) {
//                if (!isPointedBy(objs[j], objs[i]))
//                    failedObjs.put(objs[j], true);
//                if (!isPointedBy(objs[i], objs[j])) {
//                    failedObjs.put(objs[i], true);
//                    break;
//                }
//            }
//            if (j == objs.length)
//                return objs[i];
//        }
//
//        return null;
//    }

    /*
    Shuffle a given array such that each position is equally likely
     */

    // Fisher-Yates
    // Time: O(n)
    // Space: O(1)
    public static void shuffleEquallyLikely(int[] a) {
        if (a == null || a.length == 0)
            return;

        Random random = new Random();
        for (int i = 0; i < a.length - 1; ++i) {
            // r is a random integer, i <= r < a.length
            int r = random.nextInt(a.length - i) + i;

            // swap element i and r
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
//    public static void shuffleEquallyLikely2(int[] a) {
//        if (a == null || a.length == 0)
//            return;
//
//        Random random = new Random();
//        for (int i=a.length-1; i>=1; i--) {
//            // r is a random integer, 0 <= r <= i
//            int r = random.nextInt(i+1);
//            // swap element i and r
//            int temp = a[i];
//            a[i]  =a[r];
//            a[r] = temp;
//        }
//    }

    /*
    Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot be planted in adjacent plots - they would compete for water and both would die. Given a flowerbed (represented as an array containing booleans), return if a given number of new flowers can be planted in it without violating the no-adjacent-flowers rule
    Sample inputs

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
    // Time: O(n) - n is number of flower positions
    // Space: O(1)
    public static boolean canPlaceFlowers(List<Boolean> flowerbed, int numberToPlace) {
        if (numberToPlace == 0)
            return true;
        if (flowerbed == null)
            return false;

        int fbs = flowerbed.size();
        if (fbs == 0)
            return false;
        if (fbs < numberToPlace)
            return false;

        // add a fake free position at the end
        flowerbed.add(false);

        int i = 0;
        int canPlace = 0;
        do {
            if (flowerbed.get(i)) {
                // current is not free, check 2 positions ahead
                i += 2;
            } else {
                // current is free

                // check next
                if (flowerbed.get(i + 1)) {
                    // next is not free, check 3 positions ahead
                    i += 3;
                } else {
                    // next is free, then current can be placed
                    canPlace++;
                    if (canPlace >= numberToPlace)
                        return true;

                    // check 2 positions ahead
                    i += 2;
                }
            }
        } while (i < fbs);

        return false;
    }

    /*
        Write a function to compute the maximum length palindromic sub-sequence of an array.
        A palindrome is a sequence which is equal to its reverse.
        A sub-sequence of an array is a sequence which can be constructed by removing elements of the array.
        Ex: Given [4,1,2,3,4,5,6,5,4,3,4,4,4,4,4,4,4] should return 10 (all 4's)
    */

    public static int maxLengthPalindrome(int[] values, int l, int r) {
        if (values == null || values.length == 0 || l > r)
            return 0;
        if (l == r)
            return 1;

        if (values[l] == values[r])
            return maxLengthPalindrome(values, l + 1, r - 1) + 2;

        int lmax = maxLengthPalindrome(values, l, r - 1);
        int rmax = maxLengthPalindrome(values, l + 1, r);
        return Math.max(lmax, rmax);
    }

    public static int maxLengthPalindrome(String s, int l, int r) {
        if (s == null || s.length() == 0 || l > r)
            return 0;
        if (l == r)
            return 1;

        if (s.charAt(l) == s.charAt(r))
            return maxLengthPalindrome(s, l + 1, r - 1) + 2;

        int lmax = maxLengthPalindrome(s, l, r - 1);
        int rmax = maxLengthPalindrome(s, l + 1, r);
        return Math.max(lmax, rmax);

    }
    /*
    Write a program that gives count of common characters presented in an array of strings..(or array of character arrays)

    For eg.. for the following input strings..

    aghkafgklt
    dfghako
    qwemnaarkf

    The output should be 3. because the characters a, f and k are present in all 3 strings.

    Note: The input strings contains only lower case alphabets
     */

    public static int findCommonChars(String[] a) {
        if (a == null || a.length == 0)
            return 0;

        ArrayList<HashSet<Character>> sets = new ArrayList<>();

        for (int i = 0; i < a.length; ++i) {
            String s = a[i];
            HashSet<Character> set = new HashSet<>();

            for (int j = 0; j < s.length(); ++j) {
                char c = s.charAt(j);
                if (!set.contains(c)) {
                    set.add(c);
                }
            }

            sets.add(set);
        }

        int commonCharsCount = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            boolean found = true;
            for (int i = 0; i < a.length; ++i) {
                if (!sets.get(i).contains(c)) {
                    found = false;
                    break;
                }
            }
            if (found)
                commonCharsCount++;
        }

        return commonCharsCount;
    }

    public static void pp(char[] a, int start, ArrayList<String> result) {
        if (a == null || a.length == 0)
            return;

        if (a.length == start) {
            result.add(new String(a));
            return;
        }

        for (int i = start; i < a.length; ++i) {
            // swap i and start
            char t = a[start];
            a[start] = a[i];
            a[i] = t;

            pp(a, start + 1, result);

            // swap back
            t = a[start];
            a[start] = a[i];
            a[i] = t;
        }
    }

    /*
        Word Wrap / String Justification algorithm.
        Given a set of words and a length.
        You are required to print the words such that the words on each line end almost on the same column and the number of trailing spaces at the end is minimized.

        Given aaa bb cc ddddd and length is 5 print the following output.

        aaa
        bb cc
        ddddd
     */

    private final static int MIN_LENGTH_TO_SPLIT = 5;
    private final static float MIN_RATIO_TO_SPLIT = 0.9f;

    public static ArrayList<String> wordWrap(String[] words, int width) {
        ArrayList<String> justs = new ArrayList<>();

        String carry = "";
        for (int i = 0; i < words.length; ++i) {
            carry = carry.trim();
            if (carry.length() >= width) {
                // carry must be split
                String line = carry.substring(0, width);
                carry = carry.substring(width);
                if (carry != "")
                    carry = carry + " " + words[i];
                else
                    carry = words[i];
                justs.add(line);
            } else {
                String current = words[i];
                if (carry != "")
                    current = carry + " " + current;
                int len = current.length();

                if (len == width) {
                    justs.add(current);
                    carry = "";
                } else if (len > width) {
                    // need to check whether to spilt or not
                    if (carry.length() > (int) (MIN_RATIO_TO_SPLIT * width) || words[i].length() <= MIN_LENGTH_TO_SPLIT) {
                        justs.add(carry);
                        carry = words[i];
                    } else {
                        String line = current.substring(0, width);
                        carry = current.substring(width);
                        justs.add(line);
                    }
                } else
                    carry = current;
            }
        }

        // process left carry
        while (carry != "") {
            carry = carry.trim();
            if (carry.length() >= width) {
                // carry must be split
                String line = carry.substring(0, width);
                carry = carry.substring(width);
                justs.add(line);
            } else {
                justs.add(carry);
                carry = "";
            }
        }

        return justs;
    }
    /*
    Verify Sudoku
     */

    public static boolean verifySudoku(int[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0 || a.length != a[0].length || a.length % 3 != 0)
            return false;

        int len = a.length;

        // verify rows
        for (int i = 0; i < len; ++i)
            if (!verifyRange(a, i, 0, i, len))
                return false;

        // verify cols
        for (int j = 0; j < len; ++j)
            if (!verifyRange(a, 0, j, len, j))
                return false;

        // verify each 3x3 blocks
        for (int i = 0; i < len - 3; i += 3) {
            for (int j = 0; j < len - 3; j += 3) {
                if (!verifyRange(a, i, j, i + 3, j + 3))
                    return false;
            }
        }

        return true;
    }

    private static boolean verifyRange(int[][] a, int starti, int startj, int endi, int endj) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = starti; i < endi; ++i) {
            for (int j = startj; j < endj; ++j) {
                if (a[i][j] != 0) {
                    if (set.contains(a[i][j]))
                        return false;
                    else
                        set.add(a[i][j]);
                }
            }
        }
        return true;
    }

    /**
     * Given a matrix of following between N LinkedIn users (with ids from 0 to N-1):
     * followingMatrix[i][j] == true iff user i is following user j
     * thus followingMatrix[i][j] doesn't imply followingMatrix[j][i].
     * Let's also agree that followingMatrix[i][i] == false
     * <p/>
     * Influencer is a user who is:
     * - followed by everyone else and
     * - not following anyone himself
     * <p/>
     * This method should find an Influencer by a given matrix of following,
     * or return -1 if there is no Influencer in this group.
     */
    public static int getInfluencer(boolean[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0 || m.length != m[0].length)
            return -1;
        int len = m.length;

        // store checking results
        HashSet<Integer> failure = new HashSet<>();

        for (int i = 0; i < len; ++i) {
            // if we already knew i is not a influencer, don't do further checking
            if (failure.contains(i))
                continue;

            // check i with other users
            for (int j = 0; j < len; ++j) {
                if (i != j) {
                    // if i follow j or i is not followed by j, then i is not a influencer
                    if (m[i][j] || !m[j][i])
                        failure.add(i);

                    // if we already knew j is not a influencer, don't do further checking
                    if (failure.contains(j))
                        continue;
                    // if j follow i or j is not followed by i, then j is not a influencer
                    if (m[j][i] || !m[i][j])
                        failure.add(j);
                }
            }

            // if i didn't fail, i is a influencer
            if (!failure.contains(i))
                return i;
        }

        if (failure.size() < len) {
            // influencer is the one not in failure map
            for (int i = 0; i < len; ++i)
                if (!failure.contains(i))
                    return i;
        }

        // no influencer
        return  -1;
    }

    /*
    Find all the repeating sub-string sequence of specified length in a large string sequence. The sequences returned i.e. the output must be sorted alphabetically.

    For e.g.

    Input String: "ABCACBABC"
    repeated sub-string length: 3

    Output: ABC

    Input String: "ABCABCA"
    repeated sub-string length: 2

    Output: AB, BC, CA
     */

    public static HashSet<String> findRepeatingSubstrng(String s, int subStringLength) {
        HashSet<String> all = new HashSet<>();
        HashSet<String> result = new HashSet<>();

        for (int i = 0; i <= s.length() - subStringLength; ++i) {
            String ss = s.substring(i, i + subStringLength);
            if (all.contains(ss))
                result.add(ss);
            else
                all.add(ss);
        }

        return result;
    }

    /*
    Given a large document and a short pattern consisting of a few words (eg. W1 W2 W3), find the shortest
    string that has all the words in any order (for eg. W2 foo bar dog W1 cat W3 -- is a valid pattern)
     */

    public static String findShortestString(String[] doc, HashSet<String> pattern) {
        if (doc == null || pattern == null || doc.length == 0 || pattern.size() == 0)
            return null;

        int startIndex = -1;
        int endIndex = -1;
        int minLength = Integer.MAX_VALUE;
        HashMap<String, Integer> patternIndexMap = new HashMap<>();

        for (int i = 0; i < doc.length; ++i) {
            if (pattern.contains(doc[i])) {
                // update pattern word's index in doc
                patternIndexMap.put(doc[i], i);

                // matched pattern
                // for each string macthing, calculate its length and compare with the minLength we have
                if (pattern.size() == patternIndexMap.size()) {
                    int currentStartIndex = Collections.min(patternIndexMap.values());
                    int currentEndIndex = i;
                    int currentLength = stringBetweenTwoWords(doc, currentStartIndex, currentEndIndex).length();
                    if (currentLength < minLength) {
                        minLength = currentLength;
                        startIndex = currentStartIndex;
                        endIndex = currentEndIndex;
                    }
                }
            }
        }

        if (startIndex == -1)
            return null;

        return stringBetweenTwoWords(doc, startIndex, endIndex);
    }

    private static String stringBetweenTwoWords(String[] doc, int startIndex, int endIndex) {
        String s = "";
        for (int i = startIndex; i < endIndex; ++i)
            s += doc[i] + " ";
        return s + doc[endIndex];
    }

    /*
    Display an integer array of [1, 2, 3, 4, 5, 6, 7] in the following format

    1 4 6
    2 5 7
    3

    The method signature takes in an array of integers and the number of columns. In the above example,
    noOfCols = 3. The columns should contain equal number of elements as much as possible.
     */

    public static int[][] printArrayInCol(int[] a, int noOfCols) {
        if (a == null || a.length == 0)
            return null;

        int noOfRow = a.length / noOfCols + ((a.length % noOfCols != 0) ? 1 : 0);
        int[][] aa = new int[noOfRow][noOfCols];

        int elemntNumInLastCol = a.length % noOfCols;
        int difBetweenLastColAndOthers = noOfRow - elemntNumInLastCol;
        int colsNeedAdjust = difBetweenLastColAndOthers; // element num of the last colsNeedAdjust cols should be noOfRow-1
        int colStartToAdjust = noOfCols - colsNeedAdjust;

        int adjustIndex = 0;
        for (int j = 0; j < noOfCols; ++j) {
            for (int i = 0; i < noOfRow; ++i) {
                int index = j * noOfRow + i;

                if (j >= colStartToAdjust && i == noOfRow - 1) { // in a col need to adjust, the last element should be empty
                    aa[i][j] = Integer.MIN_VALUE;
                    adjustIndex--;                              // all elements after this in the original array need to be adjusted
                } else
                    aa[i][j] = a[index + adjustIndex];
            }
        }

        return aa;
    }

    /*
    Find the balance point in an array. (The index where the   sum of the elements to the left it is
    the same as the sum of the elements to the right of it.)
     */

    public static int findBalancePoint(int[] a) {
        if (a == null || a.length == 0)
            return -1;

        int[] sumFromLeft = new int[a.length];
        int sl = 0;
        for (int i = 0; i < a.length; i++) {
            sl += a[i];
            sumFromLeft[i] = sl;
        }

        int[] sumFromRight = new int[a.length];
        int sr = 0;
        for (int i = a.length - 1; i >= 0; i++) {
            sr += a[i];
            sumFromRight[i] = sr;
        }

        for (int i = 0; i < a.length; i++)
            if (sumFromLeft[i] == sumFromRight[i])
                return i;

        return -1;
    }

    /*
    Find the shortest distance between two words in a string
     */

    public static int findShorstestDistanceBetween2Words(String[] words, String w1, String w2) {
        if (words == null || words.length == 0 || w1 == null || w2 == null || w1.length() == 0 || w2.length() == 0)
            return -1;

        if (w1.equals(w2))
            return 0;

        HashMap<String, Integer> map = new HashMap<>();
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < words.length; ++i) {
            if (words[i].equals(w1) || words[i].equals(w2)) {
                map.put(words[i], i);

                if (map.size() == 2) {
                    minDistance = Math.min(minDistance, Math.abs(map.get(w1) - map.get(w2)));
                }
            }
        }

        return minDistance;
    }

    public static int findShorstestDistanceBetween2Words2(String[] words, String w1, String w2) {
        if (words == null || words.length == 0 || w1 == null || w2 == null || w1.length() == 0 || w2.length() == 0)
            return -1;

        if (w1.equals(w2))
            return 0;

        int minDistance = Integer.MAX_VALUE;
        int i = 0;
        int j = words.length - 1;

        int w1l = -1;
        int w1r = -1;
        int w2l = -1;
        int w2r = -1;

        while (i <= j) {
            if (words[i].equals(w1))
                w1l = i;
            else if (words[i].equals(w2))
                w2l = i;

            if (words[j].equals(w1))
                w1r = j;
            else if (words[j].equals(w2))
                w2r = j;

            if (w1l != -1 && w2l != -1)
                minDistance = Math.min(minDistance, Math.abs(w1l - w2l));
            if (w1r != -1 && w2r != -1)
                minDistance = Math.min(minDistance, Math.abs(w1r - w2r));
            if (w1l != -1 && w2l != -1 && w1r != -1 && w2r != -1) {
                minDistance = Math.min(minDistance, Math.abs(w1l - w2r));
                minDistance = Math.min(minDistance, Math.abs(w1r - w2l));
            }

            i++;
            j--;
        }

        return minDistance;
    }

    /* In "the 100 game," two players take turns adding, to a running
    total, any integer from 1..10. The player who first causes the running
    total to reach or exceed 100 wins.
    What if we change the game so that players cannot re-use integers?
    For example, if two players might take turns drawing from a common pool of numbers
    of 1..15 without replacement until they reach a total >= 100. This problem is
    to write a program that determines which player would win with ideal play.

    Write a procedure, "Boolean canIWin(int maxChoosableInteger, int desiredTotal)",
    which returns true if the first player to move can force a win with optimal play.

    Your priority should be programmer efficiency; don't focus on minimizing
    either space or time complexity.
    */

    public static boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        // build pool
        List<Integer> pool = new ArrayList<Integer>();
        for (int i = 1; i <= maxChoosableInteger; i++) {
            pool.add(i);
        }

        ArrayList<Integer> p1l = new ArrayList<>();
        ArrayList<Integer> p2l = new ArrayList<>();
        return helper(pool, desiredTotal, p1l, p2l);
    }

    private static boolean helper(List<Integer> pool, int desireTotal, ArrayList<Integer> p1l, ArrayList<Integer> p2l) {
        // nothing to choose, player 1 failed
        if (pool.size() == 0) {
            return false;
        }

        // with the biggest one the first player can win
        if (pool.get(pool.size() - 1) >= desireTotal) {
            return true;
        }

        // this is the last round, bu player 1 failed to win
        if (pool.size() <= 2) {
            return false;
        }

        Random rnd = new Random();

        for (int i = 0; i < pool.size(); i++) {
            int r1 = rnd.nextInt(pool.size());
            int player1Choice = pool.remove(r1);
            p1l.add(player1Choice);

            int r2 = rnd.nextInt(pool.size());
            int player2Choice = pool.remove(r2);
            p2l.add(player2Choice);

            // succeed!
            if (!helper(pool, desireTotal - player1Choice - player2Choice, p1l, p2l))
                return true;

            // failed, do another try
            pool.add(player2Choice);
            pool.add(player1Choice);
            p1l.remove(new Integer(player1Choice));
            p2l.remove(new Integer(player2Choice));
        }

        return false;
    }

    /*
    All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG".
    When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.
    Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.

    For example,
        Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT", Return: ["AAAAACCCCC", "CCCCCAAAAA"].
     */

    public static HashSet<String> findDNA(String s, int length) {
        HashSet<String> set = new HashSet<>();
        HashSet<String> all = new HashSet<>();

        for (int i = 0; i < s.length() - length; ++i) {
            String ss = s.substring(i, i + length);

            // length is not enough to match
            if (ss.length() < length)
                break;

            // check duplication
            if (all.contains(ss)) {
                // add current
                set.add(ss);
            } else
                all.add(ss);
        }

        return set;
    }

    // You need to write a function to climb n steps you can climb either 1 step at a time or 2 steps a time,
    // write a function to return number of ways to climb a ladder with n step.

    // recursive
    public static int getClimbWays(int steps) {
        if (steps <= 2)
            return steps;

        return getClimbWays(steps - 1) + getClimbWays(steps - 2);
    }


    /*
    implement Arrays.sort(Object[] a);
    //1. Mutate input or return your own array
    //2. I value run time over memory usage. Ideally both should be as minimal
        as possible, but I prefer faster runtime.
    //3. You can assume that all the objects in the array ‘a’ all implement the
        comparable interface.
     */

    // quicksort

    private static int partition(Object[] a, int l, int r) {
        int i = l;
        int j = r;
        int p = l;

        while (i < j) {
            while (((Comparable) a[i]).compareTo(a[p]) < 0)
                i++;
            while (((Comparable) a[j]).compareTo(a[p]) > 0)
                j--;

            // a[i], a[j] not fit, swap them
            if (i < j) {
                Object tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }

        // j is the final position of pivot
        return j;
    }

    private static void quickSort(Object[] a, int l, int r) {
        if (l >= r)
            return;
        int p = partition(a, l, r);
        quickSort(a, l, p);
        quickSort(a, p + 1, r);
    }

    public static void sortArrays(Object[] a) {
        quickSort(a, 0, a.length - 1);
    }

    /*
    generate an array each element is the product of other elements except the one in original array
     */

    public static int[] generateArrayWithProductExceptSamePosition(int[] a) {
        if (a == null || a.length == 0)
            return null;
        int[] b = new int[a.length];

        // product of elements before index
        int p = 1;
        for (int i = 0; i < a.length; ++i) {
            b[i] = p;
            p *= a[i];
        }

        // production of elements after index, and multiple on the product of elements before index
        p = 1;
        for (int i = a.length - 1; i >= 0; --i) {
            b[i] *= p;
            p *= a[i];
        }

        return b;
    }

    /*
    Given a number n, write an efficient function to print all prime factors of n. For example, if the input number is 12,
    then output should be “2 2 3″. And if the input number is 315, then output should be “3 3 5 7″.

    Following are the steps to find all prime factors.
    1) While n is divisible by 2, print 2 and divide n by 2.
    2) After step 1, n must be odd. Now start a loop from i = 3 to square root of n. While i divides n, print i and divide n by i,
        increment i by 2 and continue.
    3) If n is a prime number and is greater than 2, then n will not become 1 by above two steps. So print n if it is greater than 2.
     */

    public static List<Integer> findPrimeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        if (n <= 3) {
            factors.add(n);
            return factors;
        }

        // add all 2s
        while (n % 2 == 0) {
            factors.add(2);
            n = n / 2;
        }

        // now n must be odd, check from 3 to sqrt(n) to get each factor can divide n
        for (int i = 3; i <= sqrt(n); i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n = n / i;
            }
        }

        // for now, if n > 2, then n is a prime factor of original number
        if (n > 2)
            factors.add(n);

        return factors;
    }

    /*
    Given a string, find the length of the longest substring without repeating characters. For example,
    the longest substrings without repeating characters for “ABDEFGABEF” are “BDEFGA” and “DEFGAB”, with length 6.
    For “BBBB” the longest substring is “B”, with length 1. For “GEEKSFORGEEKS”, there are two longest substrings
    shown in the below diagrams, with length 7.
     */

    public static String findLongestSubstringWithoutRepeatingChars(String s) {
        if (s == null || s.length() == 0)
            return null;

        int maxLength = 0;
        int currentStart = 0;
        int currentEnd = 0;
        int start = currentStart;
        int end = currentEnd;
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);

            if (!map.containsKey(c)) {
                map.put(c, i);
                currentEnd = i;
            } else {
                // need to update max?
                if (i - start > maxLength) {
                    maxLength = i - start;
                    start = currentStart;
                    end = currentEnd;
                }

                // update current

                // old position
                int last = map.get(c);
                // remap
                for (int j = start; j <= last; ++j)
                    map.remove(s.charAt(j));
                // add new position
                map.put(c, i);

                // update current pos
                currentStart = last + 1;
                currentEnd = i;
            }
        }

        return s.substring(start, end + 1);
    }

    /*
    Given an unsorted integer array, find the first missing positive integer.

    For example,
    Given [1,2,0] return 3,
    and [3,4,-1,1] return 2.

    Your algorithm should run in O(n) time and uses constant space.

    THoughts:
    The problem is asking for the first missing POSITIVE integer.
    So, given a number in the array,

    1. if it is non-positive, ignore it;
    2. if it is positive, say we have A[i] = x, we know it should be in slot A[x-1]!
        That is to say, we can swap A[x-1] with A[i] so as to place x into the right place.

    We need to keep swapping until all numbers are either non-positive or in the right places.
    The result array could be something like [1, 2, 3, 0, 5, 6, ...]. Then it's easy to tell that
    the first missing one is 4 by iterate through the array and compare each value with their index.

    Since we only touch each value O(1) times, either swap it with another or check it with its index,
    this algorithm runs in time O(n) and use constant space.
     */

    public static int firstMissingPositive(int[] A) {
        if (A == null || A.length == 0)
            return -1;

        // make sure a[i] is at index i-1
        // in-place swap (bucket sort)
        for (int i = 0; i < A.length; ++i) {
            // out of range
            if (A[i] <= 0 || A[i] > A.length)
                continue;

            // already in position
            if (A[i] == i + 1 || A[A[i] - 1] == A[i])
                continue;

            // swap a[i] and a[a[i]-1] to put a[i] at index a[i]-1 (i.e. i == a[i]-1 or a[i] == i+1)
            int t = A[A[i] - 1];
            A[A[i] - 1] = A[i];
            A[i] = t;
        }

        // find the first positive missing integer
        // check each element to see the value at index i+1 is a[i], the first not qualified is the target
        int i = 0;
        while (i < A.length && A[i] == i + 1)
            ++i;

        return i + 1;
    }

    /*
    There are numbers from 1 to N in an array. out of these, one of the number gets duplicated and one is missing.
    The task is to find out the duplicate number.

    Conditions: you have to do it in O(n) time without using any auxilary space (array, bitsets, maps etc..).

    thoughts:
    1. put every a[i] at position a[i]-1, so that a[i]==i+1,
    2. find the a[i] not equal to i+1,
    3. the a[i] is the duplicated and i+1 is the missing one
     */

    public static int findDuplicated(int[] a) {
        if (a == null || a.length == 0)
            return -1;

        int n = a.length;
        for (int i=0; i<n; ++i) {
            if (a[i] < 0 || a[i]>n)
                return -1;

            // a[i] is already in position
            if (a[i] == i+1)
                continue;

            // found the duplicated
            if (a[i] == a[a[i]-1])
                return a[i];

            // swap a[i] and a[a[i]-1] to put a[i] at index a[i]-1 (i.e. i == a[i]-1 or a[i] == i+1)
            int t = a[a[i]-1];
            a[a[i]-1] = a[i];
            a[i] = t;
        }

        // now a[i] should be at i+1, if not this a[i] is the duplicated number, and i+1 is the missing one
        for (int i=0; i<n; ++i) {
            if (a[i] != i + 1) {
                // a[i] is the duplicated number
                // i+1 is the missing number
                return a[i];
            }
        }

        return -1;
    }

    /*
    Given a string S and a string T, count the number of distinct subsequences of T in S.
    A subsequence of a string is a new string which is formed from the original string by deleting
    some (can be none) of the characters without disturbing the relative positions of the remaining
    characters. (ie,"ACE" is a subsequence of"ABCDE" while "AEC" is not).
    Here is an example:
    S = "rabbbit", T = "rabbit"
    Return 3.
     */

    public static int countDistinctSubString(String S, String T) {
        if (T == null || T.length() == 0)
            return 1;
        if (S == null || S.length() == 0)
            return 0;
        if (T.length() > S.length())
            return 0;

//        int count = 0;
//        for (int i=0; i<S.length(); ++i) {
//            if (S.charAt(i) == T.charAt(0))
//                count += countDistinctSubString(S.substring(i+1), T.substring(1));
//        }
//
//        return count;

        if (S.charAt(0) != T.charAt(0))
            return countDistinctSubString(S.substring(1), T);
        else
            return countDistinctSubString(S.substring(1), T) + countDistinctSubString(S.substring(1), T.substring(1));
    }

    /*
    Gas Station
    There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
    You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1).
    You begin the journey with an empty tank at one of the gas stations.
    Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.

    Note:
    The solution is guaranteed to be unique.
     */

    public static int canCompleteCircularRoute(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0 || cost == null || cost.length == 0 || gas.length != cost.length)
            throw new IllegalArgumentException("invalid parameters");

        int gasAmount = 0;
        int i = 0;
        int start = i;
        int triedTimes = 0;

        for (; ; ) {
            if (gas[i] <= 0 || cost[i] <= 0)
                throw new IllegalArgumentException("invalid parameters");

            gasAmount += gas[i] - cost[i];

            i++;
            if (i == gas.length)
                i = 0;

            if (gasAmount < 0) {
                gasAmount = 0;
                start = i;
                triedTimes++;

                // if has tried gas.length times, then failed
                if (triedTimes == gas.length)
                    break;
            } else {
                // back to start, then succeed
                if (start == i)
                    return start;
            }
        }

        return -1;
    }

    /*
    Edit Distance
    Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2.
    (each operation is counted as 1 step.)
    You have the following 3 operations permitted on a word:

    a) Insert a character
    b) Delete a character
    c) Replace a character

    Key Analysis

    Let dp[i][j] stands for the edit distance between two strings with length i and j, i.e., word1[0,...,i-1] and word2[0,...,j-1].
    There is a relation between dp[i][j] and dp[i-1][j-1]. Let's say we transform from one string to another. The first string has
    length i and it's last character is "x"; the second string has length j and its last character is "y".
    The following diagram shows the relation.

    edit-distance-dynamic-programming

    if x == y, then dp[i][j] == dp[i-1][j-1]
    if x != y, and we insert y for word1, then dp[i][j] = dp[i][j-1] + 1
    if x != y, and we delete x for word1, then dp[i][j] = dp[i-1][j] + 1
    if x != y, and we replace x with y for word1, then dp[i][j] = dp[i-1][j-1] + 1
    When x!=y, dp[i][j] is the min of the three situations.

    Initial condition:
    dp[i][0] = i, dp[0][j] = j
     */

    public static int minEditDistance(String w1, String w2) {
        if (w1 == null || w2 == null)
            return 0;

        int l1  =w1.length();
        int l2 = w2.length();

        if (l1 == 0)
            return l2;
        if (l2 == 0)
            return l1;

        int[][] dp = new int[l1 + 1][l2 + 1];
        for (int i = 0; i <= l1; ++i)
            dp[i][0] = i;
        for (int j = 0; j <= l2; ++j)
            dp[0][j] = j;

        for (int i = 1; i <= l1; ++i) {
            char c1 = w1.charAt(i-1);
            for (int j = 1; j <= l2; ++j) {
                char c2 = w2.charAt(j-1);

                if (c1 == c2)
                    dp[i][j] = dp[i-1][j-1];
                else {
                    int insert = dp[i-1][j] + 1;
                    int delete = dp[i][j-1] + 1;
                    int replace = dp[i-1][j-1] + 1;
                    int md = Math.min(insert, Math.min(delete, replace));
                    dp[i][j] = md;
                }
            }
        }

        return dp[l1][l2];
    }

    /*
    Next Permutation
    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
    The replacement must be in-place, do not allocate extra memory.

    Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
    1,2,3 → 1,3,2
    3,2,1 → 1,2,3
    1,1,5 → 1,5,1
     */

    public static void nextPermutation(int[] a) {
        if (a == null || a.length <= 1)
            return;

        // scan the array from right to left, find the first element less than its previous one
        int p = 0;
        for (int i = a.length - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                p = i;
                break;
            }
        }

        // scan the array from right to left, find the first element larger than a[p]
        int q = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] > a[p]) {
                q = i;
                break;
            }
        }

        // if p and q both are 0, then reverse the whole array and return
        if (p == 0 && q == 0) {
            reverseArray(a, 0, a.length - 1);
            return;
        }

        // swap element at p and q
        int t = a[p];
        a[p] = a[q];
        a[q] = t;

        // reverse elements from p+1 to end
        reverseArray(a, p + 1, a.length - 1);
    }

    private static void reverseArray(int[] a, int l, int r) {
        if (r >= a.length)
            return;
        while (l < r) {
            int t = a[l];
            a[l] = a[r];
            a[r] = t;
            l++;
            r--;
        }
    }

    /*
    Given a string s, partition s such that every substring of the partition is a palindrome.

    Return all possible palindrome partitioning of s.

    For example, given s = "aab",
    Return

      [
        ["aa","b"],
        ["a","a","b"]
      ]
     */

    public static ArrayList<ArrayList<String>> palindromePartitions(String s) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        if (s == null || s.length() == 0)
            return result;

        int len = s.length();
        if (len == 1) {
            ArrayList<String> t = new ArrayList<>();
            t.add(s);
            result.add(t);
            return result;
        }

        ArrayList<String> partition = new ArrayList<>();
        palindromePartitions(s, 0, partition, result);

        return result;
    }

    private static void palindromePartitions(String s, int start, ArrayList<String> partition, ArrayList<ArrayList<String>> result) {
        // find a partition
        if (start == s.length()) {
            ArrayList<String> p = new ArrayList<>(partition);
            result.add(p);
            return;
        }

        for (int i = start + 1; i <= s.length(); ++i) {
            String ss = s.substring(start, i);

            if (isPalindrome(ss)) {
                partition.add(ss);
                palindromePartitions(s, i, partition, result);
                partition.remove(partition.size() - 1);
            }
        }
    }

    /*
    Triangle
    Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
    For example, given the following triangle
    [
         [2],
        [3,4],
       [6,5,7],
      [4,1,8,3]
    ]

    The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).

    Note:
    Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
     */

    public static int minPathSumTriangle(ArrayList<ArrayList<Integer>> triangle) {
        return minPathSumTriangle(triangle, 0, 0, 0, Integer.MAX_VALUE);
    }

    private static int minPathSumTriangle(ArrayList<ArrayList<Integer>> triangle, int i, int j, int sum, int minSum) {
        if (i < 0 || i >= triangle.size() || j < 0 || j >= triangle.get(i).size())
            return minSum;

        // add current
        sum += triangle.get(i).get(j);

        if (i == triangle.size() - 1)
            return Math.min(sum, minSum);

//        minSum = minPathSumTriangle(triangle, i+1, j-1, sum, minSum);
        minSum = minPathSumTriangle(triangle, i + 1, j, sum, minSum);
        minSum = minPathSumTriangle(triangle, i + 1, j + 1, sum, minSum);

        return minSum;
    }

    /*
    "Count and Say problem"

    Write a code to do following:
        n   String to print
        0   1
        1   1 1
        2   2 1
        3   1 2 1 1
        ...
        Base case: n = 0 print "1"
        for n = 1, look at previous string and write number of times a digit is seen and the digit itself.
            In this case, digit 1 is seen 1 time in a row... so print "1 1"
        for n = 2, digit 1 is seen two times in a row, so print "2 1"
        for n = 3, digit 2 is seen 1 time and then digit 1 is seen 1 so print "1 2 1 1"
        for n = 4 you will print "1 1 1 2 2 1"

        Consider the numbers as integers for simplicity. e.g. if previous string is "10 1" then the next will
        be "1 10 1 1" and the next one will be "1 1 1 10 2 1"
     */

    public static String countAndSay(int n) {
        ArrayList<Integer> lastResult = new ArrayList<>();
        // when n==0
        lastResult.add(1);
        // loop to n
        for (int i = 1; i <= n; ++i)
            lastResult = countAndSayHelper(lastResult);

        // convert to string
        String s = "";
        for (Integer i : lastResult)
            s += String.valueOf(i);
        return s;
    }

    private static ArrayList<Integer> countAndSayHelper(ArrayList<Integer> lastResult) {
        ArrayList<Integer> result = new ArrayList<>();

        int i = 0;
        while (i < lastResult.size()) {
            int digit = lastResult.get(i);
            int count = 0;
            while (i < lastResult.size() && lastResult.get(i) == digit) {
                count++;
                i++;
            }
            result.add(count);
            result.add(digit);
        }

        return result;
    }

    /*
    Jump Game I
    Given an array of non-negative integers, you are initially positioned at the first index of the array.
    Each element in the array represents your maximum jump length at that position.
    Determine if you are able to reach the last index.
    For example:
    A = [2,3,1,1,4], return true.

    A = [3,2,1,0,4], return false.
     */

    public static boolean canJumpToLast(int[] a) {
        if (a == null || a.length <= 1)
            return true;

        int maxIndex = a[0];
        for (int i = 0; i < a.length; ++i) {
            // cannot reach the end
            if (maxIndex <= i && a[i] == 0)
                return false;

            maxIndex = Math.max(maxIndex, i + a[i]);

            if (maxIndex >= a.length - 1)
                return true;
        }

        return false;
    }

    /*
    Jump Game II
    Given an array of non-negative integers, you are initially positioned at the first index of the array.
    Each element in the array represents your maximum jump length at that position.
    Your goal is to reach the last index in the minimum number of jumps.
    For example:
    Given array A = [2,3,1,1,4]

    The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
     */

    public static int minJumps(int[] a) {
        if (a == null || a.length <= 1)
            return 0;

        int[] jumps = new int[a.length];
        jumps[0] = 0;

        for (int i = 1; i < a.length; ++i) {
            jumps[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; ++j) {
                if (j + a[j] >= i && jumps[j] != Integer.MAX_VALUE) {
                    jumps[i] = Math.min(jumps[i], jumps[j] + 1);
                    break;
                }
            }
        }

        return jumps[a.length - 1];
    }


    // recursive
    public static int minStepsJumpToLast(int[] a) {
        if (a == null || a.length <= 1)
            return 0;

        return minStepsJumpToLast(a, 0, a.length - 1);
    }

    private static int minStepsJumpToLast(int[] a, int l, int r) {
        if (l == r)
            return 0;
        if (a[l] == 0)
            return Integer.MAX_VALUE;

        // Traverse through all the points reachable from arr[l]. Recursively
        // get the minimum number of jumps needed to reach arr[r] from these
        // reachable points.
        int min = Integer.MAX_VALUE;
        for (int i = l + 1; i <= r && i <= l + a[l]; i++) {
            int jumps = minStepsJumpToLast(a, i, r);
            if (jumps != Integer.MAX_VALUE && jumps + 1 < min)
                min = jumps + 1;
        }

        return min;
    }

    /*
    Longest Common Prefix
    Write a function to find the longest common prefix string amongst an array of strings.
     */

    public static String longestCommonPrefix(String[] ss) {
        int pi = 0;
        char c = 0;
        String s = "";
        while (pi < ss[0].length()) {
            boolean iscp = true;
            c = ss[0].charAt(pi);
            for (int i = 1; i < ss.length; ++i) {
                if (pi >= ss[i].length() || ss[i].charAt(pi) != c) {
                    iscp = false;
                    break;
                }
            }
            if (iscp) {
                s += Character.toString(c);
                pi++;
            } else
                break;
        }
        return s;
    }

    /*
    Longest Consecutive Sequence
    Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
    For example,
    Given [100, 4, 200, 1, 3, 2],
    The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.

    Your algorithm should run in O(n) complexity.
     */

    public static int longestConsecutiveSequence(int[] a) {
        if (a == null || a.length == 0)
            return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int i : a)
            set.add(i);

        int max = 0;
        for (int i : set) {
            int left = i - 1;
            int right = i + 1;
            int count = 1;

            // expand to left
            while (set.contains(left)) {
                count++;
                // don't check this num again
                set.remove(left);
                // check next to left
                left--;
            }

            // expand to right
            while (set.contains(right)) {
                count++;
                // don't check this num again
                set.remove(right);
                // check next right
                right++;
            }

            max = Math.max(max, count);
        }

        return max;
    }

    /*
        Subsets
        Given a set of distinct integers, S, return all possible subsets.
        Note:
        Elements in a subset must be in non-descending order.
        The solution set must not contain duplicate subsets.

        For example,
        If S = [1,2,3], a solution is:
        [
          [3],
          [1],
          [2],
          [1,2,3],
          [1,3],
          [2,3],
          [1,2],
          []
        ]
      */

    public static ArrayList<ArrayList<Integer>> subSets(int[] S) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<Integer>());

        if (S == null || S.length == 0)
            return result;

        Arrays.sort(S);

        for (int i = 0; i < S.length; ++i) {
            int currentResultSize = result.size();
            for (int j = 0; j < currentResultSize; ++j) {
                // must create a new list to add a new item
                ArrayList<Integer> ri = new ArrayList<>(result.get(j));
                ri.add(S[i]);
                if (!result.contains(ri))
                    result.add(ri);
            }
        }

        return result;
    }

    /*
    Candy
    There are N children standing in a line. Each child is assigned a rating value.

    You are giving candies to these children subjected to the following requirements:

        1. Each child must have at least one candy.
        2. Children with a higher rating get more candies than their neighbors.

    What is the minimum candies you must give?

    Analysis:

    For the first time, scan from left to right. If current rating is larger than the left one, give one
    more candy to current child than the left one.
    For the second time, scan from right to left. If current rating is larger than the right one, give one
    more candy to current child than the right one.

    We consider the policy as two folds, left policy and right policy. Left policy means a child has more
    candies than his left one if his rating is higher than his left one. The first scan ensures that the
    distribution meets left policy. The second scan ensures that the distribution meets right policy.
    However, it will not violate left policy.
     */

    public static int minCandy(int[] rating) {
        if (rating == null ||rating.length == 0)
            return 0;

        // candy num for each kid
        int[] candy = new int[rating.length];

        // assign candy to each kid by distributing from left
        candy[0] = 1;   // one candy for the first kid
        for (int i=1; i<rating.length; ++i) {
            if (rating[i] > rating[i-1])
                candy[i] = candy[i-1] + 1;  // one more candy for kid with higher rating than previous kid
            else
                candy[i] = 1;
        }

        // adjust candy to each kid by distributing from right
        for (int i = rating.length-2; i>=0; --i) {
            if (rating[i] > rating[i+1])
                candy[i] = Math.max(candy[i+1] + 1, candy[i]); // should not less then candy num distributed from left
        }

        // add candy distributed to all kids
        int sum = 0;
        for (int i=0; i<rating.length; ++i)
            sum += candy[i];

        return sum;
    }

    /*
    Trapping Rain Water
    Given n non-negative integers representing an elevation map where the width of each bar is 1,
    compute how much water it is able to trap after raining.

    For example, given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

    To find the trapped water at position i, we need to find the maximum value of the left elements of i
    and right elements of i. Assuming they are maxLeft[i] and maxRight[i]. The trapped water is
    min(maxLeft[i], maxRight[i]) – A[i] (if this value is larger than 0).

    To find maxLeft and maxRight, we need to scan the array from left to right and from right to left.
     */

    public static int trappingRainWater(int[] a) {
        if (a == null || a.length == 0)
            return 0;

        // scan from left
        int[] maxLeft = new int[a.length];
        maxLeft[0] = a[0];
        for (int i = 1; i < a.length; ++i)
            maxLeft[i] = Math.max(maxLeft[i-1], a[i]);

        // scan from right
        int[] maxRight = new int[a.length];
        maxRight[a.length - 1] = a[a.length - 1];;
        for (int i = a.length - 2; i >= 0; --i)
            maxRight[i] = Math.max(maxRight[i+1], a[i]);

        // get result
        int rainTrapped = 0;
        for (int i = 0; i < a.length; ++i)
            rainTrapped += Math.min(maxLeft[i], maxRight[i]) - a[i];

        return rainTrapped;
    }

    /*
    Container With Most Water
    Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
    n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
    Find two lines, which together with x-axis forms a container, such that the container contains the most water.

    Note: You may not slant the container.

    Initially we can assume the result is 0. Then we scan from both sides.
    If leftHeight < rightHeight, move right and find a value that is greater than leftHeight.
    If leftHeight > rightHeight, move left and find a value that is greater than rightHeight.
    Additionally, keep tracking the max value.
     */

    public static int[] maxWaterContainer(int[] a) {
        int[] res = new int[3];
        res[0] = 0;     // water amount
        res[1] = -1;    // start line index
        res[2] = -1;    // end line index

        if (a == null || a.length <= 1)
            return res;

        int l = 0;              // left
        int r = a.length - 1;   // right

        res[0] = 0;             // water amount
        res[1] = l;             // left
        res[2] = r;             // right

        while (l < r) {
            // update
            int currentArea = Math.min(a[r], a[l]) * (r - l);
            if (res[0] < currentArea) {
                res[0] = currentArea;
                res[1] = l;
                res[2] = r;
            }
            // move line
            if (a[l] <= a[r])
                l++;
            else
                r--;
        }

        return res;
    }

    /*
        Determine whether an integer is a palindrome.
        Do this without extra space.
     */

    public static boolean isPalindromeNumber(int n) {
        if (n < 0)
            return false;

        int div = 1;
        while (n / div > 10)
            div *= 10;

        while (n != 0) {
            int l = n / div;
            int r = n % 10;

            if (l != r)
                return false;

            // remove left/high digit
            n %= div;
            // remove right/low digit
            n /= 10;

            // as 2 digits were removed, div should be divided by 100
            div /= 100;
        }

        return true;
    }

    /*
    Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order. For example, given n = 4,

    [
    [1,   2,  3, 4],
    [12, 13, 14, 5],
    [11, 16, 15, 6],
    [10,  9,  8, 7]
    ]
     */

    public static int[][] spiralMatrix(int n) {
        int[][] m = new int[n][n];
        int count = n * n;

        int sr = 0;
        int er = n - 1;
        int sc = 0;
        int ec = n - 1;
        int ii = 1;
        int i;

        while (ii <= count) {
            // row -->
            for (i = sc; i <= ec; ++i)
                m[sr][i] = ii++;
            sr++;

            // col V
            for (i = sr; i <= er; ++i)
                m[i][ec] = ii++;
            ec--;

            // row <--
            for (i = ec; i >= sc; --i)
                m[er][i] = ii++;
            er--;

            // col ^
            for (i = er; i >= sr; --i)
                m[i][sc] = ii++;
            sc++;
        }

        return m;
    }

    /*
    Minimum Path Sum
    Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
    which minimizes the sum of all numbers along its path.

    Note: You can only move either down or right at any point in time.

    The path to reach (m, n) must be through one of the 2 cells: (m-1, n) or (m, n-1). So minimum cost to
    reach (m, n) can be written as “minimum of the 2 cells plus cost[m][n]”.

        minCost(m, n) = min (minCost(m-1, n), minCost(m, n-1)) + cost[m][n]

     */

    public static int minPathSumDP(int[][] g) {
        if (g == null || g.length == 0 || g[0].length == 0)
            return 0;

        int rn = g.length;
        int cn = g[0].length;
        int[][] min = new int[rn][cn];

        // init
        min[0][0] = g[0][0];
        for (int i = 1; i < rn; ++i)
            min[i][0] = min[i - 1][0] + g[i][0];
        for (int i = 1; i < cn; ++i)
            min[0][i] = min[0][i - 1] + g[0][i];

        // build dp matrix
        for (int i = 1; i < rn; ++i) {
            for (int j = 1; j < cn; ++j) {
                min[i][j] = Math.min(min[i - 1][j], min[i][j - 1]) + g[i][j];
            }
        }

        // back tracking path

        ArrayList<int[]> path = new ArrayList<>();

        // last
        int[] p = new int[2];
        p[0] = rn - 1;
        p[1] = cn - 1;
        path.add(p);

        // back track to the first row or first col
        for (int i = rn - 1; i >= 1; i--) {
            for (int j = cn - 1; j >= 1; j--) {
                p = new int[2];
                if (min[i - 1][j] < min[i][j - 1]) {
                    p[0] = i - 1;
                    p[1] = j;
                } else {
                    p[0] = i;
                    p[1] = j - 1;
                }
                path.add(p);
            }
        }

        // back track in first row or col
        if (p[0] == 0) {
            // in first row
            for (int i = p[1] - 1; i >= 0; i--) {
                p = new int[2];
                p[0] = 0;
                p[1] = i;
                path.add(p);
            }
        } else {
            // in first col
            for (int i = p[1] - 1; i >= 0; i--) {
                p = new int[2];
                p[0] = i;
                p[1] = 0;
                path.add(p);
            }
        }

        // reverse to get path
        Collections.reverse(path);

        return min[rn - 1][cn - 1];
    }

    /*
    // recuisive
    public static int minPathSum(int[][] g) {
        if (g == null || g.length == 0 || g[0].length == 0)
            return 0;
        return minPathSum(g, 0, 0, 0, Integer.MAX_VALUE);
    }

    private static int minPathSum(int[][] g, int m, int n, int sum, int minSum) {
        if (m >= g.length || n >= g[0].length)
            return minSum;

        // update sum
        sum += g[m][n];

        // reached destination
        if (m == g.length-1 && n == g[0].length-1)
            return Math.min(minSum, sum);

        // try (m, n+1)
        minSum = minPathSum(g, m, n + 1, sum, minSum);
        // try (m+1, n)
        minSum = minPathSum(g, m+1, n, sum, minSum);

        return minSum;
   }
*/

    /*
    Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where
    each word is a valid dictionary word. Return all such possible sentences.

    For example, given s = "catsanddog", dict = ["cat", "cats", "and", "sand", "dog"], the solution
    is ["cats and dog", "cat sand dog"].
     */

    public static ArrayList<ArrayList<String>> wordBreak(String s, Set<String> dict) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0 || dict == null || dict.size() == 0)
            return result;

        ArrayList<String> words = new ArrayList<>();

        wordBreak(s, dict, 0, words, result);

        return result;
    }

    // recursive: O(n*n)
    private static void wordBreak(String s, Set<String> dict, int start, ArrayList<String> words, ArrayList<ArrayList<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<String>(words));
            return;
        }

        for (int i = start + 1; i <= s.length(); ++i) {
            String ss = s.substring(start, i);

            if (dict.contains(ss)) {
                words.add(ss);
                wordBreak(s, dict, i, words, result);
                words.remove(words.size() - 1);
            }
        }
    }

    // anothor recursive: O(n*n)
    private static void wordBreak(String s, Set<String> dict, ArrayList<String> words, ArrayList<ArrayList<String>> result) {
        if (s.length() == 0)
            return;

        for (int i = 1; i <= s.length(); ++i) {
            String ss = s.substring(0, i);

            if (dict.contains(ss)) {
                words.add(ss);

                // all matched
                if (i == s.length())
                    result.add(new ArrayList<String>(words));

                wordBreak(s.substring(i), dict, words, result);
                words.remove(words.size() - 1);
            }
        }
    }

    /*
    You are a professional robber planning to rob houses along a street. Each house has a certain amount of
    money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have
    security system connected and it will automatically contact the police if two adjacent houses were broken
    into on the same night.

    Given a list of non-negative integers representing the amount of money of each house, determine the maximum
    amount of money you can rob tonight without alerting the police.

    DP: dp[i] = max(dp[i - 1], dp[i - 2] + num[i - 1]) (the max amount the robber got when robber arrive at shop i,
    he may robbed shop i-1 or not)
     */

    public static int rob(int[] a) {
        if (a == null || a.length == 0)
            return 0;

        // init
        int[] dp = new int[a.length + 1];
        dp[0] = 0;
        dp[1] = a[0];

        // build
        for (int i = 2; i <= a.length; ++i) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + a[i - 1]);
        }

        return dp[a.length];
    }

    /*
    Rotate Image
    You are given an n x n 2D matrix representing an image.
    Rotate the image by 90 degrees (clockwise).

    Follow up:
    Could you do this in-place?

    result[j][m-1-i] = matrix[i][j]

    matrix[i][j] = matrix[n-1-j][i]
     */

    public static void rotateImage(int[][] m) {
        if (m == null)
            return;
        int[][] result = new int[m.length][m.length];
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m.length; ++j)
                result[j][m.length - 1 - i] = m[i][j];
        }
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m.length; ++j)
                m[i][j] = result[i][j];
        }
    }

    public static void rotateImageInPlace(int[][] m) {
        if (m == null)
            return;
        for (int i = 0; i < m.length; ++i) {
            for (int j = 0; j < m.length; ++j) {
                int tmp = m[i][j];
                m[i][j] = m[m.length - 1 - j][i];
                m[m.length - 1 - j][i] = m[m.length - 1 - i][m.length - 1 - j];
                m[m.length - 1 - i][m.length - 1 - j] = m[j][m.length - 1 - i];
                m[j][m.length - 1 - i] = tmp;
            }
        }
    }

    /*
    Sort an array of 0s, 1s and 2s
    Given an array A[] consisting 0s, 1s and 2s, write a function that sorts A[]. The functions should
    put all 0s first, then all 1s and all 2s in last.

    Example
    Input = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};
    Output = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2}
     */

    // Time: O(n)
    public static void sortColors(int[] a) {
        if (a == null || a.length == 0)
            return;

        // move 0 to the beginning part, and 1 and 2 to the ending part

        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            while (a[i] == 0)
                i++;
            while (a[j] != 0)
                j--;

            if (i < j) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }

        // in the non-zero part, move 1 to beginning and 2 to end

        i = 0;
        while (a[i] == 0)
            i++;
        j = a.length - 1;
        while (i < j) {
            while (a[i] == 1)
                i++;
            while (a[j] == 2)
                j--;

            if (i < j) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            }
        }
    }

    /*
    Pascal's Triangle
    Given numRows, generate the first numRows of Pascal's triangle.

    For example, given numRows = 5,
    Return
    [
         [1],
        [1,1],
       [1,2,1],
      [1,3,3,1],
     [1,4,6,4,1]
    ]

     */

    public static ArrayList<int[]> pascalTriangle(int numRows) {
        if (numRows < 1)
            throw new IllegalArgumentException("row number should not less than 1");

        ArrayList<int[]> result = new ArrayList<>();
        int[] prow = new int[1];
        prow[0] = 1;
        result.add(prow);
        for (int i=2; i<=numRows; ++i) {
            prow = pascalRow(prow);
            result.add(prow);
        }

        return result;
    }

    public static int[] pascalTriangleRow(int row) {
        if (row < 1)
            throw new IllegalArgumentException("row number should not less than 1");

        int[] prow = new int[1];
        prow[0] = 1;
        for (int i=2; i<=row; ++i) {
            prow = pascalRow(prow);
        }

        return prow;
    }

    private static int[] pascalRow(int[] previous) {

        // Row is 1 element longer than previous row
        int[] row = new int[previous.length + 1];

        // First and last numbers in row are always 1
        row[0] = 1;
        row[row.length - 1] = 1;

        // The rest of the row can be
        // calculated based on previous row
        for (int i = 1; i< row.length-1; i++) {
            row[i] = previous[i-1] + previous[i];
        }

        return row;
    }

    /*
    Gray Code
    The gray code is a binary numeral system where two successive values differ in only one bit.
    Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.

    For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
    00 - 0
    01 - 1
    11 - 3
    10 - 2


    n-bit Gray Codes can be generated from list of (n-1)-bit Gray codes using following steps.
    1) Let the list of (n-1)-bit Gray codes be L1. Create another list L2 which is reverse of L1.
    2) Modify the list L1 by prefixing a ‘0’ in all codes of L1.
    3) Modify the list L2 by prefixing a ‘1’ in all codes of L2.
    4) Concatenate L1 and L2. The concatenated list is required list of n-bit Gray codes.

    For example, following are steps for generating the 3-bit Gray code list from the list of 2-bit Gray code list.
    L1 = {00, 01, 11, 10} (List of 2-bit Gray Codes)
    L2 = {10, 11, 01, 00} (Reverse of L1)
    Prefix all entries of L1 with ‘0’, L1 becomes {000, 001, 011, 010}
    Prefix all entries of L2 with ‘1’, L2 becomes {110, 111, 101, 100}
    Concatenate L1 and L2, we get {000, 001, 011, 010, 110, 111, 101, 100}

    To generate n-bit Gray codes, we start from list of 1 bit Gray codes. The list of 1 bit Gray code is {0, 1}.
    We repeat above steps to generate 2 bit Gray codes from 1 bit Gray codes, then 3-bit Gray codes from
    2-bit Gray codes till the number of bits becomes equal to n. Following is C++ implementation of this approach.
     */

    public static ArrayList<Integer> grayCode(int numBits) {
        if (numBits < 1)
            throw new IllegalArgumentException("bit number should not less than 1");

        ArrayList<Integer> gc = new ArrayList<>();
        gc.add(0);
        gc.add(1);

        for (int i=2; i<=numBits; ++i) {
            gc = grayCodeList(gc, i);
        }

        return gc;
    }

    private static ArrayList<Integer> grayCodeList(ArrayList<Integer> last, int numBits) {
        ArrayList<Integer> gcList = new ArrayList<>();
        gcList.addAll(last);
        for (int i=last.size()-1; i>=0; i--)
            gcList.add(last.get(i)|(1<<(numBits-1)));
        return gcList;
    }

    /*
    Remove Duplicates from Sorted Array I & II
    Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
    Do not allocate extra space for another array, you must do this in place with constant memory.
    For example,
    Given input array A = [1,1,2],

    Your function should return length = 2, and A is now [1,2].
     */

    public static int removeDuplicates(int[] a) {
        if (a==null || a.length==0)
            return 0;

        int ii = 0;
        a[ii++] = a[0];
        for (int i=1; i<a.length; ++i) {
            if (a[i-1] != a[i])
                a[ii++] = a[i];
        }
        return ii;
    }

    /*
    Follow up for "Remove Duplicates":
    What if duplicates are allowed at most twice?
    For example,
    Given sorted array A = [1,1,1,2,2,3],

    Your function should return length = 5, and A is now [1,1,2,2,3].
     */

    public static int removeDuplicates2(int[] a) {
        if (a==null || a.length==0)
            return 0;

        int ii = 0;
        a[ii++] = a[0];
        boolean duplicated= false;
        for (int i=1; i<a.length; ++i) {
            if (a[i-1] != a[i]) {
                a[ii++] = a[i];
                duplicated = false;
            } else {
                if (!duplicated) {
                    a[ii++] = a[i];
                    duplicated = true;
                }
            }
        }
        return ii;
    }

    /*
    Best Time to Buy and Sell Stock I
    Say you have an array for which the ith element is the price of a given stock on day i.

    If you were only permitted to complete at most one transaction (ie, buy one and sell one share of
    the stock), design an algorithm to find the maximum profit.
     */

    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1)
            return 0;
        int maxProfit = 0;
        int minPrice = prices[0];
        for (int i=1; i<prices.length; ++i) {
            maxProfit = Math.max(maxProfit, prices[i]-minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return maxProfit;
    }

    /*
    Best Time to Buy and Sell Stock II
    Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete as many transactions as you like
    (ie, buy one and sell one share of the stock multiple times). However, you may not engage in multiple
    transactions at the same time (ie, you must sell the stock before you buy again).
    */
    public static int maxProfit2(int[] prices) {
        if (prices == null || prices.length <= 1)
            return 0;
        int maxProfit = 0;
        for (int i=1; i<prices.length; ++i) {
            int diff = prices[i] - prices[i-1];
            if (diff > 0)
                maxProfit += diff;
        }
        return maxProfit;
    }

    /*
    Best Time to Buy and Sell Stock III
    Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete at most two transactions.
    Note:
    You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     */
    public static int maxProfit3(int[] prices) {
        if (prices == null || prices.length < 2)
            return 0;

        // calculate max profit forwarding
        int[] maxProfitForwarding = new int[prices.length];
        int minPrice = prices[0];
        for (int i=1; i<prices.length; ++i) {
            maxProfitForwarding[i] = Math.max(maxProfitForwarding[i-1], prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }

        // calculate max profit backwarding
        int[] maxProfitBackwarding = new int[prices.length];
        int maxPrice = prices[prices.length-1];
        for (int i=prices.length-2; i>=0; --i) {
            maxProfitBackwarding[i] = Math.max(maxProfitBackwarding[i+1], maxPrice - prices[i]);
            maxPrice = Math.max(maxPrice, prices[i]);
        }

        // the max sum of maxProfitForwarding and maxProfitBackwarding is the max profit with 2 transactions
        int maxProfit = 0;
        for (int i =0; i<prices.length; ++i) {
            maxProfit = Math.max(maxProfit, maxProfitForwarding[i] + maxProfitBackwarding[i]);
        }

        return maxProfit;
    }

    /*
    LeetCode - Reverse Integer:

    Reverse digits of an integer.
    Example1: x = 123, return 321
    Example2: x = -123, return -321
     */

    public static int reverseInteger(int n) {
        if (n == 0)
            return 0;

        boolean isNegative = false;
        if (n < 0) {
            isNegative = true;
            n *= -1;
        }

        long result = 0;    // use long to avoid overflow
        while (n != 0) {
            result = result*10 + n % 10;    // n%10 is the lowest digit
            n /= 10;                        // remove the lowest digit
        }

        if (isNegative)
            result *= -1;

        return (int)result;
    }

    /*
    Single Number I
    Given an array of integers, every element appears twice except for one. Find that single one.
     */

    public static int singleNumber(int[] a) {
        if (a==null || a.length==0)
            throw new IllegalArgumentException("invalid array");

        int result = a[0];
        for (int i = 1; i<a.length; ++i)
            result ^= a[i];

        return result;
    }

    /*
    Single Number II
    Given an array of integers, every element appears three times except for one. Find that single one.
     */

    public static int singleNumber2(int[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("invalid array");

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : a) {
            Integer count = map.get(i);
            if (count == null)
                map.put(i, 1);
            else if (count == 1)
                map.put(i, count+1);
            else
                map.remove(i);
        }

        // now only the single one is in map
        return map.keySet().iterator().next();
    }

    /*
    Return the max k numbers from an unsorted integer array. Each number in the array is in the range [0, 10000).
     */

    // Time: O(nlogk)
    // Space: O(k);
    public static int[] maxNumbers(int[] a, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i : a) {
            if (minHeap.size() >= k) {
                int min = minHeap.peek();
                if (i > min) {
                    minHeap.remove();
                    minHeap.add(i);
                }
            } else
                minHeap.add(i);
        }

        int len = minHeap.size();
        int[] result = new int[len];
        for (int i=0; i<len; ++i)
            result[i] = minHeap.remove();
        return result;
    }

    /*
    Given an array of integers and a number N, write a function to rotate the array to the right by N positions
    For example
    Given an array = [A,B,C,D,E,F,G,H] with N=3, the result will be [F,G,H,A,B,C,D,E]
     */

    // Time: O(KN)
    // Space: O(1)
    public static void rotateArray(int[] a, int K) {
        if (a == null || a.length == 0 || K <= 0)
            return;

        K = K % a.length;
        if (K == 0)
            return;

        for (int i=0; i<K; ++i) {
            int backup = a[a.length-1];
            for (int j=a.length-1; j>=1; --j)
                a[j] = a[j-1];
            a[0] = backup;
        }
    }

    // Time: O(N)
    // Space: O(K)
    public static void rotateArray2(int[] a, int K) {
        if (a == null || a.length == 0 || K <= 0)
            return;

        K = K % a.length;
        if (K == 0)
            return;

        int[] backup = new int[K];
        for (int i=0; i<K; ++i)
            backup[i] = a[a.length-K+i];

        for (int j=a.length-1; j>=K; j--)
            a[j] = a[j-K];

        for (int i=0; i<K; ++i)
            a[i] = backup[i];
    }

    // Time: O(N)
    // Space: O(1)
    /*
    The trick is to do three reverse operation.
    One for the entire string, one from index 0 to k-1, and lastly index k to n-1.
    Magically, this will yield the correct rotated array, without any extra space!

    k == 2

    0 1 2 3 4

    4 3    2 1 0

    3 4    2 1 0

    3 4    0 1 2

     */
    public static void rotateArray3(int[] a, int K) {
        if (a == null || a.length == 0 || K <= 0)
            return;

        K = K % a.length;
        if (K == 0)
            return;

        // 1. reverse the whole array
        reverseArray3(a, 0, a.length - 1);

        // 2. reverse 0 to k-1
        reverseArray3(a, 0, K - 1);

        // 3. reverse k to n-1
        reverseArray3(a, K, a.length - 1);
    }

    private static void reverseArray3(int[] a, int l, int r) {
        while (a==null || a.length == 0 || l >= r)
            return;
        while (l < r) {
            int t = a[l];
            a[l] = a[r];
            a[r] = t;
            l++;
            r--;
        }
    }

    /*
    Given an array of 1,000,000 integers where each integer is between 0 and 2^20 – 1
    Find one integer that is between 0 and 2^20 -1 that is not in the array.
    Hint: 2^20 = 1,048,576 > 1,000,000. That means there are at least 48,576 integers that are not in the array
     */

    public static int findOneNotInArray(int[] a) {
        BitSet set = new BitSet(20);
        for (int i : a) {
            set.set(i);
        }
        for (int i=0; i<set.length(); ++i) {
            if (!set.get(i))
                return i;
        }
        return -1;
    }

    /*
    struct LogEntry{
        string candidate;投票姓名
        int time; 投票时间
    };
    string findWinner(int time, vector logs); 让找出在这个时间时候的winner
    c1(1), c2(2), c1(2), c2(3),c2(4) 括号里是投票时间。 所以
    findWinner(2, logs) = c1;
    findWinner(4, logs) = c2;
     */

    static class LogEntry {
        public String candidate;
        public int time;
    }
    public static String findWinner(int time, LogEntry[] logs) {
        HashMap<String, Integer> map = new HashMap<>();

        int max = 0;
        String winner = "";
        for (LogEntry le : logs) {
            if (le.time == time) {
                int count = 1;
                if (map.containsKey(le.candidate))
                    count = map.get(le.candidate) + 1;

                map.put(le.candidate, count);

                if (count > max) {
                    max = count;
                    winner = le.candidate;
                }
            }
        }

        return winner;
    }

    // 给一个时间，找出前k个winner
    public static String[] findWinners(int time, LogEntry[] logs, int k) {
        HashMap<String, Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> lhs, Map.Entry<String, Integer> rhs) {
                return lhs.getValue()-rhs.getValue();
            }
        });

        for (LogEntry le : logs) {
            if (le.time == time) {
                int count = 1;
                if (map.containsKey(le.candidate))
                    count = map.get(le.candidate) + 1;
                map.put(le.candidate, count);
            }
        }

        for (Map.Entry<String, Integer> me : map.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.add(me);
            } else {
                Map.Entry<String, Integer> min = minHeap.peek();
                if (me.getValue() > min.getValue()) {
                    minHeap.remove();
                    minHeap.add(me);
                }
            }
        }

        k = Math.min(k, logs.length);
        String[] winners = new String[k];
        for (int i=0; i<k; ++k)
            winners[i] = minHeap.remove().getKey();
        return winners;
    }

    /*
    Split a String at spaces except when quoted.
     */

    public static String[] splitString(String s) {
        ArrayList<String> ss = new ArrayList<>();
        s = s.trim();

        StringBuilder current = new StringBuilder();
        int i = 0;
        boolean inQuotes = false;
        while (i < s.length()) {
            char ch = s.charAt(i++);

            if (ch == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    ss.add(current.toString());
                    current = new StringBuilder();
                }
            } else if (ch == '\"') {
                inQuotes = !inQuotes;
                if (!inQuotes) {
                    if (current.length() > 0) {
                        ss.add(current.toString());
                        current = new StringBuilder();
                    }
                }
            } else {
                current.append(ch);
            }
        }

        if (current.length() > 0)
            ss.add(current.toString());

        String[] array = new String[ss.size()];
        ss.toArray(array);
        return array;
    }
    /*
    You're  given  an  unsorted  array  of  integers  where  every  integer  appears  exactly 
    twice,  except  for  one  integer  which  appears  only  once.   Write  an  algorithm  (in  a 
    language  of  your  choice)  that  finds  the  integer  that  appears  only  once.
     */

    public static int findSingleInteger(int[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("null or empty array is not allowed");

        int si = a[0];
        for (int i=1; i<a.length; ++i)
            si ^= a[i];

        return si;
    }

    /*
        Given an array of Integers, and a range (low, high), find all continuous subsequences in the array
        which have sum in the range. Is there a solution better than O(n^2)?
     */

    public static ArrayList<int[]> findRange(int[] a, int low, int high) {
        ArrayList<int[]> result = new ArrayList<>();

        if (a==null || a.length==0)
            return result;

        for (int i=0; i<a.length-1; ++i) {
            int sum = a[i];
            if (low < sum && sum < high) {
                int[] range = new int[2];
                range[0] = i;
                range[1] = i;
                result.add(range);
            }
            for (int j=i+1; j<a.length; ++j) {
                sum += a[j];
                if (low < sum && sum < high) {
                    int[] range = new int[2];
                    range[0] = i;
                    range[1] = j;
                    result.add(range);
                }
            }
        }

        return result;
   }

    /*
    [3, 5, 23, 24, 60]，你要输出一个string，结果就是“0-2,4,6-22,25-59,61-99”
     */

    public static String findMissingRange(int[] a) {
        String s = "";
        if (a==null || a.length == 0)
            return s;

        if (a[0] > 1) {
            int n = a[0]-1;
            s += "0-" + n;
        }

        int l = -1;
        int h = -1;
        for (int i=1; i<a.length; i++) {
            if (a[i] - a[i-1] > 1) {
                l = a[i-1] + 1;
                h = a[i] -1;
                if (l == h)
                    s += "," + l;
                else
                    s += "," + l +"-" + h;
            }
        }

        if (a[a.length-1] < 99) {
            l = a[a.length-1] + 1;
            h = 99;
            if (l == h)
                s += "," + l;
            else
                s += "," + l +"-" + h;
        }

        return s;
    }

    /*
    Remove characters from the first string which are present in the second string
    e.g. string a = “abcde”， string b = “aba”， 最后的结果就是”cde “
     */

    public static String removeChars(String source, String target) {
        if (source == null || source.length() == 0 || target == null || target.length() == 0)
            return source;

        // build hashset for target
        HashSet<Character> tgt = new HashSet<>();
        for (int i = 0; i < target.length(); ++i)
            tgt.add(target.charAt(i));

        char[] src = source.toCharArray();
        int iOrignal = 0;
        int iResult = 0;
        while (iOrignal < src.length) {
            char ch = src[iOrignal];
            if (!tgt.contains(ch)) {
                src[iResult] = ch;
                iResult++;
            }
            iOrignal++;
        }

        source = new String(src);
        return source.substring(0, iResult);
    }

    /*
    Serialize and deserialize string array
    String serialize(String[] a);
    String[] deserialize(String b);
     */

    public static String serialize(String[] a) {
        if (a == null || a.length == 0)
            return null;

        StringBuilder sb = new StringBuilder();

        // int -> byte array -> string
        // array length
        ByteBuffer buf = ByteBuffer.allocate(4).putInt(a.length);
        byte[] bytes = buf.array();
        String sLen = new String(bytes);
        sb.append(sLen);

        // each string
        for (String s : a) {
            //string length
            buf = ByteBuffer.allocate(4).putInt(s.length());
            bytes = buf.array();
            sLen = new String(bytes);
            sb.append(sLen);
            // string
            sb.append(s);
        }

        return sb.toString();
    }

    public static String[] deserialize(String s) {
        if (s == null || s.length() == 0)
            return null;

        // string -> byte array -> int
        String ss = s.substring(0, 4);
        ByteBuffer buf = ByteBuffer.wrap(ss.getBytes()); // big-endian by default
        int len = buf.getInt();

        String[] a = new String[len];

        int i=4;
        int ai = 0;
        while (i<s.length()) {
            ss = s.substring(i, i+4);
            i += 4;
            buf = ByteBuffer.wrap(ss.getBytes()); // big-endian by default
            len = buf.getInt();

            a[ai++] = s.substring(i, i+len);
            i += len;
        }
        return a;
    }

    /*
    validate UTF-8 array
     */

    public static boolean isUTF8(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return false;
        }

        // check first byte and get expected length
        int expectedLen = 0;
        if ((bytes[0] & 0b10000000) == 0b00000000) {
            expectedLen = 1;
        } else if ((bytes[0] & 0b11100000) == 0b11000000) {
            expectedLen = 2;
        } else if ((bytes[0] & 0b11110000) == 0b11100000) {
            expectedLen = 3;
        } else if ((bytes[0] & 0b11111000) == 0b11110000) {
            expectedLen = 4;
        } else if ((bytes[0] & 0b11111100) == 0b11111000) {
            expectedLen = 5;
        } else if ((bytes[0] & 0b11111110) == 0b11111100) {
            expectedLen = 6;
        } else {
            return false;
        }

        // is length correct?
        if (expectedLen != bytes.length) {
            return false;
        }

        // check other bytes
        for (int i=1; i<bytes.length; ++i) {
            if ((bytes[0] & 0b11000000) != 0b10000000) {
                return false;
            }
        }

        return true;
    }

    /*
    The longest Increasing Subsequence (LIS) problem is to find the length of the longest subsequence
    of a given sequence such that all elements of the subsequence are sorted in increasing order.
    For example, length of LIS for { 10, 22, 9, 33, 21, 50, 41, 60, 80 } is 6 and LIS is {10, 22, 33, 50, 60, 80}.
     */

    public static int findLIS(int[] a) {
        if (a == null || a.length == 0) {
            return 0;
        }

        int[] lis = new int[a.length];
        for (int i=0; i<a.length;++i) {
            lis[i] = 1;
        }

        for (int i=1; i<a.length; ++i) {
            for (int j=0; j<i; ++j) {
                if (a[i] > a[j] && lis[i] < lis[j]+1) {
                    lis[i] = lis[j] + 1;
                }
            }
        }

        int max = 0;
        for (int i =0; i<a.length; ++i) {
            if (lis[i] > max) {
                max= lis[i];
            }
        }

        return max;
    }

        /*
    find the longest increasing sequence in an integer matrix in 4 directions (up down left right), return the sequence;
    for example:
    input:
    1   2   3   4
    8   7   6   5

    output should be 1 2 3 4 5 6 7 8
     */

    public static ArrayList<Integer> findLIS(int[][] m) {
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> lisPath = new ArrayList<>();
        ArrayList<Integer> maxLisPath = new ArrayList<>();
        for (int i=0; i<m.length; ++i) {
            for (int j=0; j<m[0].length; ++j) {
                path.add(m[i][j]);
                findLIS(m, i, j, path, lisPath);

                // update
                if (lisPath.size() > maxLisPath.size()) {
                    maxLisPath.clear();
                    maxLisPath.addAll(lisPath);
                }

                // clear
                path.clear();
                lisPath.clear();
            }
        }
        return maxLisPath;
    }

    private static void findLIS(int[][] m, int i, int j, ArrayList<Integer> path, ArrayList<Integer> lisPath) {
        boolean hasIncreaseNeighbor = false;

        if (i - 1 >= 0 && m[i][j] < m[i - 1][j]) {
            path.add(m[i-1][j]);
            findLIS(m, i - 1, j, path, lisPath);
            path.remove(path.size() - 1);
            hasIncreaseNeighbor = true;
        }

        if (j - 1 >= 0 && m[i][j] < m[i][j - 1]) {
            path.add(m[i][j-1]);
            findLIS(m, i, j - 1, path, lisPath);
            path.remove(path.size() - 1);
            hasIncreaseNeighbor = true;
        }

        if (i + 1 < m.length && m[i][j] < m[i + 1][j]) {
            path.add(m[i+1][j]);
            findLIS(m, i + 1, j, path, lisPath);
            path.remove(path.size() - 1);
            hasIncreaseNeighbor = true;
        }

        if (j + 1 < m[0].length && m[i][j] < m[i][j + 1]) {
            path.add(m[i][j+1]);
            findLIS(m, i, j+1, path, lisPath);
            path.remove(path.size()-1);
            hasIncreaseNeighbor = true;
        }

        if (!hasIncreaseNeighbor) {
            if (path.size() > lisPath.size()) {
                lisPath.clear();
                lisPath.addAll(path);
            }
        }
    }

    public static boolean isPowerOf2(int n) {
        if (n <= 0)
            return false;
        return (n & (n-1)) == 0;
    }
    public static boolean isPowerOf3(int n) {
        if (n <= 0)
            return false;

        while (n%3 == 0) {
            n /= 3;
        }

        return n == 1;
    }
    public static boolean isPowerOf5(int n) {
        if (n <= 0)
            return false;

        while (n%5 == 0) {
            n /= 5;
        }

        return n == 1;
    }

    /*
    Given an array of integers and an integer k, return true if and only if there are two distinct
    indices i and j in the array such that nums[i] = nums[j] and the difference between i and j is at most k.
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int c = nums[i];
            if (!map.containsKey(c)) {
                map.put(c, i); // c as key, index in array as val
            } else if (i - map.get(c) <= k) {
                return true;
            } else {
                map.put(c, i); // update the val to new index
            }
        }
        return false;
    }
    /*
    Contains Duplicate III
Given an array of integers, find out whether there are two distinct indices i and j in the array
such that the absolute difference between nums[i] and nums[j] is at most t and the absolute
difference between i and j is at most k.
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k < 1 || t < 0) {
            return false;
        }

        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            int c = nums[i];
            // c is current number
            // set.floor(c) is the biggest number in set that is less than c
            // set.ceiling(c) is the smallest number in set that is larger than c
            if (set.floor(c) != null && set.floor(c) >= c - t               // c - floor(c) <= t
                    || set.ceiling(c) != null && set.ceiling(c) <= c + t) {        // ceiling(c) - c <= t
                return true;
            } else {
                // add current number to set
                set.add(c);

                // make sure idex difference of nums in set at most is k
                if (i >= k) {
                    set.remove(nums[i - k]);  // make sure idice difference of nums in set at most is k
                }
            }
        }
        return false;
    }

    public static int minDistance(String[] source, String[] words) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(words[0], -1);
        map.put(words[1], -1);

        int minDist = source.length;
        int left = -1;
        int right = -1;
        for (int i=0; i<source.length; ++i) {
            if (map.containsKey(source[i])) {
                map.put(source[i], i);
                if (words[0].equals(source[i])) {
                    int li = map.get(words[1]);
                    if (li != -1) {
                        if (i-li < minDist) {
                            minDist = i-li;
                            left = li;
                            right = i;
                        }
                    }
                } else {
                    int li = map.get(words[0]);
                    if (li != -1) {
                        if (i-li < minDist) {
                            minDist = i-li;
                            left = li;
                            right = i;
                        }
                    }
                }
            }
        }

        return minDist;
    }

    /*
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        LinkedList<String> wq = new LinkedList<>();
        LinkedList<Integer> sq = new LinkedList<>();

        wq.add(beginWord);
        sq.add(1);

        while (!wq.isEmpty()) {
            String word = wq.remove();
            int step = sq.remove();

            // check if it is finished
            if (endWord.equals(word)) {
                return step;
            }

            char[] wa = word.toCharArray();
            for (int i=0; i<wa.length; ++i) {
                for (char c = 'a'; c<='z'; c++) {
                    char ctmp = wa[i];
                    wa[i] = c;

                    String newWord = String.valueOf(wa);
                    if (wordList.contains(newWord)) {
                        wq.add(newWord);
                        sq.add(step+1);
                        wordList.remove(newWord);
                    }

                    wa[i] = ctmp;
                }
            }
        }

        return 0;
    }
*/
    /*
    	Please use this Google doc to code during your interview. To free your hands for coding, we recommend that you use a headset or a phone with speaker option.




Michael Ennis

Software Engineer, Tools and Infrastructure (SETI)
Google Programmable Ads (GPA) Engineer Productivity (EngProd)

internationalization -> i18n
localization -> l10n

Write a function that when given a word like internationalization (20 letters) or localization (12 letters),
returns the abbreviated form: i18n or l10n if such an abbreviation exists.
(you might even make the function allow for an arbitrary number of letters in the front and the back e.g. in17n, i17on)



public static String abbr(String s, int charBegin, int charEnd) {
	if (s == null || s.length() == 0)
		return s;
	if (charBegin < 0 || charEnd < 0)
		return s;

	int len = s.length(); // 3
	if (len <= charBegin + charEnd)
		return s;

	int n =  len-charBegin-charEnd;
	String ns = String.valueOf(n);
	if (ns.length() + charBegin + charEnd >= len)
return s;

	StringBuilder sb = new StringBuilder();
	int i = 0;
	for (i=0; i<charBegin; ++i)  // 0-1
	      sb.append(s.charAt(i)); //c

	//int n = len-charBegin-charEnd; // 3-1-1= 1
	sb.append(String.valueOf(n)); // 1

	for (i=len-charEnd; i<len; ++i) // 2->2
	      sb.append(s.charAt(i));

	return sb.toString();
}


cat, 1, 1 -> c1t


Write a function that when given a list of words, generates a list of the shortest unique abbreviations
like the abbreviations above for that set of words.  (feel free to change how the data is returned to best
fit what you believe a client of such a function would want)

ex:
[acceptability, acquiescently, accessibility, automatically, acrobatically]
possible abbreviations: [a11y, ac10y, a10ty, au10y, acr9y]


public  static HashSet<String> genShortestAbbr(String[] words) {
	HashSet<String> result = new HashSet<>();
	if (words == null || words.length == 0)
return result;

	for (int i = 0; i<words.length; ++i) {
		int currentBegin = 1;
		int currentEnd = 1;
	String abbreviation = abbr(words[i], currentBegin, currentEnd);
	while (result.contains(abbreviation)) {
String abbr1 = abbr(words[i], currentBegin+1, currentEnd);
String abbr2 = abbr(words[i], currentBegin, currentEnd+1);

if (abbr1.length() > abbr2.length()) {
	abbreviation = abbr1;
	currentBegin++;
} else {
	abbreviation = abbr2;
	currentEnd++;
}

if (abbreviation.equals(words[i]))
	break;

}
result.add(abbreviation);
}
}


3 letters -> 1,2; 2,1
4 letters -> 1,3; 2,2; 3,1


     */
}