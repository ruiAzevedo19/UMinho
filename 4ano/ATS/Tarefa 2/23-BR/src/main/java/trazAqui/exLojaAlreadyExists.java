package trazAqui;

// Exceção para impedir fazer signup com uma loja já existente
public class exLojaAlreadyExists extends Exception {
  public exLojaAlreadyExists(String message) {
      super(message);
  }
}