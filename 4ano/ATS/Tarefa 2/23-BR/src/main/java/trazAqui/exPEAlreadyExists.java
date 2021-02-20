package trazAqui;

// Exceção para impedir fazer signup com uma plataforma de entrega já existente
public class exPEAlreadyExists extends Exception {
  public exPEAlreadyExists(String message) {
      super(message);
  }
}