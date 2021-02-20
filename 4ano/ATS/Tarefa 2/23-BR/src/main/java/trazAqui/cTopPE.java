package trazAqui;

import java.util.Comparator;

public class cTopPE implements Comparator<PlataformaEntrega> {
  // Comparator que ordena plataformas de entrega de forma crescente de número entregas realizadas
  public int compare(PlataformaEntrega pe1, PlataformaEntrega pe2) {
    int c1 = pe1.getEncomendasGuardadas().size();
    int c2 = pe2.getEncomendasGuardadas().size();

    if (c1 < c2)
      return 1;
    if (c1 > c2)
      return -1;

    return 0;
  }
}