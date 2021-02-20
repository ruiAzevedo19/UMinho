package Model.Branch;

import Model.Answer.BuyersProducts;
import Model.Answer.ProdsUnitsClients;
import Model.Answer.QuantProd;
import Model.Interface.IProduct;

import java.io.Serializable;
import java.util.*;

public interface IClientInfo extends Serializable {
    /**
     * @param month mes a procurar
     * @return o numero de compras num determinado mes
     */
    int getPurchased(int month);

    /**
     * Adiciona os dados relativos a uma venda
     * @param p produto
     * @param month mes em que ocorreu a venda
     * @param units unidades compradas
     * @param price preco unitario do produto
     */
    void addSale(IProduct p, int month, int units, double price);

    /**
     * @return lista dos produtos de um cliente dividida por mes
     */
    List<List<IProduct>> getProducts();

    /**
     * @return devolve um tuplo com o total comprado e total gasto
     */
    AbstractMap.SimpleEntry<List<Integer>,List<Double>> clientBuyingInfo();

    /**
     * @param prod produto
     * @return devolve um array com o total comprado de um produto em todos os meses pelo cliente
     */
    List<Integer> getProductPurchasedInfo(IProduct prod);

    /**
     * @return devolve um árvore com os produtos mais comprados por um cliente por ordem decrescente de quantidade e,
     * para quantidades iguais, por ordem alfabética dos códigos
     */
    TreeSet<QuantProd> getMostPurchasedProducts();

    /**
     * @return devolve um map com os produtos comprados com a quantidade comprada associada a cada produto
     */
    Map<String,Integer> getProdsQuant();

    /**
     * @return devolve um tuplo com a informação do produto caso ele exista
     */
    IClientProdInfo getProductClientInfo(IProduct p);

    /**
     * @return true se o produto existe, falso caso contrário
     */
    boolean productExist(IProduct p);

    /**
     * @return toda a faturação do cliente
     */
    double getAllMoneySpent();

    /**
     * @param bp estrutura com produtos dos clientes
     */
    void addBuyerProducts(String c, BuyersProducts bp);

    /**
     * @return Devolve a correspodencia de produtos e quantidades compradas
     */
    Map<String,Integer> getProdUnits();
}
