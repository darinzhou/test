package com.comcast.algorithms;

public class KnapSack {

    // weight: weight cn be used
    // n: item number can be selected
    public static int knapsack(int[] wt, int[] val, int weight, int n) {
        if (weight == 0 || n==0) {
            return 0;
        }

        // item n's weight is more than our need
        if (wt[n-1] > weight) {
            return knapsack(wt, val, weight, n-1);
        }

        int valInclude = val[n-1] + knapsack( wt, val, weight-wt[n-1],n-1);
        int valExclude = knapsack(wt, val, weight, n-1);
        return Math.max(valInclude, valExclude);
    }

    public static int knapsack2(int[] wt, int[] val, int weight, int n) {
        if (weight == 0 || n==0) {
            return 0;
        }

        int[][] dp = new int[n+1][weight+1];

        for (int i=0; i<=n; ++i) {
            for (int w =0; w<=weight; ++w) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (wt[i-1] > w) {
                    dp[i][w] = dp[i-1][w];
                } else {
                    int valInclude = val[i-1] + dp[i-1][w - wt[i-1]];
                    int valExclude = dp[i-1][w];
                    dp[i][w] = Math.max(valInclude, valExclude);
                }
            }
        }

        return dp[n][weight];
    }

    }
