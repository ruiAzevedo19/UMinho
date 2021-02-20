package Guiao4.ex3;

public class Main {
    public static void main(String[] args) {
        Barreira barreira = new Barreira(5);
        int nr_threads = 20;
        Thread[] threads = new Thread[nr_threads];

        for(int i = 0; i < nr_threads; i++)
            threads[i] = new Thread(new ThreadJob(barreira));

        for(int i = 0; i < nr_threads; i++)
            threads[i].start();

        try{
            for(int i = 0; i < nr_threads; i++)
                threads[i].join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
