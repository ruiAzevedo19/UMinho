package Model.Branch;

import java.io.Serializable;

public interface IClientProdInfo extends Serializable {
    /**
     * Adiciona os dados relativos a uma venda
     * @param month mes da venda
     * @param units unidades compradas
     * @param price preco unitario
     */
    public void addSale(int month, int units, double price);

    /**
     * @return devolve o numero de vezes que o produto foi comprado nos vários meses
     */
    int[] getPurchasedMonth();

    /**
     * @param month mes a procurar
     * @return devolve o numero de vezes que o produto foi comprado num mes
     */
    int getPurchasedByMonth(int month);

    /**
     * @return devolve o numero de vezes que o produto foi comprado
     */
    int getPurchased();

    /**
     * @return devolve o numero de unidades compradas deste produto
     */
    int getUnits();

    /**
     * @return devolve o faturação global do produto
     */
    double getBilling();
}
