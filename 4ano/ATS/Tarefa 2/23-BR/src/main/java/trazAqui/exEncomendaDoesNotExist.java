package trazAqui;

// Exceção para impedir a realização do transporte de uma encomenda inexistente
public class exEncomendaDoesNotExist extends Exception {
  public exEncomendaDoesNotExist(String message) {
      super(message);
  }
}