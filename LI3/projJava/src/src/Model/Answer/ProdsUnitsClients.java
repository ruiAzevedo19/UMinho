package Model.Answer;

import Model.Comparator.QuantAlfabProductComparator;
import Model.Interface.IProduct;
import Model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProdsUnitsClients {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private TreeMap<QuantProd,Set<String>> puc;
    /* -------------------------------------------------------------------------------------------------------------- */

    public ProdsUnitsClients(){
        puc =  new TreeMap<>(new QuantAlfabProductComparator().reversed());
    }

    /* --- Funcionalidades ------------------------------------------------------------------------------------------ */

    public List<IProduct> getProducts(){
        return puc.keySet().stream().map(a -> new Product(a.getCodProd())).collect(Collectors.toList());
    }

    public void addProd(QuantProd qp){
        puc.put(qp,new TreeSet<>());
    }

    public void addProdClient(String prod, String c){
        for(Map.Entry<QuantProd,Set<String>> e : puc.entrySet()){
            if(e.getKey().getCodProd().equals(prod)){ e.getValue().add(c); break;}
        }
    }

    public Map<QuantProd,Set<String>> limitProdsUnitsClients(int xProds){
        TreeMap<QuantProd,Set<String>> qpcLimited = new TreeMap<>(new QuantAlfabProductComparator().reversed());
        int i = xProds;
        while(puc.entrySet().size() > 0 && i > 0){
            Map.Entry<QuantProd,Set<String>> le = puc.pollFirstEntry();
            qpcLimited.put(le.getKey(),le.getValue());
            i--;
        }
        return puc = qpcLimited;
    }

    public Map<QuantProd,Integer> getXProdsDistinctClients(){
        Map<QuantProd,Integer> qs = new TreeMap<>(new QuantAlfabProductComparator().reversed());
        puc.forEach((key, value) -> qs.put(key, value.size()));
        return qs;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Querie 6\n\n");
        for(Map.Entry<QuantProd,Set<String>> m : puc.entrySet()){
            s.append("\n" + m.getKey().getCodProd());
            s.append("\n" + "Units -> " + m.getKey().getPurchased());
            s.append("\n" + "Numero de Clientes -> " + m.getValue().size() + "\nClientes\n");
            for(String strC : m.getValue()){s.append(strC + " ");}
        }
        return s.toString();
    }
}

