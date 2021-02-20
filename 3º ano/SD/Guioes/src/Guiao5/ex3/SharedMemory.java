package Guiao5.ex3;

public class SharedMemory {
    private RWLock rw;  /** Read-Write Lock **/
    private int i;      /** Contador **/

    /**
     * Método construtor
     *
     * @param i : valor inicial do contador
     */
    public SharedMemory(int i) {
        this.rw = new RWLock();
        this.i = i;
    }

    /**
     * @return : devolve o valor do contador
     */
    public int getCounter(){
        return this.i;
    }

    /**
     * Leitura do contador
     *
     * @return : valor do contador
     * @throws InterruptedException
     */
    public int read() throws InterruptedException{
        try {
            this.rw.readLock();
            return this.i;
        }finally {
            this.rw.readUnlock();
        }
    }

    /**
     * Incrementar o contador
     *
     * @throws InterruptedException
     */
    public void write() throws InterruptedException{
        try{
            this.rw.writeLock();
            this.i++;
        }finally {
            this.rw.writeUnlock();
        }
    }
}
