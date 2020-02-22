package Guiao3;

import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private ReentrantLock lock;     /** Lock do objeto **/
    private int id;                 /** Identificador da conta **/
    private double balance;          /** Saldo da conta **/

    /**
     * Método construtor
     * @param id : identificador da conta
     * @param initialBalance : saldo inicial da conta
     */
    public Account(int id, double initialBalance){
        this.lock = new ReentrantLock();
        this.id = id;
        this.balance = initialBalance;
    }

    /**
     * Consultar o saldo da conta
     *
     * @return saldo da conta
     */
    public double consult(){
        return this.balance;
    }

    /**
     * Levantar um montante da conta
     *
     * @param amount : montante a levantar
     */
    public void credit(double amount){
        this.balance -= amount;
    }

    /**
     * Depositar montante na conta
     *
     * @param amount
     */
    public void debit(double amount){
        this.balance += amount;
    }

    /**
     * Lock do objeto Account
     */
    public void lock(){
        this.lock.lock();
    }

    /**
     * Unlock do objeto Account
     */
    public void unlock(){
        this.lock.unlock();
    }
}
