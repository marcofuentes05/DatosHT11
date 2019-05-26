import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main (String [] args){
        ArrayList<String> lista = new ArrayList();
        int contador = 0;
        try {
            File f  = new File("C:\\Users\\fuent\\OneDrive\\Documents\\2019\\Estructuras de Datos\\DatosHT11\\guategrafo.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            String st;
            while ((st = br.readLine()) != null) {
                contador++;
                System.out.println(st);
                lista.add(st);
            }

            GraphMatrixDirected<String, Integer> grafo = new GraphMatrixDirected<>(contador);

            for (int i = 0;i<lista.size();i++){
                String[] partes = lista.get(i).split(" ");
                grafo.add(partes[0]);
                grafo.add(partes[1]);
                grafo.addEdge(partes[0],partes[1],Integer.parseInt(partes[2]));
            }

            grafo.imprimirData();
            System.out.println(grafo.pesoArista("guatemala","escuintla"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

