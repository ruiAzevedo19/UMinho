package Guiao5.ex3v2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {
    private ReentrantLock mutex;    /** Lock **/
    private Condition readerWait;   /** Condicao de espera para os leitores **/
    private Condition writerWait;   /** Condicao de espera para os escritores **/
    private boolean writer;         /** Flag - true se existe um escritor na zona critica **/
    private int readers;            /** Numero de leitores na zona critica **/
    private int reader_op;          /** Numero de leitores à espera **/
    private int writer_op;          /** Numero de escritores à espera **/
    private int R_max;              /** Numero maximo de leituras seguidas **/
    private int W_max;              /** Numero maximo de escritas seguidas **/
    private int waiting_writers;    /** Numero de escritas à espera **/
    private int waiting_readers;    /** Numero de leituras à espera **/

    /**
     * Metodo construtor
     */
    public RWLock(int reads, int writes) {
        this.mutex = new ReentrantLock();
        this.readerWait = mutex.newCondition();
        this.writerWait = mutex.newCondition();
        this.writer = false;
        this.readers = 0;
        this.reader_op = 0;
        this.writer_op = 0;
        this.R_max = reads;
        this.W_max = writes;
        this.waiting_writers = 0;
        this.waiting_readers = 0;
    }

    /**
     * Lock dos leitores
     *
     * @throws InterruptedException
     */
    public void readLock() throws InterruptedException{
        mutex.lock();
        try{
            waiting_readers++;
            while( writer || (reader_op >= R_max && waiting_writers > 0) )
                readerWait.await();
            waiting_readers--;
            readers++;
            reader_op++;
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Unlock dos leitores
     */
    public void readUnlock() {
        mutex.lock();
        try {
            readers--;
            if( reader_op == R_max ){
                writer_op = 0;
                writerWait.signal();
            }
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Lock dos escritores
     *
     *
     * @throws InterruptedException
     */
    public void writeLock() throws InterruptedException{
        mutex.lock();
        try {
            waiting_writers++;
            while( writer || (writer_op >= W_max && waiting_readers > 0) )
                writerWait.await();
            waiting_writers--;
            writer_op++;
            writer = true;
        }finally {
            mutex.unlock();
        }
    }

    /**
     * Unlock dos escritores
     */
    public void writeUnlock() throws InterruptedException{
        mutex.lock();
        try {
            writer = false;
            if( writer_op == W_max ){
                reader_op = 0;
                readerWait.signalAll();
            }
            writerWait.signal();
        }finally {
            mutex.unlock();
        }
    }
}
