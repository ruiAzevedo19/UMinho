package Utilities;

public class AverageTime {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private double time;
    private int n;
    /* -------------------------------------------------------------------------------------------------------------- */

    public AverageTime(){
        time = 0.0;
        n = 0;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */
    public double getAverage(){
        return time / (double)n;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */
    public void addTime(double time){
        this.time += time;
        n++;
    }
}
