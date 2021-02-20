package model;

import java.io.Serializable;
import java.util.Objects;

public class LinhaEncomenda implements Serializable, Comparable<LinhaEncomenda> {
    private String codProduto;
    private String descricao;
    private double quant;
    private double preco;

    public LinhaEncomenda(){
        this.codProduto = "";
        this.descricao = "";
        this.quant = 0;
        this.preco = 0;
    }

    public LinhaEncomenda(String codProduto, String descricao, double quant, double preco) {
        this.codProduto = codProduto;
        this.descricao = descricao;
        this.quant = quant;
        this.preco = preco;
    }

    public LinhaEncomenda(LinhaEncomenda outro){
        this.codProduto = outro.getCodProduto();
        this.descricao = outro.getDescricao();
        this.quant = outro.getQuant();
        this.preco = outro.getPreco();
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQuant() {
        return quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaEncomenda that = (LinhaEncomenda) o;
        return quant == that.quant &&
                Double.compare(that.preco, preco) == 0 &&
                Objects.equals(codProduto, that.codProduto) &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProduto, descricao, quant, preco);
    }

    @Override
    public LinhaEncomenda clone(){
        return new LinhaEncomenda(this);
    }

    @Override
    public String toString() {
        return "Model.LinhaEncomenda{" +
                "cod_Produto='" + codProduto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quant=" + quant +
                ", preco=" + preco +
                '}';
    }

    @Override
    public int compareTo(LinhaEncomenda linhaEncomenda) {
        return this.getCodProduto().compareTo(linhaEncomenda.getCodProduto());
    }
}
