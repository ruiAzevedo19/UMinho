package Model.Answer;

import java.util.List;
import java.util.Map;

public class ProdTotalBillings {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Map<String,List<List<Double>>> ptb;
    /* -------------------------------------------------------------------------------------------------------------- */

    public ProdTotalBillings(Map<String,List<List<Double>>> p){
        ptb = p;
    }

    /* --- Funcionalidades ------------------------------------------------------------------------------------------ */

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Querie 10\n\n");
        for(Map.Entry<String,List<List<Double>>> m : ptb.entrySet()){
            s.append("\n" + m.getKey());
            s.append("\n" + "Filial 1 -> " + m.getValue().get(0));
            s.append("\n" + "Filial 2 -> " + m.getValue().get(1));
            s.append("\n" + "Filial 2 -> " + m.getValue().get(2) + "\n");
        }
        return s.toString();
    }
}
