package Model.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Componente {

    private String codigo;
    private String descricao;
    private double preco;
    private int stock;
    private String tipo;
    private List<String> incompativeis;
    private List<String> obrigatorios;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Contrutor não parametrizado
     */
    public Componente(){
        this.codigo = "";
        this.descricao = "";
        this.stock = 0;
        this.preco = 0.0;
        this.tipo = "";
        this.incompativeis = new ArrayList<>();
        this.obrigatorios = new ArrayList<>();
    }

    /**
     * Contrutor parametrizado
     */

    public Componente (String c, String d, int s, double p, String cat, List<String> i, List<String> o){
        this.codigo = c;
        this.descricao = d;
        this.stock = s;
        this.preco = p;
        this.tipo = cat;
        this.incompativeis = i.stream().collect(Collectors.toList());
        this.obrigatorios  = o.stream().collect(Collectors.toList());
    }

    /**
     * Contrutor por copia
     */
    public Componente(Componente c){
        this.codigo        = c.getCodigo();
        this.descricao     = c.getDescricao();
        this.stock         = c.getStock();
        this.preco         = c.getPreco();
        this.tipo          = c.getTipo();
        this.incompativeis = c.getIncompativeis();
        this.obrigatorios  = c.getObrigatorios();
    }

    /**
     * Clone
     */
    public Componente clone(){
        return new Componente(this);
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public String getCodigo(){
        return this.codigo;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public int getStock(){
        return this.stock;
    }

    public double getPreco() {
        return this.preco;
    }

    public String getTipo() {
        return this.tipo;
    }

    public List<String> getIncompativeis(){
        return this.incompativeis.stream().collect(Collectors.toList());
    }

    public List<String> getObrigatorios(){
        return this.obrigatorios.stream().collect(Collectors.toList());
    }


    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setCodigo(String c){
        this.codigo = c;
    }

    public void setDescricao(String d){
        this.descricao = d;
    }

    public void setStock(int s){
        this.stock = s;
    }

    public void setPreco(double p){
        this.preco = p;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /** Outros metodos ----------------------------------------------------------------------------------------------**/
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Id: " + this.getCodigo());
        sb.append("\nDescricao: " + this.getDescricao());
        sb.append("\nPreco: " + this.getPreco());
        sb.append("\nPreco: " + this.getStock());
        sb.append("\nIncompativeis:");

        for(String s: this.getIncompativeis()){
            sb.append(s);
        }
        sb.append("\nObrigatorios:");
        for(String s: this.getIncompativeis()) {
            sb.append(s);
        }
        return sb.toString();
    }
    /**
     * Adicionar um Componente a lista de componentes incompativeis
     */
    public void addIncomp(Componente c){
        this.incompativeis.add(c.getCodigo());
    }

    /**
     * Adicionar um Componente a lista de componentes obrigatorios
     */
    public void addObr(Componente c){
        this.incompativeis.add(c.getCodigo());
    }

    /**
     * Comparacao com Componente
     */
    public boolean equals(Object o){
        if (this == o)
            return true;
        if(this.getClass()!= o.getClass() || o == null)
            return false;
        Componente c = (Componente) o;

        return this.codigo.equals(c.getCodigo()) && this.descricao.equals(c.getDescricao()) &&
               this.stock == c.getStock() && this.preco == c.getPreco() && this.tipo.equals(c.getTipo());
    }
}
