package Graphs;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class DirectedBFS
{
    private final Digraph G;
    private final int s;
    private final boolean[] marked;
    private final int[] edgeTo;

    public DirectedBFS(Digraph G, int s) throws Exception {
        this.G = G;
        this.s = new Vertex(G, s).Value();
        marked = new boolean[G.V()];
        marked[s] = true;
        edgeTo = new int[G.V()];
        explore();
    }

    private void explore() throws Exception {
        Queue<Integer> exploranda = new Queue<>();
        exploranda.enqueue(s);
        while (!exploranda.isEmpty())
        {
            int current = new Vertex(G, exploranda.dequeue()).Value();
            for (int path : G.adjacent(current))
            {
                if (!marked[new Vertex(G, path).Value()])
                {
                    edgeTo[path] = current;
                    marked[path] = true;
                    exploranda.enqueue(path);
                }
            }
        }
    }

    public boolean reachable(int v) throws Exception { return marked[new Vertex(G, v).Value()]; }

    public Iterable<Integer> pathTo(int v) throws Exception
    {
        if (!hasPathTo(new Vertex(G, v).Value())) return null;
        Stack<Integer> path = new Stack<>();
        for (int next = v; next != s; next = edgeTo[next])
            path.push(next);
        return path;
    }

}
