import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {


    public static void main(String[] args) throws SocketException, UnknownHostException {

        Server server = new Server();
        server.start();
    }
}
