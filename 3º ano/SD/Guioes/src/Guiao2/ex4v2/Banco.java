package Guiao2.ex4v2;

import java.util.Arrays;

/**
 * Nesta versao, as contas sao sincrozinadas na classe banco usando o lock implicito das contas
 */

public class Banco {
    /** Contas do banco **/
    private Conta[] contas;

    /**
     * Metodo construtor
     * @param n : numero de contas a criar
     */
    public Banco(int n){
        this.contas = new Conta[n];
        Arrays.fill(this.contas, new Conta(0));
    }

    /**
     * Consultar o saldo da conta c
     *
     * @param c : conta a consultar
     * @return  : saldo da conta
     */
    public double consultar(int c){
        return this.contas[c].consultar();
    }

    /**
     * Levantar um determinado montante da conta c
     *
     * @param c : conta a levantar montante
     * @param montante : montante do credito
     */
    public void credito(int c, double montante){
        synchronized (this.contas[c]){
            this.contas[c].credito(montante);
        }
    }

    /**
     * Depositar um determinado montante na conta c
     *
     * @param c : conta a depositar o montante
     * @param montante : montante a depositar
     */
    public void debito(int c, double montante){
        synchronized (this.contas[c]){
            this.contas[c].debito(montante);
        }
    }

    /**
     * Transferir um determinado montante de uma conta origem para uma conta destino
     *
     * @param conta_origem  : conta origem onde vai haver um credito
     * @param conta_destino : conta destino onde vai haver um debito
     * @param montante : montante a transferir
     *
     */
    public void transferir(int conta_origem, int conta_destino, int montante){
        int min = Math.min(conta_origem,conta_destino);
        int max = Math.max(conta_origem,conta_destino);

        synchronized (this.contas[min]){
            synchronized (this.contas[max]){
                this.credito(conta_origem,montante);
                this.debito(conta_destino,montante);
            }
        }
    }
}

/**

  -> Se nao garantirmos ordem de locks sobre as contas pode acontecer a seguinte situacao de DeadLock :

                  (T1)
                    |
                    |      (T2)
                    |       |
                    |       |
                    v       |
 Lock Conta1 --------       |
                    |       v
                    |       -------- Lock Conta2
                    |       |
                    |       |
                    v       |
 Lock Conta2 --------       |
                    .       v
                    .       -------- Lock Conta1
                    .       .
 ---------------------------------------------------------

                    ZONA CRITICA

            . Credito
            . Debito

 ---------------------------------------------------------

 **/