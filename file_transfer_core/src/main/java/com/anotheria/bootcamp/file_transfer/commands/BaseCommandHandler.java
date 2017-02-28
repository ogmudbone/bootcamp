package com.anotheria.bootcamp.file_transfer.commands;

public abstract class BaseCommandHandler {

    private ThreadLocal<CommandTransferConnection> connection = new ThreadLocal<>();

    protected CommandTransferConnection getConnection() {
        return connection.get();
    }

    void setConnection(CommandTransferConnection connection) {
        this.connection.set(connection);
    }

}
