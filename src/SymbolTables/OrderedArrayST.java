package SymbolTables;

import java.util.Iterator;
import edu.princeton.cs.algs4.Queue;

@SuppressWarnings("all")
public class OrderedArrayST<Key extends Comparable<Key>, Value> implements ComparableST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private int size;
    private int compares;

    public int compares() {
        int data = compares; compares = 0; return data;
    }

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
            compares++;
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
        if (arr.length() <= 1) return 0;
        int lo = 0; int hi = arr.length() - 1; int mid = (hi - lo) / 2;
        while (lo <= hi) {
            Entry midEntry = arr.get(mid);
            int result = e.compareTo(midEntry);
            if (result < 0) { hi = mid - 1; mid = lo + (hi - lo) / 2; }
            else if (result == 0) return mid;
            else { lo = mid + 1; mid = lo + (hi - lo) / 2; }
        }
        return lo;
    }

    /* For a given key, value pair, put an associated entry into the symbol table. */
    public void put(Key key, Value value) {
        Entry e = new Entry(key, value);
        int rank = search(e);
        Entry ranked = arr.get(rank);
        // if the key is already at rank, replace its value.
        if (ranked != null && ranked.compareTo(e) == 0) arr.put(rank, e);
        // otherwise, add the key at rank.
        else {
            arr.push(rank, e);
            size++;
        }
    }

    /* Get the value associated with a given key in the symbol table. */
    public Value get(Key key) {
        Entry e = new Entry(key, null);
        int rank = search(e);
        Entry found = arr.get(rank);
        if (found != null && found.key.compareTo(key) == 0) return found.value;
        else return null;
    }

    /* Remove a key-value association from the dictionary. */
    public void delete(Key key) {
        Entry e = new Entry(key, null);
        int rank = search(e);
        Entry found = arr.get(rank);
        if (found != null && found.key.compareTo(key) == 0) {
            arr.pop(rank);
            size--;
        }
    }

    /* Determine whether a given key is defined in the symbol table. */
    public boolean contains(Key key) {
        Entry e = new Entry(key, null);
        int rank = search(e);
        Entry found = arr.get(rank);
        if (found != null && found.key.compareTo(key) == 0) return true;
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Key min() {
        if (isEmpty()) return null;
        return arr.get(0).key;
    }

    public Key max() {
        if (isEmpty()) return null;
        return arr.get(size - 1).key;
    }

    public Key select(int k) {
        Entry ranked = arr.get(k);
        if (ranked != null) return ranked.key;
        return null;
    }

    // order of calls is very important here!
    public Key ceiling(Key k) {
        if (isEmpty()) return null;
        int rank = search(new Entry(k, null));
        if (rank >= size) return arr.get(size - 1).key;
        if (rank <= 0) return arr.get(0).key;
        if (arr.get(rank).key.compareTo(k) == 0) return k;
        return arr.get(rank + 1).key;
    }

    public Key floor(Key k) {
        if (isEmpty()) return null;
        int rank = search(new Entry(k, null));
        if (rank <= 0) return arr.get(0).key;
        if (rank >= size) return arr.get(size - 1).key;
        if (arr.get(rank).key.compareTo(k) == 0) return k;
        return arr.get(rank - 1).key;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> keys = new Queue<>();
        int lowerBound = search(new Entry(ceiling(lo), null));
        int upperBound = search(new Entry(floor(hi), null));
        for (int i = lowerBound; i <= upperBound; i++) {
            keys.enqueue(arr.get(i).key);
        }
        return keys;
    }

    public Iterable<Key> keys() {
        return new ST_Iterable(arr);
    }

    class ST_Iterable implements Iterable<Key> {

        ResizeableArray<Entry> arr;
        int index;

        private ST_Iterable(ResizeableArray<Entry> arr) {
            this.arr = arr;
            index = 0;
        }

        public Iterator<Key> iterator() {
            return new ST_Iterator();
        }

        class ST_Iterator implements Iterator<Key> {

            public boolean hasNext() {
                return index < arr.length();
            }

            public Key next() {
                return arr.get(index++).key;
            }
        }
    }
}