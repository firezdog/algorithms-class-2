package Graphs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DirectedDFS
{
    Digraph G;
    boolean[] marked;

    public DirectedDFS(Digraph G, int s) throws Exception {
        // find vertices in G that are reachable from s
        this.G = G;
        marked = new boolean[G.V()];
        mark(s);
    }

    DirectedDFS(Digraph G, Iterable<Integer> sources)
    {
        // find vertices in G that are reachable from sources
        throw new NotImplementedException();
    }

    void mark(int s) throws Exception
    {
        Vertex source = new Vertex(G, s);   // throws exception if s is out of bounds.
        marked[s] = true;
        Iterable<Integer> adjacencies =  G.adjacent(s);
        for (int vertex: adjacencies)
        {
            if (!marked[vertex]) mark(vertex);
        }
    }

    public boolean reachable(int v) throws Exception
    {
        // is v reachable from s / s[]?
        return marked[new Vertex(G, v).Value()];
    }

}
