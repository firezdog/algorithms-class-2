package SymbolTables;

import java.util.Iterator;
import edu.princeton.cs.algs4.*;

public class BST_ST<Key extends Comparable<Key>, Value> implements ComparableST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private BST root;
    private int compares;

    private class BST {
        private Key key;
        private Value value;
        private BST left, right;
        private int size;

        BST(Key k, Value v, int s) {
            key = k;
            value = v;
            size = s;
        }
    }

    public int compares() {
        int compares = this.compares;
        this.compares = 0;
        return compares;
    }

    public BST_ST() {
    }

    /* Find the data structure associated with a given key -- may need to change return type */
    public BST search(BST walker, Key key) {
        if (walker == null) return null;
        int compare = walker.key.compareTo(key); compares++;
        if (compare > 0) return search(walker.left, key);
        if (compare < 0) return search(walker.right, key);
        return walker;
    }

    /* Get the value associated with a given key in the symbol table. -- Some duplication of what occurs
     * in put. */
    public Value get(Key key) {
        BST result = search(root, key);
        return result == null ? null : result.value;
    }


    /* Associate a given key with a value in the symbol table. */
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public BST put(BST node, Key key, Value value) {
        if (node == null) return new BST(key, value, 1);
        int compare = node.key.compareTo(key); compares++;
        if (compare < 0) node.right = put(node.right, key, value);
        if (compare > 0) node.left = put(node.left, key, value);
        else node.value = value;
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /* Remove a key-value association from the dictionary. */
    public void delete(Key key) {
        lazyDelete(key);
    }

    private void lazyDelete(Key key) {
        BST target = search(root, key);
        if (target == null) return;
        target.value = null; resize(root);
    }

    /* Determine whether a given key is defined in the symbol table. */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(BST node) {
        if (node == null) return 0;
        if (node.value == null) return size(node.left) + size(node.right);
        return node.size;
    }

    private void resize(BST node) {
        if (node == null) return;
        resize(node.left); resize(node.right);
        node.size = size(node.left) + size(node.right) + (node.value == null ? 0 : 1);
    }

    // strategy: go left until you can go left no more
    public Key min() {
        if (isEmpty()) return null;
        BST walker = root;
        while (walker.left != null) {
            walker = walker.left;
        }
        return walker.key;
    }

    // strategy: go right until you can go right no more
    public Key max() {
        if (isEmpty()) return null;
        BST walker = root;
        while (walker.left != null) {
            walker = walker.right;
        }
        return walker.key;
    }

    public Key select(int rank) {
        BST result = select(rank, root);
        if (result == null) return null;
        return result.key;
    }

    public int rank(Key k) {
        return rank(k, root);
    }

    private int rank(Key k, BST node) {
        // a null node clearly is not greater than any other nodes in the tree.
        if (node == null || k == null) return 0;
        int rank = size(node.left);
        /* again, the rank of keys to the left is not affected by the rank of this key -- but the rank of a key
        to the right has to be one more than the rank of this key */
        int compare = k.compareTo(node.key);
        if (compare < 0) return rank(k, node.left);
        if (compare > 0) return rank + (node.key == null ? 0 : 1) + rank(k, node.right);
        return rank;
    }

    /* this method involves recursively updating the rank by comparison with the minimum possible rank for a given
    node until we get what we're looking for. */
    private BST select(int rank, BST node) {
        if (node == null) return null;
        int min = size(node.left);
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
    private Key ceiling(Key k, BST node) {
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
    private Key floor(Key k, BST node) {
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

    private Queue<Key> order(Queue<Key> keys, BST node) {
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

    // we need the ceiling for lo, then we build out a queue until we get to the floor for hi
    public Iterable<Key> keys(Key lo, Key hi) {
        return new Queue<Key>();
    }

}