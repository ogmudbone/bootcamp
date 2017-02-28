package com.anotheria.bootcamp.arguments_parser;

public class AddressAndPort {

    private String address;
    private int port;

    public AddressAndPort(String address, int port) {
        this.address = address;
        this.port = port;
    }


    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
