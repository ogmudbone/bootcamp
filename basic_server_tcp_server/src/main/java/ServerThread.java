import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        InputStream is  = null;
        OutputStream os = null;

        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        byte buf[] = new byte[64*1024];

        try {
            int r = is.read(buf);
            System.out.println(new String(buf, 0, r));
            os.write("OK".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
