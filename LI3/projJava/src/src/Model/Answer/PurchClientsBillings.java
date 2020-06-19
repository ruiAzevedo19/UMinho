package Model.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Usada na Query 4
 */

public class PurchClientsBillings {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int[] purchased;            /* Numero de compras                                                          */
    private int[] clientes;             /* clientes distintos comprados                                               */
    private double[] billings;          /* total faturado                                                             */
    /* -------------------------------------------------------------------------------------------------------------- */

    public PurchClientsBillings() {
        this.purchased = new int[12];
        this.clientes = new int[12];
        this.billings = new double[12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int[] getPurchased() {
        return purchased;
    }

    public int[] getClientes() {
        return clientes;
    }

    public double[] getBillings() {
        return billings;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setClientes(List<Integer> p){
        int i = 0;
        for(Integer n : p)
            clientes[i++] += n;
    }

    public void setPurchased(List<Integer> p){
        for(int i = 0; i < 12; i++) {
            purchased[i] += p.get(i);

        }
    }

    public void setBillings(List<Double> p){
        for(int i = 0; i < 12; i++)
            billings[i] += p.get(i);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(">>>>>>>>>>>>>>>>>>>>>>>   Querie 4   <<<<<<<<<<<<<<<<<<<<<<< \n");
        s.append("Purchased -> " + Arrays.toString(purchased) + "\n");
        s.append("Clients -> " + Arrays.toString(clientes) + "\n");
        s.append("Billings -> " + Arrays.toString(billings) + "\n");
        s.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");

        return s.toString();
    }
}
