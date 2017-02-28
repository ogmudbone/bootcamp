package com.anotheria.bootcamp.file_transfer.commands;

import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandTransferConnection {

    private Socket socket;
    private List<BaseCommandHandler> handlers = new LinkedList<>();

    public void addHandler(BaseCommandHandler handler){
        handlers.add(handler);
    }

    private Optional<BaseCommandHandler> findHandlerForCommand(BaseCommand<?> command){
        return handlers.stream()
                .filter(command::isHandlerOfThisCommand)
                .findFirst();
    }

    public CommandTransferConnection(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    CommandTransferConnection(Socket socket){
        this.socket = socket;
    }

    public boolean isConnected(){
        return socket != null && socket.isConnected();
    }

    public void sendCommand(BaseCommand command) throws IOException {

        ObjectOutputStream objOS = new ObjectOutputStream(socket.getOutputStream());
        objOS.writeObject(command);

    }

    public void receiveCommand() throws IOException, ClassNotFoundException, HandlerNotFoundException {

        ObjectInputStream objIS = new ObjectInputStream(socket.getInputStream());
        BaseCommand<?> command = (BaseCommand) objIS.readObject();

        Optional<BaseCommandHandler> handler = findHandlerForCommand(command);

        if(handler.isPresent()) {
            handler.get().setConnection(this);
            command.executeWithHandler(handler.get());
        }
        else
            throw new HandlerNotFoundException();

    }

    public void close() throws IOException {
        socket.close();
    }

}
