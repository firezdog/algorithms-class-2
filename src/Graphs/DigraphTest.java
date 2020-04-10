package Graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigraphTest {

    Digraph d;

    @BeforeEach
    void setup() { d = new Digraph(2); }

    @Test
    void testAddEdge()
    {
        d.addEdge(0, 1);
        assertEquals(d.E(), 1);
        assertTrue(d.isAdjacent(0, 1));
    }

    @Test
    void testIsAdjacent()
    {
        d.addEdge(0, 1);
        assertTrue(d.isAdjacent(0, 1));
        assertFalse(d.isAdjacent(1, 0));
    }

    @Test
    void testReverse()
    {
        d.addEdge(0, 1);
        Digraph r = d.reverse();
        assertTrue(r.isAdjacent(1, 0));
        assertFalse(r.isAdjacent(0, 1));
    }

}