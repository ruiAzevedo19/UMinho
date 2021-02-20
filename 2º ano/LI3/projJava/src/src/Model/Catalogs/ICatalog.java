package Model.Catalogs;


import java.io.Serializable;
import java.util.*;

public interface ICatalog<T> extends Serializable{

    /**
     * @return numero de elementos do catalogo
     */
    int getSize();

    /**
     * Verifica se um código é válido com base na profundidade das letras e da gama de valores que o numero do codigo
     * pode ter
     * @param code codigo a verificar
     * @return true se o codigo e valido, false caso contrario
     */
    boolean isValid(String code);

    /**
     * Verifica se existe um codigo no catalogo
     * @param elem tipo a fazer procura
     * @return true se existe, false caso contrario
     */
    boolean contains(T elem);

    /**
     * Adiciona um elemento ao catalogo, previamente verificado
     * @param elem tipo a inserir
     */
    void addElem(T elem);

    /**
     * @return lista dos elementos
     */
    List<T> getElems();
}
