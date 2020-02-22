package Guiao4.ex2;

import java.nio.Buffer;

public class Main {
    public static void main(String[] args) {
        int P = 1, C = 9, N = C + P;            /** Numero inicial de produtores e final de consumidores **/
        int Tp = 100, Tc = 50;                  /** Tempo de produção e consumo **/
        long Ti, Tf;                            /** Tempo inicial e final do programa **/
        int nr_ops = 100, work, remainder;      /** Numero de operacoes que cada thread vai executar **/
        int maxProd = 1;                        /** Numero de produtores otimo **/
        float maxdeb = 0, deb;                  /** Debito **/
        BoundedBuffer buffer = new BoundedBuffer(10);   /** Bounded buffer **/

        for( ; C > 0; P++, C--) {
            /** P produtores e C consumidores **/
            Thread[] producers = new Thread[P];
            Thread[] consumers = new Thread[C];
            System.out.println("Número de produtores = " + P);
            System.out.println("Número de consumidores = " + C);

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

            /** --- Inicio do programa ----------------------------------------------------------------------------- **/
            Ti = System.currentTimeMillis();
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
            Tf = System.currentTimeMillis();
            /** --- Fim do programa -------------------------------------------------------------------------------- **/

            /** Calculo do debito **/
            deb = (float)nr_ops / (Tf - Ti);
            if( deb > maxdeb ) {
                maxdeb = deb;
                maxProd = P;
            }
            System.out.println("Débito = " + deb + "\n");
        }

        System.out.println("---------- Relatório final ----------");
        System.out.println("Débito máximo = " + maxdeb);
        System.out.println("Numero de produtores = " + maxProd);
        System.out.println("Número de consumidores = " + (N - maxProd));
        System.out.println("Número de elementos no buffer = " + buffer.getCount());
    }
}
