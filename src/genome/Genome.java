package genome;

import calculator.Calculator;

import java.util.ArrayList;

public class Genome implements Cloneable {

    private ArrayList<NodeGene> nodes;
    private ArrayList<ConnectionGene> connections;
    private int inputSize, outputSize;

    private Brain brain;
    private Calculator calc;


    public Genome(int inputSize, int outputSize, Brain brain) {
        nodes = new ArrayList<>();
        connections = new ArrayList<>();


        this.brain = brain;

        this.inputSize = inputSize;
        this.outputSize = outputSize;

        int counter = 1;

        for (int i = 0; i < inputSize; i++) {
            NodeGene node = new NodeGene(counter, NodeGene.TYPE.INPUT);
            //node.setY(1/(inputSize+1) * (i+1));
            node.setY((i + 1) / (double) (inputSize + 1));
            node.setFunc(null);
            //System.out.println("Y -- " + node.getY());
            //System.out.println("Y -- " + 1/(inputSize+1) * (i+1));
            nodes.add(node);
            counter++;
        }
        for (int i = 0; i < outputSize; i++) {
            NodeGene node = new NodeGene(counter, NodeGene.TYPE.OUTPUT);

            node.setY((i + 1) / (double) (outputSize + 1));
            nodes.add(node);
            counter++;
        }

        calc = new Calculator(this);
//        ConnectionGene con = new ConnectionGene(nodes.get(0), nodes.get(4));
//        con.setWeight((float)Math.random());
//        connections.add(con);
    }


    public void mutate() {
        try {
            float probab = (float) Math.random();
            if (probab < brain.getLINK_MUTATION_PROBABITITY()) {
                mutateLink();
            }

            probab = (float) Math.random();
            if (probab < brain.getNODE_MUTATION_PROBABITITY()) {
                mutateNode();
            }

            probab = (float) Math.random();
            if (probab < brain.getWEIGHT_SHIFT_MUTATION_PROBABITITY()) {
                mutateShiftWeight();
            }

            probab = (float) Math.random();
            if (probab < brain.getWEIGHT_RANDOM_MUTATION_PROBABITITY()) {
                mutateRandomWeight();
            }

            probab = (float) Math.random();
            if (probab < brain.getLINK_TOGGLE_MUTATION_PROBABITITY()) {
                mutateToggleLink();
            }
            probab = (float) Math.random();
            if (probab < brain.getNODE_FUNCTIOIN_MUTATION_PROPABILITY()) {
                mutateNodeFunc();
            }
        } catch (StackOverflowError e) {
            return;

        }
    }

    public void mutateLink() {
        //if(maxSize() == connections.size())return;
        //System.out.println("mutlink -- " + maxSize());
        //Random r = new Random();

        for (int i = 0; i < 100; i++) {


            //NodeGene a = nodes.get(r.nextInt(nodes.size()));
            //NodeGene b = nodes.get(r.nextInt(nodes.size()));
            NodeGene a = randomNode(nodes);
            NodeGene b = randomNode(nodes);


            if (a == null || b == null) continue;
            if (a == b) continue;
            if (a.getX() == b.getX()) {
                //System.out.println("same x --");
                continue;

            }

            ConnectionGene con;


            if (a.getX() < b.getX()) {
                con = new ConnectionGene(a, b);
            } else {
                con = new ConnectionGene(b, a);
            }

            if (!isValidConection(con)) {
                //System.out.println("not valid -- ");
                continue;
            }

            con = brain.getConnection(con.getFrom(), con.getTo());
            con.setWeight((float) ((Math.random() * 2 - 1) * brain.getWeightRandomRange()));
            con.setEnabeled(true);

            connections.add(con);
            System.out.println("mutlink -- ");
            return;
        }
        //mutateLink();
    }

    private int maxSize() {
        int size = 0;
        int inN = 0;
        int hiN = 0;
        int ouN = 0;
        for (NodeGene n : nodes) {
            if (n.getType() == NodeGene.TYPE.INPUT) inN++;
            else if (n.getType() == NodeGene.TYPE.HIDDEN) hiN++;
            else ouN++;
        }

        size += (inN * hiN) + (inN * ouN);
        size += hiN * ouN;

        return size;
    }

    private boolean isValidConection(ConnectionGene con) {
        for (ConnectionGene c : connections) {
            if (c.getFrom() == con.getFrom() && c.getTo() == con.getTo()) {
                return false;
            }
        }

        return true;
    }

