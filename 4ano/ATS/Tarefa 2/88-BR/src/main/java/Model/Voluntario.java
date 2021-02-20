package Model;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;

public class Voluntario implements Entregador, Comparable<Voluntario>, Serializable {
    static double MIN_VELOCIDADE = 20;
    static double MAX_VELOCIDADE = 50;
    private final String codigo;
    private final String nome;
    private GPS local;
    private double raio;
    private double rating;
    private int nrReviews;
    public boolean disponivel = true;
    private List<Map.Entry<Double , Encomenda>> encomendasEntregadas = new ArrayList<>();
    private Queue<Map.Entry<Double , Encomenda>> encomendasNaQueue = new ArrayDeque<>();
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
    public Voluntario clone (){
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
        Random r = new Random();
        return MIN_VELOCIDADE + (MAX_VELOCIDADE - MIN_VELOCIDADE) * r.nextDouble();
    }

    @Override
    public int compareTo(Voluntario voluntario) {
        return this.codigo.compareTo(voluntario.codigo);
    }

    public boolean reviewEncomenda(double distancia, Encomenda enc) {
        if (distancia > this.raio * 2 || enc.getPeso() > 6)
            return false;
        else
            return true;
    }
}
