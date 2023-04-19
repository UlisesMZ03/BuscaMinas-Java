
/**
 *
 * La clase Node representa un nodo de una lista enlazada.
 *
 * Contiene dos enteros, i y j, que representan coordenadas.
 *
 * Además, tiene una referencia al siguiente nodo en la lista.
 */
public class Node {

    int i;
    int j;
    public Node next;

    /**
     *
     * Constructor de la clase Node que crea un nodo con las coordenadas dadas.
     *
     * @param i la coordenada i del nodo.
     * @param j la coordenada j del nodo.
     */
    public Node(int i, int j) {
        this.i = i;
        this.j = j;
        this.next = null;
    }
}
