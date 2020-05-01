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
        mark(new Vertex(G, s).Value());
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) throws Exception {
        // find vertices in G that are reachable from sources
        this.G = G;
        marked = new boolean[G.V()];
        for (int s: sources)
        {
            mark(new Vertex(G, s).Value());
        }
    }

    void mark(int s) throws Exception
    {
        marked[s] = true;
        for (int next : G.adjacent(s))
        {
            int nextVert = new Vertex(G, next).Value();
            if (!marked[nextVert]) mark(nextVert);
        }
    }

    public boolean reachable(int v) throws Exception
    {
        // is v reachable from s / s[]?
        return marked[new Vertex(G, v).Value()];
    }

}
