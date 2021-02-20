package Guiao3;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
/**
        int id_c1 = bank.createAccount(2000);
        int id_c2 = bank.createAccount(2000);

        try{
            System.out.println("Criada conta com o ID " + id_c1 + " com saldo inicial " + bank.consult(id_c1));
            System.out.println("Criada conta com o ID " + id_c2 + " com saldo inicial " + bank.consult(id_c2));
        }catch (InvalidAccount ia){
            ia.getMessage();
        }
**/
        Thread t1 = new Thread(new Cliente1(bank));
        Thread t2 = new Thread(new Cliente2(bank));

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
/**
        try{
            System.out.println("Saldo da conta " + id_c2 + ": " + bank.consult(id_c2));
            System.out.println("Saldo da conta " + id_c1 + ": " + bank.consult(id_c1));
        }catch (InvalidAccount ia){
            ia.getMessage();
        }
 **/
    }
}
