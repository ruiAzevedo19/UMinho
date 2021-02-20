package model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.*;

public class Transportadora implements Entregador, Comparable<Transportadora>, Serializable {
    static double minVelocidade = 40;
    static double maxVelocidade = 80;
    private final double velocidade;
    private double kmPercorridos = 0;
    private String codigo;
    private String nome;
    private GPS local;
    private String nif;
    private double rating = 0;
    private int nrReviews = 0;
    private double raio;
    private double precoKm;
    private SecureRandom r = new SecureRandom();





    private boolean disponivel = true;
    private final Queue<Map.Entry<Double , Encomenda>> encomendasPorEntregar = new ArrayDeque<>();
    private final List<Map.Entry<Double , Encomenda>> encomendasEntregadas = new ArrayList<>();

    public double getVelocidade() {
        return velocidade;
    }

    public Double getKmPercorridos() {
        return kmPercorridos;
    }

    public void setKmPercorridos(Double kmPercorridos) {
        this.kmPercorridos = kmPercorridos;
    }

    public List<Map.Entry<Double, Encomenda>> getEncomendasEntregadas() {
        return encomendasEntregadas;
    }

    public Transportadora() {
        this.codigo = "Invalid codigo";
        this.nome = "Invalid nome";
        this.local = new GPS();
        this.nif = "Invalid nif";
        this.raio = 0;
        this.precoKm = 0;
        this.velocidade = this.randomVelocidade();
    }

    public Transportadora(String codigo,
                          String nome,
                          GPS local,
                          String nif,
                          double raio,
                          double precoKm) {
        this.codigo = codigo;
        this.nome = nome;
        this.local = local.clone();
        this.nif = nif;
        this.raio = raio;
        this.precoKm = precoKm;
        this.velocidade = this.randomVelocidade();
    }

    public double getRating() {
        return rating;
    }

    public int getNrReviews() {
        return nrReviews;
    }

    public static Transportadora fromArgs(String[] args) {
        if (args.length != 7)
            return null;

        String codigo  = args[0].split(":")[1];
        String nome    = args[1];
        GPS    local   = new GPS(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        String nif     = args[4];
        double raio    = Double.parseDouble(args[5]);
        double precoKm = Double.parseDouble(args[6]);

        return new Transportadora(codigo, nome, local, nif, raio, precoKm);
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

    public String getNif() {
        return nif;
    }

    public double getRaio() {
        return raio;
    }

    public double getPrecoKm() {
        return precoKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportadora that = (Transportadora) o;
        return Double.compare(that.raio, raio) == 0 &&
                Double.compare(that.precoKm, precoKm) == 0 &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(local, that.local) &&
                Objects.equals(nif, that.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocidade, kmPercorridos, codigo, nome, local, nif, rating, nrReviews, raio, precoKm, disponivel, encomendasPorEntregar, encomendasEntregadas);
    }

    @Override
    public String toString() {
        return "Model.Transportadora{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", local=" + local +
                ", nif='" + nif + '\'' +
                ", raio=" + raio +
                ", precoKm=" + precoKm +
                '}';
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void review (double rate) {
        this.rating *= this.nrReviews++;
        this.rating += rate;
        this.rating /= this.nrReviews;
    }

    public void enviaPedido (Map.Entry<Double , Encomenda> encomenda) {
        this.encomendasPorEntregar.add(encomenda);
    }

    public Queue<Map.Entry<Double, Encomenda>> getEncomendasPorEntregar() {
        return encomendasPorEntregar;
    }

    public double randomVelocidade() {
        return minVelocidade + (maxVelocidade - minVelocidade) * r.nextDouble();
    }

    @Override
    public int compareTo(Transportadora transportadora) {
        return this.codigo.compareTo(transportadora.codigo);
    }
}
