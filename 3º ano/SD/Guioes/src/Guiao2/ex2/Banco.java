package Guiao2.ex2;

import java.util.Arrays;

public class Banco {
    /** Contas do banco **/
    private double[] contas;

    /**
     * Metodo construtor
     * @param n : numero de contas a criar
     */
    public Banco(int n){
        this.contas = new double[n];
        Arrays.fill(this.contas, 0);
    }

    /**
     * Consultar o saldo da conta c
     * @param c : conta a consultar
     * @return  : saldo da conta
     */
    public synchronized double consultar(int c){
        return this.contas[c];
    }

    /**
     * Levantar montante v da conta c
     * @param c : conta a levantar montante
     * @param v : montante do credito
     */
    public synchronized void credito(int c, double v){
        this.contas[c] -= v;
    }

    /**
     * Depositar montante v na conta c
     * @param c : Conta a depositar o montante
     * @param v : Montante a depositar
     */
    public synchronized void debito(int c, double v){
        this.contas[c] += v;
    }
}
