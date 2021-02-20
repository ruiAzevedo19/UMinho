package Model;

import java.io.*;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class Stats implements Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String lastFile;                    /* ultimo ficheiro lido                                               */
    private int clients;                        /* clientes validos                                                   */
    private int wrongClients;                   /* clientes invalidos                                                 */
    private int products;                       /* produtos validos                                                   */
    private int wrongProducts;                  /* produtos invalidos                                                 */
    private int sales;                          /* vendas validas                                                     */
    private int wrongSales;                     /* numero total de registos de venda errados                          */
    private int totalProductsBought;            /* numero total de produtos comprados                                 */
    private int totalProductsNotBought;         /* numero total de produtos nao comprados                             */
    private int totalBuyingClients;             /* total de clientes que realizaram compras                           */
    private int totalClientsNotBuying;          /* total de clientes que nada compraram                               */
    private int totalSalesNull;                 /* total de compras de valor total igual a 0.0                        */
    private double totalBilling;                /* faturacao total                                                    */
    private int[] totalSalesMonth;              /* numero total de compras por mes                                    */
    private double[][] totalBillingMonth;       /* faturacao total por mes                                            */
    private int[][] clientsBuyingMonth;         /* numero de distintos clientes que compraram em cada mes por filial  */
    /* -------------------------------------------------------------------------------------------------------------- */

    public Stats(int nrBranches){
        totalSalesMonth = new int[12];
        totalBillingMonth = new double[nrBranches][12];
        clientsBuyingMonth = new int[nrBranches][12];
    }

    public Stats(Stats stats){
        this.lastFile = stats.getLastFile();
        this.clients = stats.getClients();
        this.wrongClients = stats.getWrongClients();
        this.products = stats.getProducts();
        this.wrongProducts = stats.getWrongProducts();
        this.sales = stats.getSales();
        this.wrongSales = stats.getWrongSales();
        this.totalProductsBought = stats.getTotalProductsBought();
        this.totalProductsNotBought = stats.getTotalProductsNotBought();
        this.totalBuyingClients = stats.getTotalBuyingClients();
        this.totalClientsNotBuying = stats.getTotalClientsNotBuying();
        this.totalSalesNull = stats.getTotalSalesNull();
        this.totalBilling = stats.getTotalBilling();
        this.totalSalesMonth = stats.getTotalSalesMonth();
        this.totalBillingMonth = stats.getTotalBillingMonth();
        this.clientsBuyingMonth = stats.getClientsBuyingMonth();
    }

    public Stats clone(){
        return new Stats(this);
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public String getLastFile() {
        return lastFile;
    }

    public int getClients() {
        return clients;
    }

    public int getWrongClients() {
        return wrongClients;
    }

    public int getProducts() {
        return products;
    }

    public int getWrongProducts() {
        return wrongProducts;
    }

    public int getSales() {
        return sales;
    }

    public int getWrongSales() {
        return wrongSales;
    }

    public int getTotalProductsBought() {
        return totalProductsBought;
    }

    public int getTotalProductsNotBought() {
        return totalProductsNotBought;
    }

    public int getTotalBuyingClients() {
        return totalBuyingClients;
    }

    public int getTotalClientsNotBuying() {
        return totalClientsNotBuying;
    }

    public int getTotalSalesNull() {
        return totalSalesNull;
    }

    public double getTotalBilling() {
        return totalBilling;
    }

    public int[] getTotalSalesMonth() {
        return totalSalesMonth;
    }

    public double[][] getTotalBillingMonth() {
        return totalBillingMonth;
    }

    public int[][] getClientsBuyingMonth() {
        return clientsBuyingMonth;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setLastFile(String lastFile) {
        this.lastFile = lastFile;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public void setWrongClients(int wrongClients) {
        this.wrongClients = wrongClients;
    }

    public void setProducts(int products) {
        this.products = products;
    }

    public void setWrongProducts(int wrongProducts) {
        this.wrongProducts = wrongProducts;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public void setWrongSales(int wrongSales) {
        this.wrongSales = wrongSales;
    }

    public void setTotalProductsBought(int totalProductsBought) {
        this.totalProductsBought = totalProductsBought;
    }

    public void setTotalProductsNotBought(int totalProductsNotBought) {
        this.totalProductsNotBought = totalProductsNotBought;
    }

    public void setTotalBuyingClients(int totalBuyingClients) {
        this.totalBuyingClients = totalBuyingClients;
    }

    public void setTotalClientsNotBuying(int totalClientsNotBuying) {
        this.totalClientsNotBuying = totalClientsNotBuying;
    }

    public void setTotalSalesNull(int totalSalesNull) {
        this.totalSalesNull = totalSalesNull;
    }

    public void setTotalBilling(double totalBilling) {
        this.totalBilling = totalBilling;
    }

    public void setTotalSalesMonth(int sales, int month) {
        this.totalSalesMonth[month - 1] = sales;
    }

    public void setTotalBillingMonth(int branch, double[] months) {
        this.totalBillingMonth[branch] = months;
    }

    public void setTotalSalesMonth(int[] sales){
        this.totalSalesMonth = sales;
    }

    public void setClientsBuyingMonth(int branch, int month, int clients) {
        this.clientsBuyingMonth[branch][month] = clients;
    }

    public void setClientsBuyingMonth(int branch, int[] months){
        this.clientsBuyingMonth[branch] = months;
    }

    /* --- Funcionality --------------------------------------------------------------------------------------------- */

    public double totalBilling(){
        return Arrays.stream(totalBillingMonth).flatMapToDouble(x-> DoubleStream.of(Arrays.stream(x).sum())).sum();
    }

    public void writeStatsToFile(String file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stats loadStatsFromFile(String file) throws IOException,ClassNotFoundException{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Stats s = (Stats)ois.readObject();
        ois.close();
        return s;
    }
}
