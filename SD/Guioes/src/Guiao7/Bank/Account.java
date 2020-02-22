package Guiao7.Bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Account {
    private ReentrantLock lock;         /** Lock do objeto **/
    private int id;                     /** Identificador da conta **/
    private double balance;             /** Saldo da conta **/
    private List<Movimento> movimentos; /** Movimentos de uma conta **/

    /**
     * Método construtor
     * @param id : identificador da conta
     * @param initialBalance : saldo inicial da conta
     */
    public Account(int id, double initialBalance){
        this.lock = new ReentrantLock();
        this.id = id;
        this.balance = initialBalance;
        this.movimentos = new ArrayList<>();
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
    public void credit(double amount, String descritivo){
        this.balance -= amount;

        Movimento m = new Movimento(0, descritivo,amount,this.balance);
        this.movimentos.add(m);
    }

    /**
     * Depositar montante na conta
     *
     * @param amount
     */
    public void debit(double amount, String descritivo){
        this.balance += amount;

        Movimento m = new Movimento(1, descritivo, amount, this.balance);
        this.movimentos.add(m);
    }

    /**
     * @return : lista de movimentos de uma conta
     */
    public List<Movimento> movimentos(){
        return new ArrayList<>(this.movimentos);
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
