package Graphs.Tests;

import Graphs.Digraph;
import Graphs.DirectedDFS;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectedDFSTest
{
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

    @Test
    void testSearch() throws Exception {
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
    void testSearchForUnboundSource() {
        try
        {
            search = new DirectedDFS(g, 10);
            fail();
        } catch(Exception e)
        {
            assertTrue(true);
        }
    }

}