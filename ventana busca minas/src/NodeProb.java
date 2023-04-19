
/**
 *
 * Clase que representa un nodo de una lista enlazada utilizada para almacenar información sobre probabilidades.
 *
 * Cada nodo contiene una posición (i,j) y una etiqueta numérica.
 */
public class NodeProb {

    int i;
    int j;
    double label;
    NodeProb next;

    /**
     *
     * Constructor de la clase NodeProb que inicializa sus atributos.
     *
     * @param i Posición i del nodo.
     * @param j Posición j del nodo.
     * @param label Etiqueta numérica del nodo.
     */
    public NodeProb(int i, int j, double label) {
        this.i = i;
        this.j = j;
        this.label = label;
        next = null;
    }
}
