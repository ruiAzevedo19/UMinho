package model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.*;

public class Voluntario implements Entregador, Comparable<Voluntario>, Serializable {
    static double minVelocidade = 20;
    static double maxVelocidade = 50;
    private final String codigo;
    private final String nome;
    private GPS local;
    private double raio;
    private double rating;
    private int nrReviews;
    private SecureRandom r = new SecureRandom();


    private boolean disponivel = true;


    private final List<Map.Entry<Double , Encomenda>> encomendasEntregadas = new ArrayList<>();
    private final Queue<Map.Entry<Double , Encomenda>> encomendasNaQueue = new ArrayDeque<>();
    private Double velocidade;

    public Double getVelocidade() {
        return velocidade;
    }

    public Voluntario() {
        this.codigo = "";
        this.nome = "";
        this.rating = 0;
        this.nrReviews = 0;
        this.local = new GPS();
        this.raio = 0;
        this.velocidade = this.randomVelocidade();
    }

    public Voluntario(String codigo, String nome, GPS local, double raio) {
        this.codigo = codigo;
        this.nome = nome;
        this.local = local;
        this.raio = raio;
        this.rating = 0;
        this.nrReviews = 0;
        this.velocidade = this.randomVelocidade();
    }



    public Voluntario(Voluntario outro){
        this.codigo = outro.getCodigo();
        this.nome = outro.getNome();
        this.local = outro.getLocal().clone();
        this.raio = outro.getRaio();
        this.velocidade = this.randomVelocidade();
    }

    public static Voluntario fromArgs(String[] args) {
        if (args.length != 5)
            return null;
        String codigo = args[0].split(":")[1];
        String nome   = args[1];
        GPS local     = new GPS(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
        double raio   = Double.parseDouble(args[4]);
        return new Voluntario(codigo, nome, local, raio);
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

    public void setLocal(GPS local) {
        this.local = local;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public Queue<Map.Entry<Double, Encomenda>> getEncomendasNaQueue() {
        return encomendasNaQueue;
    }

    public double getRating() {
        return rating;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getNrReviews() {
        return nrReviews;
    }

    public void review (double rate) {
        this.rating *= this.nrReviews++;
        this.rating += rate;
        this.rating /= this.nrReviews;
    }

    public List<Map.Entry<Double , Encomenda>> getEncomendasEntregadas() {
        return encomendasEntregadas;
    }


    public boolean isDisponivel() {
        return disponivel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voluntario that = (Voluntario) o;
        return Double.compare(that.raio, raio) == 0 &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(local, that.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome, local, raio, rating, nrReviews, disponivel, encomendasEntregadas, encomendasNaQueue, velocidade);
    }

    @Override
    public Voluntario clone () throws CloneNotSupportedException {
        return new Voluntario(this);
    }

    @Override
    public String toString() {
        return "Model.Voluntario{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", local=" + local +
                ", raio=" + raio +
                '}';
    }

    public void enviaPedido (Map.Entry<Double , Encomenda> encomenda) {
        this.encomendasNaQueue.add(encomenda);
    }

    public double randomVelocidade() {
        return minVelocidade + (maxVelocidade - minVelocidade) * r.nextDouble();
    }

    @Override
    public int compareTo(Voluntario voluntario) {
        return this.codigo.compareTo(voluntario.codigo);
    }

    public boolean reviewEncomenda(double distancia, Encomenda enc) {

            return distancia > this.raio * 2 || enc.getPeso() > 6;

    }
}
