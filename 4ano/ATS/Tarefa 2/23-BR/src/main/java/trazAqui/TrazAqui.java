package trazAqui;

import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;


public class TrazAqui implements Serializable {


  /* STARTING functions */

  private Map<String, PlataformaEntrega> informacaoPlataformaEntrega;
  private Map<String, Lojas> informacaoLojas;
  private Map<String, Utilizadores> informacaoUtilizadores;
  private Map<String, Encomenda> informacaoEncomenda;
  private List<EncomendasAceites> encomendasRealizadas;

  public TrazAqui() {
    this.informacaoPlataformaEntrega = new HashMap<>();
    this.informacaoLojas = new HashMap<>();
    this.informacaoUtilizadores = new HashMap<>();
    this.informacaoEncomenda = new HashMap<>();
    this.encomendasRealizadas = new ArrayList<>();
  }

  public TrazAqui(Map<String, PlataformaEntrega> informacaoPlataformaEntrega, Map<String, Lojas> informacaoLojas, Map<String, Utilizadores> informacaoUtilizadores, Map<String, Encomenda> informacaoEncomenda, List<EncomendasAceites> encomendasRealizadas) {
    setInformacaoPlataformaEntrega(informacaoPlataformaEntrega);
    setInformacaoLojas(informacaoLojas);
    setInformacaoUtilizadores(informacaoUtilizadores);
    setInformacaoEncomenda(informacaoEncomenda);
    this.encomendasRealizadas = encomendasRealizadas;
  }

  public TrazAqui(TrazAqui ta) {
    setInformacaoPlataformaEntrega(ta.getInformacaoPlataformaEntrega());
    setInformacaoLojas(ta.getInformacaoLojas());
    setInformacaoUtilizadores(ta.getInformacaoUtilizadores());
    setInformacaoEncomenda(ta.getInformacaoEncomenda());
    setEncomendasRealizadas(ta.getEncomendasRealizadas());
  }


  /* GET functions */

  public Map<String, PlataformaEntrega> getInformacaoPlataformaEntrega() {
    Map<String, PlataformaEntrega> newInformacaoPlataformaEntrega = new HashMap<>();

    for (Map.Entry<String, PlataformaEntrega> e: this.informacaoPlataformaEntrega.entrySet())
      newInformacaoPlataformaEntrega.put(e.getKey(), e.getValue().clone());

    return newInformacaoPlataformaEntrega;
  }

  public Map<String, Lojas> getInformacaoLojas() {
    Map<String, Lojas> newInformacaoLojas = new HashMap<>();

    for (Map.Entry<String, Lojas> e: this.informacaoLojas.entrySet())
      newInformacaoLojas.put(e.getKey(), e.getValue().clone());

    return newInformacaoLojas;
  }

  public Map<String, Utilizadores> getInformacaoUtilizadores() {
    Map<String, Utilizadores> newInformacaoUtilizadores = new HashMap<>();

    for (Map.Entry<String, Utilizadores> e: this.informacaoUtilizadores.entrySet())
      newInformacaoUtilizadores.put(e.getKey(), e.getValue().clone());

    return newInformacaoUtilizadores;
  }

  public Map<String, Encomenda> getInformacaoEncomenda() {
    Map<String, Encomenda> newInformacaoEncomenda = new HashMap<>();

    for (Map.Entry<String, Encomenda> e: this.informacaoEncomenda.entrySet())
      newInformacaoEncomenda.put(e.getKey(), e.getValue().clone());

    return newInformacaoEncomenda;
  }

  public List<EncomendasAceites> getEncomendasRealizadas() {
    return this.encomendasRealizadas.stream().map(EncomendasAceites::clone).collect(Collectors.toList());
  }

  /* SET functions */

  public void setInformacaoPlataformaEntrega(Map<String, PlataformaEntrega> newInformacaoPlataformaEntrega) {
    this.informacaoPlataformaEntrega = new HashMap<>();

    newInformacaoPlataformaEntrega.entrySet().forEach(e -> this.informacaoPlataformaEntrega.put(e.getKey(), e.getValue().clone()));
  }

  public void setInformacaoLojas(Map<String, Lojas> newInformacaoLojas) {
    this.informacaoLojas = new HashMap<>();

    newInformacaoLojas.entrySet().forEach(e -> this.informacaoLojas.put(e.getKey(), e.getValue().clone()));
  }

  public void setInformacaoUtilizadores(Map<String, Utilizadores> newInformacaoUtilizadores) {
    this.informacaoUtilizadores = new HashMap<>();

    newInformacaoUtilizadores.entrySet().forEach(e -> this.informacaoUtilizadores.put(e.getKey(), e.getValue().clone()));
  }

