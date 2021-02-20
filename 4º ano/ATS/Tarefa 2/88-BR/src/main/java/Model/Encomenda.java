package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Encomenda implements Comparable<Encomenda>, Serializable {
    private String cod_Encomenda;
    private String cod_Transporte = "";
    private String cod_Utilizador;
    private String cod_Loja;
    private double peso;
    private double distancia;
    private List<LinhaEncomenda> linhas;
    private boolean accepted;

    public boolean isAccepted() {
        return accepted;
    }



    public Encomenda(){
        this.cod_Encomenda = "";
        this.cod_Utilizador = "";
        this.cod_Loja = "";
        this.peso = 0;
        this.linhas = new ArrayList<>();
        this.distancia = 0;
        this.accepted = false;
    }

    public Encomenda(String cod_Encomenda, String cod_Utilizador, String cod_Loja, double peso, List<LinhaEncomenda> linhas) {
        this.cod_Encomenda = cod_Encomenda;
        this.cod_Utilizador = cod_Utilizador;
        this.cod_Loja = cod_Loja;
        this.peso = peso;
        this.distancia = 0;
        this.linhas = new ArrayList<>(linhas);
        this.accepted = false;
    }

    public Encomenda(Encomenda outro) {
        this.cod_Encomenda = outro.getCod_Encomenda();
        this.cod_Utilizador = outro.getCod_Utilizador();
        this.cod_Loja = outro.getCod_Loja();
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


        String cod_Encomenda = args[0].split(":")[1];
        String cod_Utilizador = args[1];
        String cod_Loja = args[2];
        double peso = Double.parseDouble(args[3]);
        List<LinhaEncomenda> linhas = new ArrayList<>();
        for (int i = 4; i < args.length; i += 4) {
            String cod_Produto = args[i];
            String descricao = args[i+1];
            double quant = Double.parseDouble(args[i+2]);
            double preco = Double.parseDouble(args[i+3]);
            LinhaEncomenda novaLinha = new LinhaEncomenda(cod_Produto, descricao, quant, preco);
            linhas.add(novaLinha);
        }
        return new Encomenda(cod_Encomenda, cod_Utilizador, cod_Loja, peso, linhas);
    }

    public String getCod_Transporte() {
        return cod_Transporte;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(GPS ponto1 , GPS ponto2) {
        this.distancia = ponto1.calculaDistancia(ponto2);
    }

    public void setCod_Transporte(String cod_Transporte) {
        this.cod_Transporte = cod_Transporte;
    }

    public String getCod_Encomenda() {
        return cod_Encomenda;
    }

    public void setCod_Encomenda(String cod_Encomenda) {
        this.cod_Encomenda = cod_Encomenda;
    }

    public String getCod_Utilizador() {
        return cod_Utilizador;
    }

    public void setCod_Utilizador(String cod_Utilizador) {
        this.cod_Utilizador = cod_Utilizador;
    }

    public String getCod_Loja() {
        return cod_Loja;
    }

    public void setCod_Loja(String cod_Loja) {
        this.cod_Loja = cod_Loja;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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
                Objects.equals(cod_Encomenda, encomenda.cod_Encomenda) &&
                Objects.equals(cod_Utilizador, encomenda.cod_Utilizador) &&
                Objects.equals(cod_Loja, encomenda.cod_Loja) &&
                Objects.equals(linhas, encomenda.linhas);
    }

    @Override
    public Encomenda clone(){
        return new Encomenda(this);
    }

    @Override
    public String toString() {
        return "Model.Encomenda{" +
                "cod_Encomenda='" + cod_Encomenda + '\'' +
                ", cod_Utilizador='" + cod_Utilizador + '\'' +
                ", cod_Loja='" + cod_Loja + '\'' +
                ", peso=" + peso +
                ", linha=" + linhas +
                '}';
    }

    @Override
    public int compareTo(Encomenda encomenda) {
        return this.cod_Encomenda.compareTo(encomenda.cod_Encomenda);
    }
}
