package Guiao7;

import Guiao7.Bank.Bank;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        /** Banco partilhado **/
        Bank bank = new Bank();
        /** Lógica de processamento de pedidos **/
        Worker worker = new Worker(bank);
        /** Socket do servidor **/
        ServerSocket serverSocket = new ServerSocket(12345);
        
        while(true){
            /** Fica à escuta de conecoes a serem feitas e aceita-as **/
            Socket clienteSocket = serverSocket.accept();
            /** É criada uma thread para atender cada pedido de um cliente **/
            Thread cliente = new Thread(new Task(clienteSocket,worker));
            /** Dá inicio à thread **/
            cliente.start();
        }
    }
}
