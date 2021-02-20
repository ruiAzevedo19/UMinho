
/**
 * Write a description of class FaturaValorComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Comparator;

public class FaturaValorComparator implements Comparator<Fatura>
{
    public int compare(Fatura f1, Fatura f2){
        double v1 = f1.getValorDespesa();
        double v2 = f2.getValorDespesa();
        
        return f1.equals(f2) ? 0 : (v1 > v2 ? -1 : 1);
    }
}
