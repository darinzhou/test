package com.comcast.algorithms;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/*
Moving Average from Data Stream

Given a stream of integers and a window size, calculate the moving average of
all integers in the sliding window.

For example,
MovingAverageFromStream m = new MovingAverageFromStream(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3
*/
public class MovingAverageFromStream {

    static class MovingAverage {
        int capacity;
        Queue<Integer> q = new LinkedList<>();
        long sum;

        public MovingAverage(int cap) {
            capacity = cap;
        }

        public int next(int i) {
            if (q.size() >= capacity) {
                int oldest = q.remove();
                sum -= oldest;
            }
            q.add(i);
            sum += i;
            return (int) sum / q.size();
        }
    }

    public static void main(String[] args) {
        MovingAverage m = new MovingAverage(3);
        System.out.println(m.next(1));
        System.out.println(m.next(10));
        System.out.println(m.next(3));
        System.out.println(m.next(5));
        System.out.println(m.next(6));
    }
}
