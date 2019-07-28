package SymbolTables;

import java.util.Iterator;

public class NaiveArrayST<Key extends Comparable<Key>, Value extends Comparable<Value>> implements ST<Key, Value> {
    /* Our fields -- two associative arrays and an int to keep track of the size of the table.
    It would be nice to use resizeable arrays here -- that would require a few modifications to put, at least. */
    private ResizeableArray<Key> keys;
    private ResizeableArray<Value> values;
    private int size;

    public int compares() {
        return 0;
    }

    public NaiveArrayST() {
        // we need keys to be a Comparable (as above) because we rank them in later applications.
        keys = new ResizeableArray<>();
        values = new ResizeableArray<>();
    }

    /* Helper method to return the index of a given key in "keys" -- indexes always match between associative arrays.
    Returns N if the key is not present (for size N). */
    private int search(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys.get(i).compareTo(key) == 0) return i;
        }
        return size;
    }

    /* Cases: (1) the key is not in the table; (2) the key is associated with a different value.
    In each case, we have to search through the table for the key. */
    public void put(Key key, Value value) {
        // we start by getting index of the key -- if the key is not in the list, index will be the size of the array.
        int index = this.search(key);
        keys.put(index, key);
        values.put(index, value);
        // size only goes up if we are not replacing a key (otherwise search gives us null pointer errors!
        if (index == size) size++;
    }

    public Value get(Key key) {
        int index = this.search(key);
        if (index < size) return values.get(index);
        return null;
    }

    public void delete(Key key) {
    /* Requires shift left */
        int index = this.search(key);
        if (index == size) return;
        keys.pop(index);
        values.pop(index);
        size--;
    }

    public boolean contains(Key key) {
        return this.search(key) < size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        return new ST_Iterable();
    }

    class ST_Iterable implements Iterable<Key> {

        ResizeableArray<Key> keys;

        private ST_Iterable() {
            keys = NaiveArrayST.this.keys;
            /* in an earlier iteration I was sorting the keys -- you definitely don't want to do this -- because you
            want coordination between the values of the two arrays. */
        }

        public Iterator<Key> iterator() {
            return new ST_Iterator();
        }

        class ST_Iterator implements Iterator<Key> {
            int current;

            @Override
            public boolean hasNext() {
                return current < NaiveArrayST.this.size;
            }

            @Override
            public Key next() {
                return keys.get(current++);
            }
        }
    }
}