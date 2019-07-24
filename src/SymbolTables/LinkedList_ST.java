package SymbolTables;

import java.util.Iterator;

public class LinkedList_ST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {

    /* The number of key-value pairs in the symbol table. */
    private int size;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key newKey, Value newValue) {
            key = newKey;
            value = newValue;
        }
    }

    private Node head;

    public LinkedList_ST() {
    }

    /* Find the data structure associated with a given key -- may need to change return type */
    private Node search(Key key) {
        Node walker = head;
        while (walker != null) {
            if (walker.key.equals(key)) return walker;
            walker = walker.next;
        }
        return null;
    }

    /* Associate a given key with a value in the symbol table. */
    public void put(Key key, Value value) {
        Node result = this.search(key);
        if (result == null) {
            Node temp = head;
            head = new Node(key, value);
            head.next = temp;
            size++;
        } else {
            result.key = key;
            result.value = value;
        }
    }

    /* Get the value associated with a given key in the symbol table. */
    public Value get(Key key) {
        Node result = this.search(key);
        if (result != null) return result.value;
        return null;
    }

    /*
    Remove a key-value association from the dictionary. Note -- we cannot use search for this implementation
    because of the peculiarity of linked lists -- you have to delete from the item *before* the item that interests you
    */
    public void delete(Key key) {
        Node walker = head;
        // case where the list is empty.
        if (size == 0) return;
        // case where item appears first
        if (walker.key.equals(key)) {
            head = walker.next;
            size--;
        }
        while (walker.next != null) {
            Node next = walker.next;
            if (next.key.equals(key)) {
                walker.next = next.next;
                size--;
            }
            walker = next;
        }
    }

    /* Determine whether a given key is defined in the symbol table. */
    public boolean contains(Key key) {
        return search(key) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterable<Key> keys() {
        return new ST_Iterable(head);
    }

    class ST_Iterable implements Iterable<Key> {

        // I think we can access private variables on this, because of the inner class.
        Node current;

        private ST_Iterable(Node head) {
            current = head;
        }

        public Iterator<Key> iterator() {
            return new ST_Iterator();
        }

        class ST_Iterator implements Iterator<Key> {

            public boolean hasNext() {
                if (current == null) return false;
                return current.next != null;
            }

            /* Seems like there's two desiderata: (1) move to the next; (2) return current item of interest */
            public Key next() {
                Key key = current.key;
                current = current.next;
                return key;
            }
        }
    }
}