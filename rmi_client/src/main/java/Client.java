import com.anotheria.bootcamp.rmi.core.EchoInterface;
import com.anotheria.bootcamp.rmi.core.RegistryData;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    public static void main(String[] args){

        try {
            Registry registry = LocateRegistry.getRegistry(null, RegistryData.SERVER_PORT);
            EchoInterface server = (EchoInterface)registry.lookup(RegistryData.BIND_NAME);
            Scanner scanner = new Scanner(System.in);

            while(true){

                String line = scanner.nextLine();
                System.out.print(server.echo(line));

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }

}
