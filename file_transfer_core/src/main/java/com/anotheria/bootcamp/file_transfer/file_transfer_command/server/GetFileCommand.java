package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

public class GetFileCommand extends ServerCommand {

    private static final long serialVersionUID = -1729743630360426857L;

    private String fileName;

    public GetFileCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    protected void execute(ServerCommandHandler handler) {
        handler.processGetFileRequest(fileName);
    }

}
