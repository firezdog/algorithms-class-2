package SymbolTables;

public interface ComparableST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    public Key min();
    public Key max();
    public Key select(int r);
    public int rank(Key k);
    public Key ceiling(Key k);
    public Key floor (Key k);
    public Iterable<Key> keys(Key lo, Key hi);
}