package Guiao2.ex1v2;

public class Counter {
    /**
     * Contador a ser incrementado
     * Como a variavel e volatile, as escritas na variavel vao ser na memoria partilhada e nao em cache
     *
     **/
    public volatile int c;

    /**
     * Método construtor
     * @param ci : valor initial do contador
     */
    public Counter(int ci){
        this.c = ci;
    }
}

