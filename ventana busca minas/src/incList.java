import java.util.Random;

public class incList {
    Node head;
    int size;

    public incList() {
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
    public void clear() {
    head = null;
    size = 0;
}


    public void printList1() {
        Node current = head;
        System.out.print("Lista vacias: ");
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
    
Node removeNode() {
    Random random=new Random();
    int index = random.nextInt(size+1);
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
        System.out.println("Nodo eliminado"+temp.i+","+temp.j);
        return temp;
        
    }

    return temp;
}
}

