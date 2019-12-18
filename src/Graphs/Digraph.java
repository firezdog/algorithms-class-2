package Graphs;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Digraph {

    Bag<Integer>[] adjacencies;

    public Digraph(int v) {
        adjacencies = (Bag<Integer>[]) new Bag[v];
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
        Digraph reverse = new Digraph(totalVertices);
        for (int vertex = 0; vertex < totalVertices; vertex++) {
            for (int adjacency : adjacencies[vertex]) {
                reverse.addEdge(adjacency, vertex);
            }
        }
        return reverse;
    }

}