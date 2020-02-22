package Guiao4.ex2;

public class Consumer implements Runnable{
    private BoundedBuffer buffer;   /** Memoria partilhada pelas threads **/
    private int nr_op;              /** Numero de operacoes que a thread vai executar **/
    private int Tc;                 /** Tempo de consumo **/

    /**
     * Metodo construtor
     *
     * @param buffer : buffer partilhado entre as threads
     * @param nr_op  : numero de operacoes a executar
     * @param Tc     : tempo de consumo
     */
    public Consumer(BoundedBuffer buffer, int nr_op, int Tc){
        this.buffer = buffer;
        this.nr_op = nr_op;
        this.Tc = Tc;
    }

    /**
     * Cada thread vai nr_op gets
     */
    @Override
    public void run() {
        int x;
        try {
            for (int i = 0; i < nr_op; i++) {
                Thread.sleep(this.Tc);
                x = this.buffer.get();
                System.out.println("Get value " + x);
            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
