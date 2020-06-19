package Model.Branch;

import java.io.Serializable;

public class ClientProdInfo implements IClientProdInfo, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int units;                  /* Numero de unidades compradas deste produto                                 */
    private int purchased;              /* Numero de vezes que o produto foi comprado                                 */
    private int[] purchasedMonth;       /* Numero de vezes que o produto foi comprado dividido por mes                */
    private double billing;             /* Faturacao global                                                           */
    private double[] billingMonth;      /* Faturacao por mes                                                          */
    /* -------------------------------------------------------------------------------------------------------------- */

    public ClientProdInfo(){
        units = 0;
        purchased = 0;
        billing = 0;
        purchasedMonth = new int[12];
        billingMonth = new double[12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getUnits() {
        return units;
    }

    public int getPurchased() {
        return purchased;
    }

    public int[] getPurchasedMonth() {
        return purchasedMonth;
    }

    public int getPurchasedByMonth(int month){
        return purchasedMonth[month - 1];
    }

    public double getBilling() { return billing; }

    public double[] getBillingMonth() {
        return billingMonth;
    }

    public double getBilllingByMonth(int month){
        return billingMonth[month - 1];
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setUnits(int units) {
        this.units = units;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public void setPurchasedMonth(int month, int value) {
        this.purchasedMonth[month - 1] = value;
    }

    public void setBilling(double billing) {
        this.billing = billing;
    }

    public void setBillingMonth(int month, double value) {
        this.billingMonth[month - 1] = value;
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    public void addSale(int month, int units, double price){
        this.units += units;
        purchased++;
        purchasedMonth[month - 1]++;
        billing += units * price;
        billingMonth[month - 1] += units * price;
    }
}
