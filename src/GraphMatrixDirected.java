public class GraphMatrixDirected <V,E> extends GraphMatrix<V,E>{
    public GraphMatrixDirected(int size)
// pre: size > 0
// post: constructs an empty graph that may be expanded to
// at most size vertices. Graph is directed if dir true
// and undirected otherwise
    {
        super(size,true);
    }
}
