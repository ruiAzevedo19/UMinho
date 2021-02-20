package trazAqui;

import java.io.Serializable;

public class LinhaEncomenda implements Serializable {

  /* STARTING functions */

  private String codProduto;
  private String descricao;
  private float quantidade;
  private float valorUnitario;

  public LinhaEncomenda() {
    this.codProduto = new String();
    this.descricao = new String();
    this.quantidade = 0;
    this.valorUnitario = 0;
  }

  public LinhaEncomenda(String codProduto, String descricao, float quantidade, float valorUnitario) {
    this.codProduto = codProduto;
    this.descricao = descricao;
    this.quantidade = quantidade;
    this.valorUnitario = valorUnitario;
  }

  public LinhaEncomenda(LinhaEncomenda le) {
    this.codProduto = le.getCodProduto();
    this.descricao = le.getDescricao();
    this.quantidade = le.getQuantidade();
    this.valorUnitario = le.getValorUnitario();
  }


  /* GET functions */

  public String getCodProduto() {
    return this.codProduto;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public float getQuantidade() {
    return this.quantidade;
  }

  public float getValorUnitario() {
    return this.valorUnitario;
  }


  /* SET functions */

  public void setCodProduto(String newCodProduto) {
    this.codProduto = newCodProduto;
  }

  public void setDescricao(String newDescricao) {
    this.descricao = newDescricao;
  }

  public void setQuantidade(float newQuantidade) {
    this.quantidade = newQuantidade;
  }

  public void setValorUnitario(float newValorUnitario) {
    this.valorUnitario = newValorUnitario;
  }

  
  /* OTHER functions */

  public LinhaEncomenda clone() {
    return new LinhaEncomenda(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;

    LinhaEncomenda le = (LinhaEncomenda) obj;

    return le.getCodProduto().equals(this.codProduto);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Código: ").append(this.codProduto).append("\n");
    sb.append("Descrição: ").append(this.descricao).append("\n");
    sb.append("Quantidade: ").append(this.quantidade).append("\n");
    sb.append("Valor unitário: ").append(this.valorUnitario).append("\n");

    return sb.toString();
  }

  public String toStringCSV() {
    StringBuilder sb = new StringBuilder();

    sb.append(this.codProduto).append(",");
    sb.append(this.descricao).append(",");
    sb.append(this.quantidade).append(",");
    sb.append(this.valorUnitario).append(",");

    return sb.toString();
  }
}