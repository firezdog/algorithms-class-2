package SymbolTables;

public class RedBlackBST_ST<Key extends Comparable<Key>, Value> implements ComparableST {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // region redBlackBST
    private class RedBlackBST {
        private Value value;
        private Key key;
        private boolean color = BLACK;
        private RedBlackBST left, right;
        private int size;

        RedBlackBST(Key key, Value value, int size, boolean color) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.color = color;
        }
    }

    private int size(RedBlackBST node) {
        return 0;
    }

    /* Take the node to the right and make it a new root -- keep the new root's right, but move its left to
    * the right of the old node. */
    private RedBlackBST rotateLeft(RedBlackBST node) {
        RedBlackBST newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;

        return newRoot;
    }

    // reverse of the above.
    private RedBlackBST rotateRight(RedBlackBST node) {
        RedBlackBST newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;

        return newRoot;
    }

    private boolean isRed (RedBlackBST node) {
        if (node == null) return false;
        return node.color == RED; // this is more readable, but technically we could just return node.color
    }

    // endregion

    @Override
    public Comparable min() {
        return null;
    }

    @Override
    public Comparable max() {
        return null;
    }

    @Override
    public Comparable select(int r) {
        return null;
    }

    @Override
    public int rank(Comparable k) {
        return 0;
    }

    @Override
    public Comparable ceiling(Comparable k) {
        return null;
    }

    @Override
    public Comparable floor(Comparable k) {
        return null;
    }

    @Override
    public Iterable keys(Comparable lo, Comparable hi) {
        return null;
    }

    @Override
    public int compares() {
        return 0;
    }

    @Override
    public void put(Comparable comparable, Object o) {

    }

    @Override
    public Object get(Comparable comparable) {
        return null;
    }

    @Override
    public void delete(Comparable comparable) {

    }

    @Override
    public boolean contains(Comparable comparable) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterable keys() {
        return null;
    }
}
