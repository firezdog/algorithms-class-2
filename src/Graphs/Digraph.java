package Graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.io.File;

public class Digraph {

    private final Bag<Integer>[] adjacencies;
    private int E;
    private final int V;

    public Digraph(int v)
    {
        E = 0;
        V = v;
        adjacencies = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < v; i++)
        {
            adjacencies[i] = new Bag<Integer>();
        }
    }

    public Digraph(In in)
    {
        /* Read a digraph from standard input.  Input is a list of integers delineated by spaces,
        where the first two entries are the number of vertices and the number of edges, respectively¸ */
        this(in.readInt());
        int entries = in.readInt();
        for (int i = 0; i < entries; i++)
        {
            addEdge(in.readInt(), in.readInt());
        }
    }

    public int V() { return V; }

    public int E() { return E; }

    private void checkBounds(int v)
    {
        if (0 <= v && v < V()) return;
        throw new IndexOutOfBoundsException();
    }

    public void addEdge(int v, int w)
    {
        checkBounds(v); checkBounds(w);
        Bag<Integer> adj_list = adjacencies[v];
        for (int adj: adj_list) if (adj == w) return;
        adjacencies[v].add(w);
        E++;
    }

    public Iterable<Integer> adjacent(int v)
    {
        checkBounds(v);
        return adjacencies[v];
    }

    public boolean isAdjacent(int v, int w)
    {
        checkBounds(v); checkBounds(w);
        for (int node: adjacencies[v]) if (node == w) return true;
        return false;
    }

    public Digraph reverse()
    {
        int totalVertices = V();
        Digraph reverse = new Digraph(totalVertices);
        for (int vertex = 0; vertex < totalVertices; vertex++)
        {
            for (int adjacency : adjacencies[vertex])
            {
                reverse.addEdge(adjacency, vertex);
            }
        }
        return reverse;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < V(); i++)
        {
            s.append("[");
            s.append(i);
            s.append("]: ");
            for (int j: adjacent(i))
            {
                s.append(" ");
                s.append(j);
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args)
    {
        In file_input = new In(args[0]);
        Digraph d = new Digraph(file_input);
        System.out.println(d);
        System.out.println(d.reverse());
    }

}