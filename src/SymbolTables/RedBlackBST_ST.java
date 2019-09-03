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
        /* Below: if the left node is red, it of course exists.  Can this get hit if the first does?  In that case we
        * have left black, right red.  Next red becomes head, head becomes left (?).  Then we have a red head with a red
        * left, but that red left has a black left.  So seems like the second statement will never be hit if first
        * is, and this could possibly be an else?  -- On the other hand, it will be hit one call up the recursive
        * chain. When the first is hit one call below!  */
        if (isRed((RedBlackBST<Key, Value>) node.left) && isRed((RedBlackBST<Key, Value>) node.left.left))
            node = rotateRight(node);
        /* This should always get called if the above gets called, so the question is whether it ever gets
        * called if the first gets called?  I don't see how, because after the first we pull the red
        * node left (to the head) and get a head with a black left -- and in addition, I don't see how the
        * new head could have a red right, given that insertion has been correctly performed thus far. */
        if (isRed((RedBlackBST<Key, Value>) node.left) && isRed((RedBlackBST<Key, Value>) node.right))
            flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    // region delete
    // TODO
    @SuppressWarnings("all")
    public void delete(Key key) {
        if (isEmpty()) return;
        if (!isRed((RedBlackBST) root.left) && !isRed((RedBlackBST) root.right))
            ((RedBlackBST) root).redden();
        root = delete((RedBlackBST) root);
    }

    @SuppressWarnings("all")
    private BST<Key,Value> delete(RedBlackBST root) {
        // check for equality
            // if equal, save min in right sub-tree, delete min in right sub-tree, replace head with min
            // is this handled like an insertion?
            // balance
        // decide whether we have to go left or right (successor)
            // prep the successor (make sure it will be 3+) and go to successor
        return null;
    }

    @SuppressWarnings("all")
    private RedBlackBST deleteMin(RedBlackBST head) {
        // base case -- when you get to the end, delete
        if (head.left == null) return null;
        /* this should correspond to the condition that neither the successor nor its successor is a 3+ node. Why
        * is it OK for the successor not to be a 3+ node? -- It seems like what is meant here is that, if neither the
        * successor nor its successor is part of a chain (a 3+) node, we need to re-establish the invariant. I guess
        * that it's OK for the head to be black as long as its left child is red -- because then we're in a chain.
        * The head itself might be red, in which case we're already in a chain and we don't need to worry.  */
        if (isNot3Plus(head)) head = borrowRight(head);
        head.left = deleteMin((RedBlackBST) head.left);
        return balance(head);
    }

    @SuppressWarnings("all")
    private boolean isNot3Plus(RedBlackBST head) {
        return
            !isRed((RedBlackBST) head.left) &&
            !isRed((RedBlackBST) head.left.left);
    }

    @SuppressWarnings("all")
    private RedBlackBST borrowRight(RedBlackBST head) {
        /* since left is black (assumption from call), left will become part of chain.  Then in terms of our rep.,
        * we need to fix the invariant that red links are not allowed. */
        flipColors(head);
        boolean ThreePlusRight = isRed((RedBlackBST) head.right.left);
        // case 1: the immediate sibling is a 3+ node: move key from sibling to left
        if (ThreePlusRight) {
            // head.right -- -1 left node + 1 right node
            head.right = rotateRight((RedBlackBST) head.right);
            // head -- -1 right node + 1 left node
            head = rotateLeft(head);
        }
        /* case 2: the immediate sibling is a 2 node: move key from parent and reduce -- default for flip. */
        return head;
    }

    @SuppressWarnings("all")
    private RedBlackBST balance(RedBlackBST head) {
        /* "Then, on the way up the tree, we split any unused temporary 4-nodes." In the method as given,
        * we start by checking for red rights -- left over from the initial color flip, I assume. */
        if (isRed((RedBlackBST) head.right)) head = rotateLeft(head);
        // now we need to repair the damage, as in put.
        if (!isRed((RedBlackBST) head.left) && isRed((RedBlackBST) head.right)) head = rotateLeft(head);
        if (isRed((RedBlackBST) head.left) && isRed((RedBlackBST) head.left.left)) head = rotateRight(head);
        if (isRed((RedBlackBST) head.left) && isRed((RedBlackBST) head.right)) flipColors(head);
        // calculate size and return, as in put (1 was deleted).
        head.size = size(head.left) + size(head.right) + 1;
        return head;
    }
    // endregion delete

    // endregion basic functionality

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
    // endregion test utils

}
