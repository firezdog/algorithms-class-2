import edu.princeton.cs.algs4.*;

public class ST_Test_Client {
    static void Trace(ST<String, Integer> st) {
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
    public static void main(String[] args) {
        NaiveArrayST<String, Integer> st = new NaiveArrayST<>(100);
        Trace(st);
    }
}