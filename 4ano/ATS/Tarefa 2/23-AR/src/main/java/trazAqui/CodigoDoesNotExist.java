package trazAqui;

// Exceção para impedir fazer login com um codigo não registado
public class CodigoDoesNotExist extends Exception {
  public CodigoDoesNotExist(String message) {
      super(message);
  }
}