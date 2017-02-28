package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

import com.anotheria.bootcamp.file_transfer.Token;

public class FileTransferAbortCommand extends ServerCommand {

    private static final long serialVersionUID = -3585605835942157281L;

    private Token token;

    public FileTransferAbortCommand(Token token) {
        this.token = token;
    }

    @Override
    protected void execute(ServerCommandHandler handler) {
        handler.processFileTransferAbort(token);
    }

}
