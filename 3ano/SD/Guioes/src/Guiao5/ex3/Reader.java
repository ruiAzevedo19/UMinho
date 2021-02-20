package Guiao5.ex3;

public class Reader implements Runnable {
    private SharedMemory sharedMemory;  /** Memoria partilhada entre as threads **/

    /**
     * Metodo construtor
     *
     * @param sharedMemory : memoria partilhada entre as threads
     */
    public Reader(SharedMemory sharedMemory) {
        this.sharedMemory = sharedMemory;
    }

    /**
     * Invocacao do metodo read
     */
    @Override
    public void run() {
        try{
            Thread.sleep(1000);
            this.sharedMemory.read();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
