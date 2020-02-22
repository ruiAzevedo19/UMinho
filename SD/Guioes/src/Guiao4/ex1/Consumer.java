package Guiao4.ex1;

public class Consumer implements Runnable{
    private BoundedBuffer buffer;   /** Memoria partilhada pelas threads **/
    private int nr_op;              /** Numero de operacoes que a thread vai executar **/

    /**
     * Método construtor
     *
     * @param buffer : buffer partilhado entre as threads
     * @param nr_op  : numero de operacoes a executar
     */
    public Consumer(BoundedBuffer buffer, int nr_op){
        this.buffer = buffer;
        this.nr_op = nr_op;
    }

    /**
     * Cada thread vai fazer nr_ops gets
     */
    @Override
    public void run() {
        int x;
        try {
            for (int i = 0; i < nr_op; i++) {
                Thread.sleep(50);
                x = this.buffer.get();
                System.out.println("Get value " + x);
            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
