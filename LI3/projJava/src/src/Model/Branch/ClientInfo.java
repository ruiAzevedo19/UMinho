package Model.Branch;

import Model.Answer.BuyersProducts;
import Model.Answer.ProdsUnitsClients;
import Model.Answer.PurchProdsSpent;
import Model.Answer.QuantProd;
import Model.Comparator.QuantAlfabProductComparator;
import Model.Interface.IProduct;
import Model.Product;

import javax.lang.model.element.QualifiedNameable;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ClientInfo implements IClientInfo, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Map<IProduct,IClientProdInfo> products;     /* produtos comprados por um determinado cliente              */
    private int[] purchased;                            /* compras de produtos por mes                                */
    private double[] moneySpent;                        /* dinheiro gasto tem produtos por mes                        */
    /* -------------------------------------------------------------------------------------------------------------- */

    public ClientInfo(){
        products = new HashMap<>();
        purchased = new int[12];
        moneySpent = new double[12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getPurchased(int month) {
        return purchased[month - 1];
    }

    public double getMoneySpent(int month) {
        return moneySpent[month - 1];
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setPurchased(int month, int value) {
        this.purchased[month - 1] = value;
    }

    public void setMoneySpent(int month, double value) {
        this.moneySpent[month - 1] = value;
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    public void addSale(IProduct p, int month, int units, double price){
        IClientProdInfo pdi = products.get(p);
        purchased[month - 1]++;
        moneySpent[month - 1] += units * price;
        if( pdi == null ){
            pdi = new ClientProdInfo();
            pdi.addSale(month,units,price);
            products.put(p, pdi);
        }
        else
            pdi.addSale(month,units,price);
    }

    public List<List<IProduct>> getProducts(){
        List<List<IProduct>> p = new ArrayList<>();
        for(int i = 1; i < 13; i++)
            p.add(getProductByMonth(i));
        return p;
    }

    private List<IProduct> getProductByMonth(int month){
        return products.entrySet().stream()
                .filter(e -> e.getValue().getPurchasedByMonth(month) > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public AbstractMap.SimpleEntry<List<Integer>,List<Double>> clientBuyingInfo(){
        List<Integer> p = Arrays.stream(purchased).boxed().collect(Collectors.toList());
        List<Double> m = Arrays.stream(moneySpent).boxed().collect(Collectors.toList());
        return new AbstractMap.SimpleEntry<>(p,m);
    }

    public List<Integer> getProductPurchasedInfo(IProduct prod){
        if(! products.containsKey(prod)) return null;
        IClientProdInfo cpi = products.get(prod);
        return Arrays.stream(cpi.getPurchasedMonth()).boxed().collect(Collectors.toList());
    }

    public TreeSet<QuantProd> getMostPurchasedProducts(){
        TreeSet<QuantProd> t = new TreeSet<>(new QuantAlfabProductComparator());
        products.forEach((key, value) -> t.add(new QuantProd(key.getCode(), value.getPurchased())));
        return t;
    }

    public Map<String,Integer> getProdsQuant(){
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(a -> a.getKey().getCode(),a -> a.getValue().getUnits()));
    }

    public IClientProdInfo getProductClientInfo(IProduct p){ return products.get(p); }

    public boolean productExist(IProduct p){ return products.containsKey(p);}

    public double getAllMoneySpent(){ return Arrays.stream(moneySpent).sum(); }

    public void addBuyerProducts(String c, BuyersProducts bp){
        products.forEach((p,pi) -> bp.addBuyerClient(c,p.getCode()));
    }

    public Map<String,Integer> getProdUnits(){
       return products.entrySet().stream().collect(Collectors.toMap(e->e.getKey().getCode(), e -> e.getValue().getUnits()));
    }
}
