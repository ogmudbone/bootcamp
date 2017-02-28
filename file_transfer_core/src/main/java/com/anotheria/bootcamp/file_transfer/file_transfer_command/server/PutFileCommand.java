package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

public class PutFileCommand extends ServerCommand {

    private static final long serialVersionUID = 8616911011732702933L;

    private String fileName;
    private long fileSize;

    public PutFileCommand(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    protected void execute(ServerCommandHandler handler) {
        handler.processPutFileRequest(fileName, fileSize);
    }
}
