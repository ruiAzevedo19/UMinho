package trazAqui;

import java.time.LocalDateTime;

public class mCliente extends mPrincipal {

  private String codigoE;
  private String codigoL;
  private String codigoT;
  private String codigoTia;
  private String codigoRegisto;
  private boolean flag;
  private boolean flagPE;
  private boolean flagTM;
  private String email;
  private String password;
  private Ponto GPS;

  public mCliente() {
    super();
    this.codigoE = new String();
    this.codigoL = new String();
    this.codigoT = new String();
    this.codigoTia = new String();
    this.codigoRegisto = new String();
    this.flag = false;
    this.flagPE = false;
    this.flagTM = false;
    this.email = new String();
    this.password = new String();
    this.GPS = new Ponto();
  }

  // Método que imprime e lê a opção pretendida no menu de cliente 
  public void escolhaMenu() {
    System.out.print("1: A minha encomenda\n");
    System.out.print("2: Definições\n");
    System.out.print("3: Informações adicionais\n");
    System.out.print("\n4: Log out\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      escolhaMenu();
    }
    else
      setOpcao(opcaoAUX);
  }


    /* MENU ENCOMENDAS */

  // Método que imprime e lê a opção pretendida no menu de encomenda 
  public void menuEncomenda() {
    System.out.print("1: Ver estado da encomenda\n");
    System.out.print("2: Inserir pedido de encomenda\n");
    System.out.print("3: Pedir transporte\n");
    System.out.print("4: Classificar o serviço de transporte\n");
    System.out.print("\n5: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuEncomenda();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê a opção pretendida no menu de registo de encomendas 
  public void menuInserirEncomenda() {
    System.out.print("Código da encomenda\n");
    this.codigoE = leString();
    System.out.print("\nCódigo da loja\n");
    this.codigoL = leString();

    System.out.print("\nA sua encomenda contém medicamentos? (Yes/No)\n");
    String tm = leYesOrNo();

    if (tm.equals("null")) {
      clearScreen();
      System.out.println("Input inválido. Introduza apenas Yes ou No.\n\n");
      menuInserirEncomenda();
    }
    else {
      if (tm.equals("true"))
        this.flagTM = true;
      else
        if (tm.equals("false"))
          this.flagTM = false;
    }
  }

  // Método que imprime o estado da encomenda 
  public void menuEstadoEncomenda() {
    if (flag)
      System.out.print("Tempo estimado de espera\n");
    else
      System.out.print("Não inseriu nenhuma encomenda.\n");
  }

  // Método que imprime e lê a opção pretendida no menu de pedidos para entrega
  public void menuPedirEntrega() {
    if (flag) {
      System.out.print("1: Entrega realizada por transportadora\n");
      System.out.print("2: Entrega realizada por voluntário\n");
      System.out.print("\n3: Voltar atrás\n");

      int opcaoAUX = leOpcao();
      if (opcaoAUX == Integer.MAX_VALUE) {
        clearScreen();
        System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
        menuPedirEntrega();
      }
      else
        setOpcao(opcaoAUX);
    }
    else
      System.out.print("Ainda não inseriu nenhuma encomenda.\n");
  }

  // Método lê a transportadora pretendida para entregar a sua encomenda 
  public void menuEntregaT() {
    System.out.print("Código da transportadora\n");
    this.codigoT = leString();
    this.flagPE = true;
    this.flag = false;
  }


    /* MENU INFORMAÇÕES ADICIONAIS */

  // Método que imprime e lê a opção pretendida no menu de informações adicionais (cliente)  
  public void menuInformacoesAdicionais() {
    System.out.print("1: Registo das encomendas de um voluntário/transportadora\n");
    System.out.print("\n2: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuInformacoesAdicionais();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê a opção pretendida no menu de registo de encomendas«
  public void menuRegistoEscolha() {
    System.out.print("1: Até à data\n");
    System.out.print("2: Num certo período de tempo\n");
    System.out.print("\n3: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuRegistoEscolha();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê o código pretendido para saber o registo das encomendas
  public void menuRegistoV() {
    System.out.print("Código\n");
    this.codigoRegisto = leString();
  }


    /* MENU CLASSIFICACOES */

  // Método que imprime e lê a opção pretendida no menu de leaderboards
  public void menuClassificacoes() {
    System.out.print("1: Top 10 - Utilizadores\n");
    System.out.print("2: Top 10 - Voluntários\n");
    System.out.print("3: Top 10 - Transportadoras\n");
    System.out.print("\n4: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuClassificacoes();
    }
    else
      setOpcao(opcaoAUX);
  }


    /* DATAS */

  // Método que lê a uma data inicial
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
  

    /* MENU DEFINICOES */

  // Método que imprime e lê a opção pretendida no menu de definições de cliente 
  public void menuDefinicoes() {
    System.out.print("1: Localização atual\n");
    System.out.print("2: Histórico de encomendas realizadas\n");
    System.out.print("3: Alterar email\n");
    System.out.print("4: Alterar password\n");
    System.out.print("5: Alterar localização\n");
    System.out.print("\nAconselhamos a mudar regularmente a sua password.\n");
    System.out.print("\n6: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      menuDefinicoes();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que altera o email de um cliente
  public void menuAlterarEmail() {
    System.out.print("Novo email\n");
    this.email = leString();
  }

  // Método que altera a password de um cliente
  public void menuAlterarPW() {
    System.out.print("Nova password\n");
    this.password = leString();
  }

  // Método que altera a localização de um cliente
  public void menuAlterarLocalizacao() {
    System.out.print("Nova localização\n");
    System.out.print("X: ");
    float gpsX = leFloat();
    System.out.print("\nY: ");
    float gpsY = leFloat();

    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY) {
      clearScreen();
      System.out.print("Introduziu uma localização inválida. Introduza apenas números.\n\n");
      menuAlterarLocalizacao();
    }
    else
      this.GPS = new Ponto(gpsX, gpsY);
  }

  
  /* GET functions */

  public String getCodigoE() {
    return this.codigoE;
  }

  public String getCodigoL() {
    return this.codigoL;
  }

  public String getCodigoT() {
    return this.codigoT;
  }

  public String getCodigoTia() {
    return this.codigoTia;
  }

  public String getCodigoRegisto() {
    return this.codigoRegisto;
  }

  public boolean getFlag() {
    return this.flag;
  }

  public boolean getFlagPE() {
    return this.flagPE;
  }

  public boolean getFlagTM() {
    return this.flagTM;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public Ponto getGPS() {
    return this.GPS;
  }

  
  /* SET functions */

  public void setFlag(boolean newFlag) {
    this.flag = newFlag;
  }

  public void setFlagPE(boolean newFlagPE) {
    this.flagPE = newFlagPE;
  }
}