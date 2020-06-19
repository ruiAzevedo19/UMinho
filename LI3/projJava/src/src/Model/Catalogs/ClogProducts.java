package Model.Catalogs;

import Model.Interface.IProduct;
import Model.Product;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ClogProducts implements ICatalog<IProduct>, Serializable {
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private Set<IProduct> products;             /* Produtos do sistema                                                */
    private int size;                           /* Numero de produtos no sistema                                      */
    private int depth;                          /* Numero de caracteres diferentes do codigo                          */
    private int range;                          /* Gama de caracteres do codigo                                       */
    private int minRangeDigit;                  /* Numero minimo da parte numerica do codigo                          */
    private int maxRangeDigit;                  /* Numero maximo da parte numerica do codigo                          */
    /* -------------------------------------------------------------------------------------------------------------- */

    public ClogProducts(int depth, int range, int minRangeDigit, int maxRangeDigit){
        this.depth = depth;
        this.range = range;
        this.minRangeDigit = minRangeDigit;
        this.maxRangeDigit = maxRangeDigit;
        products = new TreeSet<>();
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

    public boolean contains(IProduct p){
        return products.contains(p);
    }

    public void addElem(IProduct p){
        IProduct product = p.clone();
        products.add(product);
    }

    public List<IProduct> getElems(){
        return products.stream().map(IProduct::clone).collect(Collectors.toList());
    }

}
