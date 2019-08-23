package SymbolTables;

import edu.princeton.cs.algs4.Queue;

class RedBlackBST<Key extends Comparable<Key>, Value> extends BST<Key, Value> {

    static final boolean RED = true;
    static final boolean BLACK = false;

    private boolean color = BLACK;

    RedBlackBST(Key key, Value value, int size, boolean color) {
        super(key, value, size);
        this.color = color;
    }

    void blacken() {
        this.color = BLACK;
    }

    /* Take the node to the right and make it a new root -- keep the new root's right, but move its left to
     * the right of the old node. */
    static RedBlackBST rotateLeft(RedBlackBST node) {
        RedBlackBST newRoot = (RedBlackBST) node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;

        return newRoot;
    }

    // reverse of the above.
    static RedBlackBST rotateRight(RedBlackBST node) {
        RedBlackBST newRoot = (RedBlackBST) node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;

        return newRoot;
    }

    static boolean isRed(RedBlackBST node) {
        if (node == null) return false;
        return node.color == RED; // this is more readable, but technically we could just return node.color
    }

    // flips colors of the children from red to black and color of parent from black to red.
    static void flipColors(RedBlackBST node) {
        node.color = RED;
        ((RedBlackBST) node.left).color = ((RedBlackBST) node.right).color = BLACK;
    }

    // Check all paths have same number of black links
    boolean isBalanced() {
        Queue<Integer> q = countBlack(this, 0, new Queue<>());
        if (q.isEmpty()) return true;
        int last = q.dequeue();
        while (!q.isEmpty()) {
            int next = q.dequeue();
            if (next != last) return false;
            last = next;
        }
        return true;
    }

    private Queue<Integer> countBlack(RedBlackBST node, int count, Queue<Integer> q) {
        if (node == null) return q;
        count = isRed(node) ? count : count + 1;
        if (node.left == null && node.right == null) { q.enqueue(count); return q; }
        q = countBlack((RedBlackBST) node.left, count, q);
        q = countBlack((RedBlackBST) node.right, count, q);
        return q;
    }

    boolean is23() {
        return is23(this);
    }

    private boolean is23(RedBlackBST node) {
        if (node == null) return true;
        if (isRed((RedBlackBST) node.right)) return false;
        if (isRed((RedBlackBST) node.left) && (isRed((RedBlackBST) node.left.left))) return false;
        return is23((RedBlackBST) node.left) && is23((RedBlackBST) node.right);
    }

}