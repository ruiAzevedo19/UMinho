package trazAqui;

// Exceção para impedir a efetuação de um login, se a password não corresponder com a que esta armazenada no sistema
public class PWIncorrect extends Exception {
  public PWIncorrect(String message) {
      super(message);
  }
}