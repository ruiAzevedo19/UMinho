package Guiao1.ex2v2;

public class ThreadJob implements Runnable{
    private Counter counter;  /** Contador partilhado pelas threads (memoria partilhada) **/
    private int I;            /** Numero de vezes que cada thread vai incrementar o contador **/

    /**
     * Metodo construtor
     * @param c : contador passado como parametro
     */
    public ThreadJob(Counter c, int I){
        this.counter = c;
        this.I = I;
    }

    /**
     * I invocacoes da funcao incremento
     */
    @Override
    public void run() {
        for(int i = 0; i < this.I; i++)
            counter.c++;
    }
}
