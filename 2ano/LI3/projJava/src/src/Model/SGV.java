package Model;

import Model.Answer.*;
import Model.Billing.Billing;
import Model.Billing.IBilling;
import Model.Branch.Branch;
import Model.Branch.IBranch;
import Model.Catalogs.ClogClients;
import Model.Catalogs.ClogProducts;
import Model.Catalogs.ICatalog;
import Model.Comparator.QuantAlfabClientComparator;
import Model.Comparator.QuantAlfabProductComparator;
import Model.Interface.IClient;
import Model.Interface.IProduct;
import Model.Interface.ISGV;
import Resources.Configuration;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SGV implements ISGV, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private ICatalog<IClient> clients;      /* Clientes do sistema                                                    */
    private ICatalog<IProduct> products;    /* Produtos do sistema                                                    */
    private IBilling billing;               /* Faturacao do sistema                                                   */
    private List<IBranch> branches;         /* Dados de cada filial do sistema                                        */
    private Configuration conf;             /* Classe de configuracao do sistema                                      */
    private Stats stats;                    /* Classe para dados estatisticos                                         */
    private boolean isLoaded;               /* flag que indica se as estruturas já foram carregadas                   */
    private boolean isStatsLoaded;          /* flag que indica se as stats foram carregadas                           */
    /* -------------------------------------------------------------------------------------------------------------- */

    public SGV(){
        conf = new Configuration();
        stats = new Stats(conf.getNrBranches());
        try{
            stats = stats.loadStatsFromFile(conf.getStatsPath());
            isStatsLoaded = true;
        }catch (Exception e){
            isStatsLoaded = false;
        }
        clients = new ClogClients(conf.getClientsDepth()   , conf.getClientsRange(),
                conf.getClientsMinRange(), conf.getClientsMaxRange());
        products = new ClogProducts(conf.getProductsDepth()   , conf.getProductsRange(),
                conf.getProductsMinRange(), conf.getProductsMaxRange());
        billing = new Billing(conf.getNrBranches());
        branches = new ArrayList<>(conf.getNrBranches());
        for(int i = 0; i < conf.getNrBranches(); i++)
            branches.add(new Branch());
        isLoaded = false;
    }

    public boolean isSGVLoaded(){
        return isLoaded;
    }

    public boolean isStatLoaded(){
        return isStatsLoaded;
    }

    public int getNrBranches(){
        return conf.getNrBranches();
    }

    /* --- ClogClients Methods -------------------------------------------------------------------------------------- */
    public void addClient(String code){
        clients.addElem(new Client(code));
    }

    public boolean isClientValid(String code){
        return clients.isValid(code);
    }

    public boolean containsClient(String code){
        return clients.contains(new Client(code));
    }

    /* --- ClogProducts Methods ------------------------------------------------------------------------------------- */
    public void addProduct(String code){
        products.addElem(new Product(code));
    }

    public boolean isProductValid(String code){
        return products.isValid(code);
    }

    public boolean containsProduct(String code){
        return products.contains(new Product(code));
    }

    /* --- Billing Methods ------------------------------------------------------------------------------------------ */
    public void addProductToBilling(String code){
        billing.addProduct(code);
    }

    public void addSaleToBilling(Sale s){
        billing.addSale(s);
    }

    /* --- Branch methods ------------------------------------------------------------------------------------------- */
    public void addSaleToBranch(Sale s){
        branches.get(s.getBranch() - 1).addSale(s);
    }

    /* --- Reading, Parsing, Saving --------------------------------------------------------------------------------- */
    public void loadSGVDefault() throws IOException{
        loadSGVFromFiles(conf.getClientsPath(),conf.getProductsPath(),conf.getSalesPath());
    }

    public void loadSGVFromFiles(String clientsPath, String productsPath, String salesPath) throws IOException{
        Parser parser = new Parser();

        parser.readClients(this, clientsPath);
        parser.readProducts(this,productsPath);
        parser.readSales(this,salesPath);

        populateStats();
        stats.writeStatsToFile(conf.getStatsPath());
        isLoaded = true;
        isStatsLoaded = true;
    }

    public void saveStateDefault(){
        saveState(conf.getObjectStreamPath());
    }

    public void saveState(String file) {
        if( file == null )
            file = conf.getObjectStreamPath();
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

    public ISGV loadStateDefault() throws IOException, ClassNotFoundException{
        isLoaded = true;
        isStatsLoaded = true;
        return loadState(conf.getObjectStreamPath());
    }

    public ISGV loadState(String file) throws IOException, ClassNotFoundException{
        isLoaded = true;
        isStatsLoaded = true;
        if( file == null )
            file = conf.getObjectStreamPath();
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ISGV isgv = (ISGV)ois.readObject();
        ois.close();
        return isgv;
    }

    /* --- Stats ---------------------------------------------------------------------------------------------------- */
    public Stats getStats(){
        return stats.clone();
    }

    public void setLastSaleFile(String file){
        stats.setLastFile(file);
    }

    public String getLastSaleFile(){
        return stats.getLastFile();
    }

    public void setVClients(int vclients){
        stats.setClients(vclients);
    }

    public void setIClients(int iclients){
        stats.setWrongClients(iclients);
    }

    public void setVProducts(int vproducts){
        stats.setProducts(vproducts);
    }

    public void setIProducts(int iproducts){
        stats.setWrongProducts(iproducts);
    }

    public void setVSales(int vsales){
        stats.setSales(vsales);
    }

    public void setISales(int isales){
        stats.setWrongSales(isales);
    }

    public void setPurchasedNull(int n){
        stats.setTotalSalesNull(n);
    }

    public int getVClients(){
        return stats.getClients();
    }

    public int getIClients(){
        return stats.getWrongClients();
    }

    public int getVProducts(){
        return stats.getProducts();
    }

    public int getIProducts(){
        return stats.getWrongProducts();
    }

    public int getVSales(){
        return stats.getSales();
    }

    public int getISales(){
        return stats.getWrongSales();
    }

    public int getPurchasedNull(){
        return stats.getTotalSalesNull();
    }

    public void populateStats(){
        stats.setTotalProductsBought((int)billing.getNumberProductsBought());
        stats.setTotalProductsNotBought(getProductsNeverBought().size());
        Set<IClient> c = new HashSet<>();
        for(IBranch b : branches)
            c.addAll(b.getClients());
        stats.setTotalBuyingClients(c.size());
        List<IClient> cli = clients.getElems();
        cli.removeAll(c);
        stats.setTotalClientsNotBuying(cli.size());
        stats.setTotalBilling(billing.getTotalBilling());
        stats.setTotalSalesMonth(billing.getTotalSales());
        for(int i = 0; i < conf.getNrBranches(); i++) {
            stats.setClientsBuyingMonth(i, branches.get(i).getClientsMonth());
            stats.setTotalBillingMonth(i,branches.get(i).getBillingMonth());
        }

    }

    /* --- Query 1 -------------------------------------------------------------------------------------------------- */

    public List<IProduct> getProductsNeverBought(){
        return billing.getProductsNeverBought();
    }

    /* --- Query 2 -------------------------------------------------------------------------------------------------- */

    public SalesClients getSalesClientsGlobalBranch(int month){
        SalesClients sc = new SalesClients();
        AbstractMap.SimpleEntry<Integer,Integer> salesClients;

        Set<IClient> set = new HashSet<>();
        int i = 0;
        for(IBranch b : branches){
            salesClients = b.getClientSales(month);
            sc.setClientsBranch(i,salesClients.getKey());
            sc.setSalesBranch(i,salesClients.getValue());
            set.addAll(b.getClientsPurchasedMonth(month));
            i++;
        }
        sc.setGlobalClients(set.size());
        sc.setGlobalSales(billing.getTotalSalesByMonth(month));
        return sc;
    }

    /* --- Query 3 -------------------------------------------------------------------------------------------------- */

    public PurchProdsSpent getClientBuyingInfo(IClient c){
        PurchProdsSpent pps = new PurchProdsSpent();

        AbstractMap.SimpleEntry<List<Integer>,List<Double>> info;
        List<Set<IProduct>> p = new ArrayList<>();
        List<List<IProduct>> ps;
        for(int i = 0; i < 12; i++)
            p.add(new HashSet<>());

        for(IBranch b : branches){
            info = b.getPurchasedSpentByClient(c);
            pps.setPurchased(info.getKey());
            pps.setSpent(info.getValue());
            ps = b.getClientProducts(c);
            for(int i = 0; i < 12; i++)
                p.get(i).addAll(ps.get(i));
        }
        List<Integer> np =  p.stream().mapToInt(Set::size).boxed().collect(Collectors.toList());
        pps.setProducts(np);

        return pps;
    }

    /* --- Query 4 -------------------------------------------------------------------------------------------------- */

    public PurchClientsBillings getProductBuyingInfo(IProduct p){
        PurchClientsBillings pcb = new PurchClientsBillings();

        AbstractMap.SimpleEntry<List<Integer>,List<Double>> info;
        List<Set<IClient>> c = new ArrayList<>();
        List<List<IClient>> cs;
        for(int i = 0; i < 12; i++)
            c.add(new HashSet<>());

        info = billing.prodPurchBilings(p);
        if(info == null) return null;

        pcb.setPurchased(info.getKey());
        pcb.setBillings(info.getValue());

        for(IBranch b : branches){
            cs = b.getProductClients(p);
            for(int i = 0; i < 12; i++)
                c.get(i).addAll(cs.get(i));
        }
        List<Integer> np =  c.stream().mapToInt(Set::size).boxed().collect(Collectors.toList());
        pcb.setClientes(np);

        return pcb;
    }

    /* --- Query 5 -------------------------------------------------------------------------------------------------- */

    public TreeSet<QuantProd> mostPurchasedProducts(IClient c){
        Map<String,Integer> m;
        TreeSet<QuantProd> t = new TreeSet<>(new QuantAlfabProductComparator().reversed());
        Map<String,Integer> cpqF = new TreeMap<>();
        for(IBranch b : branches){
            m = b.getClientProdsQuant(c);
            if(m != null){
                for(Map.Entry<String,Integer> e : m.entrySet()){
                    Integer q = cpqF.get(e.getKey());
                    if(q == null) cpqF.put(e.getKey(),e.getValue());
                    else cpqF.replace(e.getKey(), q + e.getValue());
                }
            }
        }
        cpqF.forEach((key, value) -> t.add(new QuantProd(key, value)));
        return t;
    }

    /* --- Query 6 -------------------------------------------------------------------------------------------------- */

    public Set<QuantProd> mostBoughtProducts(int xProds){
       ProductsUnitsClients puc = new ProductsUnitsClients();
       branches.forEach(b -> b.getProdsUnits(puc));
       return puc.getLimit(xProds);
    }

    /* --- Query 7 -------------------------------------------------------------------------------------------------- */

    public List<List<AbstractMap.SimpleEntry<String,Double>>> greatests3BuyersBraches(){
        List<List<AbstractMap.SimpleEntry<String,Double>>> g3bb = new ArrayList<>();
        for(IBranch b : branches) g3bb.add(b.greatests3Buyers());
        return g3bb;
    }

    /* --- Query 8 -------------------------------------------------------------------------------------------------- */

    public List<AbstractMap.SimpleEntry<String,Integer>> buyersBoughtMoreProducts(int xBuyers){
        BuyersProducts bc = new BuyersProducts();
        for(IBranch b : branches) b.addBuyersProducts(bc);
        return bc.getXBuyers(xBuyers);
    }

    /* --- Query 9 -------------------------------------------------------------------------------------------------- */

    public List<ClientQuantBillings> clientsMostBoughtProduct(IProduct p, int xClients){
        boolean e = billing.productExist(p);
        if(!e) return null;
        TreeSet<ClientQuantBillings> cmbp = new TreeSet<>(new QuantAlfabClientComparator().reversed());
        for(IBranch b : branches)
            b.getclientsMostBoughtProduct(p,cmbp);
        return cmbp.stream().limit(xClients).collect(Collectors.toList());
    }

    /* --- Query 10 ------------------------------------------------------------------------------------------------- */

    public Map<String,List<List<Double>>> prodTotalBillings(){
        return billing.getProdsBillingsMonthBranch();
    }
}

