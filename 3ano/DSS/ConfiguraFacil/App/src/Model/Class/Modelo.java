package Model.Class;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Modelo{
    private String cod;
    private String designacao;
    private double precoBase;
    private List<String> componentes;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Construtor não parametrizado
     */
    public Modelo(){
        this.cod = "";
        this.designacao  = "";
        this.precoBase   = 0;
        this.componentes = new ArrayList<>();
    }

    /**
     * Construtor parametrizado
     */
    public Modelo(String cod, String designacao, double precoBase, List<String> componentes){
        this.cod = cod;
        this.designacao  = designacao;
        this.precoBase   = precoBase;
        this.componentes = componentes.stream().collect(Collectors.toList());
    }

    /**
     * Construtor por copia
     */
    public Modelo(Modelo m){
        this.cod = m.getCod();
        this.designacao  = m.getDesignacao();
        this.precoBase   = m.getPrecoBase();
        this.componentes = m.getComponentes();
    }

    /**
     * Método clone
     */
    public Modelo clone(){
        return new Modelo(this);
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public String getCod() {
        return this.cod;
    }

    public String getDesignacao() {
        return this.designacao;
    }

    public double getPrecoBase() {
        return this.precoBase;
    }

    public List<String> getComponentes() {
        return this.componentes.stream().collect(Collectors.toList());
    }

    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setCod(final String cod) {
        this.cod = cod;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes.stream().collect(Collectors.toList());
    }

    /** outros metodos ----------------------------------------------------------------------------------------------**/

    /**
     * Adicionar componente
     */
    public void addComp(Componente c){
        this.componentes.add(c.getCodigo());
    }

    /**
     * Remover componente
     */
    public void removeComp(Componente c){
        this.componentes.remove(c.getCodigo());
    }

    /**
     * Método equals
     */
    public boolean equals(Object o) {
        if( this == o )
            return true;
        if( this.getClass() != o.getClass() || o == null )
            return false;
        Modelo m = (Modelo) o;

        return this.designacao.equals(m.getDesignacao()) && this.precoBase == m.getPrecoBase();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + this.getCod());
        sb.append("\nDescricao: " + this.getDesignacao());
        sb.append("\nPreco: " + this.getPrecoBase());
        sb.append("\n");
        return sb.toString();
    }
}