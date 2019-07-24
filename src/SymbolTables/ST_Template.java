package SymbolTables;

import java.util.Iterator;

public class ST_Template<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private int size;

    public ST_Template() {
    }

    /* Find the data structure associated with a given key -- may need to change return type */
    private int search(Key key) {
        return 0;
    }

    /* Associate a given key with a value in the symbol table. */
    public void put(Key key, Value value) {
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