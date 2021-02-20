package trazAqui;

public class SignUp extends Principal {

  private static final String LOCALIZACAO_X = "\nLocalização X";
  private static final String LOCALIZACAO_Y = "\nLocalização Y";
  private String nome;
  private String codigo;
  private String email;
  private Ponto gps;
  private double raio;
  private boolean transporteMedicamentos;
  private String nif;
  private double precoPorKm;

  public SignUp() {
    super();
    this.nome = "";
    this.codigo = "";
    this.email = "";
    this.gps = new Ponto();
    this.raio = 0;
    this.transporteMedicamentos = false;
    this.nif = "";
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

    System.out.println( LOCALIZACAO_X );
    float gpsX = leFloat();
    System.out.println( LOCALIZACAO_Y );
    float gpsY = leFloat();

    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY) {
      clearScreen();
      System.out.println("Introduziu uma localização inválida. Introduza apenas números.\n\n");
      mostrarMenuSignUpU();
    }
    else
      this.gps = new Ponto(gpsX, gpsY);
  }

  // Método que imprime e lê o menu de signup de uma loja
  public void mostrarMenuSignUpL() {
    System.out.println("Nome");
    this.nome = leString();

    System.out.println( LOCALIZACAO_X );
    float gpsX = leFloat();
    System.out.println( LOCALIZACAO_Y );
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY) {
      clearScreen();
      System.out.println("Introduziu uma localização inválida. Introduza apenas números.\n\n");
      mostrarMenuSignUpL();
    }
    else
      this.gps = new Ponto(gpsX, gpsY);
  }

  // Método que imprime e lê o menu de signup de uma transportadora
  public void mostrarMenuSignUpT() {
    System.out.println("Nome");
    this.nome = leString();

    System.out.println("\nNIF");
    this.nif = leString();

    System.out.println("\nRaio");
    double raioAUX = leDouble();

    System.out.println("\nPreço por km");
    double precoPorKMaux = leDouble();

    System.out.println("\nTransporte de medicamentos (Yes/No)");
    String transporteMedicamentosAUX = leYesOrNo();

    System.out.println( LOCALIZACAO_X );
    float gpsX = leFloat();
    System.out.println( LOCALIZACAO_Y );
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY || raioAUX == Double.POSITIVE_INFINITY || precoPorKMaux == Double.POSITIVE_INFINITY || transporteMedicamentosAUX.equals("null")) {
      clearScreen();
      System.out.println("Um dos valores que introduziu é inválido. Introduza apenas números quando necessário.\n\n");
      mostrarMenuSignUpT();
    }
    else {
      this.raio = raioAUX;
      this.precoPorKm = precoPorKMaux;
      this.gps = new Ponto(gpsX, gpsY);
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

    System.out.println( LOCALIZACAO_X );
    float gpsX = leFloat();
    System.out.println( LOCALIZACAO_Y );
    float gpsY = leFloat();
    
    if (gpsX == Float.POSITIVE_INFINITY || gpsY == Float.POSITIVE_INFINITY || raioAUX == Double.POSITIVE_INFINITY || transporteMedicamentosAUX.equals("null")) {
      clearScreen();
      System.out.println("Um dos valores que introduziu é inválido. Introduza apenas números quando necessário.\n\n");
      mostrarMenuSignUpV();
    }
    else {
      this.raio = raioAUX;
      this.gps = new Ponto(gpsX, gpsY);
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

  public Ponto getGps() {
    return this.gps;
  }

  public double getRaio() {
    return this.raio;
  }

  public boolean getTransporteMedicamentos() {
    return this.transporteMedicamentos;
  }

  public String getNif() {
    return this.nif;
  }

  public double getPrecoPorKM() {
    return this.precoPorKm;
  }
}