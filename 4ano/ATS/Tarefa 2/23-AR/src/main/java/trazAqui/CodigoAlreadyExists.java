package trazAqui;

// Exceção para impedir fazer signup com um codigo já existente
public class CodigoAlreadyExists extends Exception {
  public CodigoAlreadyExists(String message) {
    super(message);
  } 
}