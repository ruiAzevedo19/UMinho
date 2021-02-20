package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Encomenda implements Comparable<Encomenda>, Serializable {
    private String codEncomenda;
    private String codTransporte = "";
    private String codUtilizador;
    private String codLoja;
    private double peso;
    private double distancia;
    private List<LinhaEncomenda> linhas;



    private boolean accepted;

    public Encomenda(){
        this.codEncomenda = "";
        this.codUtilizador = "";
        this.codLoja = "";
        this.peso = 0;
        this.linhas = new ArrayList<>();
        this.distancia = 0;
        this.accepted = false;

    }

    public Encomenda(String codEncomenda, String codUtilizador, String codLoja, double peso, List<LinhaEncomenda> linhas) {
        this.codEncomenda = codEncomenda;
        this.codUtilizador = codUtilizador;
        this.codLoja = codLoja;
        this.peso = peso;
        this.distancia = 0;
        this.linhas = new ArrayList<>(linhas);
        this.accepted = false;

    }

    public Encomenda(Encomenda outro) {
        this.codEncomenda = outro.getCodEncomenda();
        this.codUtilizador = outro.getCodUtilizador();
        this.codLoja = outro.getCodLoja();
        this.peso = outro.getPeso();
        this.linhas = new ArrayList<>(outro.linhas);
        this.distancia = outro.getDistancia();
        this.accepted = false;

    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


    public static Encomenda fromArgs(String[] args) {
        if (args.length < 8)
            return null;


        String codEncomenda = args[0].split(":")[1];
        String codUtilizador = args[1];
        String codLoja = args[2];
        double peso = Double.parseDouble(args[3]);
        List<LinhaEncomenda> linhas = new ArrayList<>();
        for (int i = 4; i < args.length; i += 4) {
            String codProduto = args[i];
            String descricao = args[i+1];
            double quant = Double.parseDouble(args[i+2]);
            double preco = Double.parseDouble(args[i+3]);
            LinhaEncomenda novaLinha = new LinhaEncomenda(codProduto, descricao, quant, preco);
            linhas.add(novaLinha);
        }
        return new Encomenda(codEncomenda, codUtilizador, codLoja, peso, linhas);
    }

    public String getCodTransporte() {
        return codTransporte;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(GPS ponto1 , GPS ponto2) {
        this.distancia = ponto1.calculaDistancia(ponto2);
    }

    public void setCodTransporte(String codTransporte) {
        this.codTransporte = codTransporte;
    }

    public String getCodEncomenda() {
        return codEncomenda;
    }

    public void setCodEncomenda(String codEncomenda) {
        this.codEncomenda = codEncomenda;
    }

    public String getCodUtilizador() {
        return codUtilizador;
    }

    public void setCodUtilizador(String codUtilizador) {
        this.codUtilizador = codUtilizador;
    }

    public String getCodLoja() {
        return codLoja;
    }

    public void setCodLoja(String codLoja) {
        this.codLoja = codLoja;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public List<LinhaEncomenda> getLinha() {
        return new ArrayList<>(linhas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda = (Encomenda) o;
        return Double.compare(encomenda.peso, peso) == 0 &&
                Objects.equals(codEncomenda, encomenda.codEncomenda) &&
                Objects.equals(codUtilizador, encomenda.codUtilizador) &&
                Objects.equals(codLoja, encomenda.codLoja) &&
                Objects.equals(linhas, encomenda.linhas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codEncomenda, codTransporte, codUtilizador, codLoja, peso, distancia, linhas, accepted);
    }

    @Override
    public Encomenda clone(){
        return new Encomenda(this);
    }

    @Override
    public String toString() {
        return "Model.Encomenda{" +
                "cod_Encomenda='" + codEncomenda + '\'' +
                ", cod_Utilizador='" + codUtilizador + '\'' +
                ", codLoja='" + codLoja + '\'' +
                ", peso=" + peso +
                ", linha=" + linhas +
                '}';
    }

    @Override
    public int compareTo(Encomenda encomenda) {
        return this.codEncomenda.compareTo(encomenda.codEncomenda);
    }


}
