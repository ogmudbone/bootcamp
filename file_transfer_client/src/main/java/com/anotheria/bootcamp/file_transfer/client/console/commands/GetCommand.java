package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;
import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.GetFileCommand;

import java.io.IOException;

public class GetCommand extends BaseConsoleCommand {

    private String fileName;

    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

        if(args.length == 0)
            throw new InvalidConsoleArgumentsException("File name is not set!");

        fileName = args[0];

    }

    @Override
    public void processCommand() {

        if(!getFileTransferHandler().fileExists(fileName) ||
                getConsoleHandler().ask("File with such name exists. Overwrite it?"))

        try {
            getCommandsHandler().sendCommand(
                    new GetFileCommand(fileName)
            );
            getCommandsHandler().waitNextCommand(fileName);
        } catch (IOException | ClassNotFoundException | HandlerNotFoundException e) {
            getConsoleHandler().error("Error occurred when trying to get file data");
        }

    }

}
