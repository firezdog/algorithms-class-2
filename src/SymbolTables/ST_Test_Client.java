package SymbolTables;

import edu.princeton.cs.algs4.*;

@SuppressWarnings("all")
public class ST_Test_Client {

    // I guess this is Hungarian notation now...
    private static ComparableST<String, Integer> st;
    private static int totalCompares = 0;

    static ComparableST<String, Integer> initializeST() {
        return new RedBlackBST_ST<>();
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

    static void print(String lo, String hi) {
        for (String s : st.keys(lo, hi)) {
            StdOut.println(s + " " + st.get(s));
        }
    }

    /* (1) read in all the strings greater than the minimum length and keep a count of occurrences
    *  (2) retrieve the string with the highest count */
    static void performanceTest(int limit, int cutoff) {
        st = initializeST();
        StdDraw.enableDoubleBuffering();
        /* scale should reflect the number of words in standard input.  Not sure how to get without emptying StdIn.
        Note that scale for y will reflect for performance -- the worse the performance, the closer it needs to be to
        size of StdIn. */
        StdDraw.setCanvasSize(500, 100);
        StdDraw.setXscale(0, 10000000);
        StdDraw.setYscale(0, 25);

        int seen = 0;

        Stopwatch stopwatch = new Stopwatch();

        while (!StdIn.isEmpty()) {
            if (limit != -1 && seen > limit) break;
            String key = StdIn.readString();
            if (key.length() > cutoff) {
                Integer count = st.get(key);
                if (count == null) {
                    st.put(key, 1);
                    accumulate(++seen);
                }
                else {
                    st.put(key, count + 1);
                    accumulate(++seen);
                }
            }
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
        StdOut.printf("%s: %d -- %d \"words\" (%d distinct) processed in %f s\n", greatestKey, greatestCount, seen, st.size(), time);
        StdDraw.show();
    }

    static void accumulate(int seen) {
        int compares = st.compares();
        totalCompares += compares;
        double runningAverage = totalCompares / seen;
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.point(seen, compares);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(seen, runningAverage);
    }

    static void ExtendedAPITest() {
        st = initializeST();
        StdOut.println("Initialized.");
        StdOut.println("min: " + st.min());
        StdOut.println("max: " + st.max());
        StdOut.println("select(-5): " + st.select(-5));
        StdOut.println("ceiling(doge): " + st.ceiling("doge"));
        StdOut.println("floor(doge): " + st.floor("doge"));
        trace();
        StdOut.println("Putting stuff in.");
        StdOut.println("min: " + st.min());
        StdOut.println("max: " + st.max());
        // check inverse property
        StdOut.println("rank(select(1)): " + st.rank(st.select(1)));
        StdOut.println("rank(select(4000)): " + st.rank(st.select(4000)));
        // note that lower case and capital letters give different results because of compareTo
        StdOut.println("rank('zzzzz'): " + st.rank("zzzzz"));
        StdOut.println("select(rank(1)): " + st.select(st.rank("C")));
        StdOut.println("Delete A."); st.delete("A");
        StdOut.println("select(rank(1)): " + st.select(st.rank("C")));
        StdOut.println("floor(H): " + st.floor("H"));
        StdOut.println("floor(doge): " + st.floor("doge"));
        StdOut.println("ceiling(W): " + st.ceiling("W"));
        StdOut.println("ceiling(zealously): " + st.ceiling("zealously"));
        st.delete("a");
        StdOut.println("floor(a): " + st.floor("a"));
        print("wagd", "waj");
        StdOut.println("===============================");
        print("y", "zz");
        StdOut.println("===============================");
        print("aa", "ab");
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

    static void DeleteTest() {
        st = initializeST();
        trace();
        print();
        st.delete("S");
        st.delete("E");
        st.delete("A");
        st.delete("R");
        st.delete("C");
        st.delete("H");
        st.delete("E");
        st.delete("X");
        st.delete("A");
        st.delete("M");
        st.delete("P");
        st.delete("L");
        st.delete("E");
        st.delete("E");
        StdOut.println("=================");
        StdOut.println(st.size());
        print();
    }

    static void DrawTest() {
        RedBlackBST_ST<String, Integer> st = new RedBlackBST_ST<>();
        while (!StdIn.isEmpty()) {
            st.put(StdIn.readString(), 1);
        }
        st.show();
    }

    public static void main(String[] args) {
        DrawTest();
        // APITest();
        // ExtendedAPITest();
        // DeleteTest();
        /* No good way to do a doubling test that I know of so far because StdIn does not get reset between calls.
        * I guess you *could* just double until you run out of input and get some data out of that... */
        // performanceTest(-1, 1);
    }
}