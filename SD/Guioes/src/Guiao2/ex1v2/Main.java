package Guiao2.ex1v2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int N;  /** Numero de threads **/
        int I;  /** Limite superior do intervalo a imprimir **/
        Counter counter = new Counter(0);

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
            threads[i] = new Thread(new ThreadJob(counter,I));

        for(i = 0; i < N; i++)
            threads[i].start();

        try {
            for(i = 0; i < N; i++)
                threads[i].join();
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("Valor do contador := " + counter.c);
    }
}
