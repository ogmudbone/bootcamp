package com.anotheria.bootcamp.basic_server_udp_client;

import com.anotheria.bootcamp.basic_server_core.ClientArgumentsData;

import java.io.IOException;
import java.net.*;

public class ServerMessageSender {

    private int port;
    private String address;
    private String message;

    public ServerMessageSender(ClientArgumentsData data){
        this.port    = data.getPort();
        this.address = data.getAddress();
        this.message = data.getMessage();
    }

    public void send(){

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(1);
        }

        byte[] buffer = new byte[64 * 1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);

        try {

            socket.send(new DatagramPacket(
                    message.getBytes(),
                    message.getBytes().length,
                    InetAddress.getByName(address),
                    port
                    ));

            socket.receive(response);

            System.out.println(new String(response.getData(), 0, response.getLength()));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
