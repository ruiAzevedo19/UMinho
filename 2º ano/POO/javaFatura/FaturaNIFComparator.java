
/**
 * Write a description of class FaturaComparator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.Comparator;

public class FaturaNIFComparator implements Comparator<Fatura>
{
    public int compare(Fatura f1, Fatura f2){
        String nif_f1 = f1.getNifEmitente();
        String nif_f2 = f2.getNifEmitente();
        
        return nif_f1.compareTo(nif_f2);
    }
}
