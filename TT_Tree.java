/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class TT_Tree {
    public TT_Tree() {
    }

    private class TT_Node<Key extends Comparable<? super Key>, Value> {
        private Value lval;
        private Key lkey;
        private Value rval;
        private Key rkey;
        private TT_Node<Key, Value> left;
        private TT_Node<Key, Value> centre;
        private TT_Node<Key, Value> right;

        public TT_Node() {
            this.left = this.right = this.centre = null;
        }

        public TT_Node(Value lval, Key lkey, Value rval, Key rkey, TT_Node<Key, Value> left,
                       TT_Node<Key, Value> centre, TT_Node<Key, Value> right) {
            this.lval = lval;
            this.lkey = lkey;
            this.rval = rval;
            this.rkey = rkey;
            this.left = left;
            this.centre = centre;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null;
        }

        public Value getLval() {
            return lval;
        }

        public Key getLkey() {
            return lkey;
        }

        public Value getRval() {
            return rval;
        }

        public Key getRkey() {
            return rkey;
        }

        public TT_Node<Key, Value> getLeft() {
            return left;
        }

        public void setLeft(TT_Node<Key, Value> left) {
            this.left = left;
        }

        public TT_Node<Key, Value> getCentre() {
            return centre;
        }

        public void setCentre(TT_Node<Key, Value> centre) {
            this.centre = centre;
        }

        public TT_Node<Key, Value> getRight() {
            return right;
        }

        public void setRight(TT_Node<Key, Value> right) {
            this.right = right;
        }

        public void setLeft(Key k, Value val) {
            lkey = k;
            lval = val;
        }

        public void setRight(Key k, Value val) {
            rkey = k;
            rval = val;
        }
    }

    public static void main(String[] args) {

    }
}
