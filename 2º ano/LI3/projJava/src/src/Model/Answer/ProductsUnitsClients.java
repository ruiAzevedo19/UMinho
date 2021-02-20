package Model.Answer;

import Model.Comparator.QuantAlfabProductComparator;

import java.util.*;
import java.util.stream.Collectors;

public class ProductsUnitsClients {
    /* --- Variaveis de instancia ----------------------------------------------------------------------------------- */
    private Map<String, AbstractMap.SimpleEntry<Set<String>,Integer>>  puc;
    /* -------------------------------------------------------------------------------------------------------------- */

    public ProductsUnitsClients(){
        puc = new HashMap<>();
    }

    /* --- Funcionality --------------------------------------------------------------------------------------------- */

    public void update(String client, Map<String,Integer> prods){
        for(Map.Entry<String,Integer> e : prods.entrySet()){
            if(puc.containsKey(e.getKey())){
                puc.get(e.getKey()).getKey().add(client);
                puc.get(e.getKey()).setValue(puc.get(e.getKey()).getValue() + e.getValue());
            }
            else{
                Set<String> s = new TreeSet<>();
                s.add(client);
                puc.put(e.getKey(), new AbstractMap.SimpleEntry<>(s,e.getValue()));
            }
        }
    }

    public Set<QuantProd> getLimit(int x){
        return puc.entrySet().stream()
                          .map(e -> new QuantProd(e.getKey(),e.getValue().getValue(),e.getValue().getKey().size()))
                          .sorted(new QuantAlfabProductComparator().reversed())
                          .limit(x)
                          .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
