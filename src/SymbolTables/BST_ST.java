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

    /* Depends upon accurate node counts for each sub-tree.  Starting from the root: the number of nodes
    to the left is the rank of the root.  If the rank of the root is b and there are n nodes in the root tree,
    then the tree to the left has nodes with ranks in the range [0, b-1] and the tree to the right has nodes
    with ranks in the range [b+1, n-1].  Continuing from say the left of the root, if the rank of the left
    tree is c, then the rank of nodes to its left is from [0, c-1] and to its right from [c+1, b-1].

    So say we want to find the node of rank d.  Is d less than or greater than b, the rank of the right?  If less,
    go left; if greater, go right.  Say we go left -- we're looking for d in the range [0, ..., c, ...b-1], since b
    is the rank of the root.  But b is the size of the tree to the left.  Our problem is we don't know what c is --
    but we do know that everything to the left of c has rank [0, ..., c-1], and that the number of items there is the
    size of that new tree. Right? */
    public Key select(int k) {
        if (isEmpty() || 0 > k || k >= root.size ) return null;
        BST walker = root;
        int minRank = 0; int maxRank = size(walker.left);
        while (minRank <= maxRank) {
            if (maxRank > k) {
                walker = walker.left;
            } else if (maxRank < k) {
                walker = walker.right;
                minRank += 1;
            } else return walker.key;
            maxRank = size(walker) - 1;
        }
        return walker.key;
    }

    public Key ceiling(Key k) {
        return null;
    }

    public Key floor(Key k) {
        return null;
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