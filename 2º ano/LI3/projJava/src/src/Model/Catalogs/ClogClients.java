package Model.Catalogs;

import Model.Comparator.ClientComparator;
import Model.Comparator.ProductComparator;
import Model.Interface.IClient;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ClogClients implements ICatalog<IClient>, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Set<IClient> clients;               /* Clientes do sistema                                                */
    private int size;                           /* Numero de produtos no sistema                                      */
    private int depth;                          /* Numero de caracteres diferentes do codigo                          */
    private int range;                          /* Gama de caracteres do codigo                                       */
    private int minRangeDigit;                  /* Numero minimo da parte numerica do codigo                          */
    private int maxRangeDigit;                  /* Numero maximo da parte numerica do codigo                          */
    /* -------------------------------------------------------------------------------------------------------------- */

    public ClogClients(int depth, int range, int minRangeDigit, int maxRangeDigit){
        clients = new TreeSet<>();
        this.depth = depth;
        this.range = range;
        this.minRangeDigit = minRangeDigit;
        this.maxRangeDigit = maxRangeDigit;
        size = 0;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public int getSize(){
        return size;
    }

    /* --- Functionality -------------------------------------------------------------------------------------------- */

    public boolean isValid(String code){
        boolean r = true;
        int i;
        for(i = 0; r && i < depth && i < code.length(); i++)
            if( !Character.isUpperCase(code.charAt(i)) )
                r = false;
        if( r ){
            for( ; r && i < code.length() ; i++)
                if( !Character.isDigit(code.charAt(i)) )
                    r = false;
        }
        if( r ){
            i = Integer.parseInt(code.substring(depth));
            if (i < minRangeDigit || i > maxRangeDigit )
                r = false;

        }
        return r;
    }

    public boolean contains(IClient c){
        return clients.contains(c);
    }

    public void addElem(IClient c){
        IClient client = c.clone();
        clients.add(client);
    }

    public List<IClient> getElems(){
        return clients.stream().map(IClient::clone).collect(Collectors.toList());
    }

}
