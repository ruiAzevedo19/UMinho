package trazAqui;

import java.util.Comparator;

public class MelhorCusto implements Comparator<PlataformaEntrega> {
  // Comparator que ordena plataformas de entrega de forma decrescente de preço por km
  public int compare(PlataformaEntrega pe1, PlataformaEntrega pe2) {
    Transportadoras t1 = (Transportadoras) pe1;
    Transportadoras t2 = (Transportadoras) pe2;

    double p1 = t1.getPrecoPorKM();
    double p2 = t2.getPrecoPorKM();
    
    if (p1 < p2)
      return -1;
    if (p1 > p2)
      return 1;

    return 0;
  }
}