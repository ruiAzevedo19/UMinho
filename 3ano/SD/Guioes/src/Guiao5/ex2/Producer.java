package Guiao5.ex2;

public class Producer implements Runnable {
    private Warehouse warehouse;    /** warehouse partilhado entre as threads **/

    /**
     * Metodo construtor
     *
     * @param warehouse : warehouse partilhado entre as threads
     */
    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Envoca o metodo supply sobre os artigos 1, 2, 3, 4 e 5
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            warehouse.supply("Item1", 10);
            warehouse.supply("Item2", 10);
            warehouse.supply("Item3", 10);
            warehouse.supply("Item4", 10);
            warehouse.supply("Item5", 10);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
