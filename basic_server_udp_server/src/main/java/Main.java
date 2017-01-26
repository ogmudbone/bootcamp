import com.anotheria.bootcamp.basic_server_udp_server.BasicServer;

public class Main {

    public static void main(String[] args){
        new BasicServer(8080, "localhost").run();
    }

}
