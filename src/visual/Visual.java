package visual;

import genome.Brain;
import genome.ConnectionGene;
import genome.NodeGene;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Visual extends PApplet {

    private Brain brain;
    private int inNodes, hiddenNodes, outNoides;
    private float cell_size;

    private String[] buttons = {"mutate link", "mutate node", "random weight", "shift weight", "toggle link", "mutate"};
    private String[] func = {"mutateLink", "mutateNode", "mutateRandomWeight", "mutateShiftWeight", "mutateToggleLink", "mutate"};

    private ArrayList<NodeGene> nodes = new ArrayList<>();
    private ArrayList<ConnectionGene> conns = new ArrayList<>();

    private int panel_height;

    public void settings() {
        size(800, 500);
        //fullScreen();


        panel_height = 60;
    }

    public void setup() {
        Random r = new Random();
        brain = new Brain(4, 1);
        nodes = brain.getGenome().getNodes();
        conns = brain.getGenome().getConnections();


        for (int i = 0; i < 0; i++) {
            if (r.nextBoolean()) brain.getGenome().mutateLink();
            else brain.getGenome().mutateNode();
        }

        //brain.getGenome().mutateToggleLink();
        //brain.getGenome().mutateToggleLink();

//        brain.getGenome().mutateLink();
//        brain.getGenome().mutateLink();
//
//        brain.getGenome().mutateNode();
//        brain.getGenome().mutateNode();
//        brain.getGenome().mutateNode();
//        brain.getGenome().mutateLink();
//        brain.getGenome().mutateLink();
//        brain.getGenome().mutateLink();
//        brain.getGenome().mutateLink();
//        brain.getGenome().mutateLink();

        System.out.println(conns.size() + " -- size");


        for (NodeGene n : nodes) {
            if (n.getType() == NodeGene.TYPE.INPUT) inNodes++;
            else if (n.getType() == NodeGene.TYPE.HIDDEN) hiddenNodes++;
            else outNoides++;
        }

        int inputNodes = 0;

        for (NodeGene n : brain.getGenome().getNodes()) {
            if (n.getType() == NodeGene.TYPE.INPUT) {
                inputNodes++;
            }
        }

        System.out.println(inputNodes);
        float[] arr = {1, 1, 1, 1};
//        System.out.println(Arrays.toString(brain.getGenome().getCalc().outputFromArray(arr)));
        //System.out.println(Arrays.toString(brain.getGenome().calculate2(arr)));

        frameRate(10);

        cell_size = 20;

        if (inNodes * cell_size * 2 > width - panel_height)
            cell_size = width / inputNodes / 2;
    }

    public void draw() {


        background(0);

        /// Panel ///

        stroke(255);
        fill(0);

        rect(0, 0, width, panel_height);

        textAlign(CENTER);

        for (int i = 0; i < func.length; i++) {
            fill(0);
            rect(i * (width / buttons.length), 0, width / buttons.length, panel_height);
            fill(255);
            text(buttons[i], i * (width / buttons.length) + (width / buttons.length / 2), panel_height / 2);
        }

        ////////////

        for (ConnectionGene con : conns) {
            if (con.isEnabeled()) {

                stroke(16, 87, 0);
                fill(16, 87, 0);
                float x1 = con.getFrom().getX() * width;
                float y1 = (float) con.getFrom().getY() * (height - panel_height) + panel_height;
                float x2 = con.getTo().getX() * width;
                float y2 = (float) con.getTo().getY() * (height - panel_height) + panel_height;

                float x3 = (x1 + x2) / 2;
                float y3 = (y1 + y2) / 2;


                textSize(12);
                textAlign(CENTER);
                text(con.getWeight(), x3, y3);

                line(x1, y1, x2, y2);
            } else {
                stroke(186, 0, 0);
                fill(186, 0, 0);
                float x1 = con.getFrom().getX() * width;
                float y1 = (float) con.getFrom().getY() * (height - panel_height) + panel_height;
                float x2 = con.getTo().getX() * width;
                float y2 = (float) con.getTo().getY() * (height - panel_height) + panel_height;

                float x3 = (x1 + x2) / 2;
                float y3 = (y1 + y2) / 2;


                textSize(12);
                textAlign(CENTER);
                text(con.getWeight(), x3, y3);

                line(x1, y1, x2, y2);
            }
        }

        for (NodeGene n : nodes) {
            stroke(186, 186, 0);
            fill(20);
            float x = (float) n.getX() * width;
            float y = (float) n.getY() * (height - panel_height) + panel_height;
            ellipse(x, y, cell_size, cell_size);

//            fill(186, 186, 0);
//            textAlign(CENTER, CENTER);
//            if (n.getType() == NodeGene.TYPE.INPUT) {
//                text("I", x, y);
//            } else if (n.getType() == NodeGene.TYPE.HIDDEN) {
//                text("H", x, y);
//            } else {
//                text("O", x, y);
//            }
            //System.out.println(n.getX() + " -- " + n.getY());
        }

        float[] arr = new float[inNodes];
        for (int i = 0; i < inNodes; i++) {
            arr[i] = (float) Math.random();
        }
        //System.out.println(Arrays.toString(brain.getGenome().getCalc().outputFromArray(arr)));
        System.out.println(brain.getGenome().getConnections().size() + " -- conn size");
        System.out.println(brain.getGenome().getNodes().size() + " -- node size");
        System.out.println(Arrays.toString(brain.getGenome().calculate2(arr)));


        noLoop();
    }

    public void mousePressed() {
        for (int i = 0; i < func.length; i++) {
            if (mouseX < (i + 1) * (width / func.length) && mouseX > i * (width / func.length) && mouseY < panel_height) {
                try {
                    java.lang.reflect.Method method;
                    method = brain.getGenome().getClass().getMethod(func[i]);
                    method.invoke(brain.getGenome());
                    //brain.getGenome().getClass().getMethod(func[i]);
                    System.out.println(func[i] + " -- func");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getCause());
                }
                redraw();
                break;
            }
        }
    }

    public static void main(String args[]) {
        PApplet.main("visual.Visual", args);
    }

}
