package calculator;

import genome.ConnectionGene;
import genome.Genome;
import genome.NodeGene;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Comparator;

public class Calculator {

    private ArrayList<NodeGene> inputNodes = new ArrayList<>();
    private ArrayList<NodeGene> hiddenNodes = new ArrayList<>();
    private ArrayList<NodeGene> outputNodes = new ArrayList<>();

    private Genome g;

    public Calculator(Genome g) {

        this.g = g;

        ArrayList<NodeGene> nodes = g.getNodes();
        ArrayList<ConnectionGene> conns = g.getConnections();

        //HashMap<Integer, NodeGene> nodeHashMap = new HashMap<>();

        for (NodeGene n : nodes) {

            if (n.getType() == NodeGene.TYPE.INPUT) {
                inputNodes.add(n);
            } else if (n.getType() == NodeGene.TYPE.HIDDEN) {
                hiddenNodes.add(n);
            } else if (n.getType() == NodeGene.TYPE.OUTPUT) {
                outputNodes.add(n);
            }

            n.setConnections(conns);

        }

        //System.out.println(" In size: " + inputNodes.size());

        hiddenNodes.sort(new Comparator<NodeGene>() {
            @Override
            public int compare(NodeGene o1, NodeGene o2) {
                return o1.compareTo(o2);
            }
        });

//        for (ConnectionGene c : conns) {
//            //NodeGene from = c.getFrom();
//            NodeGene to = c.getTo();
//
//            to.getConnections().add(c);
//
//        }


    }

    public float[] outputFromArgs(float... input) {
        //System.out.println(input.length + " - - " + inputNodes.size());
        if (input.length != inputNodes.size()) throw new RuntimeException("Inputs don't match size");


        for (int i = 0; i < inputNodes.size(); i++) {

            inputNodes.get(i).setOutput(input[i]);
        }

        for (NodeGene n : hiddenNodes) {
            n.calculate();
        }

        float[] output = new float[outputNodes.size()];
        for (int i = 0; i < outputNodes.size(); i++) {
            outputNodes.get(i).calculate();
            output[i] = outputNodes.get(i).getOutput();
        }

        return output;

    }

    public float[] outputFromArray(float[] input) {

        if (input.length != inputNodes.size()) throw new RuntimeException("Inputs don't match size");


        for (int i = 0; i < inputNodes.size(); i++) {
            //inputNodes.get(i).setConnections(g.getConnections());
            inputNodes.get(i).setOutput(input[i]);
        }

        for (int i = 0; i < hiddenNodes.size(); i++) {
            //hiddenNodes.get(i).setConnections(g.getConnections());
            hiddenNodes.get(i).calculate();
            //System.out.println("Calc -- " + hiddenNodes.get(i).getX());
        }

        float[] output = new float[outputNodes.size()];
        //System.out.println(outputNodes.size());
        for (int i = 0; i < outputNodes.size(); i++) {
            //outputNodes.get(i).setConnections(g.getConnections());
            outputNodes.get(i).calculate();
            output[i] = outputNodes.get(i).getOutput();
        }

        return output;

    }

}
