package Guiao2.ex4v2;

public class Conta {
    /** Saldo de cada conta **/
    private double saldo;

    /**
     * Metodo construtor
     *
     * @param saldo : saldo inicial da conta
     */
    public Conta(double saldo){
        this.saldo = saldo;
    }

    /**
     * Consultar o saldo da conta
     *
     * @return saldo da conta
     */
    public synchronized double consultar(){
        return this.saldo;
    }

    /**
     * Levantar um montante da conta
     *
     * @param montante : montante a levantar
     */
    public void credito(double montante) {
        this.saldo -= montante;
    }

    /**
     * Depositar montante na conta
     *
     * @param montante
     */
    public void debito(double montante){
        this.saldo += montante;
    }
}
