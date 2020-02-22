package Guiao5.ex1;

public class Producer implements Runnable{
    private BoundedBuffer<Integer> buffer;   /** Memoria partilhada pelas threads **/
    private int nr_op;                       /** Numero de operacoes que a thread vai executar **/
    private int Tp;                          /** Tempo de producao **/

    /**
     * Metodo construtor
     *
     * @param buffer : buffer partilhado entre as threads
     * @param nr_op  : numero de operacoes a executar
     * @param Tp     : Tempo de producao
     */
    public Producer(BoundedBuffer<Integer> buffer, int nr_op, int Tp){
        this.buffer = buffer;
        this.nr_op = nr_op;
        this.Tp = Tp;
    }

    /**
     * Cada thread vai nr_op puts
     */
    @Override
    public void run() {
        try {
            for(int i = 0; i < nr_op; i++) {
                System.out.println("Put value " + i);
                Thread.sleep(this.Tp);
                this.buffer.put(i);
            }
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
