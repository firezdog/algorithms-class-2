package SymbolTables;

import edu.princeton.cs.algs4.*;

@SuppressWarnings("all")
public class SeparateChainingHash_ST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    private int numEntries;
    private int maxEntries;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHash_ST() { this(997); }

    public SeparateChainingHash_ST(int size) {
        this.maxEntries = size;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[size];
        for (int i = 0; i < size; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    /* get the hash for the key (where hash has relevant properties) modulus maxEntries, where maxEntries has
    * been chosen to be prime -- the original hash respects every bit of the key, and the modulus respects every bit
    * of the hash. 0x7fffffff represents positive infinity and "&" is a bit-mask -- each bit in the hashCode
    * is matched with a bit in positive infinity, and if both are 1, the result is 1, otherwise 0 -- this is
    * supposed to change a negative integer to a positive integer, so that overall the hash is a positive integer.
    * According to the book: "The code masks off the sign bit (to turn the 32-bit integer into a 31-bit
    * nonnegative integer)."  Experimentation shows that Integer.MAX_VALUE & 0x7fffffff is Integer.MAX_VALUE while
    * Integer.MIN_VALUE & 0x7fffffff is 0. Note -- for testing I'm making this package access -- not ideal but
    * it's not trivial to find two strings with the same hash. */
    int index(Key key) { return (key.hashCode() & 0x7fffffff) % maxEntries; }

    @Override
    public int compares() {
        return 0;
    }

    @Override
    /* Get the index corresponding to the key using the hash function, then put an entry into the symbol table at
    * that index (using sequential search to avoid collisions). */
    public void put(Key key, Value value) {
        int index = index(key);
        boolean alreadyThere = st[index].get(key) != null;
        st[index(key)].put(key, value);
        if (!alreadyThere) numEntries++;
    }

    @Override
    /* The converse of "put" -- get the index for the key from the hash function, then get the entry for that key
    * from the corresponding symbol table. */
    public Value get(Key key) {
        return st[index(key)].get(key);
    }

    @Override
    public void delete(Key key) {
    }

    @Override
    public boolean contains(Key key) {
        return st[index(key)].get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return numEntries;
    }

    @Override
    public Iterable<Key> keys() {
        return null;
    }

}
