package Model.Interface;

import java.io.Serializable;
import java.util.Comparator;

public interface IProduct extends Serializable, Comparable<IProduct> {

    /**
     * @return clonagem de um cliente
     */
    IProduct clone();

    /**
     * @return devolve o codigo de cliente
     */
    String getCode();

    /**
     * @return string da classe
     */
    String toString();

    /**
     * Compara dois clientes
     * @param p cliente a comparar
     * @return true se o codigo dos clientes for igual, false caso contrario
     */
    boolean equals(Object p);

    /**
     * Compara produtos
     * @param p produto
     * @return comparacao
     */
    int compareTo(IProduct p);

    /**
     * @return codigo de hash
     */
    int hashCode();
}
