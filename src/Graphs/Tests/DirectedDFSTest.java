package Graphs.Tests;

import Graphs.Digraph;
import Graphs.DirectedDFS;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DirectedDFSTest {
    private static Digraph g;
    DirectedDFS search;

    @BeforeAll
    static void setup()
    {
        g = new Digraph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 1);
    }

    static void setupMultiSource()
    {
        g = new Digraph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(5, 6);
        g.addEdge(5, 7);
    }

    @Test
    void testSearchSource() throws Exception
    {
        search = new DirectedDFS(g, 0);
        assertTrue(search.reachable(0));
        assertTrue(search.reachable(1));
        assertTrue(search.reachable(2));
        assertFalse(search.reachable(3));

        search = new DirectedDFS(g, 1);
        assertTrue(search.reachable(1));
        assertFalse(search.reachable(0));
        assertTrue(search.reachable(2));
        assertFalse(search.reachable(3));

        search = new DirectedDFS(g, 3);
        assertTrue(search.reachable(3));
        assertFalse(search.reachable(0));
        assertFalse(search.reachable(1));
        assertFalse(search.reachable(2));
    }

    @Test
    void testSearchForUnboundSource()
    {
        try {
            search = new DirectedDFS(g, 10);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void testSearchForSources() throws Exception
    {
        setupMultiSource();
        search = new DirectedDFS(g, Arrays.asList(0, 5));

        for (int i = 0; i < g.V(); i++)
        {
            if (i == 4) assertFalse(search.reachable(i));
            else assertTrue(search.reachable(i));
        }
    }

    @Test
    void testSearchForUnboundSources()
    {
        try
        {
            search = new DirectedDFS(g, Arrays.asList(0, 100));
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void testPathTo() throws Exception
    {
        search = new DirectedDFS(g, 0);
        Iterable<Integer> path = search.pathTo(2);
        assertEquals(path.toString(), "0 1 2 ");
    }

    @Test
    void testNoPathTo() throws Exception
    {
        search = new DirectedDFS(g, 0);
        Iterable<Integer> path = search.pathTo(3);
        assertNull(path);
    }

    @Test
    void testUnboundedPathTo() throws Exception {
        search = new DirectedDFS(g, 0);
        try {
            Iterable<Integer> path = search.pathTo(-1);
            fail();
        }  catch(Exception e) {
            assertTrue(true);
        }
    }

}