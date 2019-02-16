package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.Collections;

/**
 Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

 You may assume that the intervals were initially sorted according to their start times.

 Example 1:
 Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].

 Example 2:
 Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].

 This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
 */

public class IntervalTest {
        /*
    Given a collection of intervals, merge all overlapping intervals.

    For example,
    Given [1,3],[2,6],[8,10],[15,18],
            return [1,6],[8,10],[15,18].
    */

    public static class Interval implements Comparable<Interval> {

        int start;
        int end;

        public Interval() {
            start = 0;
            end = 0;
        }

        public Interval(int s, int e) {
            start = s;
            end = e;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public boolean canMergeWith(Interval another) {
            if (start <= another.getStart())
                return (end >= another.getStart());
            else
                return (another.getEnd() >= start);
        }

        public boolean mergeWith(Interval another) {
            if (!canMergeWith(another))
                return false;
            start = Math.min(start, another.getStart());
            end = Math.max(end, another.getEnd());
            return true;
        }

        @Override
        public int compareTo(Interval another) {
            return start - another.getStart();
        }
    }

    public static ArrayList<Interval> mergeIntervals(ArrayList<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1)
            return intervals;

        // sort
        Collections.sort(intervals);

        ArrayList<Interval> result = new ArrayList<>();

        int i = 0;
        int count = intervals.size();
        Interval current = intervals.get(i);

        i++;
        while (i < count) {
            Interval next = intervals.get(i);
            while (current.mergeWith(next)) {
                i++;
                if (i >= count - 1) {
                    result.add(current);
                    return result;
                }
                next = intervals.get(i);
            }

            result.add(current);
            current = next;
            i++;
        }

        result.add(current);
        return result;
    }

    public static ArrayList<Interval> insertInterval(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> result = new ArrayList<>();
        if (intervals == null || intervals.size() == 0) {
            result.add(newInterval);
            return result;
        }

        // if not sort, sort it
        Collections.sort(intervals);

        int i = 0;
        int count = intervals.size();
        boolean inserted = false;
        while (i < count) {
            Interval current = intervals.get(i);

            if (!inserted && current.canMergeWith(newInterval)) {
                current.mergeWith(newInterval);

                i++;
                while (i < count) {
                    Interval next = intervals.get(i);
                    i++;

                    if (current.canMergeWith(next))
                        current.mergeWith(next);
                    else {
                        result.add(current);
                        result.add(next);
                        inserted = true;
                        break;
                    }
                }
                if (!inserted)
                    result.add(current);

            } else if (newInterval.compareTo(current) < 0) {
                result.add(newInterval);
                result.add(current);
                inserted = true;
                i++;
            }
            else {
                result.add(current);
                i++;
            }
        }

        if (!inserted)
            result.add(newInterval);

        return result;
    }


}
