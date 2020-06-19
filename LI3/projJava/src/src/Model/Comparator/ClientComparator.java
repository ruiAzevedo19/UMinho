package Model.Comparator;

import Model.Interface.IClient;

import java.util.Comparator;

public class ClientComparator implements Comparator<IClient> {
    public int compare(IClient c1, IClient c2){
        return c1.getCode().compareTo(c2.getCode());
    }
}
