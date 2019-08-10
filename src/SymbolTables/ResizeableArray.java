package SymbolTables;

import edu.princeton.cs.algs4.StdOut;

@SuppressWarnings("all")
public class ResizeableArray<T extends Comparable<T>> {
    private T[] arr;
    private int size;
    private int capacity;

    public ResizeableArray() {
        arr = (T[]) new Comparable[2];
        capacity = 2;
    }

    public void push(T item) {
        arr[size] = item;
        size++;
        enlarge();
    }

    public void push(int i, T item) {
        if (i < 0) return;
        if (capacity <= i) {
            resize(i*2);
            size = i;
        }
        else {
            size++;
            enlarge();
        }
        for (int index = size - 1; index > i; index--) {
            arr[index] = arr[index-1];
        }
        arr[i] = item;
    }

    private void enlarge() {
        if (size >= capacity/2) resize(2*capacity);
    }

    /* there's no corresponding "pull", because if an item is deleted, the size always goes down.  Pull
    * would just be putting null @ i */
    public void put(int i, T item) {
        if (i < 0) return;
        if (capacity <= i) resize(i*2);
        arr[i] = item;
        size = Math.max(i + 1, size);
    }


    public T pop() {
        if (size == 0) return null;
        T item = arr[size--];
        reduce();
        return item;
    }

    public T pop(int i) {
        if (i < 0 || i >= size) return null;
        T item = arr[i];
        arr[i] = null;
        for (int pos = i; pos < size; pos++) {
            arr[pos] = arr[pos+1];
        }
        size--;
        reduce();
        return item;
    }

    private void reduce() {
        if (size <= capacity/4) resize(capacity/2);
    }

    private void resize(int newCapacity) {
        T[] temp = arr;
        arr = (T[]) new Comparable[newCapacity];
        int minLength = Math.min(Math.min(arr.length, temp.length), size);
        for (int i = 0; i < minLength; i++) {
            arr[i] = temp[i];
        }
        capacity = newCapacity;
    }

    public T get(int i) {
        if (i < 0 || i >= size) return null;
        return arr[i];
    }

    public int length() {
        return size;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            s.append(arr[i]);
            s.append(" ");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        ResizeableArray<Integer> r = new ResizeableArray<>();
        for (int i = 0; i < 10; i++) r.push(i);
        StdOut.println("r.length(): " + r.length());
        StdOut.println("r: " + r);
        StdOut.println("r[3]: " + r.get(3));
        StdOut.println("r[-100]: " + r.get(-100));
        r.push(3, 150);
        r.put(3, 151);
        StdOut.println("r: " + r);
        r.pop(100);
        StdOut.println("r: " + r);
        r.pop(3);
        StdOut.println("r: " + r);
        // it doesn't matter how much you pop from r
        for (int i = 0; i < 100; i++) r.pop();
        StdOut.println("r: " + r);
        StdOut.println("r.length(): " + r.length());
        r.put(10, 15);
        StdOut.println("r: " + r);
        StdOut.println("r.length(): " + r.length());
    }

}
