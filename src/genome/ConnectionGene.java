package genome;

import java.util.ArrayList;

public class ConnectionGene extends Gene {

    private NodeGene from, to;
    private float weight;
    private boolean enabeled;


    public ConnectionGene(NodeGene from, NodeGene to) {
        this.from = from;
        this.to = to;
    }

    public NodeGene getFrom() {
        return from;
    }

    public NodeGene getTo() {
        return to;
    }

    public void setFrom(NodeGene from) {
        this.from = from;
    }

    public void setTo(NodeGene to) {
        this.to = to;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isEnabeled() {
        return enabeled;
    }

    public void setEnabeled(boolean enabeled) {
        this.enabeled = enabeled;
    }
}
