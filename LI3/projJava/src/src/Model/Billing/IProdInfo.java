package Model.Billing;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;

public interface IProdInfo extends Serializable {

    /**
     * @return número de unidades compradas deste produto
     */
    int getUnits();

    /**
     * @return true se um produto foi comprado, false caso contrario
     */
    public boolean wasBought();

    /**
     * Faz update com os dados de uma venda
     * @param price preco do produto
     * @param units unidades do produto
     * @param month mes da compra
     * @param branch filial em que foi comprado
     */
    public void updateSale(float price, int units, int month, int branch);

    /**
     * @return devolve um tuplo com o numero de vezes que o produto foi comprado e total faturado
     */
    AbstractMap.SimpleEntry<List<Integer>,List<Double>> prodPurchBillingsInfo();

    /**
     * @return devolve o total faturado dividido por mês e filial
     */
    List<List<Double>> getBillingsMonthBranchList();


}

