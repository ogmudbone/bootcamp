package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

import com.anotheria.bootcamp.file_transfer.file_transfer_command.FileTransferRequestErrors;

public class GetFileErrorCommand extends ClientCommand {

    private static final long serialVersionUID = -1447107967777221879L;

    private FileTransferRequestErrors errorCode;

    public GetFileErrorCommand(FileTransferRequestErrors errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    protected void execute(ClientCommandHandler handler) {
        handler.processGetFileError(errorCode);
    }

}
