package com.anotheria.bootcamp.rmi.core;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoInterface extends Remote {

    String echo(String string) throws RemoteException;

}
