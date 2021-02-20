package trazAqui;

// Exceção para impedir fazer login com um email não existente
public class EmailDoesNotExist extends Exception {
  public EmailDoesNotExist(String message) {
      super(message);
  }
}