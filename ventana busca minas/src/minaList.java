
/**
 *
 * Esta clase representa una lista enlazada que contiene nodos con coordenadas (i, j) de una mina.
 * La lista está implementada como una estructura de nodos, donde cada nodo tiene un par de coordenadas (i, j).
 */
public class minaList {

    Node head;

    /**
     * Constructor de la clase minaList que inicializa el nodo cabeza de la
     * lista enlazada como null.
     */
    public minaList() {
        head = null;
    }

    /**
     * Agrega un nuevo nodo a la lista enlazada con las coordenadas (i,j). Si el
     * nodo con las mismas coordenadas ya existe en la lista, no se agrega otro
     * nodo.
     *
     * @param i Coordenada i del nodo a agregar
     * @param j Coordenada j del nodo a agregar
     */
    void addNode(int i, int j) {
        if (head == null) {
            head = new Node(i, j);
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.i == i && current.j == j) {
                return; // No se agregan nodos con coordenadas repetidas
            }
            current = current.next;
        }

        if (current.i != i || current.j != j) {
            current.next = new Node(i, j);
        }
    }

    /**
     * Verifica si la lista enlazada contiene un nodo con las coordenadas (i,j).
     *
     * @param i Coordenada i del nodo a buscar
     * @param j Coordenada j del nodo a buscar
     * @return true si la lista contiene un nodo con las coordenadas (i,j), de
     * lo contrario false.
     */
    public boolean contains(int i, int j) {
        Node current = head;

        while (current != null) {
            if (current.i == i && current.j == j) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    /**
     * Imprime la lista enlazada de nodos con sus coordenadas (i,j).
     */
    public void printList() {
        Node current = head;
        System.out.print("Lista enlazada: ");
        while (current != null) {
            System.out.print("(" + current.i + "," + current.j + ")");
            current = current.next;
            if (current != null) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}
