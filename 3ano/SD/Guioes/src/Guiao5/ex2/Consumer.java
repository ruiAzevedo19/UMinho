package Guiao5.ex2;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements Runnable{
    private Warehouse warehouse;    /** Warehouse partilhado entre as threads **/

    /**
     * Metodo construtor
     *
     * @param warehouse : warehouse partilhado entre as threads
     */
    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Cria uma lista de artigos e envoca o metodo consume sobre essa lista
     */
    @Override
    public void run() {
        try{
            List<String> it = new ArrayList<>();
            it.add("Item1");
            it.add("Item2");
            it.add("Item3");
            it.add("Item4");
            it.add("Item5");
            this.warehouse.consume(it,1);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
