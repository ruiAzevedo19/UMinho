package Guiao6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws Exception{
        /** Socket de servidor conectado na porta 12345 **/
        ServerSocket socket = new ServerSocket(12345);

        while( true ){
            /** Fica à escuta de conecoes a serem feitas e aceita-as **/
            Socket clSocket = socket.accept();

            /** in le do input do socket do cliente **/
            BufferedReader in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            /** out escreve no output do socket do cliente **/
            PrintWriter out = new PrintWriter(clSocket.getOutputStream());

            while( true ){
                String s = in.readLine();   /** Le o que foi escrito no socket do cliente **/
                System.out.println(s);      /** Faz echo para o terminal do servidor **/
                if( s.equals("Quit") )      /** Se o cliente escreveu Quit o servidor fecha **/
                    break;
                out.println(s);             /** Escreve no socket o que foi lido e envia para o cliente **/
                out.flush();                /** Limpa a stream de dados **/
            }

            clSocket.shutdownOutput();      /** Fecha o lado de escrita do socket do cliente **/
            clSocket.shutdownInput();       /** Fecha o lado de leitura do socket do cliente **/
            clSocket.close();               /** Fecha o socket do cliente **/
        }
    }

}
