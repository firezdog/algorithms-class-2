package SymbolTables;

import edu.princeton.cs.algs4.StdDraw;

public class RedBlackBST_ST<Key extends Comparable<Key>, Value> extends BST_ST<Key, Value> {

    // region RedBlackBST wrappers
    private boolean isRed(RedBlackBST<Key, Value> node) {
        return RedBlackBST.isRed(node);
    }

    private int size(BST<Key, Value> node) {
        return RedBlackBST.size(node);
    }

    private RedBlackBST<Key, Value> rotateLeft(RedBlackBST<Key, Value> node) {
        return RedBlackBST.rotateLeft(node);
    }

    private RedBlackBST<Key, Value> rotateRight(RedBlackBST<Key, Value> node) {
        return RedBlackBST.rotateRight(node);
    }

    private void flipColors(RedBlackBST<Key, Value> node) {
        RedBlackBST.flipColors(node);
    }
    // endregion

    // region basic functionality (get, put, delete)
    // the inherited code also works in place of this.
    public Value get(Key key) {
        return get(key, (RedBlackBST<Key, Value>) root);
    }

    private Value get(Key key, RedBlackBST<Key, Value> node) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare < 0) return get(key, (RedBlackBST<Key, Value>) node.left);
        if (compare > 0) return get(key, (RedBlackBST<Key, Value>) node.right);
        return node.value;
    }

    public void put(Key key, Value value) {
        root = put(key, value, (RedBlackBST<Key, Value>) root);
        ((RedBlackBST<Key, Value>) root).blacken();
    }

    private RedBlackBST<Key, Value> put(Key key, Value value, RedBlackBST<Key, Value> node) {
        if (node == null) return new RedBlackBST<>(key, value, 1, RedBlackBST.RED);
        int compare = key.compareTo(node.key); compares++;
        if (compare < 0) node.left = put(key, value, (RedBlackBST<Key, Value>) node.left);
        else if (compare > 0) node.right = put(key, value, (RedBlackBST<Key, Value>) node.right);
        else node.value = value;

        if (isRed((RedBlackBST<Key, Value>) node.right) && !isRed((RedBlackBST<Key, Value>) node.left))
            node = rotateLeft(node);
        // if the left node is red, it of course exists
        if (isRed((RedBlackBST<Key, Value>) node.left) && isRed((RedBlackBST<Key, Value>) node.left.left))
            node = rotateRight(node);
        if (isRed((RedBlackBST<Key, Value>) node.left) && isRed((RedBlackBST<Key, Value>) node.right))
            flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // TODO
    /*  3.3.39 Delete the minimum. Implement the deleteMin() operation for red-black
    *   BSTs by maintaining the correspondence with the transformations given in the text for
    *   moving down the left spine of the tree while maintaining the invariant that the current
    *   node is not a 2-node.
    */

    @SuppressWarnings("all") // I have to use casts because of my poor life decisions :(
    private void deleteMin() {
        if (isEmpty()) return;
        // start invariant: current node is not a 2-node (black) -- this prepares for processing in next step
        if (!isRed((RedBlackBST) root.left) && !isRed((RedBlackBST) root.right))
            ((RedBlackBST) root).redden();
        // send prepared root for processing in deletion algo
        root = deleteMin((RedBlackBST) root);
        // tidy up.
        if (!isEmpty()) ((RedBlackBST) root).blacken();
    }

    @SuppressWarnings("all")
    private RedBlackBST deleteMin(RedBlackBST head) {
        // base case -- when you get to the end, delete
        if (head.left == null) return null;
        /* this should correspond to the condition that neither the successor nor its successor is a 3+ node. Why
        * is it OK for the successor not to be a 3+ node? It seems like what is meant here is that, if neither the
        * head nor its successor is part of a chain (a 3+) node, we need to make sure we're in a chain. I guess
        * that it's OK for the head to be black as long as its left child is red -- because then we're in a chain.
        * The head itself might be red, in which case we're already in a chain and we don't need to worry.  */
        boolean headIsNot3Plus = !isRed((RedBlackBST) head.left) && !isRed((RedBlackBST) head.left.left);
        if (headIsNot3Plus) head = borrowRight(head);
        head.left = deleteMin(head.left);
        return balance(head);
    }

    private RedBlackBST borrowRight(RedBlackBST head) {
    }

    private RedBlackBST balance(RedBlackBST head) {
    }

    // TODO
    public void delete(Key key) {

    }
    // endregion

    protected void show(BST node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;
        if (isRed((RedBlackBST<Key, Value>) node.left)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x, y, x - xOffset, y - yOffset);
        show(node.left, x - xOffset, y - yOffset, xOffset / 2, yOffset);
        if (isRed((RedBlackBST<Key, Value>) node)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x, y, node.key.toString());
        if (isRed((RedBlackBST<Key, Value>) node.right)) StdDraw.setPenColor(StdDraw.RED);
        else StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(x, y, x + xOffset, y - yOffset);
        show(node.right, x + xOffset, y - yOffset, xOffset / 2, yOffset);
    }

    // region test utils
    private boolean is23() {
        // Check:
        // 1. No node is connected to two red links
        // 2. There are no right-leaning red links
        return ((RedBlackBST) root).is23();
    }

    private boolean isBalanced() {
        if (isEmpty()) return true;
        return ((RedBlackBST) root).isBalanced();
    }

    boolean isRedBlack() {
        // Check:
        // 1. is BST
        // 2. is23
        // 3. isBalanced
        return isBST() && isBalanced() && is23();
    }
    // endregion

}
