package Model.Billing;

import Model.Answer.ProdsUnitsClients;
import Model.Interface.IProduct;
import Model.Sale;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public interface IBilling extends Serializable {

    /**
     * @return numero de compras por mes
     */
    int[] getTotalSales();

    /**
     * @return faturacao total
     */
    double getTotalBilling();

    /**
     * @param month mes a procurar
     * @return numero de vendas de um determinado mes
     */
    public int getTotalSalesByMonth(int month);

    /**
     * Adiciona um produto
     * @param code codigo do produto
     */
    void addProduct(String code);

    /**
     * Adiciona os dados de uma venda
     * @param s venda a adicionar
     */
    void addSale(Sale s);

    /**
     * @return número de produtos comprados
     */
    long getNumberProductsBought();

    /**
     * @return produtos nunca comprados ordenados alfabeticamente
     */
    List<IProduct> getProductsNeverBought();

    /**
     * @return devolve um tuplo com o numero de vezes que o produto foi comprado e total faturado
     */
    AbstractMap.SimpleEntry<List<Integer>,List<Double>> prodPurchBilings(IProduct p);

    /**
     * @return devolve a faturação total de todos os produtos, mês a mês e filial a filial
     */
    Map<String,List<List<Double>>> getProdsBillingsMonthBranch();

    /**
     * Verifica se o produto existe na faturação
     * @param p produto a verificar
     * @return true se o produto existe, false se não existe
     */
    boolean productExist(IProduct p);

    /**
     * @param puc onde são colocados os produtos com as suas quantidades
     * @param xProds tamanho do conjunto de produtos mais vendidos
     */
    void getXProductsMostBought(ProdsUnitsClients puc, int xProds);
}
