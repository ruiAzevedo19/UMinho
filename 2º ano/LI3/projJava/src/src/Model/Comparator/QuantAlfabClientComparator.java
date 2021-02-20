package Model.Comparator;

import Model.Answer.ClientQuantBillings;
import java.util.Comparator;

public class QuantAlfabClientComparator implements Comparator<ClientQuantBillings> {
    public int compare(ClientQuantBillings qb1, ClientQuantBillings qb2){
        if(qb1.getUnits() > qb2.getUnits()) return 1;
        else if(qb1.getUnits() == qb2.getUnits() && qb1.getCodClient().compareTo( qb2.getCodClient()) > 0 ) return 1;
        else if(qb1.getUnits() == qb2.getUnits() && qb1.getCodClient().compareTo( qb2.getCodClient()) == 0 ) return 0;
        else return -1;
    }
}
