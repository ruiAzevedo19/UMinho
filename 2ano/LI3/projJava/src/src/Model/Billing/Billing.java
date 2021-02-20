package Model.Billing;

import Model.Answer.ProdsUnitsClients;
import Model.Answer.QuantProd;
import Model.Comparator.ProductComparator;
import Model.Interface.IProduct;
import Model.Product;
import Model.Sale;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Billing implements IBilling, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Map<IProduct,IProdInfo> products;   /* guarda a informacao relativa a venda de produtos                   */
    private int[] totalSales;                   /* guarda as vendas totais divididas por mes                          */
    private double totalBilling;                /* fatuacao total                                                     */
    private int nrBranches;                     /* numero de filiais do sistema                                       */
    /* -------------------------------------------------------------------------------------------------------------- */

    public Billing(int nrBranches){
        products = new HashMap<>();
        totalSales = new int[12];
        this.nrBranches = nrBranches;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getTotalSalesByMonth(int month){
        return totalSales[month - 1];
    }

    public double getTotalBilling() {
        return totalBilling;
    }

    public int[] getTotalSales() {
        return totalSales;
    }

    /* --- Populate ------------------------------------------------------------------------------------------------- */

    public void addProduct(String code){
        products.put(new Product(code),new ProdInfo(nrBranches));
    }

    public void addSale(Sale s){
        totalSales[s.getMonth() - 1]++;
        totalBilling += s.getUnits() * s.getPrice();
        IProdInfo pi = products.get(s.getProduct());
        pi.updateSale(s.getPrice(),s.getUnits(),s.getMonth(),s.getBranch());
    }

    /* --- Funcionality --------------------------------------------------------------------------------------------- */

    public List<IProduct> getProductsNeverBought(){
        return products.entrySet().stream()
                .filter(e -> !e.getValue().wasBought())
                .map(Map.Entry::getKey)
                .sorted(new ProductComparator())
                .collect(Collectors.toList());
    }

    public long getNumberProductsBought(){
        return products.entrySet().stream()
                                  .filter(e -> e.getValue().wasBought())
                                  .count();

    }

    public AbstractMap.SimpleEntry<List<Integer>,List<Double>> prodPurchBilings(IProduct p){
        IProdInfo pi = products.get(p);
        if( pi != null )
            return pi.prodPurchBillingsInfo();
        return null;
    }

    public Map<String,List<List<Double>>> getProdsBillingsMonthBranch(){
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(p -> p.getKey().getCode(),
                        p -> p.getValue().getBillingsMonthBranchList(),(e1,e2) -> e1,TreeMap::new));
    }

    public boolean productExist(IProduct p){
        return products.containsKey(p);
    }

    public void getXProductsMostBought(ProdsUnitsClients puc, int xProds){
        products.forEach((p,pi) -> puc.addProd(new QuantProd(p.getCode(),pi.getUnits())));
        puc.limitProdsUnitsClients(xProds);
    }

}
