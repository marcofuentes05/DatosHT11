//import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void floydWarshal() {
        Double [][] distancia = {
                {0d,5d},
                {10d,0d}
        };
        GraphMatrixDirected<String, Double> grafo = new GraphMatrixDirected<>(2);
        grafo.add("1");
        grafo.add("2");
        grafo.addEdge("1","2",5d);
        grafo.addEdge("2","1",10d);

        Object[][] data = grafo.data;

        Double[][] real = Main.floydWarshal(data);
        assertEquals(distancia[0][1],real[0][1]);
    }
}