package SymbolTables;

import java.util.Iterator;

public class BST_ST<Key extends Comparable<Key>, Value> implements ComparableST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private BST root;

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

    private int size(BST node) {
        if (node != null) return node.size;
        return 0;
    }

    public int compares() {
        return 0;
    }

    public BST_ST() {
    }

    /* Find the data structure associated with a given key -- may need to change return type */
    private int search(Key key) {
        return 0;
    }

    /* Associate a given key with a value in the symbol table. */
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    public BST put(BST node, Key key, Value value) {
        if (node == null) return new BST(key, value, 1);
        int compare = node.key.compareTo(key);
        if (compare < 0) node.right = put(node.right, key, value);
        if (compare > 0) node.left = put(node.left, key, value);
        else node.value = value;
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    /* Get the value associated with a given key in the symbol table. -- Some duplication of what occurs
    * in put. */
    public Value get(Key key) {
        return get(root, key);
    }

    // recursive version.
    public Value get(BST walker, Key key) {
        if (walker == null) return null;
        int compare = walker.key.compareTo(key);
        if (compare > 0) return get(walker.left, key);
        if (compare < 0) return get(walker.right, key);
        return walker.value;
    }

    /* Remove a key-value association from the dictionary. */
    public void delete(Key key) {
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

    public Iterable<Key> keys() {
        return new ST_Iterable();
    }

    @Override
    public Key min() {
        return null;
    }

    @Override
    public Key max() {
        return null;
    }

    @Override
    public Key select(int k) {
        return null;
    }

    @Override
    public Key ceiling(Key k) {
        return null;
    }

    @Override
    public Key floor(Key k) {
        return null;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    class ST_Iterable implements Iterable<Key> {

        private ST_Iterable() { }

        public Iterator<Key> iterator() {
            return new ST_Iterator();
        }

        class ST_Iterator implements Iterator<Key> {

            public boolean hasNext() {
                return false;
            }

            public Key next() {
                return null;
            }
        }
    }
}