package Graphs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DirectedDFS
{
    Digraph G;
    boolean[] marked;

    DirectedDFS(Digraph G, int s)
    {
        // find vertices in G that are reachable from s
        marked = new boolean[G.V()];
    }

    DirectedDFS(Digraph G, Iterable<Integer> sources)
    {
        // find vertices in G that are reachable from sources
        throw new NotImplementedException();
    }

    void mark(int s) throws Exception
    {
        Vertex v = new Vertex(G, s);
        Iterable<Integer> adjacencies =  G.adjacent(s);
        for (int vertex: adjacencies)
        {
            mark(vertex);
        }
        marked[v.Value()] = true;
    }

    boolean marked(int v) throws Exception
    {
        Vertex vert = new Vertex(G, v);
        return marked[vert.Value()];
    }
}
