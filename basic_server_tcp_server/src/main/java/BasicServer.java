import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class BasicServer {

    private int port;
    private String address;

    public BasicServer(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void run(){

        ServerSocket socket = null;

        try {
            socket = new ServerSocket(port, 0, InetAddress.getByName(address));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("server is started");

        try {
            while(true){

                new Thread(
                        new ServerThread(socket.accept())
                ).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
