package Guiao2.ex3;

public class Cliente1 implements Runnable{
    /** Banco partilhado pelas threads **/
    private Banco banco;

    /**
     * Metodo construtor
     * @param banco : banco passado por parametro
     */
    public Cliente1(Banco banco){
        this.banco = banco;
    }

    /**
     * O cliente1 vai depositar 100 000 vezes 5euros
     */
    @Override
    public void run() {
        banco.transferir(0, 1, 1000);
    }
}