  public void setInformacaoEncomenda(Map<String, Encomenda> newInformacaoEncomenda) {
    this.informacaoEncomenda = new HashMap<>();

    newInformacaoEncomenda.entrySet().forEach(e -> this.informacaoEncomenda.put(e.getKey(), e.getValue().clone()));
  }

  public void setEncomendasRealizadas(List<EncomendasAceites> newEncomendasRealizadas) {
    this.encomendasRealizadas = newEncomendasRealizadas.stream().map(EncomendasAceites::clone).collect(Collectors.toList());
  }

  
  /* OTHER functions */

  public TrazAqui clone() {
    return new TrazAqui(this);
  }

  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;

    TrazAqui ta = (TrazAqui) obj;

    return ta.getInformacaoPlataformaEntrega().equals(this.informacaoPlataformaEntrega) && ta.getInformacaoLojas().equals(this.informacaoLojas) && ta.getInformacaoUtilizadores().equals(this.informacaoUtilizadores) && ta.getInformacaoEncomenda().equals(this.informacaoEncomenda);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("-- TrazAqui --").append("\n");
    sb.append("Plataformas de Entrega:\n").append(this.informacaoPlataformaEntrega).append("\n");
    sb.append("Lojas:\n").append(this.informacaoLojas).append("\n");
    sb.append("Utilizadores:\n").append(this.informacaoUtilizadores).append("\n");
    sb.append("Encomendas:\n").append(this.informacaoEncomenda).append("\n");
    sb.append("Encomendas Realizadas: \n").append(this.encomendasRealizadas).append("\n");

