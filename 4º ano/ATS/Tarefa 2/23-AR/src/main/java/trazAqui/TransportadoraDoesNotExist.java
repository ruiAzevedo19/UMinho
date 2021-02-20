package trazAqui;

// Exceção para impedir a realização de uma entrega com uma transportadora inexistente
public class TransportadoraDoesNotExist extends Exception {
  public TransportadoraDoesNotExist(String message) {
      super(message);
  }
}