package Model.Comparator;

import Model.Interface.IProduct;

import java.util.Comparator;

public class ProductComparator implements Comparator<IProduct> {
    public int compare(IProduct p1, IProduct p2){
        return p1.getCode().compareTo(p2.getCode());
    }
}
