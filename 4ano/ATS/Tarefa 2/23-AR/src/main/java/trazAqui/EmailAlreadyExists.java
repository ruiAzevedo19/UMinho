package trazAqui;

// Exceção para impedir fazer signup com um email já existente
public class EmailAlreadyExists extends Exception {
  public EmailAlreadyExists(String message) {
      super(message);
  }
}