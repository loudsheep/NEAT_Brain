package genome;

import java.util.ArrayList;

public class NodeGene extends Gene implements Comparable<NodeGene> {

    public enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    private TYPE type;
    private float x;
    private double y;
    private float output;
    private ArrayList<ConnectionGene> connections = new ArrayList<>();

    public NodeGene() {
    }

    public NodeGene(int innovation, int x) {
        super(innovation);
        this.x = x;
    }

    public NodeGene(int innovation, TYPE type) {
        super(innovation);
        this.type = type;

        if (type == TYPE.INPUT) this.x = 0.1f;
        else if (type == TYPE.OUTPUT) this.x = 0.9f;
    }

    public void calculate() {
        float s = 0;
        for (ConnectionGene c : connections) {
            if (c.isEnabeled()) {
                s += c.getWeight() * c.getFrom().getOutput();
            }
        }
        output = activation(s);
        //System.out.println("Out -- " + s);
    }

    private float activation(float x) {
        //return 5f;
        return (float) (1f / (1 + Math.exp((double) -x)));
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public ArrayList<ConnectionGene> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ConnectionGene> connection) {
        //this.connections = connections;
        ArrayList<ConnectionGene> newConns = new ArrayList<>();
        for (ConnectionGene c : connection) {
            if (c.getTo() == this && c.isEnabeled() == true) {
                newConns.add(c);
            }
        }

        connections = newConns;
    }

    public float getOutput() {
        return output;
    }

    public void setOutput(float output) {
        this.output = output;
    }

    @Override
    public int compareTo(NodeGene o) {
        if (this.x > o.x) return 1;
        if (this.x < o.x) return -1;
        return 0;
    }
}
