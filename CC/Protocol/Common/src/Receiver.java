import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;

public class Receiver extends Thread {

    private Connection connection;

    private Map<Integer, PDU> packets_received;
    private Map<Integer, PDU> packets;

    private File file;

    private PDU packet_received;

    private AtomicBoolean running;



    public Receiver(Connection connection, Map<Integer, PDU> packets, File file, AtomicBoolean running) throws SocketException, UnknownHostException {
        this.connection = connection;
        this.packets_received = packets;
        this.file = file;
        this.running = running;
        this.packets = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        try {

            Thread.sleep(1000);

            while(this.running.get()) {

                this.connection.receive();

                this.packet_received = this.connection.getPacket_received();

                if(this.packet_received.getFlag() != 2){
                    CRC32 checksum = new CRC32();
                    checksum.update(this.packet_received.getSeq_Number());
                    checksum.update(this.packet_received.getData());

                    //System.out.println(this.packet_received.getCheckSum());

                    if(this.packet_received.getCheckSum() == checksum.getValue()){
                        this.packets_received.put(this.packet_received.getSeq_Number(), this.packet_received.clone());
                        this.packets.put(this.packet_received.getSeq_Number(), this.packet_received.clone());
                    }
                    else System.out.println("CHECKSUM FODIDO");
                }

                System.out.println(this.packet_received.toString());

                if(this.packet_received.getFlag() == 2) this.running.set(false);

            }

            this.file.createNewFile();
            FileOutputStream fos = new FileOutputStream(this.file);

            for(PDU pdu : this.packets.values()){
                fos.write(pdu.getData());
            }

            fos.flush();
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
