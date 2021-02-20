package trazAqui;

// Exceção para impedir fazer signup com um codigo já existente
public class exCodigoAlreadyExists extends Exception {
  public exCodigoAlreadyExists(String message) {
    super(message);
  } 
}