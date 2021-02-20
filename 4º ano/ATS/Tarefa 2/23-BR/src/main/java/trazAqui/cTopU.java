package trazAqui;

import java.util.Comparator;

public class cTopU implements Comparator<Utilizadores> {
  // Comparator que ordena utilizadores de forma crescente de número de encomendas realizadas
  public int compare(Utilizadores u1, Utilizadores u2) {
    int c1 = u1.getEncomendasGuardadas().size();
    int c2 = u2.getEncomendasGuardadas().size();

    if (c1 < c2)
      return 1;
    if (c1 > c2)
      return -1;

    return 0;
  }
}