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
}
