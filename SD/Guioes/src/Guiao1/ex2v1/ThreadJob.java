package Guiao1.ex2v1;

public class ThreadJob implements Runnable{
    private Counter c;  /** Contador partilhado pelas threads (memoria partilhada)**/
    private int I;      /** Numero de vezes que cada thread vai incrementar o contador **/

    /**
     * Metodo construtor
     * @param c : contador passado como parametro
     */
    public ThreadJob(Counter c, int I){
        this.c = c;
        this.I = I;
    }

    /**
     * I invocacoes da funcao incremento
     */
    @Override
    public void run() {
        for(int i = 0; i < this.I; i++)
            c.increment();
    }
}
