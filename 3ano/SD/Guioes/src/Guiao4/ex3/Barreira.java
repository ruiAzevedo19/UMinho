package Guiao4.ex3;

public class Barreira {
    private int N;       /** Número de threads para quebrar a barreira **/
    private int round;   /** Ronda **/
    private int arrival; /** Numero de threads a espera **/

    /**
     * Metodo construtor
     *
     * @param N : numero de threads a espera
     */
    public Barreira(int N){
        this.N = N;
        this.round = 0;
        this.arrival = 0;
    }

    /**
     *  Quando uma thread adquire o lock, e incrementado o numero de threads a espera assim como a cada thread e
     * atribuida uma ronda. As primeiras (N-1) ficam presas no ciclo à espera que chegue a N-ésima. Quando a thread N
     * adquire o lock, nao fica presa no ciclo e define o valor de threads a espera a 0 e incrementa a ronda.
     * De seguida, acorda todas as threads que estao a espera. Apenas as threads que nao verificam a condicao
     * (current_thread == this.round) passam à frente.
     *
     *      NOTA 1 : A condicao (current_round == this.round) garante que, quando a N-ésima thread faz notifyAll(),
     *      nenhuma thread que tenha chegado depois das primeiras N threads passe a frente das mesmas. Ou seja, se a
     *      thread (N + 1) adquire o lock fica presa em ambas as condições.
     *
     *      NOTA 2 : A condicao if(arrival == this.N){...} garante que apenas a N-ésima thread modifica o numero de
     *      threads a espera, a ronda e apenas esta thread notifica as outras todas.
     *
     * @throws InterruptedException
     */
    public synchronized void esperar() throws InterruptedException{
        int current_round = this.round;
        this.arrival++;

        System.out.println("--- Thread " + Thread.currentThread().getId() + " ----------");
        System.out.println("Round: " + current_round);
        System.out.println("Arrival: " + this.arrival);

        while( arrival < this.N && current_round == this.round )
            wait();

        if( arrival == this.N ) {
            arrival = 0;
            this.round++;
            notifyAll();
            System.out.println("/\\ Unlock next round");
        }
    }
}
