import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Connection {

    private DatagramSocket socket;
    private InetAddress my_adress;
    private InetAddress their_adress;

    private int my_port;
    private int port_toSend;

    private PDU packet_received;


    private String message_received;
    private String message_toSend;

    private byte[] receivedData;

    public Connection(int port, InetAddress address) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.my_port = port;
        this.my_adress = address;
        this.receivedData = new byte[1448];
        this.packet_received = new PDU();
    }

    public Connection(Connection c) throws SocketException {
        this.socket = c.getSocket();
        this.my_adress = c.getMyAdress();
        this.their_adress = c.getTheir_adress();
        this.my_port = c.getMy_port();
        this.port_toSend = c.getPort_toSend();

    }

    public InetAddress getMyAdress(){
        return this.my_adress;
    }

    public int getMy_port(){
        return this.my_port;
    }

    public DatagramSocket getSocket(){
        return this.socket;
    }

    public InetAddress getTheir_adress(){
        return this.their_adress;
    }

    public int getPort_toSend(){
        return this.port_toSend;
    }

    public String getMessage_received(){
        return this.message_received;
    }

    public PDU getPacket_received(){
        return this.packet_received;
    }

    public void setTheir_adress(InetAddress adress){
        this.their_adress = adress;
    }

    public void setPort_toSend(int port){
        this.port_toSend = port;
    }

    public void setMessage_toSend(String message){
        this.message_toSend = message;
    }

    public void receive() throws IOException {
        DatagramPacket receive = new DatagramPacket(this.receivedData, this.receivedData.length);
        this.socket.receive(receive);

        this.their_adress = receive.getAddress();
        this.port_toSend = receive.getPort();

        this.packet_received.fromBytes(receive.getData(), receive.getLength());
    }

    public void send(PDU pdu) throws IOException {
        byte[] sendData = pdu.toBytes();

        DatagramPacket send = new DatagramPacket(sendData, sendData.length, this.their_adress, this.port_toSend);
        this.socket.send(send);

    }

    public Connection clone(){
        try {
            return new Connection(this);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }





}
