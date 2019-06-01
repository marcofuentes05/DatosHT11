import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class GraphMatrix <V,E> {
    protected int size; // allocation size for graph
    protected Object data[][]; // matrix - array of arrays
    protected Map<V,GraphMatrixVertex<V>> dict; // labels -> vertices
    protected ArrayList<Integer> freeList; // available indices in matrix
    protected boolean directed; // graph is directed
    protected ArrayList<V> nodos;

    protected GraphMatrix(int size, boolean dir)
    {
        this.size = size; // set maximum size
        directed = dir; // fix direction of edges
        // the following constructs a size x size matrix
        data = new Object[size][size];
        // label to index translation table
        dict = new Hashtable<>(size);
        // put all indices in the free list
        freeList = new ArrayList<>();
        nodos = new ArrayList<>();
        for (int row = size-1; row >= 0; row--)
            freeList.add(row);
    }

    public void add(V label)
    // pre: label is a non-null label for vertex
    // post: a vertex with label is added to graph;
    // if vertex with label is already in graph, no action
    {
        // if there already, do nothing
        if (dict.containsKey(label)){
            return;
        }
        // verificar que aun existan indices disponibles para el vertice
        //Assert.pre(!freeList.isEmpty(),"Matrix not full");
        // allocate a free row and column
        int row = 0;
        if (freeList.size() > 0) {
            row = freeList.get(freeList.size()-1);
            freeList.remove(freeList.get(freeList.size()-1));
        }

        // add vertex to dictionary
        dict.put(label, new GraphMatrixVertex<V>(label, row));
        //add vertex to list
        nodos.add(label);
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
        nodos.remove(label);
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

    public boolean contains(V key){
        return dict.containsKey(key);
    }

    public Integer pesoArista(V ciudad1 , V ciudad2){
        try{
            int indice1 = dict.get(ciudad1).index;
            int indice2 = dict.get(ciudad2).index;
            if (data[indice1][indice2] != null){
                Arista t = (Arista)data[indice1][indice2];
                return (int)t.etiqueta;
            }else{
                return -1;
            }
        }catch(Exception e){
            //e.printStackTrace();
            return -2;
        }
    }

    public void imprimirData(){
        for (int i = 0;i<size;i++){
            for (int j = 0;j<size;j++){
                if (data[i][j] != null) {
                    Arista t = (Arista) data[i][j];
                    System.out.print(" "+t.etiqueta+" ");
                }else{
                    System.out.print("null");
                }
            }
            System.out.println("");
        }
    }

    public V get(int n ){
        return nodos.get(n);
    }

    public int getIndex(V vertice){
        //pre: vertice es un vertice del grafo
        //pos: retorna el indice de dicho vertice en la matriz
        int n = 0;
        ArrayList<V> keys = new ArrayList(dict.keySet());
        for (int i = 0;i<keys.size();i++){
            if (keys.get(i).equals(vertice)){
                n = i;
            }
        }
        return n;
    }
}