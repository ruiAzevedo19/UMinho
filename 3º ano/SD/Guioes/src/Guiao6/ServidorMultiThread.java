package Guiao6;

import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMultiThread {
    /** Socket de servidor conectado na porta 12345 **/
    public static void main(String[] args) throws Exception{
        ServerSocket socket = new ServerSocket(12345);
        while (true) {
            /** Fica à escuta de conecoes a serem feitas e aceita-as **/
            Socket clSocket = socket.accept();
            /** É criada uma thread para atender cada pedido de um cliente **/
            Thread cliente = new Thread(new Service(clSocket));
            /** Dá inicio à thread **/
            cliente.start();
        }
    }
}
