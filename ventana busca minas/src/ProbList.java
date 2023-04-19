public class ProbList {
    NodeProb head;
    int size;

    public ProbList() {
        head = null;
        size = 0;
    }

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
                System.out.println("Nodo actualizado"+current.i+","+current.j+"Se cambio: "+label+" por "+current.label);
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

    public void printList1() {
        NodeProb current = head;
        System.out.print("Lista prob: ");
        while (current != null) {
            System.out.print("(" + current.i + "," + current.j +"Prob: "+current.label+ ")");
            current = current.next;
            if (current != null) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
public void removeNode(int i, int j, double label) {
    NodeProb current = head;
    NodeProb previous = null;

    while (current != null) {
        if (current.i == i && current.j == j && current.label==label) {
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
    public NodeProb removeFNode() {
        if (head == null) {
            return null;
        }

        NodeProb removedNode = head;
        head = head.next;
        size--;

        return removedNode;
    }
    public void clear() {
    head = null;
    size = 0;
}
}
