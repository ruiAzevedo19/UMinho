package Guiao1.ex2v1;

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
     */
    public void increment(){
        this.c++;
    }
}
