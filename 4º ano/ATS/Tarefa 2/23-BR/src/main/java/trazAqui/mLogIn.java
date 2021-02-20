package trazAqui;

public class mLogIn extends mPrincipal {

  private String email;
  private String codigo;
  private String password;

  public mLogIn() {
    super();
    this.email = new String();
    this.codigo = new String();
    this.password = new String();
  }


    /* GERAL */

  // Método que imprime e lê a opção pretendida no menu de login e efetua o mesmo consoante o seu estatuto
  public void escolhaMenuGERAL() {
    System.out.print("1: Utilizador\n");
    System.out.print("2: Loja\n");
    System.out.print("3: Transportadora\n");
    System.out.print("4: Voluntário\n");
    System.out.print("\n5: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      escolhaMenuGERAL();
    }
    else
      setOpcao(opcaoAUX);
  }


    /* MENU UTILIZADOR */

  // Método que imprime e lê a opção pretendida no menu de login de cliente
  public void escolhaMenuLogIn() {
    System.out.print("\n1: Email");
    System.out.print("\n2: Código\n");
    System.out.print("\n3: Voltar atrás\n");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      escolhaMenuLogIn();
    }
    else
      setOpcao(opcaoAUX);
  }

  // Método que efetua o login de um cliente atraves do email 
  public void mostrarMenuLogInE() {
    System.out.println("Email");
    this.email = leString();

    System.out.println("\nPassword");
    this.password = leString();
  }

  // Método que efetua o login de um cliente atraves do código
  public void mostrarMenuLogInC() {
    System.out.println("Código");
    this.codigo = leString();

    System.out.println("\nPassword");
    this.password = leString();
  }


    /* MENU LOJAS/TRANSPORTADORAS/VOLUNTARIOS */

  // Método que efetua o login de uma plataforma de entrega/loja
  public void mostrarMenuLogInExtra() {
    System.out.print("Código\n");
    this.codigo = leString();

    System.out.print("\nPassword\n");
    this.password = leString();
  }

  
  /* GET functions */

  public String getEmail() {
    return this.email;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public String getPassword() {
    return this.password;
  }
}