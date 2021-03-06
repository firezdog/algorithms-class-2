package SymbolTables;

public interface ST<Key extends Comparable<Key>, Value> {
    int compares();
    void put(Key key, Value value);
    Value get (Key key);
    void delete (Key key);
    boolean contains(Key key); // like JS hasOwnProperty
    boolean isEmpty();
    int size();
    Iterable<Key> keys();
}