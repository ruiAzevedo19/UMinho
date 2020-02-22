import javax.xml.crypto.Data;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Server extends Thread {

    private Connection connection;

    private int port;
    private int new_port;

    private boolean running;

    public Server() throws SocketException, UnknownHostException {
        this.port = 4445;
        this.connection = new Connection(this.port, InetAddress.getLocalHost());
        this.new_port = this.port;
    }


    public void run() {
        try {
            running = true;

            while (running) {

                this.connection.receive();

                PDU p = this.connection.getPacket_received();

                System.out.println("SEQ: " + p.getSeq_Number() + " ACK: " + p.getAck_Number() + " FLAG: " + p.getFlag() + " DATA: " + new String(p.getData(), 0, p.getData().length) );

                System.out.println("Port_to_send: " + this.connection.getPort_toSend() + " Adress_to_send: " + this.connection.getTheir_adress());

                //System.out.println(this.connection.getMessage_received());

                Connection new_c = new Connection(++new_port, InetAddress.getLocalHost());
                new_c.setPort_toSend(this.connection.getPort_toSend());
                new_c.setTheir_adress(this.connection.getTheir_adress());

                Handler handler = new Handler(new_c);
                handler.start();


                /*

               System.out.println("Requesting connection");
                        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                        if(input.readLine().matches("yes")){
                            PDU accept = new PDU();
                            accept.setFlag(3);
                            accept.setData("YES".getBytes());
                            this.connection.send(accept);
                        }
                        else if(input.readLine().matches("no")){
                            PDU refuse = new PDU();
                            refuse.setFlag(4);
                            refuse.setData("YES".getBytes());
                            this.connection.send(refuse);
                        }
                 */
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

