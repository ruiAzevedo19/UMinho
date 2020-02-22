package Guiao6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws Exception{
        /** Socket conectado na porta 12345 e com o IP 127.0.0.1 (localhost) **/
        Socket socket = new Socket(InetAddress.getLocalHost(), 12345);

        /** in le do input do socket **/
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        /** buffer vai ler do System.in **/
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        /** out escreve no output do socket **/
        PrintWriter out = new PrintWriter((socket.getOutputStream()));

        while( true ){
            String s = buffer.readLine();       /** Le o que foi escrito no System.in **/
            out.println(s);                     /** Escreve no socket o que foi lido e envia para o servidor **/
            out.flush();                        /** Limpa a stream de dados **/
            System.out.println(in.readLine());  /** Obtem a resposta do servidor e faz echo para o terminal
                                                    (bloqueia a espera da resposta) **/
            if( s.equals("Quit") )              /** Se o cliente escreve Quit o cliente fecha **/
                break;
        }
        socket.shutdownOutput();                /** Fecha o lado de escrita do socket **/
        socket.shutdownInput();                 /** Fecha o lado de leitura do socket **/
        socket.close();                         /** Fecha o socket **/
    }
}
