package trazAqui;

// Exceção para impedir a efetuação de um login, se a password não corresponder com a que esta armazenada no sistema
public class exPWIncorrect extends Exception {
  public exPWIncorrect(String message) {
      super(message);
  }
}