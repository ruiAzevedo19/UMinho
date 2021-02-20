package Guiao5.ex3v2;

public class Reader implements Runnable {
    private RWLock lock;
    private int T_reader;

    public Reader(RWLock lock, int T_reader) {
        this.lock = lock;
        this.T_reader = T_reader;
    }

    /**
     * Invocacao do metodo read
     */
    @Override
    public void run() {
        try{
            lock.readLock();
            System.out.println("Thread " + Thread.currentThread().getId() + ": Reading ...");
            Thread.sleep(T_reader);
            lock.readUnlock();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
