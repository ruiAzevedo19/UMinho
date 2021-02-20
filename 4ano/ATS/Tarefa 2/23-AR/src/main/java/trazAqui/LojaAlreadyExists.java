package trazAqui;

// Exceção para impedir fazer signup com uma loja já existente
public class LojaAlreadyExists extends Exception {
  public LojaAlreadyExists(String message) {
      super(message);
  }
}