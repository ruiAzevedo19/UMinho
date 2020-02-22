package Guiao2.ex1v1;

import java.util.Scanner;

/**
 *
 *   Existem varias threads a aceder a um recurso partilhado no mesmo instante de tempo,
 * dai o resultado final nao ser o correto (Nao existe exclusao mutua).
 *   Como cada thread tem a sua cache, o valor do contador nao e escrito na memoria partilhada de imediato.
 *
 **/
public class Main {
    public static void main(String[] args) {
        int N;  /** Numero de threads **/
        int I;  /** Limite superior do intervalo a imprimir **/
        Counter c = new Counter(0);

        Scanner read = new Scanner(System.in);

        /** ----------------------------------------------------- **/
        System.out.print("Número de threads: ");
        N = read.nextInt();
        System.out.println();

        System.out.print("Limite superior do intervalo: ");
        I = read.nextInt();
        System.out.println();
        /** ----------------------------------------------------- **/
        int i;

        Thread[] threads = new Thread[N];
        for(i = 0; i < N; i++)
            threads[i] = new Thread(new ThreadJob(c,I));

        for(i = 0; i < N; i++)
            threads[i].start();

        try {
            for(i = 0; i < N; i++)
                threads[i].join();
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("Valor do contador := " + c.getCounter());
    }
}
