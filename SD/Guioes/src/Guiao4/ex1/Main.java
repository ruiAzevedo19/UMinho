package Guiao4.ex1;

public class Main {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(10);
        Thread producer = new Thread(new Producer(buffer,20));
        Thread consumer = new Thread(new Consumer(buffer,20));

        producer.start();
        consumer.start();

        try{
            producer.join();
            consumer.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

        System.out.println("Espaço ocupado: " + buffer.getCount());
    }
}
