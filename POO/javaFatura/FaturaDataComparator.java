
/**
 * Write a description of class FaturaDataComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Comparator;

public class FaturaDataComparator implements Comparator<Fatura>
{
    public int compare(Fatura f1, Fatura f2){
        if (f1.equals(f2)) return 0;
        int res = f1.getDataDespesa().compareTo(f2.getDataDespesa());
        return res == 0 ? 1 : res;
    }
}
