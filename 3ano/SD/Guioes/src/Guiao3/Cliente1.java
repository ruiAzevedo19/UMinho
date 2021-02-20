package Guiao3;

public class Cliente1 implements Runnable{
    /** Banco partilhado pelas threads **/
    private Bank bank;

    public Cliente1(Bank bank){
        this.bank = bank;
    }

    @Override
    public void run() {
        try{
            bank.createAccount(0);
            bank.transfer(0,2,5);
            int[] n = {0,1,2};
            bank.totalBalance(n);
        }catch (InvalidAccount | NotEnoughFunds ia){
            ia.getMessage();
        }
    }
}
