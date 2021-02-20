package Model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class LinhaEncomenda implements Serializable, Comparable<LinhaEncomenda> {
    private String cod_Produto;
    private String descricao;
    private double quant;
    private double preco;

    public LinhaEncomenda(){
        this.cod_Produto = "";
        this.descricao = "";
        this.quant = 0;
        this.preco = 0;
    }

    public LinhaEncomenda(String cod_Produto, String descricao, double quant, double preco) {
        this.cod_Produto = cod_Produto;
        this.descricao = descricao;
        this.quant = quant;
        this.preco = preco;
    }

    public LinhaEncomenda(LinhaEncomenda outro){
        this.cod_Produto = outro.getCod_Produto();
        this.descricao = outro.getDescricao();
        this.quant = outro.getQuant();
        this.preco = outro.getPreco();
    }

    public String getCod_Produto() {
        return cod_Produto;
    }

    public void setCod_Produto(String cod_Produto) {
        this.cod_Produto = cod_Produto;
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
                Objects.equals(cod_Produto, that.cod_Produto) &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public LinhaEncomenda clone(){
        return new LinhaEncomenda(this);
    }

    @Override
    public String toString() {
        return "Model.LinhaEncomenda{" +
                "cod_Produto='" + cod_Produto + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quant=" + quant +
                ", preco=" + preco +
                '}';
    }

    @Override
    public int compareTo(LinhaEncomenda linhaEncomenda) {
        return this.getCod_Produto().compareTo(linhaEncomenda.getCod_Produto());
    }
}
