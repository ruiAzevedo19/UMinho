package Model.Class;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Configuracao{

    private Modelo modelo;
    private String cor;
    private List<Componente> componentes;
    private List<Pacote> pacotes;
    private double preco;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Método construtor nao parametrizado
     */
    public Configuracao(){
        this.modelo = new Modelo();
        this.cor = "";
        this.componentes = new ArrayList<>();
        this.pacotes = new ArrayList<>();
        this.preco = 0;
    }

    /**
     * Método construtor não parametrizado
     */
    public Configuracao(Modelo modelo,String cor, List<Componente> componentes, List<Pacote> pacotes){
        this.modelo = modelo.clone();
        this.cor = cor;
        this.componentes = componentes.stream().collect(Collectors.toList());
        this.pacotes = pacotes.stream().collect(Collectors.toList());
        this.preco   = 0;
    }

    /**
     * Método construtor por copia
     */
    public Configuracao(Configuracao c){
        this.modelo = c.getModelo();
        this.cor = c.getCor();
        this.componentes = c.getComponentes();
        this.pacotes = c.getPacotes();
        this.preco = c.getPreco();
    }

    /**
     * Método clone
     */
    public Configuracao clone(){
        return new Configuracao(this);
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public Modelo getModelo() {
        return this.modelo.clone();
    }

    public List<Componente> getComponentes() {
        return this.componentes;
    }

    public List<Pacote> getPacotes() {
        return this.pacotes;
    }

    public String getCor(){
        return this.cor;
    }
    public double getPreco() {
        return this.preco;
    }

    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setModelo(Modelo modelo) {
        this.modelo = modelo.clone();
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes.stream().collect(Collectors.toList());
    }

    public void setPacotes(List<Pacote> pacotes) {
        this.pacotes = pacotes.stream().collect(Collectors.toList());
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCor(String cor){
        this.cor = cor;
    }

    public boolean containsPacote (String s){
        return getPacotes().contains(s);
    }

    /** Outros metodos ----------------------------------------------------------------------------------------------**/

    public void addComponente(Componente c){
        this.componentes.add(c);
    }

    public void addComponentes(List<Componente> comp){
        this.componentes.addAll(comp);
    }

    public void clear(){
        this.modelo.getComponentes().clear();
        this.modelo = null;
        this.componentes.clear();
        this.preco = 0;
    }

    public void resetConf(){
        this.modelo = new Modelo();
        this.cor = "";
        this.componentes = new ArrayList<>();
        this.preco = 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Modelo : \n");
        sb.append("\t" + getModelo().getDesignacao() + "\n");
        sb.append("============================================\n");
        sb.append("Cor: \n" );
        sb.append("\t" + this.getCor() + "\n");
        sb.append("============================================\n");
        sb.append("Componentes: \n");
        for(Componente c: this.componentes){
            sb.append("\t" + c.getDescricao()+"\n");
        }
        sb.append("============================================\n");

        this.preco = (this.componentes.stream().mapToDouble(Componente::getPreco).sum()) + this.getModelo().getPrecoBase();
        sb.append("\n\nPreço Total : "+this.preco + " €\n");
        return sb.toString();
    }

    public double actualizaPreco(){
        double res = 0;
        res += this.getModelo().getPrecoBase();
        for(Componente c: this.componentes){
            res += c.getPreco();
        }
        this.setPreco(res);
        return res;
    }

    public void removeCategoria(String cat){
        ListIterator<Componente> iter = componentes.listIterator();
        while(iter.hasNext()){
            if(iter.next().getTipo().equals(cat)){
                iter.remove();
            }
        }
    }

    public void removeComponente(String cod){
        ListIterator<Componente> iter = componentes.listIterator();
        while(iter.hasNext()){
            if(iter.next().getCodigo().equals(cod)){
                iter.remove();
            }
        }
    }
/*
    public void removePacote (String cod){
        ListIterator<Pacote> iter = pacotes.listIterator();
        while(iter.hasNext()){
            if(iter.next().getCod().equals(cod)){
                iter.remove();
            }
        }
    }*/

    public boolean containsComponente(String cod){
        boolean flag = false;
        for(Componente c: this.getComponentes()){
            if(c.getCodigo().equals(cod)) flag = true;
        }
        return flag;
    }
}