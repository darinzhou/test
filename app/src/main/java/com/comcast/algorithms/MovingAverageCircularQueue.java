package com.comcast.algorithms;

public class MovingAverageCircularQueue {

    int[] queue;
    int size;
    int pos;
    int sum;
    int count;

    public MovingAverageCircularQueue(int n) {
        size = n;
        queue = new int[size];
        pos = 0;
        sum =0;
        count = 0;
    }

    public void add(int n) {
        if (count < size) {
            count++;
        } else {
            if (pos == size) {
                pos = 0;
                sum -= queue[0];
            }
        }

        queue[pos] = n;
        pos++;
        sum += n;
    }

    public double getAverage() {
        return (double)sum/count;
    }

    public static void main(String[] args) {
        MovingAverageCircularQueue ma = new MovingAverageCircularQueue(3);

        ma.add(1);
        double av1 = ma.getAverage();

        ma.add(2);
        double av2 = ma.getAverage();

        ma.add(3);
        double av3 = ma.getAverage();

        ma.add(4);
        double av4 = ma.getAverage();

        ma.add(5);
        double av5 = ma.getAverage();

    }
}
