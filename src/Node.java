
/**
 *
 * @author fuent
 */
public class Node <E> {
    protected E data; //Valor que se almacena en este nodo
    protected Node<E> nextElement; //Referencia al siguiente nodo
    
    //Constructor

    /**
     * Constructor
     * @param v es el valor que sera almacenado en el nodo
     * @param next es la referencia al proximo valor en el siguiente nodo
     */
    public Node(E v, Node<E> next)
   {
       data = v;
       nextElement = next;
    }
    
    public Node(E v)
   // post: constructs a new tail of a list with value v
   {
      this(v,null);
   }

   public Node<E> next()
   // post: returns reference to next value in list
   {
      return nextElement;
   }
   
   public void setNext(Node<E> next)
   // post: sets reference to new next value
   {
      nextElement = next;
   }

   public E value()
   // post: returns value associated with this element
   {
      return data;
   }

   public void setValue(E value)
   // post: sets value associated with this element
   {
      data = value;
   }
}
