package SymbolTables;

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
        st.put("S", 0);
        assertFalse(st.isEmpty());
    }

    @Test
    void testSizeWhenEmpty() {
        assertEquals(0, st.size());
    }

}