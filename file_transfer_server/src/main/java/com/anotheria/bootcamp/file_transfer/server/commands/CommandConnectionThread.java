package com.anotheria.bootcamp.file_transfer.server.commands;

import com.anotheria.bootcamp.file_transfer.commands.CommandTransferConnection;
import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;
import com.anotheria.bootcamp.file_transfer.server.Entity;
import com.anotheria.bootcamp.file_transfer.server.Logger;

import java.io.IOException;

class CommandConnectionThread extends Entity implements Runnable {

    private CommandTransferConnection connection;

    CommandConnectionThread(CommandTransferConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run(){

        getLogger().log("New Connection.", Logger.LogLevel.INFO);

        connection.addHandler(new CommandsHandler(
                getTaskManager(),
                getFilesLock(),
                getDirectoryBrowser(),
                getLogger(), getFileTransferServerAddress(),
                getFileTransferServerPort()
        ));

        while(!Thread.currentThread().isInterrupted() && connection.isConnected()){
            try {
                connection.receiveCommand();
            }
            catch (IOException e){
                getLogger().log("Unexpected connection break.", e);
                break;
            }
            catch (ClassNotFoundException | HandlerNotFoundException e ) {
                getLogger().log("Corrupted data from client. Breaking connection...", e);
                break;
            }
        }

    }

}
