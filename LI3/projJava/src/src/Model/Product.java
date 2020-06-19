package Model;

import Model.Interface.IProduct;

import java.io.Serializable;
import java.util.Objects;

public class Product implements IProduct, Serializable,Comparable<IProduct> {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String code;         /* Codigo do produto                                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Método construtor
     * @param code código do cliente
     */
    public Product(String code){
        this.code = code;
    }

    /**
     * Método construtor por cópia
     * @param p Produto a copiar
     */
    public Product(Product p){
        this.code = p.getCode();
    }

    /**
     * Método clone
     * @return uma copia do Produto
     */
    public Product clone(){
        return new Product(this);
    }

    /* --- Getter --------------------------------------------------------------------------------------------------- */

    public String getCode() {
        return code;
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    @Override
    public String toString(){
        return this.code;
    }

    @Override
    public boolean equals(Object p) {
        if( this == p )
            return true;
        if( p == null || getClass() != p.getClass() )
            return false;
        Product product = (Product)p;
        return code.equals(product.getCode());
    }

    public int compareTo(IProduct p){
        return code.compareTo(p.getCode());
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
