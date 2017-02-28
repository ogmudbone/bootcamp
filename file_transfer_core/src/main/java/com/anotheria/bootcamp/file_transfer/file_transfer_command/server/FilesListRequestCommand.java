package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

public class FilesListRequestCommand extends ServerCommand {

    private static final long serialVersionUID = -4113303726301433598L;

    @Override
    protected void execute(ServerCommandHandler handler) {
        handler.processFilesListRequest();
    }
}
