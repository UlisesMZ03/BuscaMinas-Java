
import java.util.Random;

/**
 *
 * La clase incList representa una lista enlazada que almacena nodos con dos
 * coordenadas enteras i y j.
 *
 * Permite agregar nodos a la lista y verificar si la lista contiene un nodo con
 * coordenadas específicas.
 *
 * También permite eliminar un nodo de manera aleatoria o específica, y obtener
 * el tamaño actual de la lista.
 */
public class incList {

    Node head;
    int size;

    /**
     *
     * Constructor de la clase incList. Inicializa la lista vacía con un tamaño
     * de 0.
     */
    public incList() {
        head = null;
        size = 0;
    }

    /**
     *
     * Agrega un nuevo nodo a la lista con las coordenadas dadas. Si ya existe
     * un nodo con las mismas
     *
     * coordenadas en la lista, no se agrega el nodo.
     *
     * @param i La coordenada i del nodo a agregar.
     *
     * @param j La coordenada j del nodo a agregar.
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
     *
     * Elimina todos los nodos de la lista y establece el tamaño en 0.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     *
     * Imprime por pantalla los nodos de la lista en el formato "(i,j) -> (i,j)
     * -> ... -> (i,j)".
     */
    public void printList1() {
        Node current = head;
        System.out.print("Lista Inc: ");
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
     *
     * Devuelve el tamaño actual de la lista.
     *
     * @return El número de nodos en la lista.
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * Verifica si la lista contiene un nodo con las coordenadas dadas.
     *
     * @param i La coordenada i del nodo a buscar.
     *
     * @param j La coordenada j del nodo a buscar.
     *
     * @return true si la lista contiene un nodo con las coordenadas dadas,
     * false de lo contrario.
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
     *
     * Elimina un nodo aleatorio de la lista y lo devuelve.
     *
     * @return El nodo eliminado de la lista, o null si la lista está vacía.
     */
    Node removeNode() {
        Random random = new Random();
        int index = random.nextInt(size + 1);
        if (head == null) {
            return null;
        }

        Node temp = null;

        if (index == 0) {
            temp = head;
            head = head.next;
            size--;
            return temp;
        }

        Node previous = head;
        Node current = head.next;

        for (int i = 1; i < index && current != null; i++) {
            previous = current;
            current = current.next;
        }

        if (current != null) {
            temp = current;
            previous.next = current.next;
            size--;
            //System.out.println("Nodo eliminado inc"+temp.i+","+temp.j);
            return temp;

        }

        return temp;
    }

    /**
     * Método que elimina un nodo de la lista enlazada que contiene las
     * coordenadas (i,j) especificadas como parámetros. Si el nodo existe en la
     * lista, se elimina y se decrementa el tamaño de la lista en 1. Si no
     * existe, no se realiza ninguna acción.
     *
     * @param i
     * @param j
     */
    public void removeNode2(int i, int j) {
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
}
