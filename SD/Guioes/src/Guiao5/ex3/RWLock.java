package Guiao5.ex3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Esta solucao, embora correta, apresenta uma particularidade: é possível um tipo de threads sofrer de starvation, i.e,
 * embora não hajam situações de deadlock, um tipo de threads não consegue entrar na zona critica enquanto o outro tipo
 * de threads não acabarem a sua execução.
 */

public class RWLock {
    private ReentrantLock mutex;    /** Lock **/
    private Condition readerWait;   /** Condicao de espera para os leitores **/
    private Condition writerWait;   /** Condicao de espera para os escritores **/
    private boolean writer;         /** Flag - true se existe uma thread a escrever na zona critica **/
    private int readers;            /** Numero de leitores na zona critica **/

    /**
     * Metodo construtor
     */
    public RWLock() {
        this.mutex = new ReentrantLock();
        this.readerWait = mutex.newCondition();
        this.writerWait = mutex.newCondition();
        this.writer = false;
        this.readers = 0;
    }

    /**
     * Lock dos leitores
     *
     * Os leitores só bloqueiam se estiver algum escritor na zona critica. Quando um escritor é acordado é incrementada
     * a variavel readers que conta quantas threads de leitura estão na zona crítica.
     *
     * @throws InterruptedException
     */
    public void readLock() throws InterruptedException{
        mutex.lock();
        try{
            while( writer )
                this.readerWait.await();
            System.out.println("Thread " + Thread.currentThread().getId() + " (Reader)");
            this.readers++;
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Unlock dos leitores
     *
     * Quando uma thread de leitura invoca o metodo unlock, decrementa a variável reader pois existe menos uma thread
     * de leitura na zona critica. Quando esta variavel tem o valor de zero notifica as threads de escrita
     */
    public void readUnlock() {
        mutex.lock();
        try {
            this.readers--;
            if ( this.readers == 0 )
                this.writerWait.signal();
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Lock dos escritores
     *
     * Quando uma thread de escrita adquire o lock, verifica se existe alguma thread na zona critica, quer ela seja de
     * escrita (this.writer == true) quer ela seja de leitura (this.readers > 0). Se existir alguma thread na zona
     * critica, a thread fica a espera. Quando é desbloqueada, atribui o valor de true à variavel writer, significando
     * que existe uma thread a escrever na zona critica.
     *
     * @throws InterruptedException
     */
    public void writeLock() throws InterruptedException{
        mutex.lock();
        try {
            while ( this.writer || this.readers > 0 )
                this.writerWait.await();
            System.out.println("Thread " + Thread.currentThread().getId() + " (Writer)");
            writer = true;
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Unlock dos escritores
     *
     * Quando uma thread de escrita invoca o método unlock, atribui o valor de false à variável writer, significando que
     * ja nao existe nenhuma thread a escrever na zona critica. De seguida, notifica todas as threads, de escrita e
     * leitura, para que possam tentar adquirir os respetivos locks.
     */
    public void writeUnlock(){
        mutex.lock();
        try {
            this.writer = false;
            this.readerWait.signalAll();
            this.writerWait.signal();
        }finally {
            mutex.unlock();
        }
    }
}
