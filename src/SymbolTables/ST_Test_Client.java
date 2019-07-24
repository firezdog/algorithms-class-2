package SymbolTables;

import edu.princeton.cs.algs4.*;

public class ST_Test_Client {
    static void Trace(ST<String, Integer> st) {
        StdOut.println("SymbolTables.ST is empty: " + st.isEmpty());
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        StdOut.println("Putting stuff in.");
        StdOut.println("SymbolTables.ST is empty: " + st.isEmpty());
        StdOut.println("size: " + st.size());
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println("Contains A: " + st.contains("A"));
        StdOut.println("Deleting A.");
        st.delete("A");
        StdOut.println("Contains A: " + st.contains("A"));
        st.delete("C");
        st.delete("E");
        st.delete("M");
        st.delete("P");
        st.delete("R");
        st.delete("S");
        st.delete("X");
        st.delete("L");
        st.delete("H");
        StdOut.println("Deleted everything.");
        StdOut.println("size: " + st.size());
        StdOut.println("SymbolTables.ST is empty: " + st.isEmpty());
        // deleting a key that isn't there should do nothing.
        StdOut.println("Contains H: " + st.contains("H"));
        StdOut.println("Deleting non-existent key, H.");
        st.delete("H");
        StdOut.println("Contains H: " + st.contains("H"));
        StdOut.println("size: " + st.size());
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
    public static void main(String[] args) {
        ST<String, Integer> st = new LinkedList_ST<>();
        Trace(st);
    }
}