package Guiao1.ex1;

public class ThreadJob implements Runnable {
    private int I;

    /**
     * Método construtor
     * @param I : limite superior do intervalo
     */
    public ThreadJob(int I){
        this.I = I;
    }

    /**
     * Cada thread vai imprimir numeros no intervalo [1,I]
     */
    @Override
    public void run() {
        for(int i = 1; i <= I; i++)
            System.out.println("Thread " + Thread.currentThread().getId() + " := " + i);
    }
}
