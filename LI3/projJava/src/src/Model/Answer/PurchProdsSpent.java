package Model.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PurchProdsSpent {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int[] purchased;            /* Numero de compras                                                          */
    private int[] products;             /* produtos distintos comprados                                               */
    private double[] spent;             /* total gasto                                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    public PurchProdsSpent() {
        this.purchased = new int[12];
        this.products = new int[12];
        this.spent = new double[12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int[] getPurchased() {
        return purchased;
    }

    public int[] getProducts() {
        return products;
    }

    public double[] getSpent() {
        return spent;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setProducts(List<Integer> p){
        int i = 0;
        for(Integer n : p)
            products[i++] += n;
    }

    public void setPurchased(List<Integer> p){
        for(int i = 0; i < 12; i++) {
            purchased[i] += p.get(i);

        }
    }

    public void setSpent(List<Double> p){
        for(int i = 0; i < 12; i++)
            spent[i] += p.get(i);
    }
}
