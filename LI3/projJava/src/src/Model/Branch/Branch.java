package Model.Branch;

import Model.Answer.*;
import Model.Interface.IClient;
import Model.Interface.IProduct;
import Model.Sale;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Branch implements IBranch, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Map<IClient,IClientInfo> clients;   /* clientes que tem registo de compras                                */
    private int nrClients;                      /* numero de clientes que tem registo de compras                      */
    private int nrSales;                        /* numero de vendas de uma determianda filial                         */
    private int[] clientsMonth;                 /* numero de clientes distintos que fizeram compras em cada mes       */
    private int[] salesMonth;                   /* numero de vendas por mes                                           */
    private double[] billingMonth;              /* faturacao por mes                                                  */
    /* -------------------------------------------------------------------------------------------------------------- */

    public Branch(){
        clients = new HashMap<>();
        nrClients = 0;
        clientsMonth = new int[12];
        nrSales = 0;
        salesMonth = new int[12];
        billingMonth = new double[12];
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getNrClients() {
        return nrClients;
    }

    public int getNrSales(){
        return nrSales;
    }

    public int getClientsMonth(int month){
        return clientsMonth[month - 1];
    }

    public int[] getClientsMonth(){
        return clientsMonth;
    }

    public int getSalesMonth(int month){
        return salesMonth[month - 1];
    }

    public double[] getBillingMonth() {
        return billingMonth;
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    public void addSale(Sale s){
        nrSales++;
        salesMonth[s.getMonth() - 1]++;
        billingMonth[s.getMonth() - 1] += s.getPrice() * s.getUnits();

        IClientInfo ci = clients.get(s.getClient());
        if( ci == null ){
            nrClients++;
            clientsMonth[s.getMonth() - 1]++;
            ci = new ClientInfo();
            ci.addSale(s.getProduct(),s.getMonth(),s.getUnits(),s.getPrice());
            clients.put(s.getClient(),ci);
        }
        else {
            ci.addSale(s.getProduct(), s.getMonth(), s.getUnits(), s.getPrice());
            if( ci.getPurchased(s.getMonth()) - 1 == 0 )
                clientsMonth[s.getMonth() - 1]++;
        }
    }

    public List<IClient> getClients(){
        return clients.keySet().stream().map(IClient::clone).collect(Collectors.toList());
    }

    public AbstractMap.SimpleEntry<Integer,Integer> getClientSales(int month){
        return new AbstractMap.SimpleEntry<>(clientsMonth[month - 1], salesMonth[month - 1]);
    }

    public List<IClient> getClientsPurchasedMonth(int month){
        return clients.entrySet().stream()
                .filter(e -> e.getValue().getPurchased(month) > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<List<IProduct>> getClientProducts(IClient c){
        IClientInfo ci = clients.get(c);
        if( ci != null )
            return ci.getProducts();
        return null;
    }

    public AbstractMap.SimpleEntry<List<Integer>,List<Double>> getPurchasedSpentByClient(IClient c){
        IClientInfo ci = clients.get(c);
        if( ci != null )
            return ci.clientBuyingInfo();
        return null;
    }

    public List<List<IClient>> getProductClients(IProduct p){
        List<List<IClient>> c = new ArrayList<>();
        for(int i = 1; i <= 12; i++) c.add(new ArrayList<>());
        for(Map.Entry<IClient,IClientInfo> e : clients.entrySet()){
            List<Integer> purch = e.getValue().getProductPurchasedInfo(p);
            if(purch != null){
                for(int i = 0; i < 12; i++)
                    if (purch.get(i) > 0) c.get(i).add(e.getKey());
            }
        }
        return c;
    }

    public Map<String,Integer> getClientProdsQuant(IClient c){
        IClientInfo ci = clients.get(c);
        if( ci != null )
            return ci.getProdsQuant();
        return null;
    }

    public void getclientsMostBoughtProduct(IProduct p,TreeSet<ClientQuantBillings> cmbp){
        IClientProdInfo cpi;
        for(Map.Entry<IClient,IClientInfo> ci : clients.entrySet()){
            cpi = ci.getValue().getProductClientInfo(p);
            if(cpi != null){
                List<ClientQuantBillings> lc = cmbp.stream().filter(e -> e.getCodClient().equals(ci.getKey().getCode())).collect(Collectors.toList());
                if(lc.size() == 1) {
                    ClientQuantBillings c = lc.get(0);
                    c.addUnits(cpi.getUnits());
                    c.addBillings(cpi.getBilling());
                }
                else cmbp.add(new ClientQuantBillings(ci.getKey().getCode(),cpi.getUnits(),cpi.getBilling()));
            }
        }
    }

    public void addClientsProducts(ProdsUnitsClients puc){
        List<IProduct> prods = puc.getProducts();
        for(Map.Entry<IClient,IClientInfo> c : clients.entrySet()){
            for(IProduct prod : prods){
                if(c.getValue().productExist(prod)) {
                    puc.addProdClient(prod.getCode(),c.getKey().getCode());
                }
            }
        }
    }

    public List<AbstractMap.SimpleEntry<String,Double>> greatests3Buyers(){
        TreeMap<Double,String> g = new TreeMap<>();
        double f;
        for(Map.Entry<IClient,IClientInfo> c : clients.entrySet()){
            f = c.getValue().getAllMoneySpent();
            g.put(f,c.getKey().getCode());
        }
        List<AbstractMap.SimpleEntry<String,Double>> g3b = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Map.Entry<Double,String> e = g.pollLastEntry();
            g3b.add(new AbstractMap.SimpleEntry<>(e.getValue(),e.getKey()));
        }
        return g3b;
    }

    public void addBuyersProducts(BuyersProducts bc){
        for(Map.Entry<IClient,IClientInfo> c : clients.entrySet()){
            c.getValue().addBuyerProducts(c.getKey().getCode(),bc);
        }
    }

    public void getProdsUnits(ProductsUnitsClients puc){
        clients.forEach((key, value) -> puc.update(key.getCode(), value.getProdUnits()));
    }
}
