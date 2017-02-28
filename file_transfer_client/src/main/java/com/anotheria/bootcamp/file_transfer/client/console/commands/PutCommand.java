package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;
import com.anotheria.bootcamp.file_transfer.commands.exceptions.HandlerNotFoundException;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.PutFileCommand;

import java.io.IOException;

public class PutCommand extends BaseConsoleCommand {

    private String fileName;

    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

        if(args.length == 0)
            throw new InvalidConsoleArgumentsException("File name is not set!");

        fileName = args[0];

    }

    @Override
    public void processCommand() {

        if(getFileTransferHandler().fileExists(fileName))
            try {
                getCommandsHandler().sendCommand(
                        new PutFileCommand(
                                fileName,
                                getFileTransferHandler().getFileSize(fileName)
                                )
                );
                getCommandsHandler().waitNextCommand(fileName);
            } catch (IOException | HandlerNotFoundException | ClassNotFoundException e) {
                getConsoleHandler().error(
                        "Error occurred when trying to request file data"
                );
            }

        else
            getConsoleHandler().printLine("File with such name not exists");

    }

}
