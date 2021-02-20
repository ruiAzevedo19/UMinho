package trazAqui;

public class Inicial extends Principal {
  
  public Inicial() {
    super();
  }
  
  // Método que imprime e lê a opção pretendida no menu inicial 
  public void mostrarMenuInicial() {
    System.out.println("1: Log in");
    System.out.println("2: Sign up");
    System.out.println("3: Leaderboards");
    System.out.println("4: Sair");
    System.out.println("\nNunca te esqueças que a tua primeira password é sempre 'TrazAqui'!!!");

    int opcaoAUX = leOpcao();
    if (opcaoAUX == Integer.MAX_VALUE) {
      clearScreen();
      System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
      System.out.print("\tTraz Aqui\n\n");
      mostrarMenuInicial();
    }
    else
      setOpcao(opcaoAUX);
  }
}