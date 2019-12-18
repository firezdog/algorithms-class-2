package Graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {

    Bag<Integer>[] adjacencies;

    public Digraph(int v) {
        adjacencies = new Bag<Integer>[v];
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

    public void addEdge(int v, int w) {
        if (0 <= v && v < V()) adjacencies[v].add(w);
        else throw new IndexOutOfBoundsException();
    }

    public Digraph reverse() {
        int totalVertices = V();
        Digraph reverse = new Digraph(v);
        for (int vertex = 0; i < totalVertices; i ++) {
            for (int adjacency : adjacencies[vertex]) {
                reverse.addEdge(adjacency, vertex);
            }
        }
        return reverse;
    }

}