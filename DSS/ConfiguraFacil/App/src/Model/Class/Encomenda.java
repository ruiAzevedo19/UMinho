package Model.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Encomenda{

    private String id;
    private String modelo;
    private String metodoPagamento;
    private double preco;
    private String vendedor;
    private String cliente;
    private List<String> componentes;

    /** Construtores ------------------------------------------------------------------------------------------------**/

    /**
     * Método construtor nao parametrizado
     */
    public Encomenda(){
        this.id = "";
        this.modelo = new Configuracao().getModelo().getDesignacao();
        this.preco = 0;
        this.metodoPagamento = "";
        this.vendedor = "";
        this.cliente = "";
        this.componentes = new ArrayList<>();
    }

    /**
     * Método construtor parametrizado
     */

    public Encomenda(String modelo, double preco, String metodoPagamento, String u, String cliente, List<String> componente){
        this.modelo = modelo;
        this.preco = preco;
        this.metodoPagamento = metodoPagamento;
        this.vendedor = u;
        this.cliente = cliente;
        this.componentes = componente.stream().collect(Collectors.toList());
    }

    public Encomenda(String codigo, String modelo, double preco, String metodoPagamento, String u, String cliente, List<String> componente){
        this.id = codigo;
        this.modelo = modelo;
        this.preco = preco;
        this.metodoPagamento = metodoPagamento;
        this.vendedor = u;
        this.cliente = cliente;
        this.componentes = componente.stream().collect(Collectors.toList());
    }

    /** Getters -----------------------------------------------------------------------------------------------------**/

    public String getCodigo() {
        return this.id;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPreco() {
        return preco;
    }

    public String getMetodoPagamento() {
        return this.metodoPagamento;
    }

    public String getVendedor() {
        return this.vendedor;
    }

    public String getCliente() {
        return this.cliente;
    }

    public List<String> getComponentes() {
        return this.componentes.stream().collect(Collectors.toList());
    }

    /** Setters -----------------------------------------------------------------------------------------------------**/

    public void setCodigo(String codigo) {
        this.id = codigo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes.stream().collect(Collectors.toList());;
    }
}