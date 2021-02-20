package trazAqui;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Transportadoras extends PlataformaEntrega {

  /* STARTING functions */

  private String nif;
  private double precoPorKM;
  private int capacidade;

  public Transportadoras() {
    super();
    this.nif = "";
    this.precoPorKM = 0;
    this.capacidade = 0;
  }

  public Transportadoras(String codigo, String password, String nome, Ponto gps, double raio, boolean transporteMedicamentos, boolean disponivel, List<Integer> classificacoes, Map<String, Encomenda> encomendasGuardadas, String nif, double precoPorKM, int capacidade) {
    super(codigo, password, nome, gps, raio, transporteMedicamentos, disponivel, classificacoes, encomendasGuardadas);
    this.nif = nif;
    this.precoPorKM = precoPorKM;
    this.capacidade = capacidade;
  }

  public Transportadoras(Transportadoras t) {
    super(t);
    this.nif = t.getNif();
    this.precoPorKM = t.getPrecoPorKM();
    this.capacidade = t.getCapacidade();
  }

  
  /* GET functions */

  public String getNif() {
    return this.nif;
  }

  public double getPrecoPorKM() {
    return this.precoPorKM;
  }

  public int getCapacidade() {
    return this.capacidade;
  }


  /* SET functions */

  public void setNif(String newNIF) {
    this.nif = newNIF;
  }

  public void setPrecoPorKM(double newPrecoPorKM) {
    this.precoPorKM = newPrecoPorKM;
  }

  public void setCapacidade(int newCapacidade) {
    this.capacidade = newCapacidade;
  }


  /* OTHER functions */

  public Transportadoras clone() {
    return new Transportadoras(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash( super.hashCode(), nif, precoPorKM, capacidade );
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    
    Transportadoras t = (Transportadoras) obj;

    return super.equals(t) && t.getNif().equals(this.nif );
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(super.toString());
    sb.append("NIF: ").append(this.nif ).append("\n");
    sb.append("Preco por KM: ").append(this.precoPorKM).append("\n");
    sb.append("Capacidade : ").append(this.capacidade).append("\n");
    sb.append("Classificacão: ").append(super.mediaClassificacao(super.getClassificacoes())).append("\n");
    sb.append("Disponivel: ").append(super.getDisponivel()).append("\n");

    return sb.toString();
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Transportadora:");
    sb.append(super.getCodigo()).append(",");
    sb.append(super.getNome()).append(",");
    sb.append(super.getGps().getX()).append(",");
    sb.append(super.getGps().getY()).append(",");
    sb.append(this.nif ).append(",");
    sb.append(super.getRaio()).append(",");
    sb.append(this.precoPorKM);

    return sb.toString();
  }


  /* REQUIRED functions */

  // Método que devolve o tipo específico de plataforma de entrega
  public String tipoPlataformaEntrega() {
    return "Transportadora";
  }
}