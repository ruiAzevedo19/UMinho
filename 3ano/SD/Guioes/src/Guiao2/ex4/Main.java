package Guiao2.ex4;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco(2);
        Thread c1 = new Thread(new Cliente1(banco));
        Thread c2 = new Thread(new Cliente2(banco));

        banco.debito(0, 1000);

        c1.start();
        c2.start();

        try{
            c1.join();
            c2.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }

        System.out.println("Montante da Conta 0 = " + banco.consultar(0));
        System.out.println("Montante da Conta 1 = " + banco.consultar(1));
    }
}
