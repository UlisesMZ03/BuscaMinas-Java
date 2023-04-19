
/**
 *
 * Clase que representa una pila de nodos con coordenadas i y j.
 */
public class Stack {

    private Node top;
    private int size;

    /**
     *
     * Constructor de la clase incList. Inicializa la pila vacía con un tamaño
     * de 0.
     */
    public Stack() {
        this.top = null;
        this.size = 0;
    }

    /**
     *
     * Verifica si la pila está vacía.
     *
     * @return true si la pila está vacía, false de lo contrario.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     *
     * Agrega un nuevo nodo a la pila.
     *
     * @param i La coordenada i del nodo a agregar.
     * @param j La coordenada j del nodo a agregar.
     */
    public void push(int i, int j) {
        Node newNode = new Node(i, j);
        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
        size++;
    }

    /**
     *
     * Elimina y devuelve el nodo superior de la pila.
     *
     * @return El nodo superior de la pila, o null si la pila está vacía.
     */
    public Node pop() {
        if (isEmpty()) {
            return null;
        } else {
            Node temp = top;
            top = top.next;
            temp.next = null;
            size--;
            return temp;
        }
    }

    /**
     *
     * Devuelve el tamaño actual de la pila.
     *
     * @return El tamaño de la pila.
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * Devuelve el nodo superior de la pila sin eliminarlo.
     *
     * @return El nodo superior de la pila, o null si la pila está vacía.
     */
    public Node peek() {
        return top;
    }

    /**
     *
     * Verifica si la pila contiene un nodo con las coordenadas especificadas.
     *
     * @param i La coordenada i del nodo a buscar.
     * @param j La coordenada j del nodo a buscar.
     * @return true si la pila contiene un nodo con las coordenadas
     * especificadas, false de lo contrario.
     */
    public boolean contains(int i, int j) {
        Node temp = top;
        while (temp != null) {
            if (temp.i == i && temp.j == j) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
}
