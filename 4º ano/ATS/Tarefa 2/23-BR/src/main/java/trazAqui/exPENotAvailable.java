package trazAqui;

// Exceção para impedir a seleção de plataformas de entrega indisponíveis para uma entrega
public class exPENotAvailable extends Exception {
  public exPENotAvailable(String message) {
      super(message);
  }
}