    return sb.toString();
  }


  /* REQUIRED functions */

    /* INICIAL functions */

  // Método que adiciona uma plataforma de entrega ao sistema
  public void addPlataforma(PlataformaEntrega p) {
    this.informacaoPlataformaEntrega.put(p.getCodigo(), p.clone());
  }

  // Método que devolve uma plataforma de entrega com o codigo do input
  public PlataformaEntrega getPlataformaEntrega(String codigo) {
    return this.informacaoPlataformaEntrega.get(codigo);
  }

  // Método que devolve uma plataforma de entrega de acordo com o tipo colocado no input
  public List<PlataformaEntrega> getPlataformaTipo(String tipo) {
    return this.informacaoPlataformaEntrega.values().stream().filter(p -> p.tipoPlataformaEntrega().equals(tipo)).map(PlataformaEntrega::clone).collect(Collectors.toList());
  }
  
  // Método que adiciona uma loja ao sistema
  public void addLoja(Lojas l) {
    this.informacaoLojas.put(l.getCodigo(), l.clone());
  }

  // Método que devolve uma loja com o codigo do input
  public Lojas getLoja(String codigo) {
    return this.informacaoLojas.get(codigo);
  }

  // Método que adiciona um cliente ao sistema
  public void addUtilizador(Utilizadores u) {
    this.informacaoUtilizadores.put(u.getCodigo(), u.clone());
  }

  // Método que devolve um utilizador com o codigo do input
  public Utilizadores getUtilizador(String codigo) {
    Utilizadores u = this.informacaoUtilizadores.get(codigo);

    if (u == null)
      for (Utilizadores uAUX: this.informacaoUtilizadores.values())
        if (uAUX.getEmail().equals(codigo))
          return uAUX;

    return u;
  }

  // Método que adiciona uma encomenda ao sistema
  public void addEncomenda(Encomenda e) {
    this.informacaoEncomenda.put(e.getCodEncomenda(), e.clone());
  }

  // Método que devolve uma encomenda com o codigo do input
  public Encomenda getEncomenda(String codigo) {
    return this.informacaoEncomenda.get(codigo);
  }

  // Método que adiciona uma encomenda no sistema das encomendas aceites
  public void addEncomendaAceite(Encomenda e) {
    EncomendasAceites ea = new EncomendasAceites(e.getCodEncomenda());

    this.encomendasRealizadas.add(ea.clone());
  }

  // Método que classifica uma plataforma de entrega
  public void classificarPlataformaEntrega(String codigo, int classificacao) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigo);

    pe.adicionaClassificacao(classificacao);
  }


    /* RECOMENDACÕES */

  // Método que recomenda a encomenda com melhor média de classificações
  public Transportadoras recomendarClassificada(Encomenda e, boolean flagTM) {
    List<PlataformaEntrega> nPE = ordenadoClassificacoes(flagTM);

    if (nPE.size() == 0)
      return null;

    PlataformaEntrega primeiroElemento = nPE.get(0).clone();
    if (nPE.size() == 1)
      return (Transportadoras) primeiroElemento;
    
    int i = 1;
    double precoAUX = 0;
    double preco = custoTransporte(primeiroElemento.getCodigo(), e);
    double mediaAUX = primeiroElemento.mediaClassificacao(primeiroElemento.getClassificacoes());

    while (i < nPE.size() && mediaAUX == nPE.get(i).mediaClassificacao(nPE.get(i).getClassificacoes())) {
      precoAUX = custoTransporte(nPE.get(i).getCodigo(), e);
      if (preco > precoAUX) {
        primeiroElemento = nPE.get(i).clone();
        preco = precoAUX;
      }
      i++;
    }
    
    return (Transportadoras) primeiroElemento;
  }

  // Método que recomenda a encomenda com o menor custo de entrega
  public Transportadoras recomendarBarata(Encomenda e, boolean flagTM) {
    List<PlataformaEntrega> nPE = ordenadoCusto(flagTM);

    if (nPE.size() == 0)
      return null;

    PlataformaEntrega primeiroElemento = nPE.get(0).clone();
    if (nPE.size() == 1)
      return (Transportadoras) primeiroElemento;
    
    int i = 1;
    double mediaAUX = 0;
    double media = primeiroElemento.mediaClassificacao(primeiroElemento.getClassificacoes());
    double precoAUX = custoTransporte(primeiroElemento.getCodigo(), e);

    while (i < nPE.size() && precoAUX == custoTransporte(nPE.get(i).getCodigo(), e)) {
      mediaAUX = nPE.get(i).mediaClassificacao(nPE.get(i).getClassificacoes());
      if (media < mediaAUX) {
        primeiroElemento = nPE.get(i).clone();
        media = mediaAUX;
      }
      i++;
    }

    return (Transportadoras) primeiroElemento;
  }


    /* LOJAS */

  // Método que indica o tamanho da fila de uma loja
  public int getTamanhoFila(Lojas l) {
    return l.getNumeroEncomendas();
  }

  // Método que aumenta o tamanho da fila de uma loja e aceita uma encomenda
  public void encomendaAceite(Encomenda e) {
    Lojas l = this.informacaoLojas.get(e.getCodLoja());
    l.setNumeroEncomendas(l.getNumeroEncomendas() + 1);
  }

  // Método que diminui o tamanho da fila de uma loja e finaliza a encomenda
  public void encomendaFinalizada(Encomenda e) {
    Lojas l = this.informacaoLojas.get(e.getCodLoja());
    l.setNumeroEncomendas(l.getNumeroEncomendas() - 1);
    l.addEncomenda(e);
  }

  // Método que calcula o tempo médio de atedimento numa loja
  public int tempoMedioAtendimento(String codigoL) {
    double tempoMedio = 0;
    double tempo = 0;

    for (Encomenda e: this.informacaoEncomenda.values())
      tempo += e.tempoEncomenda(e);

    double numero = this.informacaoEncomenda.values().size();
    
    if (numero == 0)
      return 0;
    else
        tempoMedio = tempo / numero;

    return (int) Math.round(tempoMedio);
  }


    /* TRANSPORTADORAS */

  // Método que inicia o transporte para uma determinada loja (transportadora)
  public void iniciarEntregaT(String codigoT) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Transportadoras t = (Transportadoras) pe;

    t.setCapacidade(t.getCapacidade() - 1);

    if (t.getCapacidade() == 0)
      pe.setDisponivel(false);
  }

  // Método que finaliza o transporte a uma determinada loja (transportadora)
  public void finalizarEntregaT(String codigoT, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Transportadoras t = (Transportadoras) pe;

    if (t.getCapacidade() == 0) {
      t.setCapacidade(1);
      t.setDisponivel(true);
    }
    else
      t.setCapacidade(t.getCapacidade() + 1);;

    t.adicionarEncomenda(e);
  }

  // Método que indica se uma transportadora pode ou nao transportar para uma determinada loja
  public boolean podeTransportarT(String codigoT, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Lojas l = this.informacaoLojas.get(e.getCodLoja());
  
    float distancia = distanciaLoja(codigoT, l.getCodigo());

    if (pe.getRaio() >= distancia && pe.getDisponivel() == true)
      return true;
    else
      return false;
  }

  // Método que calcula o total faturado por uma transportadora
  public double totalDinheiro(String codigoT) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    double total = 0;

    for (Encomenda e: pe.getEncomendasGuardadas().values())
      total += custoTransporte(codigoT, e);

    return total;
  }

  // Método que calcula o total dinheiro ganho num determinado periodo de tempo
  public double totalDinheiroTempo(String codigoT, LocalDateTime dtinicio, LocalDateTime dtfinal) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    double total = 0;

    for (Encomenda e: pe.getEncomendasGuardadas().values())
      if (e.getData().isAfter(dtinicio) && e.getData().isBefore(dtfinal))
        total += custoTransporte(codigoT, e);

    return total;
  }

  //  Método que adiciona numa lista as transportadoras que podem realizar uma certa encomenda
  public List<Transportadoras> listPodeTransportarT(Encomenda e) {
    List<Transportadoras> lT = new ArrayList<>();

    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe instanceof Transportadoras) {
        Transportadoras t = (Transportadoras) pe;
        if (podeTransportarT(t.getCodigo(), e))
          lT.add(t.clone());
      }

    return lT;
  } 

  
    /* VOLUNTÁRIOS */

  // Método que inicia o transporte a uma determinada loja
  public void iniciarEntregaV(String codigoV) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoV);

    pe.setDisponivel(false);
  }

  // Método que finaliza o transporte para uma determinada loja (voluntário)
  public void finalizarEntregaV(String codigoV, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoV);

    pe.setDisponivel(true);
    pe.adicionarEncomenda(e);
  }

  // Método que verifica se o transporte para uma determinada loja e utilizador é possível (voluntário)
  public boolean podeTransportarV(String codigoV, Encomenda e) {
    if (podeTransportarT(codigoV, e)) {
      double cA = condicoesAtmosfericas();

      if (cA == 1.5 && Math.random() > 0.35)
        return true;
        
      if (cA == 1 && Math.random() > 0.10)
        return true;
        
      if (cA == 1.1 && Math.random() > 0.20)
        return true;

      if (cA == 1.25 && Math.random() > 0.15)
        return true;
  }
    
    return false;
  }

  // Método que escolhe um voluntario para entrega
  public String escolhaVoluntario(Encomenda e) throws exPENotAvailable {
    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe.tipoPlataformaEntrega().equals("Voluntario") && podeTransportarV(pe.getCodigo(), e))
        return pe.getCodigo();
    
    throw new exPENotAvailable("Nenhum voluntário se encontra disponivel.\n");
  }


    /* ENCOMENDAS ACEITES */

  // Método que adiciona uma encomenda às encomendas aceites
  public void finalizarEncomenda(Encomenda e) {
    EncomendasAceites ea = new EncomendasAceites(e.getCodEncomenda());
    this.encomendasRealizadas.add(ea);
  }


    /* LOJAS - UTILIZADORES */

  // Método que calcula a distância de uma loja até ao cliente
  public float distanciaUtilizador(String codigoL, String codigoU) {
    Lojas l = this.informacaoLojas.get(codigoL);
    Utilizadores u = this.informacaoUtilizadores.get(codigoU);

    float x = l.getGPS().getX() - u.getGPS().getX(); 
    float y = l.getGPS().getY() - u.getGPS().getY();
    float distancia = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  
    return distancia;
  }

  // Método que calcula o tempo de transporte até ao cliente
  public int tempoTransporteUtilizador(String codigoT, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Lojas l = this.informacaoLojas.get(e.getCodLoja());
    Utilizadores u = this.informacaoUtilizadores.get(e.getCodUtilizador());

    double velocidade;
    float distancia = distanciaUtilizador(l.getCodigo(), u.getCodigo());

    if (pe.tipoPlataformaEntrega().equals("Transportadora"))
      velocidade = 65;
    else
      velocidade = 60;

    double velocidadeMedia = velocidade * condicoesAtmosfericas();
    double tempo = (distancia*60) / velocidadeMedia;
    double tempoCarregamento = 0.2 * e.getPeso();

    return (int) Math.round(tempo + tempoCarregamento);
  }

  
    /* PLATAFORMA ENTREGA - LOJAS */

  // Método que calcula o tempo de viagem até uma determinada loja
  public int tempoTransporteLoja(String codigoT, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Lojas l = this.informacaoLojas.get(e.getCodLoja());

    double velocidade;
    float distancia = distanciaLoja(codigoT, l.getCodigo());

    if (pe.tipoPlataformaEntrega().equals("Transportadora"))
      velocidade = 65;
    else
      velocidade = 60;

    double velocidadeMedia = velocidade * condicoesAtmosfericas();
    double tempo = (distancia*60) / velocidadeMedia;
    double tempoCarregamento = 0.2 * e.getPeso();

    return (int) Math.round(tempo + tempoCarregamento);
  }

  // Método que calcula o custo de transporte total
  public double custoTransporte(String codigoT, Encomenda e) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Lojas l = this.informacaoLojas.get(e.getCodLoja());
    Utilizadores u = this.informacaoUtilizadores.get(e.getCodUtilizador());

    Transportadoras t = (Transportadoras) pe;
    double precoPorKM = t.getPrecoPorKM();

    float distancia = distanciaLoja(codigoT, l.getCodigo()) + distanciaUtilizador(l.getCodigo(), u.getCodigo());

    float custo = (float) precoPorKM * distancia * (1+e.getPeso()/300);

    return Math.round(custo*100)/100;
  }

  // Método que calcula a distância de uma transportadora até uma determinada loja
  public float distanciaLoja(String codigoT, String codigoL) {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoT);
    Lojas l = this.informacaoLojas.get(codigoL);

    float x = pe.getGPS().getX() - l.getGPS().getX(); 
    float y = pe.getGPS().getY() - l.getGPS().getY();
    float distancia = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
  
    return distancia;
  }


    /* AUXILIARY functions */

  // Método que devolve um multiplicador dependendo das condições atmosféricas na data do transporte 
  // São usadas as 4 estações do ano para diferenciarmos as velocidades
  public double condicoesAtmosfericas() {
    LocalDateTime dataA = LocalDateTime.now();
    int ano = dataA.getYear();

    LocalDateTime primaveraInicio = LocalDateTime.of(ano, 03, 20, 00, 00);
    LocalDateTime primaveraFim = LocalDateTime.of(ano, 6, 20, 23, 59);

    LocalDateTime veraoInicio = LocalDateTime.of(ano, 06, 21, 00, 00);
    LocalDateTime veraoFim = LocalDateTime.of(ano, 9, 21, 23, 59);

    LocalDateTime outonoInicio = LocalDateTime.of(ano, 9, 22, 00, 00);
    LocalDateTime outonoFim = LocalDateTime.of(ano, 12, 20, 23, 59);
    
    if (dataA.isAfter(primaveraInicio) && dataA.isBefore(primaveraFim))
      return 1;

    if (dataA.isAfter(veraoInicio) && dataA.isBefore(veraoFim))
      return 1.1;

    if (dataA.isAfter(outonoInicio) && dataA.isBefore(outonoFim))
      return 1.25;

    return 1.5;
  }


    /* EXCEPTIONS functions */

  // Método que verifica se a password dada no login corresponde ao que está guardado no sistema (através do código)
  public boolean pwVerificaC(String codigoU, String pw, int n) throws exPWIncorrect {
    if (n == 1) { // Utilizador
      Utilizadores u = this.informacaoUtilizadores.get(codigoU);
      if (u.getPW().equals(pw))
        return true;
    }

    if (n == 2) { // Loja
      Lojas l = this.informacaoLojas.get(codigoU);
      if (l.getPW().equals(pw))
        return true;
    }

    if (n == 3) { // Transportadora
      PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoU);
      if (pe.getPW().equals(pw) && pe instanceof Transportadoras)
        return true;
    }

    if (n == 4) { // Voluntário
      PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigoU);
      if (pe.getPW().equals(pw) && pe instanceof Voluntarios)
        return true;
    }

    throw new exPWIncorrect("\nA palavra passe que inseriu está incorreta.\n");
  }

  // Método que verifica se a password dada no login corresponde ao que está guardado no sistema (através do email)
  public boolean pwVerificaE(String email, String pw) throws exPWIncorrect {
    for (Utilizadores u: this.informacaoUtilizadores.values())
      if (u.getEmail().equals(email))
        if (u.getPW().equals(pw))
          return true;

    throw new exPWIncorrect("\nA palavra passe que inseriu está incorreta.\n");
  }

  // Método que verifica se o email já está registado no sistema (login)
  public boolean emailLogIn(String email) throws exEmailDoesNotExist {
    for (Utilizadores u: this.informacaoUtilizadores.values())
      if (u.getEmail().equals(email))
        return true;
    
    throw new exEmailDoesNotExist("\nO email que inseriu nao está associado a nenhuma conta existente.\n");
  }

  // Método que verifica se o código já está registado no sistema (login)
  public boolean codigoLogIn(String codigo, int n) throws exCodigoDoesNotExist {
    if (n == 1) // Utilizador
      for (Utilizadores u: this.informacaoUtilizadores.values())
        if (u.getCodigo().equals(codigo))
          return true;

    if (n == 2) // Loja
      for (Lojas l: this.informacaoLojas.values())
        if (l.getCodigo().equals(codigo))
          return true;

    if (n == 3) // Transportadora
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.getCodigo().equals(codigo) && pe instanceof Transportadoras)
          return true;

    if (n == 4) // Voluntário
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.getCodigo().equals(codigo) && pe instanceof Voluntarios)
          return true;

    throw new exCodigoDoesNotExist("\nO código que inseriu nao está associado a nenhuma conta existente.\n");
  }

  // Método que verifica se o email já está registado no sistema (signup)
  public boolean emailSignUp(String email) throws exEmailAlreadyExists {
    if (this.informacaoUtilizadores.isEmpty())
      return true;
    
    for (Utilizadores u: this.informacaoUtilizadores.values())
      if (u.getEmail().equals(email))
        throw new exEmailAlreadyExists("O email que inseriu já está associado a uma conta existente.\n");

    return true;
  }

  // Método que verifica se o código já está registado no sistema (signup)
  public boolean codigoSignUp(String codigo) throws exCodigoAlreadyExists {
    if (this.informacaoUtilizadores.isEmpty())
      return true;
    
    for (Utilizadores u: this.informacaoUtilizadores.values())
      if (u.getCodigo().equals(codigo))
        throw new exCodigoAlreadyExists("O código que inseriu já está associado a uma conta existente.\n");

    return true;
  }

  // Método que verifica se a loja já está registada no sistema
  public boolean lojaVerifica(String nome, Ponto GPS) throws exLojaAlreadyExists {
    if (this.informacaoLojas.get(nome) != null)
      throw new exLojaAlreadyExists("O nome da loja que está a tentar criar já está associado a uma loja existente.\n");
    
    for (Lojas l: this.informacaoLojas.values())
      if (l.getGPS().equals(GPS))
        throw new exLojaAlreadyExists("A localizacao da loja que está a tentar criar já está associada a uma loja existente.\n");

    return true;
  }

  // Método que verifica se uma plataforma de entrega já está registada no sistema
  public boolean peVerifica(String nome, Ponto GPS) throws exPEAlreadyExists {
    if (this.informacaoPlataformaEntrega.get(nome) != null)
      throw new exPEAlreadyExists("O nome da Plataforma de Entrega que está a tentar criar já está associado a uma Plataforma de Entrega existente.\n");
    
    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe.getGPS().equals(GPS))
        throw new exPEAlreadyExists("A localizacao da Plataforma de Entrega que está a tentar criar já está associada a uma Plataforma de Entrega existente.\n");

    return true;
  }

  // Método que verifica se uma encomenda existe no nosso sistema ou não
  public boolean verificaEncomenda(String codigo) throws exEncomendaDoesNotExist {
    if (this.informacaoEncomenda.get(codigo) == null)
      throw new exEncomendaDoesNotExist("A encomenda que introduziu não existe no nosso sistema.\n");

    return true;
  }

  // Método que verifica se uma loja existe no nosso sistema ou não
  public boolean verificaLoja(String codigo) throws exLojaDoesNotExist {
    if (this.informacaoLojas.get(codigo) == null)
      throw new exLojaDoesNotExist("A loja que introduziu não existe no nosso sistema.\n");

    return true;
  }

  // Método que verifica se uma transportadora existe no nosso sistema ou não
  public boolean verificaT(String codigo) throws exTransportadoraDoesNotExist {
    PlataformaEntrega pe = this.informacaoPlataformaEntrega.get(codigo);

    if (pe == null || pe instanceof Voluntarios)
      throw new exTransportadoraDoesNotExist("A transportadora que inseriu não existe no nosso sistema.\n");

    return true;
  }

  // Método que verifica se o valor inteiro introduzido não é negativo ou igual a zero
  public boolean verificaValoresINT(int n) throws exNegativeValues {
    if (n <= 0)
      throw new exNegativeValues("Inseriu um valor inválido.\n");

    return true;
  }

  // Método que verifica se o valor double introduzido não é negativo ou igual a zero
  public boolean verificaValoresDOUBLE(double n) throws exNegativeValues {
    if (n <= 0)
      throw new exNegativeValues("Inseriu um valor inválido.\n");

    return true;
  }

  // Método que verifica se o valor float introduzido não é negativo ou a igual a zero
  public boolean verificaValoresFLOAT(float n) throws exNegativeValues {
    if (n <= 0)
      throw new exNegativeValues("Inseriu um valor inválido.\n");

    return true;
  }

  // Método que verifica se o NIF introduzido é válido
  public boolean verificaNIF(String nif) throws exInvalidNIF {
    if (nif.length() == 9 && nif.matches("[0-9]+"))
      return true;
    
    throw new exInvalidNIF("O NIF que inseriu é inválido.\n");
  }


    /* SORTING functions */

  // Método que ordena as transportadoras por ordem crescente da classificação média
  public List<PlataformaEntrega> ordenadoClassificacoes(boolean flagTM) {
    List<PlataformaEntrega> nPE = new ArrayList<>();

    if (flagTM) {
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.tipoPlataformaEntrega() == "Transportadora" && pe.getTransporteMedicamentos())
          nPE.add(pe.clone());
    }
    else {
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.tipoPlataformaEntrega() == "Transportadora")
          nPE.add(pe.clone());
    }

    if (this.informacaoPlataformaEntrega.values().size() == 1)
      return nPE;
    else
      nPE.sort(new cMelhorClassificada());
    
    return nPE;
  }

  // Método que ordena as transportadoras por ordem crescente de custo por km
  public List<PlataformaEntrega> ordenadoCusto(boolean flagTM) {
    List<PlataformaEntrega> nPE = new ArrayList<>();

    if (flagTM) {
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.tipoPlataformaEntrega() == "Transportadora" && pe.getTransporteMedicamentos())
          nPE.add(pe.clone());
    }
    else {
      for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
        if (pe.tipoPlataformaEntrega() == "Transportadora")
          nPE.add(pe.clone());
    }
    
    if (this.informacaoPlataformaEntrega.values().size() == 1)
      return nPE;
    else
      nPE.sort(new cMelhorCusto());

    return nPE;
  }

  // Método que devolve o Top 10 clientes mais ativos na plataforma
  public List<Utilizadores> ordenadoTop10U() {
    List<Utilizadores> nU = new ArrayList<>();
    List<Utilizadores> nU_final = new ArrayList<>();

    for (Utilizadores u: this.informacaoUtilizadores.values()) {
      if( u.getEncomendasGuardadas().size() > 0 ) {
        nU.add( u.clone() );
        System.out.println( "!Aqui!" );
      }
    }

    if (nU.size() <= 1)
      return nU;
    else
      nU.sort(new cTopU());

    for (int i = 0; i < 10; i++)
      nU_final.add(nU.get(i));

    return nU_final;
  }

  // Método que devolve o Top 10 voluntários com maior número de entregas realizadas
  public List<PlataformaEntrega> ordenadoTop10V() {
    List<PlataformaEntrega> nPE = new ArrayList<>();
    List<PlataformaEntrega> nPE_final = new ArrayList<>();

    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe.tipoPlataformaEntrega().equals("Voluntario"))
        if (pe.getEncomendasGuardadas().size() > 0)
          nPE.add(pe.clone());

    if (nPE.size() <= 1)
      return nPE;
    else
      nPE.sort(new cTopPE());

    for (int i = 0; i < 10; i++)
      nPE_final.add(nPE.get(i));

    return nPE_final;
  }

  // Método que devolve o Top 10 transportadoras com maior número de entregas realizadas
  public List<PlataformaEntrega> ordenadoTop10T() {
    List<PlataformaEntrega> nPE = new ArrayList<>();
    List<PlataformaEntrega> nPE_final = new ArrayList<>();

    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe.tipoPlataformaEntrega().equals("Transportadora"))
        if (pe.getEncomendasGuardadas().size() > 0)
          nPE.add(pe.clone());

    if (nPE.size() <= 1)
      return nPE;
    else
      nPE.sort(new cTopPE());

    for (int i = 0; i < 10; i++)
      nPE_final.add(nPE.get(i));

    return nPE_final;
  }
  

    /* FILE functions */

  public void gravaFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException {
    FileOutputStream fos = new FileOutputStream(nomeFicheiro);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(this);
    oos.flush();
    oos.close();
  }

  public TrazAqui leFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(nomeFicheiro);
    ObjectInputStream ois = new ObjectInputStream(fis);
    TrazAqui result = (TrazAqui) ois.readObject();
    ois.close();

    return result;
  }

  public void gravaCSV(String nomeFicheiro) throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(nomeFicheiro);
    
    for (Utilizadores u: this.informacaoUtilizadores.values())
      pw.println(u.toStringCSV());

    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe instanceof Voluntarios)
        pw.println(pe.toStringCSV());

    for (PlataformaEntrega pe: this.informacaoPlataformaEntrega.values())
      if (pe instanceof Transportadoras)
        pw.println(pe.toStringCSV());

    for (Lojas l: this.informacaoLojas.values())
      pw.println(l.toStringCSV());

    for (Encomenda e: this.informacaoEncomenda.values())
      pw.println(e.toStringCSV());
    
    for (EncomendasAceites ea: this.encomendasRealizadas)
      pw.println(ea.toStringCSV());

    pw.flush();
    pw.close();
  }

  public List<String> lerFicheiroTexto(String nomeFicheiro) {
    Scanner read = null;
    List<String> linhas = new ArrayList<>();

    try {
      read = new Scanner(new File(nomeFicheiro));
    } catch (IOException exc) {
      System.out.println(exc.getMessage());
    }

    while (read.hasNextLine()) {
      linhas.add(read.nextLine());
    }
    
    return linhas;
  }


    /* PARSING FUNCTIONS */

  public void parseTotal(String nomeFicheiro) {
    List<String> linhas = lerFicheiroTexto(nomeFicheiro);
    String[] linhaAUX;

    for (String linha: linhas) {
      linhaAUX = linha.split(":", 2);
      switch(linhaAUX[0]) {
        case "Utilizador":
          Utilizadores u = parseUtilizador(linhaAUX[1]);
          this.informacaoUtilizadores.put(u.getCodigo(), u.clone());
          break;
        case "Loja":
          Lojas l = parseLoja(linhaAUX[1]);
          this.informacaoLojas.put(l.getCodigo(), l.clone());
          break;
        case "Voluntario":
          Voluntarios v = parseVoluntario(linhaAUX[1]);
          this.informacaoPlataformaEntrega.put(v.getCodigo(), v.clone());
          break;
        case "Transportadora":
          Transportadoras t = parseTransportadora(linhaAUX[1]);
          this.informacaoPlataformaEntrega.put(t.getCodigo(), t.clone());
          break;
        case "Encomenda":
          Encomenda e = parseEncomenda(linhaAUX[1]);
          this.informacaoEncomenda.put(e.getCodEncomenda(), e.clone());
          break;
        case "Aceite":
          EncomendasAceites ea = parseEncomendaAceite(linhaAUX[1]);
          this.encomendasRealizadas.add(ea.clone());
          break;
        default:
          break;
      }
    }

    System.out.println("Concluído!\n");
  }

  public Utilizadores parseUtilizador (String input) {
    String[] inputs = input.split(",");

    String codigo = inputs[0];
    String nome = inputs[1];
    String email = codigo + "@trazaqui.com";

    float gpsX = Float.parseFloat(inputs[2]);
    float gpsY = Float.parseFloat(inputs[3]);
    Ponto GPS = new Ponto(gpsX, gpsY);
    

    Map<String, Encomenda> encomendasGuardadas = new HashMap<>();

    return new Utilizadores(codigo, nome, email, "TrazAqui", GPS, encomendasGuardadas);
  }

  public Lojas parseLoja(String input) {
    String[] inputs = input.split(",");

    String codigo = inputs[0];
    String nome = inputs[1];

    float gpsX = Float.parseFloat(inputs[2]);
    float gpsY = Float.parseFloat(inputs[3]);
    Ponto GPS = new Ponto(gpsX, gpsY);

    Map<String, Encomenda> registoEncomendas = new HashMap<>();

    return new Lojas(codigo, "TrazAqui", nome, GPS, 0, registoEncomendas);
  }

  public Voluntarios parseVoluntario(String input) {
    String[] inputs = input.split(",");

    String codigo = inputs[0];
    String nome = inputs[1];
    
    float gpsX = Float.parseFloat(inputs[2]);
    float gpsY = Float.parseFloat(inputs[3]);
    Ponto GPS = new Ponto(gpsX, gpsY);

    double raio = Double.parseDouble(inputs[4]);

    List<Integer> classificacoes = new ArrayList<>();
    Map<String, Encomenda> encomendasGuardadas = new HashMap<>();

    return new Voluntarios(codigo, "TrazAqui", nome, GPS, raio, false, true, classificacoes, encomendasGuardadas);
  }

  public Transportadoras parseTransportadora(String input) {
    String[] inputs = input.split(",");

    String codigo = inputs[0];
    String nome = inputs[1];

    float gpsX = Float.parseFloat(inputs[2]);
    float gpsY = Float.parseFloat(inputs[3]);
    Ponto GPS = new Ponto(gpsX, gpsY);

    String NIF = inputs[4];

    double raio = Double.parseDouble(inputs[5]);
    double precoPorKM = Double.parseDouble(inputs[6]);

    List<Integer> classificacoes = new ArrayList<>();
    Map<String, Encomenda> encomendasGuardadas = new HashMap<>();

    return new Transportadoras(codigo, "TrazAqui", nome, GPS, raio, false, true, classificacoes, encomendasGuardadas, NIF, precoPorKM, 2);
  }

  public Encomenda parseEncomenda(String input) {
    String[] inputs = input.split(",");

    String codigoE = inputs[0];
    String codigoU = inputs[1];
    String codigoL = inputs[2];

    float peso = Float.parseFloat(inputs[3]);

    List<LinhaEncomenda> encomendas = new ArrayList<>();
    for (int i = 4; i < inputs.length; i += 4) {
      encomendas.add(parseLinhaEncomenda(inputs, i));
    }

    LocalDateTime data = LocalDateTime.now();

    return new Encomenda(codigoE, codigoU, codigoL, peso, data, encomendas);
  }

  public LinhaEncomenda parseLinhaEncomenda(String[] inputs, int i) {
    String codigoP = inputs[i];
    String descricao = inputs[i+1];
    
    float quantidade = Float.parseFloat(inputs[i+2]);
    float valorUnitario = Float.parseFloat(inputs[i+3]);

    return new LinhaEncomenda(codigoP, descricao, quantidade, valorUnitario);
  }

  public EncomendasAceites parseEncomendaAceite(String input) {
    String codigoE = input;

    return new EncomendasAceites(codigoE);
  }
}