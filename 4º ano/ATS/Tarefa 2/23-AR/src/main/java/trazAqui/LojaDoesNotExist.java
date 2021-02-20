package trazAqui;

// Exceção para impedir a realização de uma encomenda numa loja inexistente
public class LojaDoesNotExist extends Exception {
  public LojaDoesNotExist(String message) {
      super(message);
  }
}