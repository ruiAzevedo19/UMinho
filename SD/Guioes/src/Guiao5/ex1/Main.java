package Guiao5.ex1;


public class Main {
    public static void main(String[] args) {
        int P = 2, C = 8;                       /** Numero de produtores e consumidores **/
        int Tp = 100, Tc = 50;                  /** Tempo de produção e consumo **/
        int nr_ops = 100, work, remainder;      /** Numero de operacoes que cada thread vai executar **/
        BoundedBuffer<Integer> buffer = new BoundedBuffer(10);   /** Bounded buffer **/

         /** P produtores e C consumidores **/
         Thread[] producers = new Thread[P];
         Thread[] consumers = new Thread[C];

         for (int i = 0; i < P; i++)
             producers[i] = new Thread(new Producer(buffer, nr_ops, Tp));

         /** Cada thread vai ter igual trabalho, exceto a ultima que fica com o trabalho que sobra **/
         work = P * nr_ops / C;
         remainder = (P * nr_ops) % C;
         for (int i = 0; i < C; i++) {
             if ( i != C - 1 )
                 consumers[i] = new Thread(new Consumer(buffer, work, Tc));
             else
                 consumers[i] = new Thread(new Consumer(buffer, work + remainder, Tc));
         }

         for(int i = 0; i < P; i++)
             producers[i].start();
         for(int i = 0; i < C; i++)
             consumers[i].start();

         try{
             for(int i = 0; i < P; i++)
                 producers[i].join();
             for(int i = 0; i < C; i++)
                 consumers[i].join();
         }catch (InterruptedException ie){
             ie.printStackTrace();
         }

        System.out.println("Capacidade final do buffer = " + buffer.getCount());
    }
}
