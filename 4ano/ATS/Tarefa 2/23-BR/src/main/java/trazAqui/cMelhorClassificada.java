package trazAqui;

import java.util.Comparator;

public class cMelhorClassificada implements Comparator<PlataformaEntrega> {
  // Comparator que ordena plataformas de entrega de forma crescente de classificação média
  public int compare(PlataformaEntrega pe1, PlataformaEntrega pe2) {
    double c1 = pe1.mediaClassificacao(pe1.getClassificacoes());
    double c2 = pe2.mediaClassificacao(pe2.getClassificacoes());

    if (c1 < c2)
      return 1;
    if (c1 > c2)
      return -1;

    return 0;
  }
}