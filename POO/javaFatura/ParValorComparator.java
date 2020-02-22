
/**
 * Write a description of class ParValorComaprator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Comparator;

public class ParValorComparator implements Comparator<Par>
{
    public int compare(Par p1, Par p2){
        double v1 = p1.getTotalFaturado();
        double v2 = p2.getTotalFaturado();
        
        return p1.equals(p2) ? 0 : (v1 > v2 ? -1 : 1);
    }
}
