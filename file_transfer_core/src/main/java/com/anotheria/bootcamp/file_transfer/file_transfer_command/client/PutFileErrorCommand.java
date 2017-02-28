package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

import com.anotheria.bootcamp.file_transfer.file_transfer_command.FileTransferRequestErrors;

public class PutFileErrorCommand extends ClientCommand {

    private static final long serialVersionUID = 1198037033231849402L;

    private FileTransferRequestErrors errorCode;

    public PutFileErrorCommand(FileTransferRequestErrors errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    protected void execute(ClientCommandHandler handler) {
        handler.processPutFileError(errorCode);
    }

}