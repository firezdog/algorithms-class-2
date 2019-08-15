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

    // TODO
    void nodeCountCheck() {

    }

    // TODO
    void nodeOrderCheck() {

    }

    // TODO
    void nodeNoDuplicateCheck() {

    }

    // TODO
    void isBST() {
        // Check:
        // 1. Count for node is correct
        // 2. Order for node is correct (all nodes are between
        // a min and max, recursively?)
        // 3. There are no duplicates
    }

    // TODO
    void is23() {
        // Check:
        // 1. No node is connected to two red links
        // 2. There are no right-leaning red links
    }

    // TODO
    void isBalanced() {
        // Check:
        // All paths have same number of black links
    }

    // TODO
    void isRedBlack() {
        // Check:
        // 1. is BST
        // 2. is23
        // 3. isBalanced
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