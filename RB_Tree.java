/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class RB_Tree<Key extends Comparable<Key>, Value> {
    private Node root;
    private final boolean RED = true;
    private final boolean BLACK = false;

    public RB_Tree() {

    }

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;
        private boolean color;

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }

        public Node() {
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return BLACK;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, RED);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.count = 1 + size(x.left) + size(x.right);
        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }


    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public Value min() {
        return min(root).val;
    }

    public Node min(Node x) {
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public Value max() {
        return max(root).val;
    }

    public Node max(Node x) {
        while (x.right != null) {
            x = x.right;
        }
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }


    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        preorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    private void preorder(Node x, Queue<Key> q) {
        if (x == null) return;
        q.enqueue(x.key);
        preorder(x.left, q);
        preorder(x.right, q);
    }

    public static void main(String[] args) {
        RB_Tree rb_tree = new RB_Tree();
        rb_tree.put('S', 19);
        rb_tree.put('E', 20);
        rb_tree.put('A', 11);
        rb_tree.put('R', 12);
        rb_tree.put('C', 13);
        rb_tree.put('H', 14);
        rb_tree.put('X', 15);
        rb_tree.put('M', 16);
        rb_tree.put('P', 17);
        rb_tree.put('L', 18);
        System.out.println(rb_tree.keys());

    }
}

