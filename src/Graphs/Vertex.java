package Graphs;

public class Vertex
{
    private int value;

    public Vertex(Digraph G, int value) throws Exception {
        int lower_bound = 0;
        int upper_bound = G.V() - 1;
        if (value < lower_bound || value > upper_bound)
            throw new Exception("Vertex value out of bounds.");
        this.value = value;
    }

    public int Value()
    {
        return value;
    }
}