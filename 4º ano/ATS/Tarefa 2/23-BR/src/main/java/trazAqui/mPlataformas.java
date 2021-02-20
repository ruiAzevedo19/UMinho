package trazAqui;

import java.time.LocalDateTime;

public class mPlataformas extends mPrincipal {

  private String password;
  private double raio;
  private int capacidade;
  private double precoPorKm;
  private boolean transporteMedicamentos;

  public mPlataformas() {
    super();
    this.password = new String();
    this.raio = 0;
    this.capacidade = 0;
    this.precoPorKm = 0;
    this.transporteMedicamentos = false;
  }


    /* MENU LOJAS */

  // Método que imprime e lê a opção pretendida no menu de uma loja
  public void menuLoja() {
    System.out.print("1: Número de pessoas atualmente na fila\n");
    System.out.print("2: Registo de encomendas entregues até à data\n");
    System.out.print("3: Registo de encomendas entregues num certo período de tempo\n");
    System.out.print("4: Alterar password\n");
    System.out.print("\n5: Log out\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuLoja();
    }
    else
      setOpcao(opcaoAUX);
  }

  
    /* MENU TRANSPORTADORAS */

  // Método que imprime e lê a opção pretendida no menu de uma transportadora
  public void menuTransportadoras() {
    System.out.print("1: Total faturado até à data\n");
    System.out.print("2: Total faturado num certo período\n");
    System.out.print("3: Classificação atual da empresa\n");
    System.out.print("4: Registo de entregas realizadas até à data\n");
    System.out.print("5: Registo de entregas realizadas num certo período de tempo\n");
    System.out.print("6: Definições\n");
    System.out.print("\n7: Log out\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuTransportadoras();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê a opção pretendida no menu de definições de uma transportadora
  public void menuTransportadorasDefinicoes() {
    System.out.print("1: Alterar password\n");
    System.out.print("2: Alterar raio\n");
    System.out.print("3: Alterar capacidade da empresa\n");
    System.out.print("4: Alterar preço por km\n");
    System.out.print("5: Validar o transporte de medicamentos\n");
    System.out.print("\n6: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuTransportadorasDefinicoes();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que altera a password de uma plataforma 
  public void menuAlterarPW() {
    System.out.print("Nova password\n");
    this.password = leString();
  }

  // Método que altera o raio de uma plataforma 
  public void menuAlterarRaio() {
    System.out.print("Novo raio\n");
    double raioAUX = leDouble();

    if (raioAUX == Double.POSITIVE_INFINITY) {
      clearScreen();
      System.out.print("Introduziu um raio inválido. Introduza apenas número.\n\n");
      menuAlterarRaio();
    }
    else
      this.raio = raioAUX;
  }

  // Método que altera a capacidade de uma transportadora (número de trabalhadores de uma empresa)
  public void menuAlterarCapacidade() {
    System.out.print("Nova capacidade\n");
    int capacidadeAUX = leInt();

    if (capacidadeAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.print("Indroduziu uma capacidade inválida. Introduza apenas números.\n\n");
      menuAlterarCapacidade();
    }
    else
      this.capacidade = capacidadeAUX;
  }

  // Método que altera o preço por km de uma transportadora 
  public void menuAlterarPrecoPorKM() {
    System.out.print("Novo preço\n");
    double precoPorKMaux = leDouble();

    if (precoPorKMaux == Double.POSITIVE_INFINITY) {
      clearScreen();
      System.out.print("Indroduziu um preço por km inválido. Introduza apenas números.\n\n");
      menuAlterarPrecoPorKM();
    }
    else
      this.precoPorKm = precoPorKMaux;
  }

  // Método que altera a validade do transporte de medicamentos de uma plataforma de entrega
  public void menuValidarTM() {
    System.out.print("Transporte de medicamentos (Yes/No)\n");
    String transporteMedicamentosAUX = leYesOrNo();

    if (transporteMedicamentosAUX.equals("null")) {
      clearScreen();
      System.out.print("Input inválido. Introduza apenas Yes ou No.\n\n");
      menuValidarTM();
    }
    else {
      if (transporteMedicamentosAUX.equals("true"))
        this.transporteMedicamentos = true;
      else
        if (transporteMedicamentosAUX.equals("false"))
          this.transporteMedicamentos = false;
    }
  }

  // Método que lê uma data inicial
  public void menuDataI() {
    System.out.print("Data inicial\n\n");
    System.out.print("Ano\n");
    setAno(leInt());

    System.out.print("\nMês\n");
    setMes(leInt());

    System.out.print("\nDia\n");
    setDia(leInt());

    System.out.print("\nHora\n");
    setHora(leInt());

    System.out.print("\nMinuto\n");
    setMinuto(leInt());

    if (getAno() == Integer.MAX_VALUE || getMes() == Integer.MAX_VALUE || getDia() == Integer.MAX_VALUE || getHora() == Integer.MAX_VALUE || getMinuto() == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma data inválida. Introduza apenas números.\n\n");
      menuDataI();
    }
    else {
      if (!verificaData()) {
        System.out.print("\nData inválida.\n");
        System.out.print("\nInsira um caracter para voltar a inserir uma data.\n");
        String i = leString();
        clearScreen();
        menuDataI();
      }
      else
        setDataI(LocalDateTime.of(getAno(), getMes(), getDia(), getHora(), getMinuto()));
    }
  }

  // Método que lê uma data final 
  public void menuDataF() {
    System.out.print("Data final\n\n");
    System.out.print("Ano\n");
    setAno(leInt());

    System.out.print("\nMês\n");
    setMes(leInt());

    System.out.print("\nDia\n");
    setDia(leInt());

    System.out.print("\nHora\n");
    setHora(leInt());

    System.out.print("\nMinuto\n");
    setMinuto(leInt());

    if (getAno() == Integer.MAX_VALUE || getMes() == Integer.MAX_VALUE || getDia() == Integer.MAX_VALUE || getHora() == Integer.MAX_VALUE || getMinuto() == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma data inválida. Introduza apenas números.\n\n");
      menuDataF();
    }
    else {
      if (!verificaData()) {
        System.out.print("\nData inválida.\n");
        System.out.print("\nInsira um caracter para voltar a inserir uma data.\n");
        String i = leString();
        clearScreen();
        menuDataF();
      }
      else
        setDataF(LocalDateTime.of(getAno(), getMes(), getDia(), getHora(), getMinuto()));
    }
  }


    /* MENU VOLUNTARIOS */

  // Método que imprime e lê a opção pretendida no menu de um voluntário
  public void menuVoluntarios() {
    System.out.print("1: Classificação atual\n");
    System.out.print("2: Registo das entregas realizadas até à data\n");
    System.out.print("3: Registo das entregas realizadas num certo período de tempo\n");
    System.out.print("4: Definições\n");
    System.out.print("\n5: Log out\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuVoluntarios();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê a opção pretendida no menu de definições de um voluntário
  public void menuVoluntariosDefinicoes() {
    System.out.print("1: Alterar password\n");
    System.out.print("2: Alterar raio\n");
    System.out.print("3: Validar o transporte de medicamentos\n");
    System.out.print("\n4: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuVoluntariosDefinicoes();
    }
    else
      setOpcao(opcaoAUX);
  }
  

  /* GET functions */

  public String getPW() {
    return this.password;
  }

  public double getRaio() {
    return this.raio;
  }

  public int getCapacidade() {
    return this.capacidade;
  }

  public double getPrecoPorKM() {
    return this.precoPorKm;
  }

  public boolean getTM() {
    return this.transporteMedicamentos;
  }
}