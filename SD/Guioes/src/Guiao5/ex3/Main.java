package Guiao5.ex3;

public class Main {
    public static void main(String[] args) {
        int R = 100;
        int W = 10;

        SharedMemory sharedMemory = new SharedMemory(0);

        Thread[] readers = new Thread[R];
        Thread[] writers = new Thread[W];

        for(int i = 0; i < W; i++){
            writers[i] = new Thread(new Writer(sharedMemory));
            writers[i].start();
        }

        for(int i = 0; i < R; i++){
            readers[i] = new Thread(new Reader(sharedMemory));
            readers[i].start();
        }


        try{
            for(int i = 0; i < R; i++)
                readers[i].join();
            for(int i = 0; i < W; i++)
                writers[i].join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

        System.out.println("--- Relatório final -----------------");
        System.out.println("Contador = " + sharedMemory.getCounter());
    }
}
