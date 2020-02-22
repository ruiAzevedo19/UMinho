package Server;

import Server.ThreadPool.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static SoundCloud soundCloud;
    private static ThreadPool threadPool;
    private static ThreadPool threadPoolDownloads;
    private static final int MAXDOWN = 1;
    private static volatile boolean shutdown = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing server...");
        threadPool = new ThreadPool(4, 10000);
        threadPoolDownloads = new ThreadPool(MAXDOWN, 10000);
        MinHeap tasks = new MinHeap(10,10);
        serverSocket = new ServerSocket(12345);
        soundCloud = new SoundCloud();
        Thread scheduler = new Thread(new Scheduler(tasks, threadPool, threadPoolDownloads, shutdown)); /** passar o map das tasks**/
        scheduler.start();
        System.out.println("Initialization completed.");

        while( !shutdown ){
            Socket clientSocket = serverSocket.accept();
            Thread client = new Thread(new Worker(tasks,soundCloud,clientSocket));
            client.start();
        }
    }

    public static void shutdown(){
        shutdown = true;
        System.out.println("Shutting down the server...");
        System.out.println("Shutting down thread pool...");
        //threadPool.shutdownPool();
        soundCloud.warnClientsAboutShutdown();
        System.out.println("ThreadPool ok");
        System.out.println("Closing server socket...");
        try{
            serverSocket.close();
            System.out.println("Socket ok");
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
