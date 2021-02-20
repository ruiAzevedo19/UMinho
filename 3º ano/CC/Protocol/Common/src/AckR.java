import java.io.IOException;
import java.util.Map;

public class AckR extends Thread {

    private Connection connection;
    private PDU packet;
    private Map<Integer, PDU> packets;

    public AckR(Connection connection, Map<Integer, PDU> packets){
        this.connection = connection;
        this.packets = packets;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);


            boolean running = true;

            while(running){
                this.connection.receive();
                packet = this.connection.getPacket_received();

                System.out.println(" <ACK RECEIVED> --> " + this.packet.toString());

                System.out.println();
                this.packets.remove(this.packet.getSeq_Number());

                if(this.packets.size() == 0) running = false;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
