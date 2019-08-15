package SymbolTables;

public class RedBlackBST<Key extends Comparable<Key>, Value> extends BST<Key, Value> {

    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private boolean color = BLACK;

    RedBlackBST(Key key, Value value, int size, boolean color) {
        super(key, value, size);
        this.color = color;
    }

    public void blacken() {
        this.color = BLACK;
    }

    /* Take the node to the right and make it a new root -- keep the new root's right, but move its left to
     * the right of the old node. */
    public static RedBlackBST rotateLeft(RedBlackBST node) {
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
    public static RedBlackBST rotateRight(RedBlackBST node) {
        RedBlackBST newRoot = (RedBlackBST) node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;

        return newRoot;
    }

    public static boolean isRed (RedBlackBST node) {
        if (node == null) return false;
        return node.color == RED; // this is more readable, but technically we could just return node.color
    }

    // flips colors of the children from red to black and color of parent from black to red.
    public static void flipColors(RedBlackBST node) {
        node.color = RED;
        ((RedBlackBST) node.left).color = ((RedBlackBST) node.right).color = BLACK;
    }

}