
import java.util.Random;

/**
 *
 * La clase segList implementa una lista enlazada que almacena coordenadas (i,j)
 * correspondientes a celdas seguras en un tablero.
 *
 * @author [nombre del autor]
 * @version [versión del programa]
 */
public class segList {

    Node head;
    int size;

    /**
     * Constructor de la clase segList. Inicializa la lista vacía.
     */
    public segList() {
        head = null;
        size = 0;
    }

    /**
     * Agrega un nodo a la lista con las coordenadas (i,j) especificadas. Si la
     * lista está vacía, el nuevo nodo se convierte en la cabeza de la lista. Si
     * el nodo a agregar tiene las mismas coordenadas que algún otro nodo en la
     * lista, no se agrega.
     *
     * @param i la coordenada i del nodo a agregar
     * @param j la coordenada j del nodo a agregar
     */
    void addNode(int i, int j) {
        if (head == null) {
            head = new Node(i, j);
            size++;
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
            size++;
        }
    }

    /**
     * Imprime las coordenadas de todos los nodos de la lista.
     */
    public void printList1() {
        Node current = head;
        System.out.print("Lista segura: ");
        while (current != null) {
            System.out.print("(" + current.i + "," + current.j + ")");
            current = current.next;
            if (current != null) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * Devuelve el número de nodos en la lista.
     *
     * @return el tamaño de la lista
     */
    public int getSize() {
        return size;
    }

    /**
     * Determina si la lista contiene un nodo con las coordenadas (i,j)
     * especificadas.
     *
     * @param i la coordenada i del nodo a buscar
     * @param j la coordenada j del nodo a buscar
     * @return true si la lista contiene un nodo con esas coordenadas, false de
     * lo contrario
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
     * Elimina el primer nodo de la lista (la cabeza).
     *
     * @return el nodo eliminado, o null si la lista estaba vacía
     */
    public Node removeFNode() {
        if (head == null) {
            return null;
        }
        Node removedNode = head;
        head = head.next;
        size--;
        return removedNode;

    }

    /**
     * Elimina el nodo de la lista que tiene las coordenadas (i,j)
     * especificadas. Si no se encuentra un nodo con esas coordenadas, la lista
     * no se modifica.
     *
     * @param i la coordenada i del nodo a eliminar
     * @param j la coordenada j del nodo a eliminar
     */
    public void removeNode(int i, int j) {
        Node current = head;
        Node previous = null;

        while (current != null) {
            if (current.i == i && current.j == j) {
                if (previous == null) {
                    // Si el nodo a eliminar es el primero de la lista
                    head = current.next;
                } else {
                    // Si el nodo a eliminar está en medio o al final de la lista
                    previous.next = current.next;
                }
                size--;
                return; // Se encontró y eliminó el nodo
            }
            previous = current;
            current = current.next;
        }
    }

    /**
     *
     * Elimina todos los nodos de la lista y establece el tamaño en 0.
     */
    public void clear() {
        head = null;
        size = 0;
    }

}
