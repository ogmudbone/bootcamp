package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;

public class LsCommand extends BaseConsoleCommand {
    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

    }

    @Override
    public void processCommand() {

        String[] filesList = getFileTransferHandler().getFilesNamesList();

        getConsoleHandler().printLine();
        getConsoleHandler().printLine("Local files:");

        for (String fileName : filesList
                ) {

            getConsoleHandler().printLine(fileName + ',');

        }

    }
}
