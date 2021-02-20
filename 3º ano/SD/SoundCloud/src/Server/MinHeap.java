package Server;

import Server.Tasks.*;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe que implementa a logica de uma minheap.
 *
 * Estrutura usada para garantir que os pedidos são atendidos por ordem de
 * chegada ao sistema
 */
public class MinHeap {
    private PriorityQueue<TimeTask> heap;
    private int size;
    private int elems;
    private int take;
    private ReentrantLock heap_lock;
    private Condition isFull;
    private Condition isEmpty;

    /**
     * Método construtor
     *
     * @param size : tamanho da heap
     * @param take : numero maximo de takes pela thread de escalonamento
     */
    public MinHeap(int size, int take) {
        this.heap = new PriorityQueue<TimeTask>(size,new Compare());
        this.size = size;
        this.elems = 0;
        this.take = take;
        this.heap_lock = new ReentrantLock();
        this.isFull = heap_lock.newCondition();
        this.isEmpty = heap_lock.newCondition();
    }

    /**
     * Adiciona uma tarefa com o tempo de chegada do pedido à heap
     *
     * @param t : Tarefa a adicionar
     */
    public void put(TimeTask t){
        try{
            heap_lock.lock();
            while( elems == size )
                isFull.await();
            elems++;
            heap.add(t);
            isEmpty.signalAll();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }finally {
            heap_lock.unlock();
        }
    }

    /**
     * @return : Devolve no maximo 'take' elementos da heap
     */
    public List<Task> get() {
        List<Task> tasks = new ArrayList<>();
        int i;
        try {
            heap_lock.lock();
            while( elems == 0 )
                isEmpty.await();
            for(i = 0; i < take && heap.peek() != null; i++)
                tasks.add(heap.poll().getTask());
            elems -= i;
            isFull.signal();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        return tasks;
    }
}

/**
 * Comparador para a fila de prioridades, permite que os pedidos sejam ordenados por
 * ordem de chegada ao sistema
 */
class Compare implements Comparator<TimeTask> {
    @Override
    public int compare(TimeTask t1, TimeTask t2) {
        if( t1.getArrival() - t2.getArrival() > 0 )
            return 1;
        if( t1.getArrival() - t2.getArrival() < 0 )
            return -1;
        return 0;
    }
}