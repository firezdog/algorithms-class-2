package SymbolTables;

import java.util.Iterator;

@SuppressWarnings("all")
public class OrderedArrayST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private int size;

    class Entry implements Comparable<Entry> {

        private Key key;
        private Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public int compareTo(Entry e) {
            if (e == null) return -1;
            if (e.getClass() != this.getClass()) return -1;
            return this.key.compareTo(e.key);
        }
    }

    private ResizeableArray<Entry> arr;

    public OrderedArrayST() {
        arr = new ResizeableArray<>();
    }

    /* return the rank of the given key -- i.e. the number of entries with keys less than it,
    * or its expected position.. */
    private int search(Entry e) {
        return 0;
    }

    /* For a given key, value pair, put an associated entry into the symbol table. */
    public void put(Key key, Value value) {
        Entry e = new Entry(key, value);
        int rank = search(e);
        if (rank == 0) arr.push(0, e);
        else {
            Entry ranked = arr.get(rank);
            // if the key is already at rank, replace its value.
            if (arr.get(rank).compareTo(e) == 0) arr.put(rank, e);
            // otherwise, add the key at rank.
            else arr.push(rank, e);
        }
    }

    /* Get the value associated with a given key in the symbol table. */
    public Value get(Key key) {
        return null;
    }

    /* Remove a key-value association from the dictionary. */
    public void delete(Key key) {
    }

    /* Determine whether a given key is defined in the symbol table. */
    public boolean contains(Key key) {
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        return new ST_Iterable();
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