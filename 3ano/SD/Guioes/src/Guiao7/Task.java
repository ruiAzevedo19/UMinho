package Guiao7;

import Guiao7.Bank.InvalidAccount;
import Guiao7.Bank.Movimento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Task implements Runnable{
    private Socket clientSocket;    /** Socket do cliente **/
    private Worker worker;          /** Instancia do trabalhador **/

    /**
     * Metodo construtor
     *
     * @param clientSocket : socket do cliente
     * @param worker : Instancia do trabalhador
     */
    public Task(Socket clientSocket, Worker worker) {
        this.clientSocket = clientSocket;
        this.worker = worker;
    }

    @Override
    public void run() {
        String answer;
        try {
            /** in le do input do socket do cliente **/
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            /** out escreve no output do socket do cliente **/
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            while (true) {
                String s = in.readLine();
                if (s.equals("Quit") || s.equals(null) )
                    break;
                System.out.println(s);
                String[] parser = s.trim().split("\\s+");
                if ( parser[0].equals("get_moves") ) {
                    ArrayList<Movimento> moves = null;
                    try {
                        moves = (ArrayList<Movimento>) worker.executeMoves(Integer.parseInt(parser[1]));
                    } catch (InvalidAccount ia) {
                        ia.getMessage();
                    }
                    for (Movimento m : moves) {
                        answer = m.toString();
                        out.println(answer);
                        out.flush();
                    }
                    answer = "Out of moves!";
                    out.println(answer);
                    out.flush();
                } else {
                    answer = worker.execute(s);
                    out.println(answer);
                    out.flush();
                }
            }
        }catch(IOException io){
            io.printStackTrace();
        }finally {
            try {
                clientSocket.shutdownOutput();
                clientSocket.shutdownInput();
                clientSocket.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
