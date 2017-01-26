package com.anotheria.bootcamp.basic_server_udp_server;

import java.io.IOException;
import java.net.*;

public class BasicServer {

    private int port;
    private String address;

    public BasicServer(int port, String address) {
        this.port = port;
        this.address = address;
    }

    public void run(){

        DatagramSocket socket = null;
        byte[] buffer = new byte[64 * 1024];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        DatagramPacket response = new DatagramPacket("OK".getBytes(), "OK".getBytes().length);

        try {

            socket = new DatagramSocket(port, InetAddress.getByName(address));
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("server is started");

        try {
            while(true){

                socket.receive(incoming);
                byte[] data = incoming.getData();

                response.setAddress(incoming.getAddress());
                response.setPort(incoming.getPort());

                System.out.println(new String(data, 0, data.length));
                socket.send(response);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
