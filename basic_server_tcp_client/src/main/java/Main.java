import com.anotheria.bootcamp.basic_server_core.ClientArgumentsData;
import com.anotheria.bootcamp.basic_server_tcp_client.ServerMessageSender;

public class Main {

    public static void main(String[] args){

        try {

            new ServerMessageSender(
                    ClientArgumentsData.parse(args)
            ).send();

        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid arguments : " + e.getMessage());
        }


    }

}
