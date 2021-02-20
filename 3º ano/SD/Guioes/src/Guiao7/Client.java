package Guiao7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(), 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter((socket.getOutputStream()));

        while( true ){
            String s = buffer.readLine();
            out.println(s);
            out.flush();
            if( s.equals("Quit") )
                break;
            if( s.contains("get_moves") ){
                String answer = in.readLine();
                while( !answer.equals("Out of moves!") ){
                    System.out.println(answer);
                    answer = in.readLine();
                }
                System.out.println(answer);
            }
            else
                System.out.println(in.readLine());
        }
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
    }
}
