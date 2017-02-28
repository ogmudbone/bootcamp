package com.anotheria.bootcamp.file_transfer.client;

import com.anotheria.bootcamp.file_transfer.client.commands.CommandsHandler;
import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.ConsoleHandler;
import com.anotheria.bootcamp.file_transfer.client.console.commands.*;
import com.anotheria.bootcamp.file_transfer.client.file_transfer.FileTransferHandler;

import java.io.IOException;

public class Client {

    private final String address;
    private final int port;
    private final String folderPath;
    private ConsoleHandler consoleHandler;
    private FileTransferHandler fileTransferHandler;
    private CommandsHandler commandsHandler;

    private boolean isInterrupted = false;

    ConsoleHandler getConsoleHandler() {
        return consoleHandler;
    }
    FileTransferHandler getFileTransferHandler() {
        return fileTransferHandler;
    }
    CommandsHandler getCommandsHandler(){
        return commandsHandler;
    }

    public Client(String address, int port, String folderPath){

        this.address = address;
        this.port = port;
        this.folderPath = folderPath;
    }

    public void start(){

        consoleHandler = new ConsoleHandler();
        consoleHandler.setClient(this);
        consoleHandler.addCommand("GET",  new GetCommand());
        consoleHandler.addCommand("PUT",  new PutCommand());
        consoleHandler.addCommand("DIR",  new DirCommand());
        consoleHandler.addCommand("EXIT", new ExitCommand());
        consoleHandler.addCommand("LS",   new LsCommand());
        consoleHandler.addCommand("HELP", new HelpCommand());

        try {
            fileTransferHandler = new FileTransferHandler(folderPath);
            fileTransferHandler.setClient(this);
        } catch (IOException e) {
            consoleHandler.error("No such directory exists!", true);
        }

        try {
            commandsHandler = new CommandsHandler(address, port, this);
        } catch (IOException e) {
            consoleHandler.error("Error occurred when trying to connect to server");
        }

        consoleHandler.printLine("Connected to server " + address + ":" + port);
        consoleHandler.invokeCommand("HELP").processCommand();

        while(!isInterrupted){
            BaseConsoleCommand command = consoleHandler.nextCommand();
            if (command != null) command.processCommand();
        }

    }

    public void close(){
        try {
            isInterrupted = true;
            commandsHandler.close();
        } catch (IOException ignored) {}
    }

}
