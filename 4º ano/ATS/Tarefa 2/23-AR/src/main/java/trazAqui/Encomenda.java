package trazAqui;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Encomenda implements Serializable {

  /* STARTING functions */

  private String codEncomenda;
  private String codUtilizador;
  private String codLoja;
  private float peso;
  private LocalDateTime data;
  private List<LinhaEncomenda> encomendas;

  public Encomenda() {
    this.codEncomenda = "";
    this.codUtilizador = "";
    this.codLoja = "";
    this.peso = 0;
    this.data = LocalDateTime.now();
    this.encomendas = new ArrayList<>();
  }

  public Encomenda(String codEncomenda, String codUtilizador, String codLoja, float peso, LocalDateTime data, List<LinhaEncomenda> encomendas) {
    this.codEncomenda = codEncomenda;
    this.codUtilizador = codUtilizador;
    this.codLoja = codLoja;
    this.peso = peso;
    this.data = data;
    this.encomendas = encomendas;
  }

  public Encomenda(Encomenda e) {
    this.codEncomenda = e.getCodEncomenda();
    this.codUtilizador = e.getCodUtilizador();
    this.codLoja = e.getCodLoja();
    this.peso = e.getPeso();
    this.data = e.getData();
    this.encomendas = e.getEncomendas();
  }


  /* GET functions */

  public String getCodEncomenda() {
    return this.codEncomenda;
  }

  public String getCodUtilizador() {
    return this.codUtilizador;
  }

  public String getCodLoja() {
    return this.codLoja;
  }

  public float getPeso() {
    return this.peso;
  }

  public LocalDateTime getData() {
    return this.data;
  }

  public List<LinhaEncomenda> getEncomendas() {
    return this.encomendas.stream().map(LinhaEncomenda::clone).collect(Collectors.toList());
  }


  /* SET functions */

  public void setCodEncomenda(String newCodEncomenda) {
    this.codEncomenda = newCodEncomenda;
  }

  public void setCodUtilizador(String newCodUtilizador) {
    this.codUtilizador = newCodUtilizador;
  }

  public void setCodLoja(String newCodLoja) {
    this.codLoja = newCodLoja;
  }

  public void setPeso(float newPeso) {
    this.peso = newPeso;
  }

  public void setData(LocalDateTime newData) {
    this.data = newData;
  }

  public void setEncomendas(List<LinhaEncomenda> newEncomendas) {
    this.encomendas = newEncomendas.stream().map(LinhaEncomenda::clone).collect(Collectors.toList());
  }


  /* OTHER functions */

  public Encomenda clone() {
    return new Encomenda(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;

    Encomenda e = (Encomenda) obj;

    return e.getCodEncomenda().equals(this.codEncomenda);
  }

  @Override
  public int hashCode() {
    return Objects.hash( codEncomenda, codUtilizador, codLoja, peso, data, encomendas );
  }

  public String toString() {

    return "Código da encomenda: " + this.codEncomenda + "\n" + "Código da loja: " + this.codLoja + "\n" + "Peso total: " + this.peso + "\n" + "Data: " + this.data + "\n" + "Produtos: " + this.encomendas;
  }

  public String toStringCSV() {
    StringBuilder result = new StringBuilder();
    StringBuilder sb = new StringBuilder();

    sb.append("Encomenda:");
    sb.append(this.codEncomenda).append(",");
    sb.append(this.codUtilizador).append(",");
    sb.append(this.codLoja).append(",");
    sb.append(this.peso).append(",");
    
    for (LinhaEncomenda le: this.encomendas)
      sb.append(le.toStringCSV());

    result.append(sb.substring(0, sb.length() - 1));

    return result.toString();
  }


  /* REQUIRED functions */

  // Método que calcula a quantidade total de produtos numa encomenda
  public float quantidadeProdutosTotal() {
    float total = 0;

    for (LinhaEncomenda le: this.encomendas)
      total += le.getQuantidade();

    return total;
  }

  // Método que calcula a variedade de produtos numa encomenda
  public int numeroProdutos() {
    int total = 0;

    for (int i = 0; i < this.encomendas.size(); i++)
      total += 1;

    return total;
  }

  // Método que calcula o custo total de uma encomenda
  public float custoEncomendaTotal() {
    int total = 0;

    for (LinhaEncomenda le: this.encomendas)
      total += (le.getValorUnitario() * le.getQuantidade());

    return total;
  }

  // Método que calcula o tempo que demora a preparar uma encomenda
  public double tempoEncomenda(Encomenda e) {
    double tempoNormal = e.quantidadeProdutosTotal();
    double tempoExtra = 0.03 * e.numeroProdutos() * e.getPeso();

    return (tempoNormal + tempoExtra);
  }
}