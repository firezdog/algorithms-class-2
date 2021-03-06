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
        assertTrue(st.contains("A"));
        st.delete("A");
        assertFalse(st.contains("A"));
        assertTrue(st.contains("C"));
        st.delete("C");
        assertFalse(st.contains("C"));
        assertTrue(st.contains("E"));
        st.delete("E");
        assertFalse(st.contains("E"));
        st.delete("");
        assertTrue(st.isRedBlack());
    }

    void trace() {
        int i = 0;
        StdIn.fromFile("data/symbol_tables/trace.txt");
        while (!StdIn.isEmpty()) {
            st.put(StdIn.readString(), i++);
        }
    }

    void count() {
        StdIn.fromFile("data/symbol_tables/trace.txt");
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            Integer count = st.get(key);
            if (count == null) st.put(key, 1);
            else st.put(key, count + 1);
        }
    }

}