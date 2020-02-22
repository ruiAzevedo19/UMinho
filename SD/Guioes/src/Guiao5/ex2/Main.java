package Guiao5.ex2;

public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        int C = 10;
        int P = 10;
        Thread[] producers = new Thread[P];
        Thread[] consumers = new Thread[C];

        warehouse.addItem("Item1",0);
        warehouse.addItem("Item2",0);
        warehouse.addItem("Item3",0);
        warehouse.addItem("Item4",0);
        warehouse.addItem("Item5",0);

        for(int i = 0; i < P; i++) {
            producers[i] = new Thread(new Producer(warehouse));
            producers[i].start();
        }

        for(int i = 0; i < C; i++) {
            consumers[i] = new Thread(new Consumer(warehouse));
            consumers[i].start();
        }

        try{
            for(int i = 0; i < P; i++)
                producers[i].join();

            for(int i = 0; i < C; i++)
                consumers[i].join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

        System.out.println("Stock do Item1 = " + warehouse.getStock("Item1"));
        System.out.println("Stock do Item2 = " + warehouse.getStock("Item2"));
        System.out.println("Stock do Item3 = " + warehouse.getStock("Item3"));
        System.out.println("Stock do Item4 = " + warehouse.getStock("Item4"));
        System.out.println("Stock do Item5 = " + warehouse.getStock("Item5"));
    }
}
