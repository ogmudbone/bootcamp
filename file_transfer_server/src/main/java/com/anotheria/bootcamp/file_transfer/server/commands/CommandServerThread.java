package com.anotheria.bootcamp.file_transfer.server.commands;

import com.anotheria.bootcamp.file_transfer.commands.CommandTransferServer;
import com.anotheria.bootcamp.file_transfer.server.Entity;
import com.anotheria.bootcamp.file_transfer.server.Server;

import java.io.IOException;

public class CommandServerThread extends Entity implements Runnable {

    private String address;
    private int port;

    public CommandServerThread(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void run(){

        CommandTransferServer commandTransferServer;

        try {

            commandTransferServer = new CommandTransferServer(address, port);

            while (!Thread.currentThread().isInterrupted()){
                try {

                    CommandConnectionThread commandConnectionThread =
                            new CommandConnectionThread(commandTransferServer.accept());
                    commandConnectionThread.initHandlers(this);
                    new Thread(commandConnectionThread).start();
                } catch (IOException e) {
                    getLogger().log("Problems of new connection init", e);
                }
            }

        } catch (IOException e) {
            getLogger().log("Problems init command transfer server. Shutting down...", e);
            stop();
        }

    }

}
