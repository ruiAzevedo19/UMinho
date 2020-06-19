package Controller;

import java.util.List;

public interface INavegator {

    /**
     * Define de é necessário usar titulo nas tabelas
     */
    void setTotal();

    /**
     * @return devolve os cabecalhos superiores
     */
    String[] getTopHeaders();

    /**
     * @return devolve se a tablea tem titulo
     */
    boolean isHasTitle();

    /**
     * @return devolve o numero maximo de paginas
     */
    int getMaxPage();

    /**
     * @return devolve o tamanho total de uma pagina
     */
    int getPageSize();

    /**
     * @return devolve a pagina atual
     */
    int getCurrentPage();

    /**
     * @return Devolve o o numero de elementos total
     */
    int getSize();

    /**
     * @return devolve se é necessário colocar os valores totais na tabela
     */
    boolean isTotal();

    /**
     * @return devolve o titulo de uma tabela
     */
    Object getTitle();

    /**
     * @return devolve os elementos da pagina atual num array de objectos
     */
    Object[][] convert();

    /**
     * @param action acao a executar
     * @return devolve o numero de uma pagina apos ser executada uma acao
     * */
    boolean getPage(String action);
}
