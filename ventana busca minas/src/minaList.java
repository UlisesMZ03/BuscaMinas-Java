
public class minaList {

    Node head;

    public minaList() {
        head = null;
    }

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
