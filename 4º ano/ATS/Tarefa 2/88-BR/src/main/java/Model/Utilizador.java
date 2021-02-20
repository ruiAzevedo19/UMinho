package Model;

import java.io.Serializable;
import java.util.*;

public class Utilizador implements Comparable<Utilizador>, Serializable {
    private String codigo;
    private String nome;
    private GPS local;
    private List<Encomenda> historico = new ArrayList<>();
    private List<Encomenda> encomendasClassificadas = new ArrayList<>();
    private List<Encomenda> encomendasPorClassificar = new ArrayList<>();
    private Queue<Map.Entry<Double , Encomenda>> encomendasDeTransportadoras = new ArrayDeque<>();

    public Queue<Map.Entry<Double, Encomenda>> getEncomendasDeTransportadoras() {
        return encomendasDeTransportadoras;
    }

    public Utilizador() {
        this.codigo = "Invalid codigo";
        this.nome = "Invalid nome";
        this.local = new GPS();
    }

    public Utilizador(String codigo, String nome, GPS local) {
        this.codigo = codigo;
        this.nome = nome;
        this.local = local.clone();
    }

    public List<Encomenda> getEncomendasClassificadas() {
        return encomendasClassificadas;
    }

    public List<Encomenda> getHistorico() {
        return historico;
    }

    public List<Encomenda> getEncomendasPorClassificar() {
        return encomendasPorClassificar;
    }

    public Utilizador(Utilizador outro) {
        this.codigo = outro.getCodigo();
        this.nome = outro.getNome();
        this.local = outro.getLocal().clone();
    }

    public static Utilizador fromArgs(String[] args) {
        if (args.length != 4)
            return null;
        String codigo = args[0].split(":")[1];
        String nome   = args[1];
        GPS    gps    = new GPS(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        return new Utilizador(codigo, nome, gps);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public GPS getLocal() {
        return local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return Objects.equals(codigo, that.codigo) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(local, that.local);
    }

    @Override
    public Utilizador clone(){
        return new Utilizador(this);
    }

    @Override
    public String toString() {
        return "Model.Utilizador{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", local=" + local +
                '}';
    }

    @Override
    public int compareTo(Utilizador utilizador) {
        return this.codigo.compareTo(utilizador.codigo);
    }
}
