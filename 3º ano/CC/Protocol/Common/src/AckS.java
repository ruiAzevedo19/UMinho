import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AckS extends Thread {

    private Connection connection;
    private PDU packet;
    private Map<Integer, PDU> packets;
    private AtomicBoolean running;

    public AckS(Connection connection, Map<Integer, PDU> packets, AtomicBoolean running){
        this.connection = connection;
        this.packets = packets;
        this.running = running;
    }

    @Override
    public void run() {
        try {

            Thread.sleep(1000);


            while(this.running.get()){

                Iterator iterator = this.packets.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<Integer, PDU> entry = (Map.Entry<Integer, PDU>) iterator.next();

                    this.connection.send(entry.getValue());
                    System.out.println(" <ACK SENT> --> " + entry.getValue().toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
