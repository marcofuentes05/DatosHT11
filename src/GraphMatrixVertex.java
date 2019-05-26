public class GraphMatrixVertex <V> extends Vertice <V>{
    protected int index;

    public GraphMatrixVertex(V label, int idx)
    {
        super(label);
        index = idx;
    }

    public int index()
    {
        return index;
    }
}
