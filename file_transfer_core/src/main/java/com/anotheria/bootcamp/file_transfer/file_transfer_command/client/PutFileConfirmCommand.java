package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

import com.anotheria.bootcamp.file_transfer.Token;

public class PutFileConfirmCommand extends ClientCommand {

    private static final long serialVersionUID = -1037984437146148186L;

    private Token token;
    private boolean isOverwrite;
    private String address;
    private int port;

    public PutFileConfirmCommand(Token token, boolean isOverwrite, String address, int port) {
        this.token = token;
        this.isOverwrite = isOverwrite;
        this.address = address;
        this.port = port;
    }

    @Override
    protected void execute(ClientCommandHandler handler) {
        handler.processPutFileConfirm(token, isOverwrite, address, port);
    }

}
