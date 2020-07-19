package genome;

public class Gene {

    protected int innovation;

    public Gene(int innovation) {
        this.innovation = innovation;
    }

    public Gene() {
    }

    public int getInnovation() {
        return innovation;
    }

    public void setInnovation(int innovation) {
        this.innovation = innovation;
    }
}
