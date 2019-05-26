import java.util.Hashtable;
import java.util.Map;

public class GraphMatrix <V,E> {
    protected int size; // allocation size for graph
    protected Object data[][]; // matrix - array of arrays
    protected Map<V,GraphMatrixVertex<V>> dict; // labels -> vertices
    protected SingleLinkedList<Integer> freeList; // available indices in matrix
    protected boolean directed; // graph is directed

    protected GraphMatrix(int size, boolean dir)
    {
        this.size = size; // set maximum size
        directed = dir; // fix direction of edges
        // the following constructs a size x size matrix
        data = new Object[size][size];
        // label to index translation table
        dict = new Hashtable<>(size);
        // put all indices in the free list
        freeList = new SingleLinkedList<>();
        for (int row = size-1; row >= 0; row--)
            freeList.addLast(row);
    }

    public void add(V label)
    // pre: label is a non-null label for vertex
    // post: a vertex with label is added to graph;
    // if vertex with label is already in graph, no action
    {
        // if there already, do nothing
        if (dict.containsKey(label)) return;
        // verificar que aun existan indices disponibles para el vertice
        //Assert.pre(!freeList.isEmpty(),"Matrix not full");
        // allocate a free row and column
        int row = freeList.removeFirst().intValue();
        // add vertex to dictionary
        dict.put(label, new GraphMatrixVertex<V>(label, row));
    }
    public V remove(V label)
    // pre: label is non-null vertex label
    // post: vertex with "equals" label is removed, if found
    {
        // find and extract vertex
        GraphMatrixVertex<V> vert;
        vert = dict.remove(label);
        if (vert == null) return null;
        // remove vertex from matrix
        int index = vert.index();
        // clear row and column entries
        for (int row=0; row<size; row++) {
            data[row][index] = null;
            data[index][row] = null;
        }
        // add node index to free list
        freeList.add(new Integer(index));
        return vert.getEtiqueta();
    }

    public void addEdge(V vLabel1, V vLabel2, E label)
    // pre: vLabel1 and vLabel2 are labels of existing vertices
    // post: an edge is inserted between vLabel1 and vLabel2;
    // if edge is new, it is labeled with label (can be null)
    {
        GraphMatrixVertex<V> vtx1,vtx2;
        // get vertices
        vtx1 = dict.get(vLabel1);
        vtx2 = dict.get(vLabel2);
        // update matrix with new edge
        Arista<V,E> e = new Arista<V,E>(vtx1.getEtiqueta(),vtx2.getEtiqueta(),label,true);
        data[vtx1.index()][vtx2.index()] = e;
    }
}