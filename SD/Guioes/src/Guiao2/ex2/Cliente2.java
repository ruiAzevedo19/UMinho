package Guiao2.ex2;

public class Cliente2 implements Runnable{
    /** Banco partilhado pelas threads **/
    private Banco banco;

    /**
     * Metodo construtor
     * @param banco : banco passado por parametro
     */
    public Cliente2(Banco banco){
        this.banco = banco;
    }

    /**
     * O cliente1 vai depositar 100 000 vezes 5euros
     */
    @Override
    public void run() {
        for(int i = 0; i < 100000; i++)
            banco.credito(0,5);
    }
}
