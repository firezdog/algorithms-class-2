package SymbolTables;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BST_ST<Key extends Comparable<Key>, Value> implements ComparableST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    protected BST<Key, Value> root;
    protected int compares;

    public int compares() {
        int compares = this.compares;
        this.compares = 0;
        return compares;
    }

    /* Find the data structure associated with a given key -- may need to change return type */
    public BST<Key, Value> search(BST<Key, Value> walker, Key key) {
        if (walker == null) return null;
        int compare = walker.key.compareTo(key); compares++;
        if (compare > 0) return search(walker.left, key);
        if (compare < 0) return search(walker.right, key);
        return walker;
    }

    /* Get the value associated with a given key in the symbol table. -- Some duplication of what occurs
     * in put. */
    public Value get(Key key) {
        BST<Key, Value> result = search(root, key);
        return result == null ? null : result.value;
    }


    /* Associate a given key with a value in the symbol table. */
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private BST<Key, Value> put(BST<Key, Value> node, Key key, Value value) {
        if (node == null) return new BST<>(key, value, 1);
        int compare = node.key.compareTo(key); compares++;
        if (compare < 0) node.right = put(node.right, key, value);
        // the "else" below is really important -- even though you'd think compare ! < and > than 0 ?!
        // -- because otherwise the "else" is only attached to the second block!
        else if (compare > 0) node.left = put(node.left, key, value);
        else node.value = value;
        node.size = BST.size(node.left) + BST.size(node.right) + 1;
        return node;
    }

    /* Remove a key-value association from the dictionary. */
    public void delete(Key key) {
        root = delete(key, root);
    }

    private BST<Key, Value> delete(Key key, BST<Key, Value> node) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = delete(key, node.left);
        }
        else if (compare > 0) {
            node.right = delete(key, node.right);
        }
        else {
            boolean replaceWithMax = StdRandom.bernoulli();
            if (replaceWithMax) {
                if (node.left == null) node = node.right;
                else {
                    BST<Key, Value> max = max(node.left);
                    node.left = deleteMax(node.left);
                    node.key = max.key;
                    node.value = max.value;
                }
            } else {
                if (node.right == null) node = node.left;
                else {
                    BST<Key, Value> min = min(node.right);
                    node.right = deleteMin(node.right);
                    node.key = min.key;
                    node.value = min.value;
                }
            }
        }
        if (node == null) return null;
        node.size = BST.size(node.left) + BST.size(node.right) + 1;
        return node;
    }

    private void lazyDelete(Key key) {
        BST target = search(root, key);
        if (target == null) return;
        target.value = null; resize(root);
    }

    // delete the smallest node and return it.
    private BST iterativeDeleteMin(BST node) {
        if (isEmpty()) return null;
        if (root.left == null) {
            BST deleted = root;
            root = root.right;
            resize(root);
            return deleted;
        }
        BST walker = root;
        while (walker.left.left != null) {
            walker = walker.left;
        }
        // remember, walker.left.left is null.
        BST deleted = walker.left;
        walker.left = deleted.right;
        resize(root);
        return deleted;
    }

    private BST<Key, Value> deleteMin(BST<Key, Value> node) {
        // when we get to the end (half-leaf), return the replacement (guaranteed to be smaller than the parent)
        if (node == null) return null;
        if (node.left == null) return node.right;
        // deleting the min for a tree is equivalent to replacing it's left node with the result of deleting its min
        node.left = deleteMin(node.left);
        node.size = BST.size(node.left) + BST.size(node.right) + 1;
        return node;
    }

    private BST<Key, Value> deleteMax(BST<Key, Value> node) {
        // safety
        if (node == null) return null;
        /* the ending node (with no right) is the one we're replacing -- we replace it with its left, which, as above
        * is still larger than the parent */
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = BST.size(node.left) + BST.size(node.right) + 1;
        return node;
    }

    /* Determine whether a given key is defined in the symbol table. */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return BST.size(root) == 0;
    }

    public int size() {
        return BST.size(root);
    }

    private void resize(BST node) {
        if (node == null) return;
        resize(node.left); resize(node.right);
        node.size = BST.size(node.left) + BST.size(node.right) + (node.value == null ? 0 : 1);
    }

    // strategy: go left until you can go left no more
    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    }

    private BST<Key, Value> min(BST<Key, Value> node) {
        if (node == null) return null;
        if (node.left == null) return node;
        return min(node.left);
    }

    // strategy: go right until you can go right no more
    public Key max() {
        if (isEmpty()) return null;
        return max(root).key;
    }

    private BST<Key, Value> max(BST<Key, Value> node) {
        if (node == null) return null;
        if (node.right == null) return node;
        return max(node.right);
    }

    public Key select(int rank) {
        BST<Key, Value> result = select(rank, root);
        if (result == null) return null;
        return result.key;
    }

    public int rank(Key k) {
        return rank(k, root);
    }

    private int rank(Key k, BST<Key, Value> node) {
        // a null node clearly is not greater than any other nodes in the tree.
        if (node == null || k == null) return 0;
        int rank = BST.size(node.left);
        /* again, the rank of keys to the left is not affected by the rank of this key -- but the rank of a key
        to the right has to be one more than the rank of this key */
        int compare = k.compareTo(node.key);
        if (compare < 0) return rank(k, node.left);
        if (compare > 0) return rank + (node.key == null ? 0 : 1) + rank(k, node.right);
        return rank;
    }

    /* this method involves recursively updating the rank by comparison with the minimum possible rank for a given
    node until we get what we're looking for. */
    private BST<Key, Value> select(int rank, BST<Key, Value> node) {
        if (node == null) return null;
        int min = BST.size(node.left);
        /* If I'm looking for an item of rank x in the larger tree and I go right, the item will not have
        * the same rank in the right sub-tree.  Every item in the right sub-tree has a rank of at least the size
        * of the previous node's left + 1 -- because each of these nodes have greater keys than the total of
        * nodes above and to their left.  So whatever rank the item I'm looking for has in the larger sub-tree,
        * it has that rank - (min + 1) in the new tree. Slight complication -- we don't count nodes with null values
        * for lazy delete. */
        if (min < rank) return select(rank - min - (node.value == null ? 0 : 1), node.right);
        /* going left doesn't affect the rank i'm looking for -- an item of rank x in a larger tree will maintain
        rank x in the left sub-tree -- the subset of items smaller than the root of the larger tree. */
        if (min > rank) return select(rank , node.left);
        /* for the root, min is actually the rank -- there are cases I'd think where size(node.left) == rank, but
        I assume the progress of the algo itself, by updating rank, eliminates them. I guess we put == last to save
        a compare. */
        return node;
    }

    public Key ceiling(Key k) {
        return ceiling(k, root);
    }

    /* Some cases: ceiling of a key that is in the tree is that key; ceiling of a key than which everything
    is greater is the least element in the tree; ceiling of a key than which everything is less is null. */
    private Key ceiling(Key k, BST<Key, Value> node) {
        if (node == null) return null;
        int compare = k.compareTo(node.key);
        // CASE 1: our key is less than the node we're on.
        if (compare < 0) {
            /* see if any nodes smaller than the node we're on could be a ceiling for our key. */
            Key next = ceiling(k, node.left);
            return next == null ? node.key : next;
        }
        // CASE 2: our key is greater than the node we're on. (the simple case)
        if (compare > 0) {
            // look for the ceiling of our key in the right subtree (all nodes greater than current).
            return ceiling(k, node.right);
        }
        return k;
    }

    public Key floor(Key k) {
        return floor(k, root);
    }

    // doesn't seem to work inter-defining floor and ceiling
    private Key floor(Key k, BST<Key,   Value> node) {
        if (node == null) return null;
        int compare = k.compareTo(node.key);
        // keep looking for keys smaller than ours in the left tree
        if (compare < 0) return floor(k, node.left);
        // make sure that there are no keys smaller than ours in the right subtree.
        if (compare > 0) {
            Key next = floor(k, node.right);
            return next == null ? node.key : next;
        }
        return node.key;
    }

    // seems like we can use a queue again -- recursively add nodes to left, node itself, and nodes to right
    public Iterable<Key> keys() {
        Queue<Key> keys = new Queue<>();
        return order(keys, root);
    }

    private Queue<Key> order(Queue<Key> keys, BST<Key, Value> node) {
        if (node == null) return keys;
        if (node.left == null) {
            // for lazy delete
            if (node.value != null) keys.enqueue(node.key);
            if (node.right == null) return keys;
            keys = order(keys, node.right);
            return keys;
        }
        keys = order(keys, node.left);
        // for lazy delete
        if (node.value != null) keys.enqueue(node.key);
        keys = order(keys, node.right);
        return keys;
    }

    // a lazy way to do it -- iterate inorder and add everything to >= lo and <= hi to the queue -- O(N)!
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<Key>();
        q = getRange(root, lo, hi, q);
        return q;
    }

    private Queue<Key> getRange(BST<Key, Value> node, Key lo, Key hi, Queue<Key> q) {
        if (node == null) return q;
        int compareLo = node.key.compareTo(lo);
        int compareHi = node.key.compareTo(hi);
        // if the key is bigger than lo, keep going left
        if (compareLo > 0) q = getRange(node.left, lo, hi, q);
        // then, if the key is between lo and hi, add it to the queue
        if (compareLo >= 0 && compareHi <= 0) q.enqueue(node.key);
        // then, if the key is less than the hi, keep going right.
        if (compareHi < 0) q = getRange(node.right, lo, hi, q);
        return q;
    }

    public void show() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-20, 20);
        StdDraw.setYscale(-20, 20);
        show(root, 0, 15, 10, 2);
    }

    protected void show(BST node, double x, double y, double xOffset, double yOffset) {
        if (node == null) return;
        StdDraw.line(x, y, x - xOffset, y - yOffset);
        show(node.left, x - xOffset, y - yOffset, xOffset / 2, yOffset);
        StdDraw.text(x, y, node.key.toString());
        StdDraw.line(x, y, x + xOffset, y - yOffset);
        show(node.right, x + xOffset, y - yOffset, xOffset / 2, yOffset);
    }

    // region test utils
    // Count for node is correct
    boolean nodeCountCheck() {
        if (isEmpty()) return true;
        return root.nodeCountCheck();
    }

    // Check that given a range, all of a node's sub-nodes (including itself) are within
    // that range.
    boolean nodeOrderCheck() {
        if (isEmpty()) return true;
        return root.nodeOrderCheck();
    }

    boolean nodeNoDuplicateCheck() {
        // this test assumes that the BST being checked is ordered
        if (isEmpty()) return true;
        return root.nodeNoDuplicateCheck();
    }

    // TODO
    void isBST() {
        // Check:
        // 1. nodeCountCheck
        // 2. Order for node is correct (all nodes are between
        // a min and max, recursively?)
        // 3. There are no duplicates
    }
    // endregion

}