package SymbolTables;

import edu.princeton.cs.algs4.*;

@SuppressWarnings("all")
public class ST_Test_Client {

    // I guess this is Hungarian notation now...
    private static ST<String, Integer> st;

    static ST<String, Integer> initializeST() {
        return new OrderedArrayST<>();
    }

    static void trace() {
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
    }

    static void print() {
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }

    /* (1) read in all the strings greater than the minimum length and keep a count of occurrences
    *  (2) retrieve the string with the highest count */
    static void performanceTest(int limit, int cutoff) {
        st = initializeST();
        int seen = 0;
        Stopwatch stopwatch = new Stopwatch();
        while (!StdIn.isEmpty()) {
            if (limit != -1 && seen > limit) break;
            String key = StdIn.readString();
            if (key.length() > cutoff) {
                Integer count = st.get(key);
                if (count == null) {
                    st.put(key, 1);
                }
                else st.put(key, count + 1);
            }
            seen++;
        }
        Integer greatestCount = 0;
        String greatestKey = "";
        for (String newKey: st.keys()) {
            Integer newCount = st.get(newKey);
            if (newCount != null && newCount > greatestCount) {
                greatestCount = newCount;
                greatestKey = newKey;
            }
        }
        double time = stopwatch.elapsedTime();
        StdOut.printf("%s: %d -- %d \"words\" (%d distinct) processed in %f ms\n", greatestKey, greatestCount, seen, st.size(), time);
    }

    static void APITest() {
        st = initializeST();
        StdOut.println("SymbolTables.ST is empty: " + st.isEmpty());
        StdOut.println("Putting stuff in.");
        trace();
        StdOut.println("SymbolTables.ST is empty: " + st.isEmpty());
        StdOut.println("size: " + st.size());
        print();
        StdOut.println("Contains A: " + st.contains("A"));
        StdOut.println("Deleting A.");
        st.delete("A");
        StdOut.println("Contains A: " + st.contains("A"));
        // this makes assumptions about what StdIn looks like.  Make sure to use "data/tiny_trace.txt"
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
        print();
    }

    public static void main(String[] args) {
        // APITest();
        /* No good way to do a doubling test that I know of so far because StdIn does not get reset between calls.
        * I guess you *could* just double until you run out of input and get some data out of that... */
        performanceTest(2000000, 1);
    }
}