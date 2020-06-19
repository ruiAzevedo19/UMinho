package Model.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Usada na Query 2
 */

public class SalesClients {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private int globalSales;
    private int globalClients;
    private List<Integer> sales;
    private List<Integer> clients;
    /* -------------------------------------------------------------------------------------------------------------- */

    public SalesClients(){
        sales = new ArrayList<>(3);
        clients = new ArrayList<>(3);
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getGlobalSales() {
        return globalSales;
    }

    public int getGlobalClients() {
        return globalClients;
    }

    public int getSalesBranch(int branch){
        return sales.get(branch);
    }

    public int getClientsBranch(int branch){
        return clients.get(branch);
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setGlobalSales(int globalSales) {
        this.globalSales = globalSales;
    }

    public void setGlobalClients(int globalClients) {
        this.globalClients = globalClients;
    }

    public void setSalesBranch(int branch, int sales){
        this.sales.add(branch, sales);
    }

    public void setClientsBranch(int branch, int clients){
        this.clients.add(branch, clients);
    }
}
