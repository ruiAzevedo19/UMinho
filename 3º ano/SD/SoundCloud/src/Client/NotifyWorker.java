package Client;

import Utilities.Utility;

import java.io.*;
import java.net.Socket;

public class NotifyWorker implements Runnable{
    /** Extremidade de leitura do socket de notificacoes **/
    private BufferedReader in;

    /**
     * Metodo construtor
     * @param socketNotify : Extremidade de leitura do socket de notificacoes
     */
    public NotifyWorker(Socket socketNotify) {
        try {
            this.in = new BufferedReader(new InputStreamReader(socketNotify.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fica a escuta do socket de notificacoes a espera que chegam notificacoes
     */
    @Override
    public void run() {
        String answer;
        try {
            answer = in.readLine();
            while(!answer.equals("stop") && !answer.equals("serverDown")){
                Utility.prettyNotify(answer);
                answer = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
