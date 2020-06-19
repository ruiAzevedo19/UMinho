package View.Interface;

public interface IRequestView {
    /**
     * Imprime o tempo de uma query
     * @param time tempo da query
     */
    void showTime(double time);

    /**
     * Imprime o header do menu de uma query
     * @param h header a imprimir
     */
    void printQueryHeader(String h);

    /**
     * Imprime uma mensagem
     * @param o mensagem a imprimir
     */
    void printMessage(Object o);

    /**
     * Aprensenta um pedido
     * @param o pedido
     */
    public void show(Object o);

    /**
     * Limpa o ecra
     */
    void clearScreen();

}
