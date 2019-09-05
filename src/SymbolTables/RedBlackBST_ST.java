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
        root = delete(key, (RedBlackBST) root);
        if (!isEmpty()) ((RedBlackBST) root).blacken();
    }

    @SuppressWarnings("all")
    private BST<Key,Value> delete(Key key, RedBlackBST<Key, Value> head) {
        /* "The same transformations along the search path just described for deleting the
        minimum are effective to ensure that the current node is not a 2-node during a search
        for any key. If the search key is at the bottom, we can just remove it. If the key is not
        at the bottom, then we have to exchange it with its successor as in regular BSTs. Then,
        since the current node is not a 2-node, we have reduced the problem to deleting the
        minimum in a subtree whose root is not a 2-node, and we can use the procedure just
        described for that subtree. After the deletion, as usual, we split any remaining 4-nodes
        on the search path on the way up the tree." */
        // case where we've moved out of the tree
        if (head == null) return null;
        if (keyLess(key, head)) {
            if (!is3Plus(head)) head = borrowRight(head);
            head.left = delete(key, (RedBlackBST) head.left);
        // either we're going right or we've arrived.
        } else {
            /* first book says move a red left right: if the left is not red, then current node is red, I think --
            * because if current is black and left is black, the nodes we move to will not have a 3+ parent and the
            * invariant will be violated.  In any event, we want the current node to be red before the next step --
            * why? because then the current node is guaranteed to be part of a 3+ node and we can delete it without
            * consequence. Now -- oving right changes the current key we're comparing with to the left of
            * the current node?  But that shouldn't matter in some sense, because if the key is greater than
            * current node, it will also be greater than current node's left.  Eventually we will reach a key whose
            * left is not red and the algo will proceed. */
            if (isRed((RedBlackBST) head.left)) head = rotateRight(head);
            /* case where we've found our node and it has no right -- we can just delete it.  What about the left? I
            * don't know, but I would guess we've reached a terminal right (a greatest right for that branch --
            * which makes sense, since this is supposed to be the case where we got to the bottom of the tree,
            * and the tree (notwithstanding red-black != bst) is balanced.  In that case, left is automatically null. */
            if (keyEqual(key, head) && head.right == null) return null;
            /* now prepare for the case where the key is equal but it has a right.  If we're going to go right, we
            * should make sure that our next node is also 3+ -- not sure why this is done before the actual deletion
            * -- is it to prepare for delete min in some way?  Didn't we already make sure the current node is 3+
            * above? */
            if (!isRed((RedBlackBST) head.right) && !is3Plus((RedBlackBST) head.right))
                head = borrowRight(head);
            /* now the case where we've found our key -- we're going to swap it with the min from the right, thus
            * preserving the BST ordering (because everything on the right will be > right's min) and then delete
            * that min. */
            if (keyEqual(key, head)) {
                // we only need the min as instance of a BST here, because we don't access color
                BST min = min(head.right);
                head.key = (Key) min.key;
                head.value = (Value) min.value;
                head.right = deleteMin((RedBlackBST) head.right);
            } else head.right = delete(key, (RedBlackBST) head.right);
        }
        // finally balance everything out.
        return balance(head);
    }

    private boolean keyLess(Key key, BST<Key, Value> node) {
        return key.compareTo(node.key) < 0;
    }

    private boolean keyEqual(Key key, BST<Key, Value> node) {
        return key.compareTo(node.key) == 0;
    }

    private boolean keyGreater(Key key, BST<Key, Value> node) {
        return key.compareTo(node.key) > 0;
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
        if (!is3Plus(head)) head = borrowRight(head);
        head.left = deleteMin((RedBlackBST) head.left);
        return balance(head);
    }

    @SuppressWarnings("all")
    private boolean is3Plus(RedBlackBST head) {
        if (head == null || head.left == null) return false;
        return
            isRed((RedBlackBST) head.left) ||
            isRed((RedBlackBST) head.left.left);
    }

    @SuppressWarnings("all")
    private boolean has3PlusRight(RedBlackBST head) {
        if (head.right == null) return false; // null links are black
        return isRed((RedBlackBST) head.right.left);
    }

    @SuppressWarnings("all")
    private RedBlackBST borrowRight(RedBlackBST head) {
        /* since left is black (assumption from call), left will become part of chain.  Then in terms of our rep.,
        * we need to fix the invariant that red links are not allowed. */
        flipColors(head);
        // case 1: the immediate sibling is a 3+ node: move key from sibling to left
        if (has3PlusRight(head)) {
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
