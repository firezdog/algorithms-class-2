package SymbolTables;

public class RedBlackBST_ST<Key extends Comparable<Key>, Value> extends BST_ST<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    // region redBlackBST
    private class RedBlackBST extends BST_ST<Key, Value>.BST {
        private Value value;
        private Key key;
        private boolean color = BLACK;
        private RedBlackBST left, right;
        private int size;

        RedBlackBST(Key key, Value value, int size, boolean color) {
            super(key, value, size);
            this.color = color;
        }
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

    // flips colors of the children from red to black and color of parent from black to red.
    private void flipColors(RedBlackBST node) {
        node.color = RED;
        node.left.color = node.right.color = BLACK;
    }
    // endregion

    RedBlackBST root;

    public void put(Key key, Value value) {
        root = put(key, value, root);
    }

    private RedBlackBST put(Key key, Value value, RedBlackBST node) {
        return null;
    }

}
