package trazAqui;

import java.util.*;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Lojas implements Serializable {

  /* STARTING functions */

  private String codigo;
  private String password;
  private String nome;
  private Ponto gps;
  private int numeroEncomendas;
  private Map<String, Encomenda> registoEncomendas;

  public Lojas() {
    this.codigo = "";
    this.password = "";
    this.nome = "";
    this.gps = new Ponto();
    this.numeroEncomendas = 0;
    this.registoEncomendas = new HashMap<>();
  }

  public Lojas(String codigo, String password, String nome, Ponto gps, int numeroEncomendas, Map<String, Encomenda> registoEncomendas) {
    this.codigo = codigo;
    this.password = password;
    this.nome = nome;
    this.gps = gps.clone();
    this.numeroEncomendas = numeroEncomendas;
    setRegistoEncomendas(registoEncomendas);
  }

  public Lojas(Lojas l) {
    this.codigo = l.getCodigo();
    this.password = l.getPW();
    this.nome = l.getNome();
    this.gps = l.getGps();
    this.numeroEncomendas = l.getNumeroEncomendas();
    setRegistoEncomendas(l.getRegistoEncomendas());
  }


  /* GET functions */

  public String getCodigo() {
    return this.codigo;
  }

  public String getPW() {
    return this.password;
  }

  public String getNome() {
    return this.nome;
  }

  public Ponto getGps() {
    return this.gps.clone();
  }

  public int getNumeroEncomendas() {
    return this.numeroEncomendas;
  }

  public Map<String, Encomenda> getRegistoEncomendas() {
    Map<String, Encomenda> newRegistoEncomendas = new HashMap<>();

    for (Map.Entry<String, Encomenda> e: this.registoEncomendas.entrySet())
      newRegistoEncomendas.put(e.getKey(), e.getValue().clone());

    return newRegistoEncomendas;
  }


  /* SET functions */

  public void setCodigo(String newCodigo) {
    this.codigo = newCodigo;
  }

  public void setPW(String newPassword) {
    this.password = newPassword;
  }

  public void setNome(String newNome) {
    this.nome = newNome;
  }

  public void setGps(Ponto newGPS) {
    this.gps = newGPS.clone();
  }

  public void setNumeroEncomendas(int newNumeroEncomendas) {
    this.numeroEncomendas = newNumeroEncomendas;
  }

  public void setRegistoEncomendas(Map<String, Encomenda> newRegistoEncomendas) {
    this.registoEncomendas = new HashMap<>();

    newRegistoEncomendas.forEach( (key, value) -> this.registoEncomendas.put( key, value.clone() ) );
  }


  /* OTHER functions */

  @Override
  public int hashCode() {
    return Objects.hash( codigo, password, nome, gps, numeroEncomendas, registoEncomendas );
  }

  public Lojas clone() {
    return new Lojas(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    
    Lojas l = (Lojas) obj;

    return l.getCodigo().equals(this.codigo);
  }

  public String toString() {

    return "Código: " + this.codigo + "\n" + "Nome: " + this.nome + "\n" + "GPS: " + this.gps + "\n" + "Número de pessoas em fila: " + this.numeroEncomendas + "\n";
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Loja:");
    sb.append(this.codigo).append(",");
    sb.append(this.nome).append(",");
    sb.append(this.gps.getX()).append(",");
    sb.append(this.gps.getY());

    return sb.toString();
  }

  
  /* REQUIRED functions */

  // Método que adiciona uma encomenda ao registo de encomendas da loja
  public void addEncomenda(Encomenda e) {
    this.registoEncomendas.put(e.getCodEncomenda(), e.clone());
  }

  // Método que devolve o histórico de encomendas até à data
  public List<Encomenda> historicoL() {
    return this.registoEncomendas.values().stream().map(Encomenda::clone).collect(Collectors.toList());
  }

  // Método que devolve o historico de encomendas entre datas
  public List<Encomenda> historicoLdata(LocalDateTime dtinicio, LocalDateTime dtfinal) {
    List<Encomenda> nEncomendas = new ArrayList<>();

    for (Encomenda e: this.registoEncomendas.values())
      if (e.getData().isAfter(dtinicio) && e.getData().isBefore(dtfinal))
        nEncomendas.add(e.clone());

    return nEncomendas;
  }
}