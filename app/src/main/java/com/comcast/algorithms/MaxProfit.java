package com.comcast.algorithms;

public class MaxProfit {
    /*
    Best Time to Buy and Sell Stock I

    Say you have an array for which the ith element is the price of a given stock on day i.

    If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

    Note that you cannot sell a stock before you buy one.

    Example 1:

    Input: [7,1,5,3,6,4]
    Output: 5
    Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
     */
    public static int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int min = prices[0];
        int maxp = Integer.MIN_VALUE;
        for (int p : prices) {
            maxp = Math.max(maxp, p - min);
            min = Math.min(min, p);
        }

        return maxp;
    }

    /*
    Best Time to Buy and Sell Stock II

    Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times).

    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

    Example 1:

    Input: [7,1,5,3,6,4]
    Output: 7
    Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
             Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
     */
    public static int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int maxp = 0;
        for (int i=1; i<prices.length; ++i) {
            int dif = prices[i] - prices[i-1];
            if (dif > 0) {
                maxp += dif;
            }
        }

        return maxp;
    }

    /*
    Best Time to Buy and Sell Stock III

    Say you have an array for which the ith element is the price of a given stock on day i.

    Design an algorithm to find the maximum profit. You may complete at most two transactions.

    Note: You may not engage in multiple transactions at the same time (i.e., you must sell the
    stock before you buy again).

    Example 1:

    Input: [3,3,5,0,0,3,1,4]
    Output: 6
    Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
             Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
     */

    public static int maxProfit3(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        // save max profit before day i
        int[] left = new int[prices.length];
        int min = prices[0];
        for (int i=1; i<prices.length; ++i) {
            left[i] = Math.max(left[i-1], prices[i] - min);
            min = Math.min(min, prices[i]);
        }

        // save max profit after day i
        int[] right = new int[prices.length];
        int max = prices[0];
        for (int i=prices.length-2; i>=0; --i) {
            right[i] = Math.max(right[i+1], max - prices[i]);
            max = Math.max(max, prices[i]);
        }

        // max profit of 2 transactions
        int maxp = 0;
        for (int i=0; i<prices.length;++i) {
            maxp = Math.max(maxp, left[i] + right[i]);
        }

        return maxp;
    }
}
