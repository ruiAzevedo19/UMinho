package Guiao6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Service implements Runnable {
    private Socket clSocket;    /** Socket do cliente **/

    /**
     * Método construtor
     *
     * @param clSocket : socket do cliente
     */
    public Service(Socket clSocket) {
        this.clSocket = clSocket;
    }

    @Override
    public void run(){
        try {
            /** in le do input do socket do cliente **/
            BufferedReader in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            /** out escreve no output do socket do cliente **/
            PrintWriter out = new PrintWriter(clSocket.getOutputStream());
            while (true) {
                String s = in.readLine();   /** Le o que foi escrito no socket do cliente **/
                System.out.println(s);      /** Faz echo para o terminal do servidor **/
                out.println(s);             /** Escreve no socket do cliente o que foi lido e envia para o cliente **/
                out.flush();                /** Limpa a stream de dados **/
                if (s.equals("Quit"))       /** Se o cliente escreveu Quit fecha-se a conexao com o cliente **/
                    break;
            }
        }catch(IOException io){
            io.printStackTrace();
        }finally {
            try {
                clSocket.shutdownOutput();  /** Fecha o lado de escrita do socket do cliente **/
                clSocket.shutdownInput();   /** Fecha o lado de leitura do socket do cliente **/
                clSocket.close();           /** Fecha o socket do cliente **/
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
