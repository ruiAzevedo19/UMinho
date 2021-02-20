package trazAqui;

// Exceção para impedir fazer login com um codigo não registado
public class exCodigoDoesNotExist extends Exception {
  public exCodigoDoesNotExist(String message) {
      super(message);
  }
}