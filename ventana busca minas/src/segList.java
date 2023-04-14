
import java.util.Random;

public class segList {
    Node head;
    int size;

    public segList() {
        head = null;
        size = 0;
    }

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

    public int getSize() {
        return size;
    }
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
 public Node removeFNode() {
    if (head == null) {
        return null;
    }
    Node removedNode = head;
    head = head.next;
    size--;
    return removedNode;
}
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

   
}