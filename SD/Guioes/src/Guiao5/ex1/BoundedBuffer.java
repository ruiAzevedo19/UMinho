package Guiao5.ex1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe Bounded Buffer generica.
 *
 * @param <T> : tipo de dados do BoundedBuffer
 */

public class BoundedBuffer<T> {
    private ReentrantLock mutex;
    private Condition isEmpty;
    private Condition isFull;
    private T[] buffer;
    private int head, tail, count;

    /**
     * Metodo construtor
     *
     * @param capacity : capacidade do buffer
     */
    public BoundedBuffer(int capacity){
        this.buffer = (T[])new Object[capacity];
        this.mutex = new ReentrantLock();
        this.isEmpty = mutex.newCondition();
        this.isFull  = mutex.newCondition();
        this.head = this.tail = this.count;
    }

    /**
     * @return : devolve o numero de elementos no buffer
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Insere um elemento no buffer.
     *  Metodo usado pelo produtor. Quando o buffer esta cheio e o produtor quer inserir elementos, o produtor fica em
     * espera sobre a condicao isFull. É acordado quando um consumidor remove um elemento do buffer. No fim, manda sinal
     * para as threads que estao dependentes da condicao isEmpty caso haja algum consumidor a espera que o produtor
     * insira valores
     *
     * @param value : valor a inserir
     * @throws InterruptedException
     */
    public void put(T value) throws InterruptedException{
        mutex.lock();
        try {
            while ( this.count == buffer.length )
                isFull.await();
            this.buffer[tail] = value;
            this.count++;
            if ( ++tail == buffer.length )
                tail = 0;
            isEmpty.signal();
        }finally {
            mutex.unlock();
        }

    }

    /**
     * Remove um elemento do buffer
     *
     *  Metodo usado pelo consumidor. Quando o buffer esta vazio e o consumidor que retirar um elemento do buffer, o
     * consumidor espera sobre a condicao isEmpty. É acordado quando um produtor insere um elemento no buffer . No fim,
     * manda um sinal as threads que estao dependentes da condicao isFull caso haja algum produtor a espera que haja
     * espaco no buffer para inserir valores
     *
     * @return : valor removido
     * @throws InterruptedException
     */
    public T get() throws InterruptedException{
        mutex.lock();
        try {
            while ( this.count == 0 )
                this.isEmpty.await();
            T value = this.buffer[head];
            this.count--;
            if ( ++head == buffer.length )
                head = 0;
            this.isFull.signal();
            return value;
        }finally {
            mutex.unlock();
        }
    }
}
