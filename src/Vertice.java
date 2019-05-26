public class Vertice <E> {
    E etiqueta;
    public Vertice (E etiqueta){
        this.etiqueta = etiqueta;
    }

    public E getEtiqueta(){
        return etiqueta;
    }

    public boolean isEqual(E l){
        if (this.etiqueta.equals(l)){
            return true;
        }
        return false;
    }
}
