package SymbolTables;

public interface ComparableST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    Key min();
    Key max();
    Key select(int r);
    int rank(Key k);
    Key ceiling(Key k);
    Key floor (Key k);
    Iterable<Key> keys(Key lo, Key hi);
}