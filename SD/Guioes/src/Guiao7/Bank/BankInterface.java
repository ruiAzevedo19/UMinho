package Guiao7.Bank;

public interface BankInterface {
    int createAccount(double initialBalance);
    double closeAccount(int id) throws InvalidAccount;
    void transfer(int from, int to, double amount, String descritivo) throws InvalidAccount, NotEnoughFunds;
    double totalBalance(int accounts[]);
}
