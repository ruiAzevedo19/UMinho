package trazAqui;

// Exceção para impedir fazer signup com NIF invalido
public class exInvalidNIF extends Exception {
  public exInvalidNIF(String message) {
    super(message);
  }
}