package Model;

import Model.Interface.IClient;
import Model.Interface.IProduct;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Client implements IClient, Serializable, Comparable<IClient> {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String code;         /* Codigo do cliente                                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    /**
     * Método construtor
     * @param code código do cliente
     */
    public Client(String code){
        this.code = code;
    }
    /**
     * Método construtor por cópia
     * @param c Cliente a copiar
     */
    public Client(Client c){
        this.code = c.getCode();
    }

    /**
     * Método clone
     * @return uma copia do Cliente
     */
    public Client clone(){
        return new Client(this);
    }

    /* --- Getter --------------------------------------------------------------------------------------------------- */

    public String getCode() {
        return code;
    }

    /* --- Funcionalidade ------------------------------------------------------------------------------------------- */

    @Override
    public String toString(){
        return this.code;
    }

    @Override
    public boolean equals(Object c){
        if( this == c )
            return true;
        if( c == null || getClass() != c.getClass() )
            return false;
        Client client = (Client)c;
        return code.equals(client.getCode());
    }

    public int compareTo(IClient p){
        return code.compareTo(p.getCode());
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
