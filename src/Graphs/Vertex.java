package Graphs;

public class Vertex
{
    private int value;
    private final int lower_bound;
    private final int upper_bound;

    public Vertex(Digraph G, int value) throws Exception {
        lower_bound = 0;
        upper_bound = G.V() - 1;
        try {
            checkValue(value);
        } catch(Exception e) {
            throw new Exception("Error while initializing Vertex: ", e);
        }
    }

    private void checkValue(int value) throws Exception
    {
        if (value < lower_bound || value > upper_bound)
            throw new Exception("Vertex value out of bounds.");
    }

    public int Value()
    {
        return value;
    }

    public void Value(int new_value) throws Exception {
        checkValue(value);
        value = new_value;
    }
}