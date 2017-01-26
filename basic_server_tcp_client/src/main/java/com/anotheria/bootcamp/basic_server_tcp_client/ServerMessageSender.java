package com.anotheria.bootcamp.basic_server_tcp_client;

import java.io.IOException;
import java.net.Socket;

public class ServerMessageSender {

    private int port;
    private String address;
    private String message;

    public ServerMessageSender(String address, int port, String message){
        this.port    = port;
        this.address  = address;
        this.message = message;
    }

    public void send(){

        Socket socket;
        byte[] buffer = new byte[64 * 1024];

        try {

            socket = new Socket(address, port);

            socket.getOutputStream().write(message.getBytes());
            int messageLength = socket.getInputStream().read(buffer);

            System.out.println(new String(buffer, 0, messageLength));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
