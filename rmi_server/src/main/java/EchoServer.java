

import com.anotheria.bootcamp.rmi.core.EchoInterface;
import com.anotheria.bootcamp.rmi.core.RegistryData;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EchoServer implements EchoInterface {

    public static void main(String[] args){

        EchoServer server = new EchoServer();

        try{

            EchoInterface stub = (EchoInterface) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(RegistryData.SERVER_PORT);
            registry.bind(RegistryData.BIND_NAME, stub);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String echo(String string) throws RemoteException{
        System.out.println(string);
        return new StringBuilder(string).reverse().toString();
    }

}
