package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.commands.BaseCommandHandler;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.FileTransferRequestErrors;

public abstract class ClientCommandHandler extends BaseCommandHandler{

    public abstract void processFilesList(String[] filesList);
    public abstract void processGetFileError(FileTransferRequestErrors errorCode);
    public abstract void processPutFileError(FileTransferRequestErrors errorCode);
    public abstract void processGetFileConfirm(Token token, long fileSize, String address, int port);
    public abstract void processPutFileConfirm(Token token, boolean isOverwrite, String address, int port);

}
