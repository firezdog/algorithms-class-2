package Graphs;

import edu.princeton.cs.algs4.Stack;

public class DirectedDFS
{
    private final Digraph G;
    private int s;
    private final boolean[] marked;
    private final int[] edgeTo;

    public DirectedDFS(Digraph G, int s) throws Exception {
        // find vertices in G that are reachable from s
        this.G = G;
        this.s = new Vertex(G, s).Value();
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        mark(this.s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) throws Exception {
        // find vertices in G that are reachable from sources
        this.G = G;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int s: sources)
        {
            mark(new Vertex(G, s).Value());
        }
    }

    void mark(int v) throws Exception
    {
        marked[v] = true;
        for (int next : G.adjacent(v))
        {
            int w = new Vertex(G, next).Value();
            if (!marked[w]) {
                edgeTo[w] = v;
                mark(w);
            }
        }
    }

    public boolean reachable(int v) throws Exception
    {
        // is v reachable from s / s[]?
        return marked[new Vertex(G, v).Value()];
    }

    public Iterable<Integer> pathTo(int v) throws Exception
    {
        // TODO: this doesn't work if multiple sources have been defined...
        if (!reachable(new Vertex(G, v).Value())) return null;
        Stack<Integer> path = new Stack<>();
        for (int curr = v; curr != s; curr = edgeTo[curr])
            path.push(curr);
        path.push(s);
        return path;
    }

}
