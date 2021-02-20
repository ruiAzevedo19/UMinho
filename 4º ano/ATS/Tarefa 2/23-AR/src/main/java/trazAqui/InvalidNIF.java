package trazAqui;

// Exceção para impedir fazer signup com NIF invalido
public class InvalidNIF extends Exception {
  public InvalidNIF(String message) {
    super(message);
  }
}