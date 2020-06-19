package Model.Comparator;

import Model.Answer.QuantProd;

import java.util.Comparator;

public class QuantAlfabProductComparator implements Comparator<QuantProd> {
    public int compare(QuantProd qp1, QuantProd qp2){
        if(qp1.getPurchased() > qp2.getPurchased()) return 1;
        else if(qp1.getPurchased() == qp2.getPurchased() && qp1.getCodProd().compareTo( qp2.getCodProd()) > 0 ) return 1;
        else if(qp1.getPurchased() == qp2.getPurchased() && qp1.getCodProd().compareTo( qp2.getCodProd()) == 0 ) return 0;
        else return -1;
    }
}
