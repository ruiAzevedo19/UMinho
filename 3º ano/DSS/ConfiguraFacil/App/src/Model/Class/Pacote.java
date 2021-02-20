package Model.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Pacote{

    private String cod;
    private String designacao;
    private double preco;
    private double desconto;
    private List<String> componentes;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Método construtor não parametrizado
     */
    public Pacote(){
        this.cod = "";
        this.designacao = "";
        this.preco = 0;
        this.desconto = 0;
        this.componentes = new ArrayList<>();
    }

    /**
     * Método construtor parametrizado
     */
    public Pacote(String c, String d, double p, double des, List<String> comp){
        this.preco = p;
        this.cod = c;
        this.designacao = d;
        this.desconto = des;
        this.componentes = new ArrayList<>(comp);
    }

    /**
     * Método construtor por copia
     */
    public Pacote(Pacote p){
        this.cod = p.getCod();
        this.designacao = p.getDesignacao();
        this.preco = p.getPreco();
        this.desconto = p.getDesconto();
        this.componentes = p.getComponentes();
    }

    /**
     * Método clone
     */
    public Pacote clone(){
        return new Pacote(this);
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public String getCod(){
        return this.cod;
    }

    public String getDesignacao() {
        return this.designacao;
    }

    public double getPreco() {
        return this.preco;
    }

    public double getDesconto() {
        return this.desconto;
    }

    public List<String> getComponentes() {
        return this.componentes.stream().collect(Collectors.toList());
    }


    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes.stream().collect(Collectors.toList());
    }

    /** Outros metodos ----------------------------------------------------------------------------------------------**/

    /**
     * Método equals
     */
    public boolean equals(Object o){
        if (this == o)
            return true;
        if(this.getClass()!= o.getClass() || o == null)
            return false;
        Pacote p = (Pacote) o;

        return this.cod.equals(p.getCod()) && this.designacao.equals(p.getDesignacao()) &&
               this.preco == p.getPreco() && this.desconto == p.getDesconto();
    }
}