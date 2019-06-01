import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa con grafos de ciudades, camino mas corto y centro
 * @author Andy Castillo, Marco Fuentes
 */
public class Main {
    public static void main (String [] args){
        ArrayList<String> lista = new ArrayList();
        int contador = 0;
        try {
            //Aqui se pone la direccion del archivo que vamos a usar para armar el grafo
            //NOTA el archivo ha de tener, en cada linea, NOMBRECIUDAD1 NOMBRECIUDAD2 KM, con los km en numeros, y sin espacios de mas
            File f  = new File("C:\\Users\\fuent\\OneDrive\\Documents\\2019\\Estructuras de Datos\\DatosHT11\\guategrafo.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String st;
            while ((st = br.readLine()) != null) {
                contador++;
                //System.out.println(st);
                lista.add(st);
            }

            //Creamos el objeto grafo
            GraphMatrixDirected<String, Double> grafo = new GraphMatrixDirected<>(contador);

            //Analizamos el txt y agregamos los nodos correspondientes
            for (int i = 0;i<lista.size();i++){
                String[] partes = lista.get(i).split(" ");
                grafo.add(partes[0]);
                grafo.add(partes[1]);
                grafo.addEdge(partes[0],partes[1],Double.parseDouble(partes[2]));
            }

            //grafo.imprimirData();
            //System.out.println(grafo.pesoArista("guatemala","peten"));
            //System.out.println("\n\n\tHOLA\n\n");
            Double[][] m0 = floydWarshal(grafo.data);
            /*for (int i = 0;i<m0[0].length;i++){
                for (int j = 0;j<m0[0].length;j++){
                    if (m0[i][j] != Double.POSITIVE_INFINITY) {
                        System.out.print(" - " + m0[i][j]);
                    }else{
                        System.out.print(" - 00");
                    }
                }
                System.out.println("");
            }*/
            boolean sigue = true;
            while (sigue){
                String menu = "\n\tBienvenido al programa de las elecciones\n1. Quiero conocer la ruta mas corta entre dos ciudades\n2. Quiero saber el nombre de la ciudad del dentro del grafo\n3. Quiero modificar el grafo\n4. Salir";
                System.out.println(menu);
                Scanner sc = new Scanner(System.in);
                String respuesta = sc.nextLine();

                if (respuesta.equals("1")){
                    System.out.println("Ingrese el nombre de la ciudad 1");
                    String c1 = sc.nextLine();
                    if (grafo.contains(c1)){
                        System.out.println("Ingrese el nombre de la ciudad 2");
                        String c2 = sc.nextLine();
                        if (grafo.contains(c2)){
                            if (m0[grafo.dict.get(c1).index][grafo.dict.get(c2).index] != Double.POSITIVE_INFINITY){
                                System.out.println("La ruta mas corta entre "+c1+" y "+c2+" es de "+m0[grafo.dict.get(c1).index][grafo.dict.get(c2).index]+"Km");
                                System.out.println("La ruta es la siguiente:");
                                double[][] ady = adyacencia(grafo.data);
                                dijkstra(ady,grafo.dict.get(c1).index,grafo.dict.get(c2).index,grafo);
                            }else{
                                //Si la ruta mas corta es infinito, no hay rutas
                                System.out.println("No hay rutas entre estas dos ciudades...");
                            }
                        }
                        else{
                            System.out.println("Esa ciudad no esta en el grafo :(");
                        }
                    }else{
                        System.out.println("Esa ciudad no esta en el grafo :(");
                    }
                }else if(respuesta.equals("2")){
                    System.out.println("El centro del grafo es: "+centroDeGrafo(m0,grafo));
                }else if(respuesta.equals("3")){
                    System.out.println("1. Quiero modificar la distancia entre dos ciudades\n2. Quiero agregar una conexion entre ciudades");
                    String r = sc.nextLine();
                    if (r.equals("1")){
                        System.out.println(("Ingrese el nombre de la ciudad 1"));
                        String c1 = sc.nextLine();
                        if (grafo.contains(c1)){
                            System.out.println(("Ingrese el nombre de la ciudad 2"));
                            String c2 = sc.nextLine();
                            if (grafo.contains(c2)){
                                Arista t = (Arista) grafo.data[grafo.dict.get(c1).index][grafo.dict.get(c2).index];
                                if (t  == null){
                                    System.out.println("No hay una carretera directa entre esas dos ciudades");
                                }else{
                                    System.out.println("¿Cual es la nueva distancia (en km)? - Ingrese -1 (MENOS UNO) para indicar que una distancia infinita");
                                    Scanner n = new Scanner(System.in);
                                    try{
                                        double distancia = Double.parseDouble(n.nextLine());
                                        if (distancia > -1){
                                            t.etiqueta = distancia;
                                        }else if (distancia == -1){
                                            t.etiqueta = Double.POSITIVE_INFINITY;
                                        }else{
                                            System.out.println("Ese no es un valor permitido...");
                                        }
                                    }catch(Exception e){
                                        System.out.println("Ese no es un dato valido...");
                                    }
                                }
                            }else{
                                System.out.println(c2+" no esta en el grafo :(");
                            }
                        }else{
                            System.out.println(c1 + " no esta en el grafo :(");
                        }
                    }else if(r.equals("2")){
                        System.out.println("Ingrese el nombre de la ciudad 1");
                        String c1 = sc.nextLine();
                        if (grafo.contains(c1)){
                            System.out.println(("Ingrese el nombre de la ciudad 2"));
                            String c2 = sc.nextLine();
                            if(grafo.contains(c2)){
                                System.out.println("Ingrese la distancia entre las dos ciudades (Ingrese '-1' para una distancia infinita)");
                                Scanner n = new Scanner(System.in);
                                try{
                                    double distancia = Double.parseDouble(n.nextLine());
                                    if (distancia > -1){
                                        grafo.addEdge(c1,c2,distancia);
                                    }else if (distancia == -1){
                                        grafo.addEdge(c1,c2,Double.POSITIVE_INFINITY);
                                    }else{
                                        System.out.println("Ese no es un valor permitido...");
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                    System.out.println("Ese no es un dato valido...");
                                }
                            }else{
                                System.out.println(c2+" no esta en el grafo...");
                            }
                        }else{
                            System.out.println(c1+" no esta en el grafo...");
                        }
                    }else{
                        System.out.println("Ese no es un dato valido...");
                    }
                    //En esta linea actualizo las distancias en la matriz m0
                    m0 = floydWarshal(grafo.data);
                }else if(respuesta.equals("4")){
                    System.out.println("¡Hasta luego!");
                    sigue =false;
                }else{

                }
            }
            //System.out.println("\n\n\t"+centroDeGrafo(m0,grafo));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo para la distancia mas corta entre dos pares cualesquiera de nodos del grafo
     * @param grafo
     * @return
     */
    public static Double [][] floydWarshal(Object[][] grafo){
        int tamano = grafo.length;
        Double distancia[][] = new Double[tamano][tamano];
        int i,j,k;
        for (i=0;i<tamano;i++){
            for (j=0;j<tamano;j++){
                if (grafo[i][j]!=null){
                    Arista t = (Arista) grafo[i][j];

                    distancia[i][j] = Double.parseDouble(t.etiqueta.toString());
                }else{
                    distancia[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for(k=0;k<tamano;k++){
            for(i=0;i<tamano;i++){
                for (j=0;j<tamano;j++){
                    if (distancia[i][k] + distancia[k][j] < distancia[i][j]) {
                        distancia[i][j] = distancia[i][k] + distancia[k][j];
                    }
                }
            }
        }
        for (int a = 0;a<tamano;a++){
            distancia[a][a]=0d;
        }
        return distancia;
    }

    /**
     * Metodo para calcular el centro del grafo
     * @param grafo
     * @param g
     * @return
     */
    public static String centroDeGrafo(Double[][]grafo, GraphMatrixDirected<String, Double> g){
        String centro;
        int tamano = grafo[0].length;
        Double maximos[] = new Double[tamano];
        //Encuentro el minimo en cada columna
        for (int i = 0;i<tamano;i++){
            maximos[i]=0d;//Inicializo
            for (int j = 0;j<tamano;j++){
                //j es fila
                if (maximos[i]<grafo[j][i]) {
                    maximos[i] = grafo[j][i];
                }
            }
        }
        //Aqui ya tengo el arreglo con las distancias maximas
        //Ahora busco el minimo (centro)
        Double minimo = maximos[0];
        int indice = 0;
        for (int i = 0;i<tamano;i++){
            if (minimo>maximos[i]){
                minimo = maximos[i];
                indice = i;
            }
        }

        centro = g.get(indice);
        return centro;
    }

    /**
     * Metodo para la ruta corta y los nodos que estan en la ruta
     * @param adjacencyMatrix
     * @param startVertex
     * @param finalVertex
     * @param grafo
     */
    /*El siguiente codigo fue obtenido de https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
    * y fue modificado para que funcionara con nuestra matriz de adyacencia*/
    //Le paso la matriz de adyacencia que tengo del metodo ADYACENCIA() y el indice de los nodos que quiero
    private static void dijkstra(double[][] adjacencyMatrix,
                                 int startVertex,
                                 int finalVertex,
                                 GraphMatrixDirected<String, Double> grafo)
    {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        double[] shortestDistances = new double[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = -1;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents,finalVertex,grafo);
    }

    /**
     * Metodo para imprimir la solucion
     * @param startVertex
     * @param distances
     * @param parents
     * @param finalVertex
     * @param grafo
     */
    private static void printSolution(int startVertex,
                                      double[] distances,
                                      int[] parents,
                                      int finalVertex,
                                      GraphMatrixDirected<String, Double> grafo)
    {

        System.out.println("----------------------------------");
        System.out.print("\t");
        printPath(finalVertex, parents, grafo);
        System.out.println("\n----------------------------------");
    }

    /**
     * Metodo para imprimit el camino
     * @param currentVertex
     * @param parents
     * @param grafo
     */
    private static void printPath(int currentVertex,
                                  int[] parents,GraphMatrixDirected<String, Double> grafo)
    {

        // Base case : Source node has
        // been processed
        if (currentVertex == -1)
        {
            return;
        }
        printPath(parents[currentVertex], parents, grafo);
        ArrayList<String> keys = new ArrayList(grafo.dict.keySet());

        System.out.print(grafo.nodos.get(currentVertex) + " ");
    }

    /**
     * Metodo para el nodo de adyacencia
     * @param matriz
     * @return
     */
    private static double[][] adyacencia(Object[][] matriz){
        int n = matriz[0].length;
        double[][] res = new double[n][n];
        for (int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                //recorro toda la matriz
                Arista<String,Double> t = (Arista) matriz[i][j];
                if (t != null){
                    res[i][j]=t.etiqueta;
                }else{
                    res[i][j] = Double.POSITIVE_INFINITY;
                }

            }
        }
        return res;
    }
}

