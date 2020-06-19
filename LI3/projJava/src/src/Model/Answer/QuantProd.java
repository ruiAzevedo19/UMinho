package Model.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuantProd implements Comparable<QuantProd>{
    /* --- Variáveis de instância ----------------------------------------------------------------------------------- */
    private String codProd;
    private int purchased;
    private int clients;
    /* -------------------------------------------------------------------------------------------------------------- */

    public QuantProd(){
        codProd = "";
        purchased = 0;
        clients = 1;
    }

    public QuantProd(String code, int p){
        codProd = code;
        purchased = p;
        clients = 1;
    }

    public QuantProd(String code, int p, int c){
        codProd = code;
        purchased = p;
        clients = c;
    }

    /* --- Getters -------------------------------------------------------------------------------------------------- */

    public String getCodProd() {
        return codProd;
    }

    public int getPurchased() {
        return purchased;
    }

    public int getClients(){
        return clients;
    }

    /* --- Setters -------------------------------------------------------------------------------------------------- */

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    /* --- Funcionalidades ------------------------------------------------------------------------------------------ */

    public void addPurchase(int purchase) {
        this.purchased += purchase;
    }

    public void addClients(int clients){
        this.clients += clients;
    }

    public int compareTo(QuantProd p){
       return codProd.compareTo(p.getCodProd());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantProd quantProd = (QuantProd) o;
        return Objects.equals(codProd, quantProd.getCodProd());
    }

    @Override
    public int hashCode() {
        return codProd.hashCode();
    }

    @Override
    public String toString(){
        return "(" + codProd + "," + purchased + ")";
    }
}
