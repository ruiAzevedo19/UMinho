package Model.Billing;

import Model.Sale;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProdInfo implements IProdInfo, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int units;                       /* Numero de unidades compradas deste produto                            */
    private boolean bought;                  /* true se o produto foi comprado, false caso contrario                  */
    private int[] timesPurchased;            /* numero de vezes que um produto foi comprado                           */
    private double[] billingsMonth;          /* total faturado por mes                                                */
    private double[][] billingsMonthBranch;  /* total faturado dividido por mes e filial                               */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Método construtor
     * @param branches numero de filiais do sistema
     */
    public ProdInfo(int branches){
        bought = false;
        timesPurchased = new int[12];
        billingsMonth = new double[12];
        billingsMonthBranch = new double[branches][12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getUnits() { return units; }

    public boolean wasBought(){
        return bought;
    }

    public int[] getTimesPurchased() {
        return timesPurchased;
    }

    public int getTimesPurchasedMonth(int month){
        return timesPurchased[month - 1];
    }

    public double[] getBillingsMonth() {
        return billingsMonth;
    }

    public double getBillingsByMinth(int month){
        return billingsMonth[month - 1];
    }

    public double[][] getBillingsMonthBranch() {
        return billingsMonthBranch;
    }

    public double getBillingsByMonthBranch(int month, int branch){
        return billingsMonthBranch[branch][month];
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setUnits(int units) {
        this.units = units;
    }

    public void setBought(){
        bought = true;
    }

    public void increamentTimesPurchased(int month){
        this.timesPurchased[month - 1]++;
    }

    public void setBillingsMonth(int month, double billing) {
        this.billingsMonth[month - 1] += billing;
    }

    public void setBillingsMonthBranch(int branch, int month, double billing){
        this.billingsMonthBranch[branch][month] += billing;
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    public void updateSale(float price, int units, int month, int branch){
        this.units += units;
        bought = true;
        timesPurchased[month - 1]++;
        billingsMonth[month - 1] += price * units;
        billingsMonthBranch[branch - 1][month - 1] += price * units;
    }

    public AbstractMap.SimpleEntry<List<Integer>,List<Double>> prodPurchBillingsInfo(){
        List<Integer> p = Arrays.stream(timesPurchased).boxed().collect(Collectors.toList());
        List<Double> b = Arrays.stream(billingsMonth).boxed().collect(Collectors.toList());
        return new AbstractMap.SimpleEntry<>(p,b);
    }

    public List<List<Double>> getBillingsMonthBranchList(){
        List<List<Double>> bmbl = new ArrayList<>();
        for(double[] d : billingsMonthBranch){
            bmbl.add(Arrays.stream(d).boxed().collect(Collectors.toList()));
        }
        return bmbl;
    }

}
