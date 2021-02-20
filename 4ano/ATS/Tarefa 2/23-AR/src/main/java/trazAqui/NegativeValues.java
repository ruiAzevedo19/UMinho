package trazAqui;

// Exceção para impedir a introdução de valores negativos ou iguais a zero
public class NegativeValues extends Exception {
  public NegativeValues(String message) {
    super(message);
  }
}