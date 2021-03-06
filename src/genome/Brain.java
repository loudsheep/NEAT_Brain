package genome;

import java.util.ArrayList;

public class Brain implements Cloneable{

    private Genome genome;

    private ArrayList<ConnectionGene> allCons = new ArrayList<>();

    private float weightShiftRange = 0.2f;
    private float weightRandomRange = 1f;

    private int inNodes, outNodes;

    private float LINK_MUTATION_PROBABITITY = 0.4f;
    private float NODE_MUTATION_PROBABITITY = 0.4f;
    private float WEIGHT_SHIFT_MUTATION_PROBABITITY = 0.4f;
    private float WEIGHT_RANDOM_MUTATION_PROBABITITY = 0.4f;
    private float LINK_TOGGLE_MUTATION_PROBABITITY = 0.4f;
    private float NODE_FUNCTIOIN_MUTATION_PROPABILITY = 0.4f;

    public Brain(int inNodes, int outNodes) {
        genome = new Genome(inNodes, outNodes, this);
        this.inNodes = inNodes;
        this.outNodes = outNodes;
    }

    public ConnectionGene getConnection(NodeGene from, NodeGene to) {
        ConnectionGene gene = new ConnectionGene(from, to);

        gene.setInnovation(allCons.size() + 1);
        allCons.add(gene);

        return gene;

    }

    public int getInNodes() {
        return inNodes;
    }

    public int getOutNodes() {
        return outNodes;
    }

    public float[] calculateFromArray(float[] arr) {
        return genome.calculate2(arr);
    }

    public float[] calculateFromInputs(float... inputs) {
        return genome.calculate1(inputs);
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public float getWeightShiftRange() {
        return weightShiftRange;
    }

    public void setWeightShiftRange(float weightShiftRange) {
        this.weightShiftRange = weightShiftRange;
    }

    public float getWeightRandomRange() {
        return weightRandomRange;
    }

    public void setWeightRandomRange(float weightRandomRange) {
        this.weightRandomRange = weightRandomRange;
    }

    public float getLINK_MUTATION_PROBABITITY() {
        return LINK_MUTATION_PROBABITITY;
    }

    public void setLINK_MUTATION_PROBABITITY(float LINK_MUTATION_PROBABITITY) {
        this.LINK_MUTATION_PROBABITITY = LINK_MUTATION_PROBABITITY;
    }

    public float getNODE_MUTATION_PROBABITITY() {
        return NODE_MUTATION_PROBABITITY;
    }

    public void setNODE_MUTATION_PROBABITITY(float NODE_MUTATION_PROBABITITY) {
        this.NODE_MUTATION_PROBABITITY = NODE_MUTATION_PROBABITITY;
    }

    public float getWEIGHT_SHIFT_MUTATION_PROBABITITY() {
        return WEIGHT_SHIFT_MUTATION_PROBABITITY;
    }

    public void setWEIGHT_SHIFT_MUTATION_PROBABITITY(float WEIGHT_SHIFT_MUTATION_PROBABITITY) {
        this.WEIGHT_SHIFT_MUTATION_PROBABITITY = WEIGHT_SHIFT_MUTATION_PROBABITITY;
    }

    public float getWEIGHT_RANDOM_MUTATION_PROBABITITY() {
        return WEIGHT_RANDOM_MUTATION_PROBABITITY;
    }

    public void setWEIGHT_RANDOM_MUTATION_PROBABITITY(float WEIGHT_RANDOM_MUTATION_PROBABITITY) {
        this.WEIGHT_RANDOM_MUTATION_PROBABITITY = WEIGHT_RANDOM_MUTATION_PROBABITITY;
    }

    public float getLINK_TOGGLE_MUTATION_PROBABITITY() {
        return LINK_TOGGLE_MUTATION_PROBABITITY;
    }

    public void setLINK_TOGGLE_MUTATION_PROBABITITY(float LINK_TOGGLE_MUTATION_PROBABITITY) {
        this.LINK_TOGGLE_MUTATION_PROBABITITY = LINK_TOGGLE_MUTATION_PROBABITITY;
    }

    public float getNODE_FUNCTIOIN_MUTATION_PROPABILITY() {
        return NODE_FUNCTIOIN_MUTATION_PROPABILITY;
    }

    public void setNODE_FUNCTIOIN_MUTATION_PROPABILITY(float NODE_FUNCTIOIN_MUTATION_PROPABILITY) {
        this.NODE_FUNCTIOIN_MUTATION_PROPABILITY = NODE_FUNCTIOIN_MUTATION_PROPABILITY;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
