package View.Interface;

import Model.Stats;
import com.jakewharton.fliptables.FlipTableConverters;

public interface ITableView {

    /**
     * Imprime uma tabela simples
     * @param headers cabeçalhos da tabela
     * @param data dados da tabela
     */
    void simpleTable(String[] headers, Object[][] data);

    /**
     * Imprime uma tabela simples com side headers
     * @param sideHeaders sideheader da tabela
     * @param topHeaders top headers da tabela
     * @param data conteudo da tabela
     */
    void simpleTable(String[] sideHeaders, String[] topHeaders, Object[][] data);

    /**
     * Apresenta os dados de parsing
     * @param vCli clientes validos
     * @param iCli clientes invalidos
     * @param vProd produtos validos
     * @param iProd produtos invalidos
     * @param vSale vendas validas
     * @param iSale vendas invalidas
     * @param time tempo de parsing
     */
    void showParsingStats(int vCli, int iCli, int vProd, int iProd, int vSale, int iSale, double time);

    /**
     * Aprensenta dados estatisticos
     * @param s classe de estatisticas
     */
    void showStatsFull(Stats s);

    /**
     * Apresenta dados estatisticos
     * @param s classe de estatisticas
     * @param nrBranches numero de filiais
     */
    void showStats(Stats s, int nrBranches);

    /**
     * Aprenseta informacao por mes
     * @param data informacao a apresentar
     */
    void showMonthInfo(Object[][] data);

    /**
     * Imprime uma página
     * @param currentPage
     * @param maxPage
     * @param pageSize
     * @param total
     * @param totalSize
     * @param hasTitle
     * @param title
     * @param topHeaders
     * @param data
     */
    void printTable(int currentPage, int maxPage, int pageSize, boolean total, int totalSize, boolean hasTitle, String title, String[] topHeaders, Object[][] data );
}
