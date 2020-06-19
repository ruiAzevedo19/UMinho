package Model.Answer;

import java.util.*;

public class BuyersProducts {
    /* --- Variáveis de instancia ----------------------------------------------------------------------------------- */
    private TreeMap<String, Set<String>> bc;
    /* -------------------------------------------------------------------------------------------------------------- */

    public BuyersProducts(){
        bc = new TreeMap<>();
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    public void addBuyerClient(String c, String p){
        if(! bc.containsKey(c)) bc.put(c,new HashSet<>());
        bc.get(c).add(p);
    }

    public List<AbstractMap.SimpleEntry<String,Integer>> getXBuyers(int xBuyers){
        TreeMap<Integer,String> gxb = new TreeMap<>();
        bc.forEach((key, value) -> gxb.put(value.size(),key));
        List<AbstractMap.SimpleEntry<String,Integer>> bl = new ArrayList<>();
        int i = xBuyers;
        while(gxb.entrySet().size() > 0 && i > 0){
            Map.Entry<Integer,String> le = gxb.pollLastEntry();
            bl.add(new AbstractMap.SimpleEntry<>(le.getValue(),le.getKey()));
            i--;
        }
        return bl;
    }
}
