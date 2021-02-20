package trazAqui;

// Exceção para impedir fazer login com um email não existente
public class exEmailDoesNotExist extends Exception {
  public exEmailDoesNotExist(String message) {
      super(message);
  }
}