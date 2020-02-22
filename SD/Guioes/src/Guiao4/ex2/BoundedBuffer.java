package Guiao4.ex2;

/**
 * Implementacao do bounded buffer como uma queue (FIFO)
 */

public class BoundedBuffer {
    private int[] buffer;
    private int head, tail, count;

    /**
     * Metodo construtor
     *
     * @param capacity : capacidade do buffer
     */
    public BoundedBuffer(int capacity){
        this.buffer = new int[capacity];
        this.head = this.tail = this.count = 0;
    }

    /**
     * @return : Numero de elementos do buffer
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Inserir um elemento no buffer
     *
     * @param x : elemento a introduzir no buffer
     * @throws InterruptedException
     */
    public synchronized void put(int x) throws InterruptedException {
        while (count == this.buffer.length)
            wait();
        this.buffer[tail] = x;
        count++;
        if (++tail == this.buffer.length)
            tail = 0;
        notifyAll();
    }


    /**
     * Remove um elemento no buffer
     *
     * @return : elemento removido
     * @throws InterruptedException
     */
    public synchronized int get() throws InterruptedException {
        int x;
        while (count == 0)
            wait();
        x = this.buffer[head];
        count--;
        if (++head == this.buffer.length)
            head = 0;
        notifyAll();
        return x;
    }
}
