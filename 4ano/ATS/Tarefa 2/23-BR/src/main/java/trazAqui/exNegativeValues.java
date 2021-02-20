package trazAqui;

// Exceção para impedir a introdução de valores negativos ou iguais a zero
public class exNegativeValues extends Exception {
  public exNegativeValues(String message) {
    super(message);
  }
}