import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main (String [] args){
        ArrayList<String> lista = new ArrayList();
        int contador = 0;
        try {
            File f  = new File("C:\\Users\\fuent\\OneDrive\\Documents\\2019\\Estructuras de Datos\\DatosHT11\\guategrafo0.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String st;
            while ((st = br.readLine()) != null) {
                contador++;
                System.out.println(st);
                lista.add(st);
            }

            GraphMatrixDirected<String, Integer> grafo = new GraphMatrixDirected<>(5);

            for (int i = 0;i<lista.size();i++){
                String[] partes = lista.get(i).split(" ");
                grafo.add(partes[0]);
                grafo.add(partes[1]);
                grafo.addEdge(partes[0],partes[1],Integer.parseInt(partes[2]));
            }

            grafo.imprimirData();
            //System.out.println(grafo.pesoArista("guatemala","peten"));
            System.out.println("\n\n\tHOLA\n\n");
            Double[][] m0 = floydWarshal(grafo.data);
            for (int i = 0;i<m0[0].length;i++){
                for (int j = 0;j<m0[0].length;j++){
                    if (m0[i][j] != Double.POSITIVE_INFINITY) {
                        System.out.print(" - " + m0[i][j]);
                    }else{
                        System.out.print(" - 00");
                    }
                }
                System.out.println("");
            }

            System.out.println("\n\n\t"+centroDeGrafo(m0,grafo));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

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

    public static String centroDeGrafo(Double[][]grafo, GraphMatrixDirected<String, Integer> g){
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
        for (int i = 0;i<tamano;i++){
            System.out.print(maximos[i]+" ");
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
}

