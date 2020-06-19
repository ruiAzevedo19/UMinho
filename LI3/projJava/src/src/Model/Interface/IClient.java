package Model.Interface;

import java.io.Serializable;
import java.util.Comparator;

public interface IClient extends Serializable, Comparable<IClient> {

    /**
     * @return clonagem de um cliente
     */
    IClient clone();

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
     * @param c cliente a comparar
     * @return true se o codigo dos clientes for igual, false caso contrario
     */
    boolean equals(Object c);

    /**
     * @return codigo de hash
     */
    int hashCode();
}
