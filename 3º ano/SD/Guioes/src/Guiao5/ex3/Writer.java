package Guiao5.ex3;

public class Writer implements Runnable {
    private SharedMemory sharedMemory;  /** Memoria partilhada entre as threads **/

    /**
     * Metodo construtor
     *
     * @param sharedMemory : memoria partilhada entre as threads
     */
    public Writer(SharedMemory sharedMemory) {
        this.sharedMemory = sharedMemory;
    }

    /**
     * Invocacao do metodo write
     */
    @Override
    public void run() {
        try{
            Thread.sleep(500);
            this.sharedMemory.write();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
