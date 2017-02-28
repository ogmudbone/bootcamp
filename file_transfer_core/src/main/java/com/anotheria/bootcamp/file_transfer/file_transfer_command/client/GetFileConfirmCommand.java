package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

import com.anotheria.bootcamp.file_transfer.Token;

public class GetFileConfirmCommand extends ClientCommand{

    private static final long serialVersionUID = -8561819577996677692L;

    private Token token;
    private long fileSize;
    private String address;
    private int port;

    public GetFileConfirmCommand(Token token, long fileSize, String address, int port) {
        this.token = token;
        this.fileSize = fileSize;
        this.address = address;
        this.port = port;
    }

    @Override
    protected void execute(ClientCommandHandler handler) {
        handler.processGetFileConfirm(token, fileSize, address, port);
    }

}
