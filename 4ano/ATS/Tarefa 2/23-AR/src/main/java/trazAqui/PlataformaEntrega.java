package trazAqui;

import java.util.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public abstract class PlataformaEntrega implements Comparable<PlataformaEntrega>, Serializable {

     /* STARTING functions */

     private String codigo;
     private String password;
     private String nome;
     private Ponto gps;
     private double raio;
     private boolean transporteMedicamentos;
     private boolean disponivel;
     private final List<Integer> classificacoes;
     private Map<String, Encomenda> encomendasGuardadas;

     public PlataformaEntrega() {
          this.codigo = "";
          this.password = "";
          this.nome = "";
          this.gps = new Ponto();
          this.raio = 0;
          this.transporteMedicamentos = false;
          this.disponivel = false;
          this.classificacoes = new ArrayList<>();
          this.encomendasGuardadas = new HashMap<>();
     }

     public PlataformaEntrega(String codigo, String password, String nome, Ponto gps, double raio, boolean transporteMedicamentos, boolean disponivel, List<Integer> classificacoes, Map<String, Encomenda> encomendasGuardadas) {
          this.codigo = codigo;
          this.password = password;
          this.nome = nome;
          this.gps = gps.clone();
          this.raio = raio;
          this.transporteMedicamentos = transporteMedicamentos;
          this.disponivel = disponivel;
          this.classificacoes = classificacoes;
          setEncomendasGuardadas( encomendasGuardadas );
     }

     public PlataformaEntrega(PlataformaEntrega pe) {
          this.codigo = pe.getCodigo();
          this.password = pe.getPW();
          this.nome = pe.getNome();
          this.gps = pe.getGps();
          this.raio = pe.getRaio();
          this.transporteMedicamentos = pe.getTransporteMedicamentos();
          this.disponivel = pe.getDisponivel();
          this.classificacoes = pe.getClassificacoes();
          setEncomendasGuardadas( pe.getEncomendasGuardadas() );
     }


     /* GET functions */

     public String getCodigo() {
          return this.codigo;
     }

     public String getPW() {
          return this.password;
     }

     public String getNome() {
          return this.nome;
     }

     public Ponto getGps() {
          return this.gps.clone();
     }

     public double getRaio() {
          return this.raio;
     }

     public boolean getTransporteMedicamentos() {
          return this.transporteMedicamentos;
     }

     public boolean getDisponivel() {
          return this.disponivel;
     }

     public List<Integer> getClassificacoes() {
          return this.classificacoes.stream().collect( Collectors.toList() );
     }

     public Map<String, Encomenda> getEncomendasGuardadas() {
          Map<String, Encomenda> newEncomendasGuardadas = new HashMap<>();

          for(Map.Entry<String, Encomenda> e : this.encomendasGuardadas.entrySet())
               newEncomendasGuardadas.put( e.getKey(), e.getValue().clone() );

          return newEncomendasGuardadas;
     }


     /* SET functions */

     public void setCodigo(String newCodigo) {
          this.codigo = newCodigo;
     }

     public void setPW(String newPassword) {
          this.password = newPassword;
     }

     public void setNome(String newNome) {
          this.nome = newNome;
     }

     public void setGps(Ponto newGPS) {
          this.gps = newGPS.clone();
     }

     public void setRaio(double newRaio) {
          this.raio = newRaio;
     }

     public void setTransporteMedicamentos(boolean newTransporteMedicamentos) {
          this.transporteMedicamentos = newTransporteMedicamentos;
     }

     public void setDisponivel(boolean newDisponivel) {
          this.disponivel = newDisponivel;
     }

     public void setEncomendasGuardadas(Map<String, Encomenda> newEncomendasGuardadas) {
          this.encomendasGuardadas = new HashMap<>();

          newEncomendasGuardadas.entrySet().forEach( e -> this.encomendasGuardadas.put( e.getKey(), e.getValue().clone() ) );
     }


     /* OTHER functions */

     public abstract PlataformaEntrega clone();

     public boolean equals(Object obj) {
          if( obj == this )
               return true;
          if( obj == null || obj.getClass() != this.getClass() )
               return false;

          PlataformaEntrega pe = (PlataformaEntrega) obj;

          return pe.getCodigo().equals( this.codigo );
     }

     public String toString() {
          StringBuilder sb = new StringBuilder();

          sb.append( "Código: " ).append( this.codigo ).append( "\n" );
          sb.append( "Nome: " ).append( this.nome ).append( "\n" );
          sb.append( "GPS: " ).append( this.gps ).append( "\n" );
          sb.append( "Raio: " ).append( this.raio ).append( "\n" );
          sb.append( "Transporte medicamentos: " ).append( this.transporteMedicamentos ).append( "\n" );

          return sb.toString();
     }

     public int compareTo(PlataformaEntrega pe) {
          return this.codigo.compareTo( pe.getCodigo() );
     }

     public abstract String toStringCSV();


     /* REQUIRED functions */

     // Método que adiciona uma encomenda ao registo de encomendas de uma plataforma de entrega
     public void adicionarEncomenda(Encomenda e) {
          this.encomendasGuardadas.put( e.getCodEncomenda(), e.clone() );
     }

     // Método que adiciona uma classicação a uma plataforma de entrega
     public void adicionaClassificacao(Integer classificacao) {
          this.classificacoes.add( classificacao );
     }

     // Método que calcula a média das classificações de uma plataforma de entrega
     public double mediaClassificacao(List<Integer> classificacao) {
          int soma = 0;
          double media = 0;

          for(Integer i : classificacao)
               soma += i;

          if( !classificacao.isEmpty() )
               media = (double)soma / ( classificacao.stream().count() );

          return media;
     }

     // Método que devolve o histórico de encomendas até à data
     public List<Encomenda> historicoPE() {
          return this.encomendasGuardadas.values().stream().map( Encomenda::clone ).collect( Collectors.toList() );
     }

     // Método que devolve o histórico de encomendas entre datas
     public List<Encomenda> historicoPEdata(LocalDateTime dtinicio, LocalDateTime dtfinal) {
          List<Encomenda> nEncomendas = new ArrayList<>();

          for(Encomenda e : this.encomendasGuardadas.values())
               if( e.getData().isAfter( dtinicio ) && e.getData().isBefore( dtfinal ) )
                    nEncomendas.add( e.clone() );

          return nEncomendas;
     }

     @Override
     public int hashCode() {
          return Objects.hash( codigo, password, nome, gps, raio, transporteMedicamentos, disponivel, classificacoes, encomendasGuardadas );
     }

     // Método abstrato que pede um certo tipo de plataforma de entrega
     public abstract String tipoPlataformaEntrega();
}