package genome;

import java.util.*;

public class NodeGene extends Gene implements Comparable<NodeGene> {

    public enum TYPE {
        INPUT,
        HIDDEN,
        OUTPUT
    }

    public enum FUNC {
        SQR,
        TAN,
        SIN,
        SIG,
        SIGN;

        private static final List<FUNC> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static FUNC randomFunc() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public static float sqr(float x) {
        return x * x;
    }

    public static float tan(float x) {
        return (float) Math.tan(x);
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float sign(float x) {
        return Math.signum(x);
    }

    public static float sig(float x) {
        return (float) (1f / (1 + Math.exp(-x)));
    }

    private TYPE type;
    private FUNC func = FUNC.SIG;
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
        output = 0;
        float s = 0;
        for (ConnectionGene c : connections) {
            if (c.isEnabeled()) {
                s += c.getWeight() * c.getFrom().getOutput();
            }
        }
        output = activation(s);
    }

    private float activation(float x) {
        if (func == FUNC.SQR) return sqr(x);
        if (func == FUNC.TAN) return tan(x);
        if (func == FUNC.SIN) return sin(x);
        if (func == FUNC.SIGN) return sign(x);

        return sig(x);
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

    public FUNC getFunc() {
        return func;
    }

    public void setFunc(FUNC func) {
        this.func = func;
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
        float out = output;
        output = 0;
        return out;
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
