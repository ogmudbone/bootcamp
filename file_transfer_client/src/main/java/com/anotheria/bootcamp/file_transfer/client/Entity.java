package com.anotheria.bootcamp.file_transfer.client;

import com.anotheria.bootcamp.file_transfer.client.commands.CommandsHandler;
import com.anotheria.bootcamp.file_transfer.client.console.ConsoleHandler;
import com.anotheria.bootcamp.file_transfer.client.file_transfer.FileTransferHandler;

public abstract class Entity {

    private Client client;

    public void setClient(Client client){
        this.client = client;
    }

    public Client getClient(){
        return this.client;
    }

    protected FileTransferHandler getFileTransferHandler(){
        return client.getFileTransferHandler();
    }

    protected ConsoleHandler getConsoleHandler(){
        return client.getConsoleHandler();
    }

    protected CommandsHandler getCommandsHandler() {
        return client.getCommandsHandler();
    }

}
