public class Arista <V,E> {
    V aqui;
    V alla;
    E etiqueta;
    boolean dirigido;
    public Arista (V vertice1 ,V vertice2, E etiqueta, boolean dirigido){
        aqui = vertice1;
        alla = vertice2;
        this.etiqueta = etiqueta;
        this.dirigido = dirigido;
    }

    public V getAqui(){
        return aqui;
    }
    public V getAlla(){
        return alla;
    }

    public boolean esDirigido(){
        return dirigido;
    }
}
