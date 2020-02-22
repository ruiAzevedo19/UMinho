package Guiao5.ex3v2;

public class Main {
    public static void main(String[] args) {
        int R = 18;
        int W = 18;
        int T_reader = 100;
        int T_writer = 100;

        RWLock lock = new RWLock(3,3);
        //Thread[] readers = new Thread[R];
        //Thread[] writers = new Thread[W];
        Thread[] threads = new Thread[R + W];

        for(int i = 0; i < W + R; i++){
            if( i % 2 == 0 )
                threads[i] = new Thread(new Writer(lock,T_writer));
            else
                threads[i] = new Thread(new Reader(lock,T_writer));
        }

        for(int i = 0; i < W + R; i++){
            threads[i].start();
        }

        /**
        for(int i = 0; i < W; i++){
            writers[i] = new Thread(new Writer(lock,T_writer));
            writers[i].start();
        }

        for(int i = 0; i < R; i++){
            readers[i] = new Thread(new Reader(lock,T_reader));
            readers[i].start();
        }
        **/
        try{
            /**
            for(int i = 0; i < R; i++)
                readers[i].join();
            for(int i = 0; i < W; i++)
                writers[i].join();
             **/
            for(int i = 0; i < R + W; i++)
                threads[i].join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
