package com.comcast.algorithms;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

interface PointsOnAPlane {

    /**
     * Stores a given point in an internal data structure
     */
    void addPoint(Point point);

    /**
     * For given 'center' point returns a subset of 'm' stored points that are
     * closer to the center than others.
     *
     * E.g. Stored: (0, 1) (0, 2) (0, 3) (0, 4) (0, 5)
     *
     * findNearest(new Point(0, 0), 3) -> (0, 1), (0, 2), (0, 3)
     */
    Collection<Point> findNearest(Point center, int m);

}

public class PointsOnPlane implements PointsOnAPlane {

    private List<Point> points = new ArrayList<>();

    class PointWithCenter implements Comparable<PointWithCenter> {

        private Point center;
        private Point point;
        private long distance;

        public PointWithCenter(Point center, Point point) {
            this.center = center;
            this.point = point;
            distance = (long) Math.sqrt((point.x - center.x) * (point.x - center.x) + (point.y - center.y) * (point.y - center.y));
        }

        public Point getPoint() {
            return point;
        }

        @Override
        public int compareTo(PointWithCenter another) {
            if (distance < another.distance)
                return -1;
            else if (distance > another.distance)
                return 1;
            return 0;
        }
    }

    @Override
    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public Collection<Point> findNearest(Point center, int m) {
        PriorityQueue<PointWithCenter> pq = new PriorityQueue<>();

        for (Point p : points) {
            PointWithCenter pwc = new PointWithCenter(center, p);
            pq.add(pwc);
        }

        List<Point> nearestPoints = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            PointWithCenter pwc = pq.poll();
            if (pwc != null) {
                nearestPoints.add(pwc.getPoint());
            }
        }

        return nearestPoints;
    }
}
