package Guiao4.ex3;

public class ThreadJob implements Runnable{
    private Barreira barreira;  /** Barreira partilhada entre as threads **/

    /**
     * Metodo construtor
     *
     * @param barreira : barreira partilhada entre as threads
     */
    public ThreadJob(Barreira barreira){
        this.barreira = barreira;
    }

    /**
     * Cada thread vai invocar o metodo esperar
     */
    @Override
    public void run() {
        try{
            barreira.esperar();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
