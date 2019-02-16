package com.comcast.algorithms;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CoinChangeCombinations {

        // Method 1: Recursive with Hashmap to memorize results
        //  O(M*N) / O(M+log(N))  where M is money amount, N is number of coins
        public static long makeChange(int[] coins, int money) {
            if (coins == null || coins.length == 0 || money < 0) {
                return 0;
            }
            HashMap<String, Long> memo = new HashMap<>();
            return makeChange(coins, 0, money, memo);
        }
        private static long makeChange(int[] coins, int si, int money, HashMap<String, Long> memo) {
            if (money == 0) {
                return 1;
            }
            if (si >= coins.length) {
                return 0;
            }

            String key = money + "-" + coins[si];
            if (memo.containsKey(key)) {
                return memo.get(key);
            }

            int moneyWithCoin = 0;
            long ways = 0;
            while (moneyWithCoin <= money) {
                int remainingMoney = money - moneyWithCoin;
                ways += makeChange(coins, si+1, remainingMoney, memo);
                moneyWithCoin += coins[si];
            }
            memo.put(key, ways);
            return ways;
        }

        // method 2: DP: O(M*N) / O(M)  where M is money amount, N is number of coins
        public static long makeChange2(int[] coins, int money) {
            if (coins == null || coins.length == 0 || money < 0) {
                return 0;
            }

            long[] dp =  new long[money+1]; // ways to make changes, index is money (change amount)
            dp[0] = 1L;
            for (int coin : coins) {
                for (int amount = coin; amount < dp.length; ++amount) {
                    dp[amount] = dp[amount] + dp[amount-coin];
                }
            }

            return dp[money];
        }


        public static void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            int m = in.nextInt();
            int coins[] = new int[m];
            for(int coins_i=0; coins_i < m; coins_i++){
                coins[coins_i] = in.nextInt();
            }
            System.out.println(makeChange2(coins, n));
        }

    }

