
public class NodeProb {
    int i;
    int j;
    double label;
    NodeProb next;

    public NodeProb(int i, int j, double label) {
        this.i = i;
        this.j = j;
        this.label = label;
        next = null;
    }
}