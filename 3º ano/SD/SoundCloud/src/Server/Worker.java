package Server;
import Server.Tasks.*;
import Exception.*;
import Server.ThreadPool.ThreadPool;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Worker implements Runnable{
    private SoundCloud soundcloud;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean log;
    private MinHeap tasks;

    public Worker(MinHeap tasks, SoundCloud soundCloud, Socket client){
        this.soundcloud = soundCloud;
        this.client = client;
        this.log = false;
        this.tasks = tasks;
        try {
            this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.out = new PrintWriter(client.getOutputStream());
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @Override
    public void run() {
        String[] request;
        try{
            Task task = null;
            request = in.readLine().split(";");
            /** autenticar **/
            while( !log ){
                switch (request[0]){
                    case "A" : try{
                                   soundcloud.authentication(request[1], request[2]);
                                   this.username = request[1];
                                   log = true;
                               }catch (InvalidLoginException l){
                                   out.println("e;" + l.getMessage());
                                   out.flush();
                               }
                               break;
                    case "R" : try{
                                   soundcloud.registration(request[1],request[2]);
                                   this.username = request[1];
                                   log = true;
                               }catch (AlreadyRegistedException r){
                                   out.println("e;" + r.getMessage());
                                   out.flush();
                               }
                               break;
                    case "N" : this.log = true;
                               this.soundcloud.addToNotifications(request[1], this.out);
                               return;
                    case "L" : return;
                }
                if( !log ) {
                    try {
                        request = in.readLine().split(";");
                    }catch (NullPointerException np){
                        return;
                    }
                }
            }
            out.println("Login com sucesso");
            out.flush();

            /** Username = getUser() **/
            /** Adicionar correspodencia username socket**/

            UploadState uploadState;
            long arrival;
            TimeTask time_task;
            while( log ){
                try {
                    request = in.readLine().split(";");
                    uploadState = new UploadState();
                    arrival = System.currentTimeMillis();
                }catch (NullPointerException np){
                    log = false;
                    return;
                }
                switch (request[0]){
                    case "C" : task = new SearchTask(soundcloud,out,request[1],request);
                               uploadState.changeUploadFinished();
                               break;
                    case "D" : task = new DownloadTask(soundcloud,Integer.parseInt(request[1]), client, out);
                               uploadState.changeUploadFinished();
                               break;
                    case "U" : task = new UploadTask(soundcloud, out, client, request, uploadState);
                               break;
                    case "L" : {
                        log = false;
                        this.soundcloud.sendNotification(this.username, "stop");
                        this.soundcloud.removeNotification(this.username);
                        uploadState.changeUploadFinished();
                    }
                }
                if( log ) {
                    time_task = new TimeTask(arrival, task);
                    tasks.put(time_task);
                }

                while(!uploadState.isUploadFinished()){
                    Thread.sleep(100);
                }
            }
        } catch (NullPointerException | SocketException n){
            log = false;
        } catch (IOException | InterruptedException io){
            io.printStackTrace();
        } finally {
            try {
                if(!log){
                    System.out.println("Shutting down client...");
                    client.shutdownOutput();
                    client.shutdownInput();
                    client.close();
                    System.out.println("Client closed.");
                }
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
