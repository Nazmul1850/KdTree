/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;


public class kdTreeSelf {
    private Node root;
    private boolean RED = true;
    private int size;
    private ArrayList<RectHV> insertVLines = new ArrayList<>();
    private ArrayList<RectHV> insertHLines = new ArrayList<>();

    public kdTreeSelf() {
        this.size = 0;
    }

    private class Node {
        private Point2D point;
        private boolean color;
        private Node left;
        private Node right;
        private Node parent;
        private RectHV line;

        public Node(Point2D point) {
            this.point = point;
            this.left = this.right = this.parent = null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (!contains(p)) {
            size++;
            root = insert(root, p, RED, null);
        }
    }

    private Node insert(Node x, Point2D p, boolean color, Node parent) {

        if (x == null) {
            Node newNode = new Node(p);
            newNode.color = color;
            try {
                newNode.parent = parent;
                double xmax = 1;
                double ymax = 1;
                double xmin = 0;
                double ymin = 0;
                if (newNode.color == RED) {
                    if (newNode.parent.point.y() < newNode.point.y()) {
                        xmin = newNode.point.x();
                        ymin = newNode.parent.point.y();
                        xmax = xmin;
                        ymax = 1;
                    }
                    else {
                        xmin = newNode.point.x();
                        ymin = 0;
                        xmax = newNode.point.x();
                        ymax = newNode.parent.point.y();
                    }
                }
                else {
                    if (newNode.parent.point.x() < newNode.point.x()) {
                        xmin = newNode.parent.point.x();
                        ymin = newNode.point.y();
                        xmax = 1;
                        ymax = newNode.point.y();
                    }
                    else {
                        xmin = 0;
                        ymin = newNode.point.y();
                        xmax = newNode.parent.point.x();
                        ymax = newNode.point.y();
                    }
                }
                RectHV temp = new RectHV(xmin, ymin, xmax, ymax);
                ArrayList<RectHV> intersected = new ArrayList<>();
                if (newNode.color == RED) {
                    for (RectHV line : insertHLines) {
                        if (!temp.equals(line) && temp.intersects(line) && !line
                                .contains(newNode.parent.point)) {
                            intersected.add(line);
                        }
                    }
                }
                else {
                    for (RectHV line : insertVLines) {
                        if (!temp.equals(line) && temp.intersects(line) && !line
                                .contains(newNode.parent.point)) {
                            intersected.add(line);
                        }
                    }
                }
                if (ymax == 1) {
                    for (RectHV line : intersected) {
                        if (line.ymax() < ymax) {
                            ymax = line.ymax();
                        }
                    }
                }
                else if (ymin == 0) {
                    for (RectHV line : intersected) {
                        if (line.ymin() > ymin) {
                            ymin = line.ymin();
                        }
                    }
                }
                else if (xmax == 1) {
                    for (RectHV line : intersected) {
                        if (line.xmax() < xmax) {
                            xmax = line.xmin();
                        }
                    }
                }
                else if (xmin == 0) {
                    for (RectHV line : intersected) {
                        if (line.xmin() > xmin) {
                            xmin = line.xmin();
                        }
                    }
                }
                if (newNode.color == RED) {
                    insertVLines.add(new RectHV(xmin, ymin, xmax, ymax));
                }
                else {
                    insertHLines.add(new RectHV(xmin, ymin, xmax, ymax));
                }
                newNode.line = new RectHV(xmin, ymin, xmax, ymax);
            }
            catch (NullPointerException e) {
                newNode.line = new RectHV(p.x(), 0, p.x(), 1);
                insertVLines.add(new RectHV(p.x(), 0, p.x(), 1));
            }
            return newNode;
        }
        int cmp;
        if (x.color == RED) {
            cmp = Double.compare(p.x(), x.point.x());
        }
        else {
            cmp = Double.compare(p.y(), x.point.y());
        }
        if (cmp < 0) {
            x.left = insert(x.left, p, !x.color, x);
        }
        else if (cmp > 0) {
            x.right = insert(x.right, p, !x.color, x);
        }
        else x.point = p;
        return x;
    }

    public boolean contains(Point2D p) {
        Node x = root;
        while (x != null) {
            int cmp;
            if (x.color == RED) {
                cmp = Double.compare(p.x(), x.point.x());
            }
            else {
                cmp = Double.compare(p.y(), x.point.y());
            }
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return true;
        }
        return false;
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        draw(x.left);
        if (x.color == RED) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLUE);
        x.line.draw();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.03);
        x.point.draw();
        StdDraw.setPenRadius(0.01);
        draw(x.right);
    }


    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> rngpoints = new TreeSet<Point2D>();
        rngpoints = range(root, rngpoints, rect);
        return rngpoints;
    }

    private TreeSet<Point2D> range(Node x, TreeSet<Point2D> rngPoints, RectHV rect) {
        if (x == null) return rngPoints;
        int cmp = rectCompare(rect, x.line, x.color);
        if (cmp > 0) {
            if (rect.contains(x.point)) rngPoints.add(x.point);
            range(x.right, rngPoints, rect);
        }
        else if (cmp < 0) {
            if (rect.contains(x.point)) rngPoints.add(x.point);
            range(x.left, rngPoints, rect);
        }
        else {
            range(x.left, rngPoints, rect);
            if (rect.contains(x.point)) rngPoints.add(x.point);
            range(x.right, rngPoints, rect);
        }
        return rngPoints;
    }

    private int rectCompare(RectHV rect, RectHV line, boolean color) {
        if (line.intersects(rect)) return 0;
        if (color == RED) {
            if (rect.xmax() < line.xmax()) return -1;
            else return 1;
        }
        else {
            if (rect.ymax() < line.ymax()) return -1;
            else return 1;
        }
    }

    public Point2D nearest(Point2D p) {
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node x, Point2D query, Point2D nearestPoint) {
        if (x == null) return nearestPoint;
        double distance = x.point.distanceSquaredTo(query);
        double leftdistance;
        double rightdistance;
        if (x.left != null) leftdistance = x.left.point.distanceSquaredTo(query);
        else leftdistance = Double.POSITIVE_INFINITY;
        if (x.right != null) rightdistance = x.right.point.distanceSquaredTo(query);
        else rightdistance = Double.POSITIVE_INFINITY;
        if (leftdistance > distance && rightdistance > distance) {
            nearestPoint = nearest(x.left, query, nearestPoint);
            nearestPoint = nearest(x.right, query, nearestPoint);
        }
        if (leftdistance < distance) {
            assert x.left != null;
            nearestPoint = x.left.point;
            nearestPoint = nearest(x.left, query, nearestPoint);
        }
        if (rightdistance < distance) {
            assert x.right != null;
            nearestPoint = x.right.point;
            nearestPoint = nearest(x.right, query, nearestPoint);
        }
        return nearestPoint;

    }


    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        kdTreeSelf kdtree = new kdTreeSelf();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        kdtree.draw();
        StdDraw.show();
    }
}



