package trazAqui;

// Exceção para impedir fazer signup com um email já existente
public class exEmailAlreadyExists extends Exception {
  public exEmailAlreadyExists(String message) {
      super(message);
  }
}