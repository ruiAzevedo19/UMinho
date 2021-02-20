package trazAqui;

import java.util.Map;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lojas implements Serializable {

  /* STARTING functions */

  private String codigo;
  private String password;
  private String nome;
  private Ponto GPS;
  private int numeroEncomendas;
  private Map<String, Encomenda> registoEncomendas;

  public Lojas() {
    this.codigo = new String();
    this.password = new String();
    this.nome = new String();
    this.GPS = new Ponto();
    this.numeroEncomendas = 0;
    this.registoEncomendas = new HashMap<>();
  }

  public Lojas(String codigo, String password, String nome, Ponto GPS, int numeroEncomendas, Map<String, Encomenda> registoEncomendas) {
    this.codigo = codigo;
    this.password = password;
    this.nome = nome;
    this.GPS = GPS.clone();
    this.numeroEncomendas = numeroEncomendas;
    setRegistoEncomendas(registoEncomendas);
  }

  public Lojas(Lojas l) {
    this.codigo = l.getCodigo();
    this.password = l.getPW();
    this.nome = l.getNome();
    this.GPS = l.getGPS();
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

  public Ponto getGPS() {
    return this.GPS.clone();
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

  public void setGPS(Ponto newGPS) {
    this.GPS = newGPS.clone();
  }

  public void setNumeroEncomendas(int newNumeroEncomendas) {
    this.numeroEncomendas = newNumeroEncomendas;
  }

  public void setRegistoEncomendas(Map<String, Encomenda> newRegistoEncomendas) {
    this.registoEncomendas = new HashMap<>();

    newRegistoEncomendas.entrySet().forEach(e -> this.registoEncomendas.put(e.getKey(), e.getValue().clone()));
  }


  /* OTHER functions */

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
    StringBuilder sb = new StringBuilder();

    sb.append("Código: ").append(this.codigo).append("\n");
    sb.append("Nome: ").append(this.nome).append("\n");
    sb.append("GPS: ").append(this.GPS).append("\n");
    sb.append("Número de pessoas em fila: ").append(this.numeroEncomendas).append("\n");

    return sb.toString();
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append("Loja:");
    sb.append(this.codigo).append(",");
    sb.append(this.nome).append(",");
    sb.append(this.GPS.getX()).append(",");
    sb.append(this.GPS.getY());

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