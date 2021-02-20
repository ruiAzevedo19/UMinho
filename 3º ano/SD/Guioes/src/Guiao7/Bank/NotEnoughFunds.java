package Guiao7.Bank;

public class NotEnoughFunds extends Exception{
    /** Metodo construtor **/
    public NotEnoughFunds(String msg){
        super(msg);
    }
}
