package Guiao3;

public class Cliente2 implements Runnable {
    private Bank bank;

    public Cliente2(Bank bank){
        this.bank = bank;
    }

    @Override
    public void run() {
        try{
            bank.transfer(0,1,10);
            bank.closeAccount(1);
            bank.consult(0);
        }catch (InvalidAccount ia){
            ia.getMessage();
        }catch (NotEnoughFunds nef) {
            nef.getMessage();
        }
    }
}
