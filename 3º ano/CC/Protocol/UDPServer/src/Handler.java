import com.sun.corba.se.impl.oa.poa.AOMEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Handler extends Thread{

    private Connection connection;
    private Map<Integer, PDU> packets;
    private PDU received;
    private boolean running;

    public Handler(Connection c){
        this.connection = c;
        this.packets = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        try{

            this.running = true;

            while(running){

                PDU ahoy = new PDU();
                ahoy.setData("AHOY".getBytes());

                this.connection.send(ahoy);

                this.connection.receive();
                received = this.connection.getPacket_received();

                switch(received.getFlag()){
                    case 0:

                        System.out.println(" < Requested download ");

                        File f = new File("adolf.jpg");


                        Sender sender = new Sender(this.connection, f, this.packets);
                        AckR r = new AckR(this.connection, this.packets);
                        sender.start();
                        r.start();
                        sender.join();
                        r.join();


                        //for(PDU p: packets) System.out.println(p.toString());

                        break;
                    case 1:

                        AtomicBoolean flag = new AtomicBoolean(true);
                        System.out.println(" < Requested upload ");

                        File file = new File("UDPServer/src/adolf.jpg");

                        Receiver receiver = new Receiver(this.connection, this.packets, file, flag);
                        AckS s = new AckS(this.connection, this.packets, flag);
                        receiver.start();
                        s.start();
                        receiver.join();
                        s.join();

                        break;
                    case 2:
                        System.out.println("WAZAAAA");
                        break;
                    case 5:
                        break;
                    default:
                        this.running = false;
                        System.out.println("OUT BITCHES");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
