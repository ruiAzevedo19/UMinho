package Model.Interface;

import Model.Answer.*;
import Model.Stats;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public interface ISGV extends Serializable {

    /**
     * @return true se as stats estão carregadas, false caso contrario
     */
    boolean isStatLoaded();

    /**
     * @return true se as estruturas foram carregadas, false caso contrario
     */
    boolean isSGVLoaded();

    /**
     * @return numero de filiais
     */
    int getNrBranches();

    /**
     * Carrega o estado com o ficheiro por omissao
     * @return estrutura carregada
     * @throws IOException
     * @throws ClassNotFoundException
     */
    ISGV loadStateDefault() throws IOException, ClassNotFoundException;

    /**
     * @return devolve o ultimo ficheiro de vendas lido
     */
    String getLastSaleFile();

    /**
     * @return numero de cliente validos
     */
    int getVClients();

    /**
     * @return numero de cliente invalidos
     */
    int getIClients();

    /**
     * @return numero de produtos validos
     */
    int getVProducts();

    /**
     * @return numero de produtos invalidos
     */
    int getIProducts();

    /**
     * @return numero de vendas validas
     */

    int getVSales();

    /**
     * @return numero de vendas invalidas
     */
    int getISales();

    /**
     * Guarda o estado atual das estruturas no ficheiro default
     */
    void saveStateDefault();

    /**
     *
     * @param month mes a procurar
     * @return vendas e clientes que as fizeram
     */
    SalesClients getSalesClientsGlobalBranch(int month);

    /**
     * @param c cliente
     * @return informacao de compras de um cliente
     */
    PurchProdsSpent getClientBuyingInfo(IClient c);

    /**
     * @param code codigo de cliente
     * @return true se o cliente existe no sistema, false caso contrario
     */
    boolean containsClient(String code);

    /**
     *
     * @param code codigo do produto
     * @return true se o produto existe no sistema, false caso contrario
     */
    boolean containsProduct(String code);

    /**
     * @return classe das estatisticas
     */
    Stats getStats();

    /**
     * Carrega as estruturas com os ficheiros default
     */
    void loadSGVDefault() throws IOException;

    /**
     * Carrega as estruturas com os dados dos ficheiros
     * @param clientsPath caminho para o ficheiro de clientes
     * @param productsPath caminho para o ficheiro de produtos
     * @param salesPath caminho para o ficheiro das vendas
     */
    void loadSGVFromFiles(String clientsPath, String productsPath, String salesPath) throws IOException;

    /**
     * Guarda o estado das estruturas num ficheiro objecto
     * @param file nome do ficheiro
     */
    void saveState(String file);

    /**
     * Carrega as estruturas com os dados no ficheiro objecto
     * @param file ficheiro objecto
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    ISGV loadState(String file) throws FileNotFoundException, IOException, ClassNotFoundException;

    /**
     * @return lista dos produtos nunca comprados
     */
    List<IProduct> getProductsNeverBought();

    /**
     * @return Dado um produto, quantas vezes foi comprado, por quantos clientes diferentes e o total faturado, divido por mês
     */
    PurchClientsBillings getProductBuyingInfo(IProduct p);

    /**
     * @return Dado um cliente, determinar a lista de códigos de produtos que mais comprou (e quantos),
     * ordenada por ordem decrescente de quantidade e, para quantidades iguais, por ordem alfabética dos códigos
     */
    TreeSet<QuantProd> mostPurchasedProducts(IClient c);

    /**
     * @return faturação total de todos os produtos, mês a mês e filial a filial
     */
    Map<String,List<List<Double>>> prodTotalBillings();

    /**
     * @param p produto
     * @param xClients numero do conjunto de clientes
     * @return Dado um código de um produto, determinar o conjunto dos X clientes que mais o compraram e, para cada um, qual o valor gasto
     */
    List<ClientQuantBillings> clientsMostBoughtProduct(IProduct p, int xClients);

    /**
     * @return devolve os melhores compradores
     */
    List<List<AbstractMap.SimpleEntry<String,Double>>> greatests3BuyersBraches();

    /**
     * @param xProds numero do conjunto de produtos
     * @return conjunto dos X produtos mais vendidos associado ao número total de distintos clientes que o compraram
     */
    Set<QuantProd> mostBoughtProducts(int xProds);
    //Map<QuantProd,Integer> mostBoughtProducts(int xProds);

    /**
     * @param xBuyers numero de compradores
     * @return faturação dos 3 maiores compradores, filial a filial
     */
    List<AbstractMap.SimpleEntry<String,Integer>> buyersBoughtMoreProducts(int xBuyers);
}
