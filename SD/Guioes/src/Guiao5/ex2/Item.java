package Guiao5.ex2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Item {
    private int quantity;           /** Quantidade em stock do produto **/
    private ReentrantLock mutex;    /** Lock explicito **/
    private Condition no_stock;     /** Condicao associada ao mutex **/

    /**
     * Metodo construtor
     *
     * @param quantity : quantidade em stock inicial
     */
    public Item(int quantity){
        this.quantity = quantity;
        this.mutex = new ReentrantLock();
        this.no_stock = mutex.newCondition();
    }

    /**
     * @return : stock do artigo
     */
    public int getStock(){
        return this.quantity;
    }

    /**
     * Adicionar stock ao produto
     *
     * Adicona-se o stock ao produto e notificam-se as threads dependentes da condicao no_stock caso haja alguma thread
     * a espera que seja adicionado stock
     *
     * @param quantity : quantidade adicionada de stock
     */
    public void supply(int quantity){
        mutex.lock();
        this.quantity += quantity;
        this.no_stock.signal();
        mutex.unlock();
    }

    /**
     * Retirar stock ao produto
     *
     * Quando nao existe stock de um produto o consumidor espera ate que alguem adicione stock ao artigo.
     *
     * @param quantity : quantidade a retirar ao stock
     * @throws InterruptedException
     */
    public void consume(int quantity) throws InterruptedException{
        mutex.lock();
        try {
            while (this.quantity == 0)
                this.no_stock.await();
            this.quantity -= (this.quantity - quantity < 0) ? this.quantity : quantity;
        }finally {
            mutex.unlock();
        }
    }
}
