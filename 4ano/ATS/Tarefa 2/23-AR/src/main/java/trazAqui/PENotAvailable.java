package trazAqui;

// Exceção para impedir a seleção de plataformas de entrega indisponíveis para uma entrega
public class PENotAvailable extends Exception {
  public PENotAvailable(String message) {
      super(message);
  }
}