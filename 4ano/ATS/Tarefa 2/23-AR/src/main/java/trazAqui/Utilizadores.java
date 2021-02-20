package trazAqui;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Utilizadores implements Serializable {

  /* STARTING functions */

  private String codigo;
  private String nome;
  private String email;
  private String pw;
  private Ponto gps;
  private Map<String, Encomenda> encomendasGuardadas;

  public Utilizadores() {
    this.codigo = "";
    this.nome = "";
    this.email = "";
    this.pw = "";
    this.gps = new Ponto();
    this.encomendasGuardadas = new HashMap<>();
  }

  public Utilizadores(String codigo, String nome, String email, String pw, Ponto gps, Map<String, Encomenda> encomendasGuardadas) {
    this.codigo = codigo;
    this.nome = nome;
    this.email = email;
    this.pw = pw;
    this.gps = gps.clone();
    setEncomendasGuardadas(encomendasGuardadas);
  }

  public Utilizadores(Utilizadores u) {
    this.codigo = u.getCodigo();
    this.nome = u.getNome();
    this.email = u.getEmail();
    this.pw = u.getPW();
    this.gps = u.getGps();
    setEncomendasGuardadas(u.getEncomendasGuardadas());
  }


  /* GET functions */

  public String getCodigo() {
    return this.codigo;
  }

  public String getNome() {
    return this.nome;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPW() {
    return this.pw;
  }

  public Ponto getGps() {
    return this.gps.clone();
  }

  public Map<String, Encomenda> getEncomendasGuardadas() {
    Map<String, Encomenda> newEncomendasGuardadas = new HashMap<>();

    for (Map.Entry<String, Encomenda> e: this.encomendasGuardadas.entrySet())
      newEncomendasGuardadas.put(e.getKey(), e.getValue().clone());
    
    return newEncomendasGuardadas;
  }


  /* SET functions */

  public void setCodigo(String newCodigo) {
    this.codigo = newCodigo;
  }

  public void setNome(String newNome) {
    this.nome = newNome;
  }

  public void setEmail(String newEmail) {
    this.email = newEmail;
  }

  public void setPW(String newPW) {
    this.pw = newPW;
  }

  public void setGps(Ponto newGPS) {
    this.gps = newGPS.clone();
  }

  public void setEncomendasGuardadas(Map<String, Encomenda> newEncomendasGuardadas) {
    this.encomendasGuardadas = new HashMap<>();

    newEncomendasGuardadas.entrySet().forEach(e -> this.encomendasGuardadas.put(e.getKey(), e.getValue().clone()));
  }


  /* OTHER functions */

  public Utilizadores clone() {
    return new Utilizadores(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    
    Utilizadores u = (Utilizadores) obj;
    
    return super.equals(u);
  }

  @Override
  public int hashCode() {
    return Objects.hash( codigo, nome, email, pw, gps, encomendasGuardadas );
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Código: ").append(this.codigo).append("\n");
    sb.append("Nome: ").append(this.nome).append("\n");
    sb.append("Email: ").append(this.email).append("\n");
    sb.append("GPS: ").append(this.gps ).append("\n");
    
    return sb.toString();
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Utilizador:");
    sb.append(this.codigo).append(",");
    sb.append(this.nome).append(",");
    sb.append(this.gps.getX()).append(",");
    sb.append(this.gps.getY());

    return sb.toString();
  }
  

  /* REQUIRED functions */

  // Método que adiciona uma encomenda ao registo de encomendas do cliente
  public void adicionarEncomenda(Encomenda e) {
    this.encomendasGuardadas.put(e.getCodEncomenda(), e.clone());
  }

  // Método que devolve o histórico de encomendas até à data
  public List<Encomenda> historicoEncomenda() {
    return this.encomendasGuardadas.values().stream().map(Encomenda::clone).collect(Collectors.toList());
  }
}