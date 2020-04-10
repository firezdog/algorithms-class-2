package Graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {

    Bag<Integer>[] adjacencies;

    public Digraph(int v) {
        adjacencies = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < v; i++)
        {
            adjacencies[i] = new Bag<Integer>();
        }
    }

    public Digraph(In in) {

    }

    public int V() {
        return adjacencies.length;
    }

    public int E() {
        int sum = 0;
        for (int vertex = 0; vertex < V(); vertex++) {
            sum += adjacencies[vertex].size();
        }
        return sum;
    }

    private void checkBounds(int v) {
        if (0 <= v && v < V()) return;
        throw new IndexOutOfBoundsException();
    }

    public void addEdge(int v, int w) {
        checkBounds(v); checkBounds(w);
        adjacencies[v].add(w);
    }

    public boolean isAdjacent(int v, int w) {
        checkBounds(v); checkBounds(w);
        for (int node: adjacencies[v]) {
            if (node == w) return true;
        }
        return false;
    }

    public Digraph reverse() {
        int totalVertices = V();
        Digraph reverse = new Digraph(totalVertices);
        for (int vertex = 0; vertex < totalVertices; vertex++) {
            for (int adjacency : adjacencies[vertex]) {
                reverse.addEdge(adjacency, vertex);
            }
        }
        return reverse;
    }

}