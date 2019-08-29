package SymbolTables;

import stdlib.StdIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackBST_STTest {

    RedBlackBST_ST<String, Integer> st;

    @BeforeEach
    void setup() {
        st = new RedBlackBST_ST<>();
    }

    @Test
    void testIsEmptyWhenEmpty() {
        assertTrue(st.isEmpty());
    }

    @Test
    void testIsEmptyWhenNotEmpty() {
        trace();
        assertFalse(st.isEmpty());
    }

    @Test
    void testSizeWhenEmpty() {
        assertEquals(0, st.size());
    }

    @Test
    void testSizeOnInsertion() {
        trace();
        assertEquals(st.size(), 10);
    }

    @Test
    void testGet() {
        count();
        assertEquals(2, st.get("A"));
        assertEquals(3, st.get("E"));
    }

    @Test
    void testIsRedBlackBST() {
        trace();
        assertTrue(st.isRedBlack());
    }

    // test deleteMin for now.
    @Test
    void testDelete() {
        trace();
        st.delete("");
        st.delete("");
        st.delete("");
        assertFalse(st.contains("A"));
        assertFalse(st.contains("C"));
        assertFalse(st.contains("E"));
    }

    void trace() {
        int i = 0;
        StdIn.fromFile("data/trace.txt");
        while (!StdIn.isEmpty()) {
            st.put(StdIn.readString(), i++);
        }
    }

    void count() {
        StdIn.fromFile("data/trace.txt");
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            Integer count = st.get(key);
            if (count == null) st.put(key, 1);
            else st.put(key, count + 1);
        }
    }

}