package com.anotheria.bootcamp.arguments_parser.parsers;

import com.anotheria.bootcamp.arguments_parser.AddressAndPort;
import com.anotheria.bootcamp.arguments_parser.InvalidArgumentException;
import com.anotheria.bootcamp.arguments_parser.ValidatorInterface;

public class AddressAndPortValidator implements ValidatorInterface<AddressAndPort> {

    public AddressAndPort parseArgument(String argumentString) throws InvalidArgumentException {

        String address;
        int port;

        String[] addressAndPort = argumentString.split(":");

        if (addressAndPort.length != 2)
            throw new InvalidArgumentException("Invalid server address");

        address = addressAndPort[0];

        try {
            port = Integer.parseInt(addressAndPort[1]);
        }
        catch (NumberFormatException e){
            throw new InvalidArgumentException("Invalid port");
        }

        return new AddressAndPort(address, port);

    }

}
