package Guiao7.Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Bank implements BankInterface {
    private Map<Integer, Account> accounts;     /** Contas do banco **/
    private int id;                             /** ID da proxima conta a criar no banco **/
    private ReentrantLock bank_lock;            /** Lock do objeto Bank **/

    public Bank(){
        this.accounts = new HashMap<>();
        this.id = 0;
        this.bank_lock = new ReentrantLock();
    }

    /**
     * Cria uma conta no banco
     * @param initialBalance : saldo inicial da conta
     * @return : id da conta criada
     */
    public int createAccount(double initialBalance){
        int idA;
        this.bank_lock.lock();
        idA = this.id++;
        this.accounts.put(idA, new Account(idA, initialBalance));
        this.bank_lock.unlock();
        return idA;
    }

    /**
     * Fechar uma conta
     *
     * @param id : ID da conta a fechar
     * @return : saldo da conta fechada
     * @throws InvalidAccount
     */
    public double closeAccount(int id) throws InvalidAccount {
        double balance;

        this.bank_lock.lock();
        try{
            if( !this.accounts.containsKey(id) )
                throw new InvalidAccount("A conta com o ID " + id + " não existe!");
            balance = this.accounts.get(id).consult();
            this.accounts.remove(id);
            return balance;
        }finally {
            this.bank_lock.unlock();
        }
    }

    /**
     * Transferir um montante de uma conta origem para uma conta destino
     *
     * @param from : conta origem
     * @param to : conta destino
     * @param amount : montante a transferir
     * @throws InvalidAccount
     * @throws NotEnoughFunds
     */
    public void transfer(int from, int to, double amount, String descritivo) throws InvalidAccount, NotEnoughFunds {
        this.bank_lock.lock();
        if( !this.accounts.containsKey(from) || !this.accounts.containsKey(to)){
            this.bank_lock.unlock();
            throw new InvalidAccount("A conta com o ID " + from + " ou a conta com o ID " + to + " não existe!");
        }
        if( this.accounts.get(from).consult() - amount < 0 ){
            this.bank_lock.unlock();
            throw new NotEnoughFunds("O saldo não é suficiente para a operação!");
        }

        Account a_from = this.accounts.get(from);
        Account a_to = this.accounts.get(to);

        a_from.lock();
        a_to.lock();
        this.bank_lock.unlock();

        a_from.credit(amount, descritivo);
        a_to.debit(amount,descritivo);

        a_to.unlock();
        a_from.unlock();
    }

    @Override
    public double totalBalance(int[] accounts) {
        List<Account> valid_accounts = new ArrayList<>();
        int i = 0;
        this.bank_lock.lock();
        for(Integer c : accounts)
            if( this.accounts.containsKey(c) )
                valid_accounts.add(this.accounts.get(c));
        valid_accounts.forEach(Account::lock);
        this.bank_lock.unlock();

        double sum = valid_accounts.stream().mapToDouble(Account::consult).sum();

        valid_accounts.forEach(Account::unlock);

        return sum;
    }

    /**
     * Levantar montante de uma conta
     *
     * @param id : ID da conta a levantar montante
     * @param amount : montante a levantar
     * @throws InvalidAccount
     * @throws NotEnoughFunds
     */
    public void credit(int id, double amount, String descritivo) throws InvalidAccount, NotEnoughFunds {
        this.bank_lock.lock();
        if( !this.accounts.containsKey(id) ){
            this.bank_lock.unlock();
            throw new InvalidAccount("A conta com o ID " + id + " não existe!");
        }

        if( this.accounts.get(id).consult() - amount < 0 ) {
            this.bank_lock.unlock();
            throw new NotEnoughFunds("O saldo não é suficiente para a operação!");
        }

        Account a = this.accounts.get(id);

        a.lock();
        this.bank_lock.unlock();
        a.credit(amount, descritivo);
        a.unlock();
    }

    /**
     * Depositar montante numa conta
     *
     * @param id : ID da conta a depositar
     * @param amount : montante a depositar
     * @throws InvalidAccount
     */
    public void debit(int id, double amount, String descritivo) throws InvalidAccount {
        this.bank_lock.lock();
        if( !this.accounts.containsKey(id) ){
            this.bank_lock.unlock();
            throw new InvalidAccount("A conta com o ID " + id + " não existe!");
        }
        Account a = this.accounts.get(id);

        a.lock();
        this.bank_lock.unlock();
        a.debit(amount, descritivo);
        a.unlock();
    }

    /**
     * Consultar o saldo de uma conta
     *
     * @param id : id da conta a consultar
     * @return : saldo da conta
     * @throws InvalidAccount
     */
    public double consult(int id) throws InvalidAccount {
        this.bank_lock.lock();
        if( !this.accounts.containsKey(id)){
            this.bank_lock.unlock();
            throw new InvalidAccount("A conta com o ID " + id + " não existe!");
        }
        Account a = this.accounts.get(id);
        a.lock();
        this.bank_lock.unlock();
        double balance = a.consult();
        a.unlock();
        return balance;
    }

    /**
     * Devolve a lista de movimentos de uma determinada conta
     *
     * @param id : ID da conta a consultar
     * @return : Lista de movimentos da conta
     * @throws InvalidAccount
     */
    public List<Movimento> movimentos(int id) throws InvalidAccount{
        this.bank_lock.lock();
        if( !this.accounts.containsKey(id)){
            this.bank_lock.unlock();
            throw new InvalidAccount("A conta com o ID " + id + " não existe!");
        }
        Account a = this.accounts.get(id);
        a.lock();
        List<Movimento> m = a.movimentos();
        a.unlock();
        return m;
    }
}
