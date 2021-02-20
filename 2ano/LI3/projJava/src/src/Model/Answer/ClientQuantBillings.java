package Model.Answer;

public class ClientQuantBillings {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String codClient;
    private int units;
    private double billings;
    /* -------------------------------------------------------------------------------------------------------------- */

    public ClientQuantBillings(){
        codClient = "";
        units = 0;
        billings = 0;
    }

    public ClientQuantBillings(String c, int u, double b){
        codClient = c;
        units = u;
        billings = b;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public String getCodClient() {
        return codClient;
    }

    public int getUnits() {
        return units;
    }

    public double getBillings() {
        return billings;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setCodClient(String c) {
        this.codClient = c;
    }

    public void setUnits(int u) {
        this.units = u;
    }

    public void setBillings(double b) {
        this.billings = b;
    }

    /* --- Funcionalidades ------------------------------------------------------------------------------------------ */

    public void addUnits(int u) {
        this.units += u;
    }

    public void addBillings(double b) {
        this.billings += b;
    }


    @Override
    public String toString(){
        return "(" + codClient + "," + units + "," + billings + ")";
    }

}
