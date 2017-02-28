package com.anotheria.bootcamp.file_transfer.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class CommandTransferServer {

    private ServerSocket socket;

    public CommandTransferServer(String address, int port) throws IOException {
        socket = new ServerSocket(port, 0, InetAddress.getByName(address));
    }

    public CommandTransferConnection accept() throws IOException {
        return new CommandTransferConnection(socket.accept());
    }

    public void close() throws IOException {
        socket.close();
    }

}
