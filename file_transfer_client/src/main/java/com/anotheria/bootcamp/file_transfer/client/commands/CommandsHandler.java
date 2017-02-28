package com.anotheria.bootcamp.file_transfer.client.commands;

import com.anotheria.bootcamp.file_transfer.client.Client;
import com.anotheria.bootcamp.file_transfer.client.Entity;
import com.anotheria.bootcamp.file_transfer.commands.CommandTransferConnection;
import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.ServerCommand;

import java.io.IOException;

public class CommandsHandler extends Entity {

    private CommandTransferConnection connection;
    private CommandsResponseHandler responseHandler;

    public CommandsHandler(String address, int port, Client client) throws IOException {
        setClient(client);
        connection = new CommandTransferConnection(address, port);
        responseHandler = new CommandsResponseHandler(
                getConsoleHandler(), getFileTransferHandler()
        );
        connection.addHandler(responseHandler);
    }

    public void waitNextCommand() throws HandlerNotFoundException, IOException, ClassNotFoundException {
        connection.receiveCommand();
    }

    public void waitNextCommand(String fileName) throws HandlerNotFoundException, IOException, ClassNotFoundException {
        responseHandler.setNextFileName(fileName);
        connection.receiveCommand();
    }

    public void sendCommand(ServerCommand command) throws IOException {
        connection.sendCommand(command);
    }

    public void close() throws IOException {
       // connection.close();
    }

}