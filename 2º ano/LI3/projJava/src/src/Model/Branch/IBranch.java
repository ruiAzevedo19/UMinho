package Model.Branch;

import Model.Answer.*;
import Model.Interface.IClient;
import Model.Interface.IProduct;
import Model.Sale;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface IBranch extends Serializable {
    /**
     * @return numero de clientes que compraram numa determinada filial
     */
    int getNrClients();

    /**
     * @return numero total de vendas de uma determinada filial
     */
    int getNrSales();

    /**
     * @param month mes a procurar
     * @return numero de clientes compradores de um determinado mes
     */
    int getClientsMonth(int month);

    /**
     * @return lista do numero de clientes que compraram em cada mes
     */
    int[] getClientsMonth();

    /**
     * @return faturacao por mes
     */
    double[] getBillingMonth();

    /**
     * @param month mes a procurar
     * @return numero de vendas de um determinado mes
     */
    int getSalesMonth(int month);

    /**
     * Adiciona informacao relativa a uma venda
     *
     * @param s venda a adicionar
     */
    void addSale(Sale s);

    /**
     * @return devolve os clientes de uma filial
     */
    List<IClient> getClients();

    /**
     * Devolve o numero de vendas e o numero de clientes distintos num determinado mes
     *
     * @param month mes a procurar
     * @return numero de vendas e clientes distintos
     */
    AbstractMap.SimpleEntry<Integer, Integer> getClientSales(int month);

    /**
     * @param month mes a procurar
     * @return lista de todos os clientes que compraram num determinado mes
     */
    List<IClient> getClientsPurchasedMonth(int month);

    /**
     * @param c cliente a procurar
     * @return retorna a lista dos codigos dos produtos comprados por um determinado cliente, dividido por mes
     */
    List<List<IProduct>> getClientProducts(IClient c);

    /**
     * @param c codigo do produto a procurar
     * @return tuplo com total comprado e total gasto
     */
    AbstractMap.SimpleEntry<List<Integer>, List<Double>> getPurchasedSpentByClient(IClient c);

    /**
     * @param p codigo do produto a procurar
     * @return retorna lista dos códigos dos clientes que compraram um determinado produto, dividido por mês
     */
    List<List<IClient>> getProductClients(IProduct p);

    /**
     * @return devolve um map com os produtos comprados por um cliente com a quantidade associada a cada produto
     */
    Map<String,Integer> getClientProdsQuant(IClient c);


    /**
     * @param p codigo do produto a procurar
     * @param cmbp TreeSet com codigos dos cliente que compraram o produto associando a cada cliente as unidades compradas e faturação
     */
    void getclientsMostBoughtProduct(IProduct p, TreeSet<ClientQuantBillings> cmbp);

    /**
     * @param puc estrutura com produtos associados às suas quantidades e seus clientes
     */
    void addClientsProducts(ProdsUnitsClients puc);

    /**
     * @return lista com os clientes que mais compraram associando o respectivo valor da faturação
     */
    List<AbstractMap.SimpleEntry<String,Double>> greatests3Buyers();

    /**
     * @param bc  estrutura com os produtos dos clientes
     */
    void addBuyersProducts(BuyersProducts bc);

    /**
     * @return devolve a correspondecia produtos e unidadew compradas
     */
    void getProdsUnits(ProductsUnitsClients puc);
}
