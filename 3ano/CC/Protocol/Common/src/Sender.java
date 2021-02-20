import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;

public class Sender extends Thread {


    private Connection connection;

    private File file;

    private Map<Integer, PDU> packets;


    private boolean running;


    public Sender(Connection connection, File file, Map<Integer, PDU> packetsSent){
        this.connection = connection;
        this.file = file;
        this.packets = packetsSent;
    }

    public ArrayList<byte[]> messageFrag(String message){

        ArrayList<byte[]> frag = new ArrayList<>();

        for(char c : message.toCharArray()){
            byte[] new_b = new byte[1];
            new_b[0] = (byte) c;

            frag.add(new_b);
        }

        return frag;
    }

    public ArrayList<byte[]> fileToBytes(File file) throws IOException {

        ArrayList<byte[]> broken = new ArrayList<>();
        FileInputStream input = new FileInputStream(file);
        byte[] buffer = new byte[1428];

        boolean reading = true;
        int read;

        while(reading){
            read = input.read(buffer);

            if(read != -1) {
                byte[] add = Arrays.copyOfRange(buffer, 0, read);
                broken.add(add);

                buffer = new byte[1428];
            }

            if(read == -1) reading = false;

        }

        input.close();

        return broken;

    }

    public ArrayList<PDU> generatePackets(ArrayList<byte[]> data){

        ArrayList<PDU> all_packets = new ArrayList<>();

        int i = 0;

        for(byte[] b : data) {
            CRC32 checksum = new CRC32();
            PDU p = new PDU();
            p.setSeq_Number(i);
            p.setAck_Number(i);
            p.setFlag(5);
            p.setData(b);

            checksum.update(p.getSeq_Number());
            checksum.update(p.getData());
            p.setCheckSum(checksum.getValue());

            System.out.println(p.getCheckSum());

            all_packets.add(p);
            i++;
        }

        return all_packets;

    }



    @Override
    public void run() {

        try {

            Thread.sleep(1000);
            ArrayList<PDU> toSend = generatePackets(fileToBytes(this.file));

            for(PDU p : toSend){
                this.connection.send(p);
                this.packets.put(p.getSeq_Number(), p.clone());
            }

            Thread.sleep(500);

            while(this.packets.size() > 0){
                for(PDU pdu : this.packets.values()){
                    System.out.println("SENDING ---> " + pdu.toString());
                    this.connection.send(pdu);
                }
            }


            PDU fin = new PDU();
            fin.setFlag(2);
            fin.setData("end".getBytes());

            this.connection.send(fin);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
