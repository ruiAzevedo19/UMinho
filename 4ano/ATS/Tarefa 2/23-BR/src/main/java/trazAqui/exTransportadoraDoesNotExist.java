package trazAqui;

// Exceção para impedir a realização de uma entrega com uma transportadora inexistente
public class exTransportadoraDoesNotExist extends Exception {
  public exTransportadoraDoesNotExist(String message) {
      super(message);
  }
}