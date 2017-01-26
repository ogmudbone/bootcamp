import com.anotheria.bootcamp.basic_server_tcp_client.ServerMessageSender;

public class Main {

    public static void main(String[] args){

        try {

            if (args.length < 2) {
                throw new IllegalArgumentException("There must be at least 2 arguments");
            }

            StringBuilder message = new StringBuilder();
            String address;
            int port;

            String[] addressAndPort = args[0].split(":");

            for (int i = 1; i < args.length; i++) {
                if (i != 1) message.append(' ');
                message.append(args[i]);
            }

            if (addressAndPort.length != 2)
                throw new IllegalArgumentException("Invalid server address");

            address = addressAndPort[0];

            try {
                port = Integer.parseInt(addressAndPort[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid server address");
            }

            new ServerMessageSender(address, port, message.toString()).send();

        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid arguments : " + e.getMessage());
        }
    }

}
