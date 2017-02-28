package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;

public class HelpCommand extends BaseConsoleCommand {

    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

    }

    @Override
    public void processCommand() {
        getConsoleHandler().printLine();
        getConsoleHandler().printLine(
                   "DIR - view list of server files;\n" +
                        "GET $fileName - download file with given name from server;\n" +
                        "PUT $fileName - upload file with given name to server\n" +
                        "LS - view local files\n" +
                        "EXIT - shut down connection\n" +
                        "HELP - view list of commands\n"
        );
        getConsoleHandler().printLine();
    }

}
