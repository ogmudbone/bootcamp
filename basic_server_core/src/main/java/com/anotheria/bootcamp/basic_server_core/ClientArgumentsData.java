package com.anotheria.bootcamp.basic_server_core;

public class ClientArgumentsData {

    private String address;
    private int port;
    private String message;

    public static ClientArgumentsData parse(String[] args) throws IllegalArgumentException{

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

        return new ClientArgumentsData(address, port, message.toString());

    }

    private ClientArgumentsData(String address, int port, String message) {
        this.address = address;
        this.port = port;
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getMessage() {
        return message;
    }

}
