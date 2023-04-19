
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * Esta clase representa una lista enlazada de nodos de probabilidad, donde cada
 * nodo tiene una etiqueta de probabilidad asociada y dos índices enteros (i,j)
 * que lo identifican.
 */
public class ProbList {

    NodeProb head;
    int size;

    /**
     *
     * Constructor de la clase que inicializa la lista vacía.
     */
    public ProbList() {
        head = null;
        size = 0;
    }

    /**
     *
     * Agrega un nuevo nodo a la lista con los índices (i,j) y la etiqueta de
     * probabilidad especificados.
     *
     * Si ya existe un nodo con los mismos índices, actualiza su etiqueta de
     * probabilidad si la nueva es mayor.
     *
     * @param i Índice entero del primer elemento asociado al nodo.
     *
     * @param j Índice entero del segundo elemento asociado al nodo.
     *
     * @param label Etiqueta de probabilidad asociada al nodo.
     */
    void addNode(int i, int j, double label) {
        NodeProb newNode = new NodeProb(i, j, label);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        NodeProb current = head;
        NodeProb prev = null;
        while (current != null) {
            if (current.i == i && current.j == j) {
                if (label >= current.label) {
                    //System.out.println("Nodo actualizado"+current.i+","+current.j+"Se cambio: "+label+" por "+current.label);
                    //removeNode(i, j, label);
                    current.label = label;
                }
                return;
            }
            if (label < current.label) {
                break;
            }
            prev = current;
            current = current.next;
        }

        if (prev == null) {
            newNode.next = head;
            head = newNode;
        } else {
            newNode.next = current;
            prev.next = newNode;
        }
        size++;
    }

    /**
     *
     * Verifica si existe un nodo en la lista con los índices (i,j) y la
     * etiqueta de probabilidad especificados.
     *
     * @param i Índice entero del primer elemento asociado al nodo.
     *
     * @param j Índice entero del segundo elemento asociado al nodo.
     *
     * @param label Etiqueta de probabilidad asociada al nodo.
     *
     * @return true si existe un nodo con los mismos índices (i,j) y etiqueta de
     * probabilidad especificados, false de lo contrario.
     */
    public boolean contains(int i, int j, double label) {
        NodeProb current = head;

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
     * Imprime los nodos de la lista enlazada junto con su probabilidad
     * asociada.
     */
    public void printList1() {
        NodeProb current = head;
        System.out.print("Lista prob: ");
        while (current != null) {
            System.out.print("(" + current.i + "," + current.j + "Prob: " + current.label + ")");
            current = current.next;
            if (current != null) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     *
     * Elimina un nodo con las coordenadas (i,j) y la etiqueta label de la lista
     * enlazada.
     *
     * @param i Coordenada i del nodo a eliminar.
     *
     * @param j Coordenada j del nodo a eliminar.
     *
     * @param label Etiqueta del nodo a eliminar.
     */
    public void removeNode(int i, int j, double label) {
        NodeProb current = head;
        NodeProb previous = null;

        while (current != null) {
            if (current.i == i && current.j == j && current.label == label) {
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
     * Elimina el primer nodo de la lista enlazada y lo devuelve.
     *
     * @return El primer nodo de la lista enlazada.
     */
    public NodeProb removeFNode() {
        if (head == null) {
            return null;
        }

        NodeProb removedNode = head;
        head = head.next;
        size--;

        return removedNode;
    }

    /**
     *
     * Actualiza la lista eliminando los nodos con etiquetas menores a las
     * máximas de cada coordenada (i,j).
     */
    public void updateMaxLabels() {
        Map<Pair<Integer, Integer>, Double> maxLabels = new HashMap<>();

        // Obtener los valores máximos para cada par (x,y)
        NodeProb current = head;
        while (current != null) {
            Pair<Integer, Integer> key = new Pair<>(current.i, current.j);
            double currentLabel = current.label;

            if (maxLabels.containsKey(key)) {
                double maxLabel = maxLabels.get(key);
                if (currentLabel > maxLabel) {
                    maxLabels.put(key, currentLabel);
                }
            } else {
                maxLabels.put(key, currentLabel);
            }

            current = current.next;
        }

        // Eliminar nodos con etiquetas menores
        current = head;
        while (current != null) {
            Pair<Integer, Integer> key = new Pair<>(current.i, current.j);
            double maxLabel = maxLabels.get(key);

            if (current.label < maxLabel) {
                // System.out.println("Nodo eliminadoooooooo: "+current.i+"," +current.j+","+ current.label);
                removeNode(current.i, current.j, current.label);
            }

            current = current.next;
        }
    }

    /**
     *
     * Limpia la lista enlazada.
     */
    public void clear() {
        head = null;
        size = 0;
    }
}
