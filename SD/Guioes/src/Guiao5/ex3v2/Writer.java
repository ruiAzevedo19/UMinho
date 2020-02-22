package Guiao5.ex3v2;

public class Writer implements Runnable {
    private RWLock lock;
    private int T_writer;

    public Writer(RWLock lock, int T_writer) {
        this.lock = lock;
        this.T_writer = T_writer;
    }

    /**
     * Invocacao do metodo write
     */
    @Override
    public void run() {
        try{
            lock.writeLock();
            System.out.println("Thread " + Thread.currentThread().getId() + ": Writing ...");
            Thread.sleep(T_writer);
            lock.writeUnlock();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
