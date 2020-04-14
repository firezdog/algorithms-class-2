package SymbolTables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SeparateChainingHash_STTest {

    SeparateChainingHash_ST<String, Integer> st;

    @BeforeEach
    void setup() {
        st = new SeparateChainingHash_ST<>();
    }

    void trace() {
        int i = 0;
        stdlib.StdIn.fromFile("data/symbol_tables/trace.txt");
        while (!stdlib.StdIn.isEmpty()) {
            st.put(stdlib.StdIn.readString(), i++);
        }
    }

    @Test
    // I don't think there's any way to test these independently.
    void testContainsSizeAndPut() {
        trace();
        assertEquals(10, st.size());
        assertTrue(st.contains("S"));
        assertTrue(st.contains("X"));
        assertFalse(st.contains("Y"));
        assertFalse(st.contains("N"));
    }

    @Test
    void testGet() {
        trace();
        assertEquals(8, st.get("A"));
        assertEquals(12, st.get("E"));
    }

    @Test @SuppressWarnings("all")
    void testCollission() {
        st = spy(SeparateChainingHash_ST.class);
        when(st.index("A")).thenReturn(10);
        when(st.index("B")).thenReturn(10);
        st.put("A", 15);
        st.put("B", 20);
        assertEquals(15, st.get("A"));
        assertEquals(20, st.get("B"));
    }

}