package Guiao4.ex1;

public class Producer implements Runnable{
    private BoundedBuffer buffer;   /** Memoria partilhada pelas threads **/
    private int nr_op;              /** Numero de operacoes que a thread vai executar **/

    /**
     * Metodo construtor
     *
     * @param buffer : buffer partilhado entre as threads
     * @param nr_op  : numero de operacoes a executar
     */
    public Producer(BoundedBuffer buffer, int nr_op){
        this.buffer = buffer;
        this.nr_op = nr_op;
    }

    /**
     * Cada thread vai nr_op puts
     */
    @Override
    public void run() {
        try {
            for(int i = 0; i < nr_op; i++) {
                System.out.println("Put value " + i);
                Thread.sleep(100);
                this.buffer.put(i);
            }
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
