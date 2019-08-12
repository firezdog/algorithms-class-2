package SymbolTables;

import edu.princeton.cs.algs4.StdDraw;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class RedBlackBST_ST<Key extends Comparable<Key>, Value> extends BST_ST<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // region redBlackBST
    private class RedBlackBST extends BST_ST<Key, Value>.BST {
        private boolean color = BLACK;

        RedBlackBST(Key key, Value value, int size, boolean color) {
            super(key, value, size);
            this.color = color;
        }
    }

    /* Take the node to the right and make it a new root -- keep the new root's right, but move its left to
    * the right of the old node. */
    private RedBlackBST rotateLeft(RedBlackBST node) {
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
    private RedBlackBST rotateRight(RedBlackBST node) {
        RedBlackBST newRoot = (RedBlackBST) node.left;
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

    // flips colors of the children from red to black and color of parent from black to red.
    private void flipColors(RedBlackBST node) {
        node.color = RED;
        ((RedBlackBST) node.left).color = ((RedBlackBST) node.right).color = BLACK;
    }
    // endregion

    public Value get(Key key) {
        return get(key, (RedBlackBST) root);
    }

    private Value get(Key key, RedBlackBST node) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare < 0) return get(key, (RedBlackBST) node.left);
        if (compare > 0) return get(key, (RedBlackBST) node.right);
        return node.value;
    }

    public void put(Key key, Value value) {
        root = put(key, value, (RedBlackBST) root);
        ((RedBlackBST) root).color = BLACK;
    }

    private RedBlackBST put(Key key, Value value, RedBlackBST node) {
        RedBlackBST node_copy = node;
        if (node == null) return new RedBlackBST(key, value, 1, RED);
        int compare = key.compareTo(node.key); compares++;
        if (compare < 0) node.left = put(key, value, (RedBlackBST) node.left);
        else if (compare > 0) node.right = put(key, value, (RedBlackBST) node.right);
        else node.value = value;

        if (isRed((RedBlackBST) node.right) && !isRed((RedBlackBST) node.left)) node = rotateLeft(node);
        // if the left node is red, it of course exists
        if (isRed((RedBlackBST) node.left) && isRed((RedBlackBST) node.left.left)) node = rotateRight(node);
        if (isRed((RedBlackBST) node.left) && isRed((RedBlackBST) node.right)) flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    protected void show(BST node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;
        if (isRed((RedBlackBST) node.left)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x, y, x - xOffset, y - yOffset);
        show(node.left, x - xOffset, y - yOffset, xOffset / 2, yOffset);
        if (isRed((RedBlackBST) node)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x, y, node.key.toString());
        if (isRed((RedBlackBST) node.right)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x, y, x + xOffset, y - yOffset);
        show(node.right, x + xOffset, y - yOffset, xOffset / 2, yOffset);
    }

}