    public void mutateNode() {
        //Random r = new Random();

        ConnectionGene con;
        con = randomCon(connections);
//        try {
//            con = connections.get(r.nextInt(connections.size() - 1));
//        } catch (Exception e) {
//            return;
//        }

        if (con != null) {

            if (!con.isEnabeled()) {
                mutateNode();
                return;
            }

            NodeGene from = con.getFrom();
            NodeGene to = con.getTo();

            NodeGene middle = new NodeGene();
            middle.setType(NodeGene.TYPE.HIDDEN);

            middle.setX((from.getX() + to.getX()) / 2);
            middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);

            ConnectionGene con1 = brain.getConnection(from, middle);
            ConnectionGene con2 = brain.getConnection(middle, to);


            con1.setWeight(1);
            con1.setEnabeled(true);
            con2.setWeight(con.getWeight());
            con2.setEnabeled(true);

            //connections.remove(con);
            con.setEnabeled(false);
            connections.add(con1);
            connections.add(con2);

            nodes.add(middle);

            //calc = new Calculator(this);
        }

    }

    public void mutateRandomWeight() {
        //Random r = new Random();
        ConnectionGene con;// = connections.get(r.nextInt(connections.size()));
        con = randomCon(connections);
//        try {
//            con = connections.get(r.nextInt(connections.size() - 1));
//        } catch (Exception e) {
//            return;
//        }

        if (con != null) {
            float newWeight = (float) (Math.random()) * 2 - brain.getWeightRandomRange();
            con.setWeight(newWeight);
        }

        //calc = new Calculator(this);
    }

    public void mutateShiftWeight() {
        //Random r = new Random();
        ConnectionGene con;

        con = randomCon(connections);

//        try {
//            con = connections.get(r.nextInt(connections.size() - 1));
//        } catch (Exception e) {
//            return;
//        }
        if (con != null) {
            con.setWeight((float) (con.getWeight() + (Math.random() * 2 - 1) * brain.getWeightShiftRange()));
        }
        //float newWeight = r.nextFloat() * 2 - weightShiftRange;
        //con.setWeight(con.getWeight() + newWeight);

        //calc = new Calculator(this);
    }

    public void mutateToggleLink() {
        //Random r = new Random();
        ConnectionGene con;
        con = randomCon(connections);
//        try {
//            con = connections.get(r.nextInt(connections.size() - 1));
//        } catch (Exception e) {
//            return;
//        }
        if (con != null)
            con.setEnabeled(!con.isEnabeled());

        //calc = new Calculator(this);
    }

    public void mutateNodeFunc() {
        if(nodes.size() == inputSize + outputSize) return;

        ArrayList<NodeGene> hid = new ArrayList<>();

        for(NodeGene n: nodes) {
            if(n.getType() == NodeGene.TYPE.HIDDEN) {
                hid.add(n);
            }
        }

        NodeGene n = randomNode(hid);

        n.setFunc(NodeGene.FUNC.randomFunc());

    }

    private ConnectionGene randomCon(ArrayList<ConnectionGene> arr) {
        if (arr.size() > 0) {
            return arr.get((int) (Math.random() * arr.size()));
        }
        return null;
//        if (arr.size() < 1) return null;
//        int index = (int) (Math.random() * (arr.size() - 1));
//
//        System.gc();
//
//        return arr.get(index);
    }

    private NodeGene randomNode(ArrayList<NodeGene> arr) {
        if (arr.size() > 0) {
            return arr.get((int) (Math.random() * arr.size()));
        }
        return null;

//        if (arr.size() < 1) return null;
//        int index = (int) (Math.random() * (arr.size() - 1));
//        System.gc();
//
//        return arr.get(index);
    }

    public ArrayList<NodeGene> getNodes() {
        return nodes;
    }

    public ArrayList<ConnectionGene> getConnections() {
        return connections;
    }

    public void setNodes(ArrayList<NodeGene> nodes) {
        this.nodes = nodes;
    }

    public void setConnections(ArrayList<ConnectionGene> connections) {
        this.connections = connections;
    }

    public Calculator getCalc() {
        return calc;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public static void main(String args[]) {
        //Genome g = new Genome(3, 5);
        //Calculator calc = new Calculator(g);

        //System.out.println(Arrays.toString(calc.outputFromArgs(1, 1, 1)));
    }

    public float[] calculate1(float... inputs) {

        return calc.outputFromArgs(inputs);
    }

    public float[] calculate2(float[] inputs) {
        calc = new Calculator(this);
        return calc.outputFromArray(inputs);
    }

    public Genome crossOver(Genome g) {
        Genome gg = new Genome(g.getInputSize(), g.getOutputSize(), brain);


        return gg;
    }

    public void clear() {
        connections.clear();
        nodes.clear();
//        for (int i = 0; i < nodes.size(); i++) {
//            NodeGene n = nodes.get(i);
//            if (n.getType() != NodeGene.TYPE.INPUT && n.getType() != NodeGene.TYPE.OUTPUT) {
//                nodes.remove(n);
//            }
//        }

        int counter = 1;

        for (int i = 0; i < inputSize; i++) {
            NodeGene node = new NodeGene(counter, NodeGene.TYPE.INPUT);
            //node.setY(1/(inputSize+1) * (i+1));
            node.setY((i + 1) / (double) (inputSize + 1));
            node.setFunc(null);
            //System.out.println("Y -- " + node.getY());
            //System.out.println("Y -- " + 1/(inputSize+1) * (i+1));
            nodes.add(node);
            counter++;
        }
        for (int i = 0; i < outputSize; i++) {
            NodeGene node = new NodeGene(counter, NodeGene.TYPE.OUTPUT);

            node.setY((i + 1) / (double) (outputSize + 1));
            nodes.add(node);
            counter++;
        }
    }

    /////////////////////////// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! \/\/\/\/\/\/\/\/\/\/\/\/\/\/

    public Genome clone() {
        Genome g = new Genome(inputSize, outputSize, (Brain) brain.clone());
        g.setConnections((ArrayList<ConnectionGene>) connections.clone());
        g.setNodes((ArrayList<NodeGene>) nodes.clone());
        return g;
    }
}
