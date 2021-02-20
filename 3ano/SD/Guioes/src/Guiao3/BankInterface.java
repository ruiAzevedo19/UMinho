package Guiao3;

public interface BankInterface {
    int createAccount(double initialBalance);
    double closeAccount(int id) throws InvalidAccount;
    void transfer(int from, int to, double amount) throws InvalidAccount, NotEnoughFunds;
    double totalBalance(int accounts[]);
}
