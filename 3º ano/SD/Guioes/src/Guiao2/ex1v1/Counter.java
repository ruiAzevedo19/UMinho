package Guiao2.ex1v1;

public class Counter {
    /** Contador a ser incrementado **/
    private int c;

    /**
     * Método construtor
     * @param ci : valor initial do contador
     */
    public Counter(int ci){
        this.c = ci;
    }

    /**
     * @return Contador
     */
    public int getCounter(){
        return this.c;
    }

    /**
     * Metodo que incrementa o contador
     *
     *   O metodo esta protegido por um bloco synchrozined garantindo exclusidade mutua, i.e,
     * apenas uma thread de cada vez executa o codigo da zona critica.
     *   A sincronizacao garante que ao fim de uma thread executar este codigo, a escrita e feita na
     * memoria partilhada e nao na cache da thread
     */
    public synchronized void increment(){
        this.c++;
    }
}
