package trazAqui;

// Exceção para impedir a realização de uma encomenda numa loja inexistente
public class exLojaDoesNotExist extends Exception {
  public exLojaDoesNotExist(String message) {
      super(message);
  }
}