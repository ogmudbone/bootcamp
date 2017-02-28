package com.anotheria.bootcamp.file_transfer.file_transfer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class FileTransferServer {

    private ServerSocket socket;

    public FileTransferServer(String address, int port) throws IOException {
        socket = new ServerSocket(port, 0, InetAddress.getByName(address));
    }

    public void close() throws IOException {
        socket.close();
    }

    public FileTransferConnection accept() throws IOException {
        return new FileTransferConnection(socket.accept());
    }

}
