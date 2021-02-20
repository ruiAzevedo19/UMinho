package trazAqui;

// Exceção para impedir fazer signup com uma plataforma de entrega já existente
public class PEAlreadyExists extends Exception {
  public PEAlreadyExists(String message) {
      super(message);
  }
}