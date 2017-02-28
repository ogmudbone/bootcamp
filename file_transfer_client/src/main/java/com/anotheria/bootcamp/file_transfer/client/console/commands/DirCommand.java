package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;
import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.FilesListRequestCommand;

import java.io.IOException;

public class DirCommand extends BaseConsoleCommand {

    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

    }

    @Override
    public void processCommand() {

        try {
            getCommandsHandler().sendCommand(
                    new FilesListRequestCommand()
            );
            getCommandsHandler().waitNextCommand();
        } catch (IOException | HandlerNotFoundException | ClassNotFoundException e) {
            getConsoleHandler().error(
                    "Error occurred when trying to get files list"
            );
            e.printStackTrace();
        }

    }

}
