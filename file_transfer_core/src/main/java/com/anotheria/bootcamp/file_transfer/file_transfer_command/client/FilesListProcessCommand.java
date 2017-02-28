package com.anotheria.bootcamp.file_transfer.file_transfer_command.client;

public class FilesListProcessCommand extends ClientCommand {

    private static final long serialVersionUID = -1256928828492169337L;

    private String[] filesNames;

    public FilesListProcessCommand(String[] filesNames) {
        this.filesNames = filesNames;
    }

    @Override
    protected void execute(ClientCommandHandler handler) {
        handler.processFilesList(filesNames);
    }

}
