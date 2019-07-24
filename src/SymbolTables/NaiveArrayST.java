package SymbolTables;

import java.util.Arrays;
import java.util.Iterator;

public class NaiveArrayST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    /* Our fields -- two associative arrays and an int to keep track of the size of the table.
    It would be nice to use resizeable arrays here -- that would require a few modifications to put, at least. */
    private Key[] keys;
    private Value[] values;
    private int size;

    @SuppressWarnings("unchecked")
    public NaiveArrayST(int capacity) {
        // we need keys to be a Comparable (as above) because we rank them in later applications.
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    /* Helper method to return the index of a given key in "keys" -- indexes always match between associative arrays.  Returns N if the key is not present (for size N). */
    private int search(Key key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) return i;
        }
        return size;
    }

    @Override
    public void put(Key key, Value value) {
    /* Cases: (1) the key is not in the table; (2) the key is associated with a different value.  In each case, we have to search through the table for the key. */
        // we start by getting index of the key -- if the key is not in the list, index will be the size of the array.
        int index = this.search(key);
        keys[index] = key;
        values[index] = value;
        // size only goes up if we are not replacing a key (otherwise search gives us null pointer errors!
        if (index == size) size++;
    }

    @Override
    public Value get(Key key) {
        int index = this.search(key);
        if (index < size) return values[index];
        return null;
    }

    @Override
    public void delete(Key key) {
    /* Requires shift left */
        int index = this.search(key);
        if (index == size) return;
        /* last element should become null by default. Note we have to shift both because array coordination
        is required. */
        for (int i = index; i < size; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        size--;
    }

    @Override
    public boolean contains(Key key) {
        return this.search(key) < size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        return new ST_Iterable();
    }

    class ST_Iterable implements Iterable<Key> {

        Key[] keys;

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
                return keys[current++];
            }
        }
    }
}