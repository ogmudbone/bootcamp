package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.commands.BaseCommandHandler;

public abstract class ServerCommandHandler extends BaseCommandHandler {

    public abstract void processFilesListRequest();
    public abstract void processGetFileRequest(String fileName);
    public abstract void processPutFileRequest(String fileName, long fileSize);
    public abstract void processFileTransferAbort(Token token);
    public abstract void closeConnection();

}
