package Graphs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

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
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1); expected.add(3);

        d = new Digraph(4);
        d.addEdge(0, 1); d.addEdge(0, 3);

        ArrayList<Integer> actual = new ArrayList<>();
        Iterable<Integer> adj = d.adjacent(0);
        for (int i: adj) actual.add(i);
        Collections.sort(expected); Collections.sort(actual);

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