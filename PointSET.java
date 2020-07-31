/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points = new TreeSet<>();

    public PointSET() {

    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);

    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);

    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        double x1 = rect.xmin();
        double y1 = rect.ymin();
        double x2 = rect.xmax();
        double y2 = rect.ymax();
        TreeSet<Point2D> rngpoints = new TreeSet<Point2D>();
        for (Point2D p : points) {
            double x = p.x();
            double y = p.y();
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                rngpoints.add(p);
            }
        }
        return rngpoints;

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double dist = Double.POSITIVE_INFINITY;
        Point2D r = p;
        if (points.isEmpty()) return null;
        for (Point2D x : points) {
            double temp = p.distanceSquaredTo(x);
            if (temp < dist) {
                dist = temp;
                r = x;
            }
        }
        return r;
    }


}
