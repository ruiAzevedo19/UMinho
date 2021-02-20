package trazAqui;

public class mSignUp extends mPrincipal {
  
  private String nome;
  private String codigo;
  private String email;
  private Ponto GPS;
  private double raio;
  private boolean transporteMedicamentos;
  private String NIF;
  private double precoPorKm;

  public mSignUp() {
    super();
    this.nome = new String();
    this.codigo = new String();
    this.email = new String();
    this.GPS = new Ponto();
    this.raio = 0;
    this.transporteMedicamentos = false;
    this.NIF = new String();
    this.precoPorKm = 0;
  }

  // Método que imprime e lê a opção pretendida no menu de signup e efetua o mesmo consoante o seu estatuto 
  public void escolhaMenu() {
    System.out.println("1: Utilizador");
    System.out.println("2: Loja");
    System.out.println("3: Transportadora");
    System.out.println("4: Voluntário");
    System.out.println("\n5: Voltar atrás");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      escolhaMenu();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que imprime e lê o menu de signup de um utilizador
  public void mostrarMenuSignUpU() {
    System.out.println("Nome");
    this.nome = leString();
    System.out.println("\nCódigo de utilizador");
    this.codigo = leString();
    System.out.println("\nEmail");
    this.email = leString();

    System.out.println("\nLocalização X");
    float gpsX = leFloat();
    System.out.println("\nLocalização Y");
    float gpsY = leFloat();

    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY) {
      clearScreen();
      System.out.println("Introduziu uma localização inválida. Introduza apenas números.\n\n");
      mostrarMenuSignUpU();
    }
    else
      this.GPS = new Ponto(gpsX, gpsY);
  }

  // Método que imprime e lê o menu de signup de uma loja
  public void mostrarMenuSignUpL() {
    System.out.println("Nome");
    this.nome = leString();

    System.out.println("\nLocalização X");
    float gpsX = leFloat();
    System.out.println("\nLocalização Y");
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY) {
      clearScreen();
      System.out.println("Introduziu uma localização inválida. Introduza apenas números.\n\n");
      mostrarMenuSignUpL();
    }
    else
      this.GPS = new Ponto(gpsX, gpsY);
  }

  // Método que imprime e lê o menu de signup de uma transportadora
  public void mostrarMenuSignUpT() {
    System.out.println("Nome");
    this.nome = leString();

    System.out.println("\nNIF");
    this.NIF = leString();

    System.out.println("\nRaio");
    double raioAUX = leDouble();

    System.out.println("\nPreço por km");
    double precoPorKMaux = leDouble();

    System.out.println("\nTransporte de medicamentos (Yes/No)");
    String transporteMedicamentosAUX = leYesOrNo();

    System.out.println("\nLocalização X");
    float gpsX = leFloat();
    System.out.println("\nLocalização Y");
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY || raioAUX == Double.POSITIVE_INFINITY || precoPorKMaux == Double.POSITIVE_INFINITY || transporteMedicamentosAUX.equals("null")) {
      clearScreen();
      System.out.println("Um dos valores que introduziu é inválido. Introduza apenas números quando necessário.\n\n");
      mostrarMenuSignUpT();
    }
    else {
      this.raio = raioAUX;
      this.precoPorKm = precoPorKMaux;
      this.GPS = new Ponto(gpsX, gpsY);
      if (transporteMedicamentosAUX.equals("true"))
        this.transporteMedicamentos = true;
      else
        if (transporteMedicamentosAUX.equals("false"))
          this.transporteMedicamentos = false;
    }
  }

  // Método que imprime e lê o menu de signup de um voluntário
  public void mostrarMenuSignUpV() {
    System.out.println("Nome");
    this.nome = leString();

    System.out.println("\nRaio");
    double raioAUX = leDouble();

    System.out.println("\nTransporte de medicamentos (Yes/No)");
    String transporteMedicamentosAUX = leYesOrNo();

    System.out.println("\nLocalização X");
    float gpsX = leFloat();
    System.out.println("\nLocalização Y");
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY || raioAUX == Double.POSITIVE_INFINITY || transporteMedicamentosAUX.equals("null")) {
      clearScreen();
      System.out.println("Um dos valores que introduziu é inválido. Introduza apenas números quando necessário.\n\n");
      mostrarMenuSignUpV();
    }
    else {
      this.raio = raioAUX;
      this.GPS = new Ponto(gpsX, gpsY);
      if (transporteMedicamentosAUX.equals("true"))
        this.transporteMedicamentos = true;
      else
        if (transporteMedicamentosAUX.equals("false"))
          this.transporteMedicamentos = false;
    }
  }


  /* GET functions */

  public String getNome() {
    return this.nome;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public String getEmail() {
    return this.email;
  }

  public Ponto getGPS() {
    return this.GPS;
  }

  public double getRaio() {
    return this.raio;
  }

  public boolean getTransporteMedicamentos() {
    return this.transporteMedicamentos;
  }

  public String getNIF() {
    return this.NIF;
  }

  public double getPrecoPorKM() {
    return this.precoPorKm;
  }
}