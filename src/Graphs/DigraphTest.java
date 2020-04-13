package Graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DigraphTest {

    Digraph d;

    @BeforeEach
    void setup() { d = new Digraph(2); }

    @Test
    void testV() {
        assertEquals(d.V(), 2);
    }

    @Test
    void testE() {
        // test that initially there are no edges
        d = new Digraph(5);
        assertEquals(d.E(), 0);

        // test addEdge increments number of edges
        d.addEdge(0, 1);
        assertEquals(d.E(), 1);
        d.addEdge(1, 0);
        assertEquals(d.E(), 2);

        // test adding duplicate edge does not increment number of edges
        d.addEdge(0, 1);
        assertEquals(d.E(), 2);
    }

    @Test
    void testAddEdge()
    {
        d.addEdge(0, 1);
        assertEquals(d.E(), 1);
        assertTrue(d.isAdjacent(0, 1));
    }

    @Test
    void testAddingEdgeOutOfBounds()
    {
        try
        {
            d.addEdge(0, 2);
            fail();
        } catch(IndexOutOfBoundsException e)
        {
            assertTrue(true);
        }
    }

    @Test
    void testIsAdjacent()
    {
        d.addEdge(0, 1);
        assertTrue(d.isAdjacent(0, 1));
        assertFalse(d.isAdjacent(1, 0));
    }

    @Test
    void testIsAdjacentOutofBounds()
    {
        try {
            d.isAdjacent(0, 2);
            fail();
        } catch (IndexOutOfBoundsException e)
        {
            assertTrue(true);
        }
    }

    @Test
    void testGetAdjacencies()
    {
        HashMap<Integer, Boolean> expected = new HashMap<>();
        expected.put(1, true);
        expected.put(3, true);

        d = new Digraph(4);
        d.addEdge(0, 1);
        d.addEdge(0, 3);

        HashMap<Integer, Boolean> actual = new HashMap<>();
        Iterable<Integer> adj = d.adjacent(0);
        for (int i: adj) actual.put(i, true);

        assertEquals(expected, actual);
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