package com.anotheria.bootcamp.file_transfer.file_transfer_command.server;

public class CloseConnectionCommand extends ServerCommand {

    private static final long serialVersionUID = 4187152811045510250L;

    @Override
    protected void execute(ServerCommandHandler handler) {
        handler.closeConnection();
    }

}
