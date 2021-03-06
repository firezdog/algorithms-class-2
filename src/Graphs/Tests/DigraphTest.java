package Graphs.Tests;

import Graphs.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DigraphTest {

    Digraph d;

    @BeforeEach
    void setup() {
        d = new Digraph(2);
    }

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
    void testAddEdge() {
        d.addEdge(0, 1);
        assertEquals(d.E(), 1);
        assertTrue(d.isAdjacent(0, 1));
    }

    @Test
    void testAddingEdgeOutOfBounds() {
        try {
            d.addEdge(0, 2);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    void testIsAdjacent() {
        d.addEdge(0, 1);
        assertTrue(d.isAdjacent(0, 1));
        assertFalse(d.isAdjacent(1, 0));
    }

    @Test
    void testIsAdjacentOutOfBounds() {
        try {
            d.isAdjacent(0, 2);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    void testGetAdjacencies() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);

        d = new Digraph(4);
        d.addEdge(0, 1);
        d.addEdge(0, 3);

        ArrayList<Integer> actual = new ArrayList<>();
        Iterable<Integer> adj = d.adjacent(0);
        for (int i : adj) actual.add(i);
        Collections.sort(expected);
        Collections.sort(actual);

        assertEquals(expected, actual);
    }

    @Test
    void testReverse() {
        d.addEdge(0, 1);
        Digraph r = d.reverse();
        assertTrue(r.isAdjacent(1, 0));
        assertFalse(r.isAdjacent(0, 1));
    }

    @Test
    void testToString() {
        d = new Digraph(3);
        d.addEdge(0, 1);
        d.addEdge(0, 2);
        d.addEdge(1, 0);

        String expected = "[0]: 2 1\n[1]: 0\n[2]:\n";
        String actual = d.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testConstructorWithIn()
    {
        In mockIn = Mockito.mock(In.class);
        // mock an input file of the form "2 1 1 1"
        when(mockIn.readInt()).thenReturn(2).thenReturn(1);

        String expected = "[0]:\n[1]: 1\n";
        String actual = new Digraph(mockIn).toString();

        assertEquals(expected, actual);
    }

}