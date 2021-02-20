package trazAqui;

// Exceção para impedir a realização do transporte de uma encomenda inexistente
public class EncomendaDoesNotExist extends Exception {
  public EncomendaDoesNotExist(String message) {
      super(message);
  }
}