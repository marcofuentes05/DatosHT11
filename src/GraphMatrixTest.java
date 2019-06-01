import static org.junit.jupiter.api.Assertions.*;

class GraphMatrixTest {

    @org.junit.jupiter.api.Test
    void add() {
        GraphMatrixDirected<String,Integer> grafo = new GraphMatrixDirected<>(1);
        grafo.add("Prueba");
        assertEquals("Prueba",grafo.get(0));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        GraphMatrixDirected<String,Integer> grafo = new GraphMatrixDirected<>(1);
        grafo.add("Prueba");
        grafo.remove("Prueba");
        assertEquals(0,grafo.nodos.size());
    }

    @org.junit.jupiter.api.Test
    void addEdge() {
        GraphMatrixDirected<String,Integer> grafo = new GraphMatrixDirected<>(2);
        grafo.add("Prueba");
        grafo.add("Prueba1");
        grafo.addEdge("Prueba","Prueba1",50);
        Arista temp = (Arista)grafo.data[0][1];
        assertEquals(50, temp.etiqueta);
    }

}