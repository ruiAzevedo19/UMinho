package View.Interface;

public interface IView {

    /**
     * Funcao de impressao sem parametros
     */
    void show();

    /**
     * Funcao de impressao com parametro
     * @param o parametro a imprimir
     */
    void show(Object o);
}
