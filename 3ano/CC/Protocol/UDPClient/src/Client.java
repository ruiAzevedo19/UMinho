import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client extends Thread {


    private Connection connection;

    private boolean receiving;

    private Map<Integer, PDU> packets;


    public Client(int port) throws SocketException, UnknownHostException {
        this.connection = new Connection(port, InetAddress.getLocalHost());
        this.connection.setPort_toSend(4445);
        this.connection.setTheir_adress(InetAddress.getLocalHost());
        this.packets = new ConcurrentHashMap();
    }

    public void run(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            boolean running = true;

            while(running){

                this.receiving = true;

                System.out.print("G A P > ");

                String line = input.readLine();

                if(line.matches("")) continue;
                else if(line.matches("end")){
                    running = false;
                    continue;
                }

                System.out.println("Client: " + line);

                PDU p = new PDU();
                p.setFlag(2);
                p.setData(line.getBytes());


                byte[] pdu = p.toBytes();

                this.connection.send(p);
                System.out.println("SENT --->  SEQ: " + p.getSeq_Number() + " ACK: " + p.getAck_Number() + " FLAG: " + p.getFlag() + " DATA: " + new String(p.getData()));

                this.connection.receive();
                System.out.println("RECEIVED --->  " + connection.getPacket_received().toString());


                if(line.matches("download")) {
                    PDU request = this.connection.getPacket_received().clone();
                    request.setFlag(0);
                    this.connection.send(request);

                    AtomicBoolean complete = new AtomicBoolean(true);

                    File file = new File("UDPClient/src/adolf.jpg");

                    Receiver t = new Receiver(this.connection, this.packets, file, complete);
                    AckS s = new AckS(this.connection, this.packets, complete);
                    t.start();
                    s.start();
                    t.join();
                    s.join();

                    continue;
                }
                else if(line.matches("upload")){
                    PDU request = this.connection.getPacket_received().clone();
                    request.setFlag(1);
                    this.connection.send(request);

                    File f = new File("adolf.jpg");

                    Sender s = new Sender(this.connection, f, this.packets);
                    AckR r = new AckR(this.connection, this.packets);
                    s.start();
                    r.start();
                    s.join();
                    r.join();

                    continue;

                }
                else if(line.matches("leave handler")){
                    PDU fin = new PDU();
                    fin.setFlag(2);
                    fin.setData("ADIÓS".getBytes());

                    this.connection.send(fin);
                }


                //this.connection.receive();

                //this.connection.setPort_toSend(4445);


                //System.out.println("Printing..." + this.connection.getMessage_received());


            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //clientSocket.close();

    }
}
