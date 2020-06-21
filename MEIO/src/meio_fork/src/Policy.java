public class Policy {
    private double gain;
    private Integer[] decisions;

    public Policy(double gain, Integer[] decisions) {
        this.gain = gain;
        this.decisions = decisions;
    }

    public double getGain() {
        return gain;
    }

    public Integer[] getDecisions() {
        return decisions;
    }
}
