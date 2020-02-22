package Guiao2.ex3;

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
     *
     * @param c : conta a consultar
     * @return  : saldo da conta
     */
    public synchronized double consultar(int c){
        return this.contas[c];
    }

    /**
     * Levantar um determinado montante da conta c
     *
     * @param c : conta a levantar montante
     * @param montante : montante do credito
     */
    public synchronized void credito(int c, double montante){
        this.contas[c] -= montante;
    }

    /**
     * Depositar um determinado montante na conta c
     *
     * @param c : conta a depositar o montante
     * @param montante : montante a depositar
     */
    public synchronized void debito(int c, double montante){
        this.contas[c] += montante;
    }

    /**
     * Transferir um determinado montante de uma conta origem para uma conta destino
     *
     * @param conta_origem  : conta origem onde vai haver um credito
     * @param conta_destino : conta destino onde vai haver um debito
     * @param montante : montante a transferir
     *
     *    Apesar dos metodos credito e debito terem blocos de sincronizacao, o metodo transferir tambem tem que ter. Se nao
     * usarmos synchronized no metodo transferir, era possivel que entre o credito e o debito acontecesse outra operacao por outra
     * thread. Desta maneira, garantimos que o credito e o debito sao executados consecutivamente fazendo a operacao de transferencia atomica
     */
    public synchronized void transferir(int conta_origem, int conta_destino, int montante){
        this.credito(conta_origem,montante);
        this.debito(conta_destino,montante);
    }
}
