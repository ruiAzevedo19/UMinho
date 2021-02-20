package trazAqui;

import java.util.Map;
import java.util.List;

public class Voluntarios extends PlataformaEntrega {

  /* STARTING functions */

  public Voluntarios() {
    super();
  }

  public Voluntarios(String codigo, String password, String nome, Ponto gps, double raio, boolean transporteMedicamentos, boolean disponivel, List<Integer> classificacoes, Map<String, Encomenda> encomendasGuardadas) {
    super(codigo, password, nome, gps, raio, transporteMedicamentos, disponivel, classificacoes, encomendasGuardadas);
  }

  public Voluntarios(Voluntarios v) {
    super(v);
  }


  /* OTHER functions */

  public Voluntarios clone() {
    return new Voluntarios(this);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    
    Voluntarios v = (Voluntarios) obj;

    return super.equals(v);
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Voluntario:");
    sb.append(super.getCodigo()).append(",");
    sb.append(super.getNome()).append(",");
    sb.append(super.getGps().getX()).append(",");
    sb.append(super.getGps().getY()).append(",");
    sb.append(super.getRaio());

    return sb.toString();
  }


  /* REQUIRED functions */

  // Método que devolve o tipo específico de plataforma de entrega
  public String tipoPlataformaEntrega() {
    return "Voluntario";
  }
}