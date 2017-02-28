package com.anotheria.bootcamp.file_transfer.client.console.commands;

import com.anotheria.bootcamp.file_transfer.client.console.BaseConsoleCommand;
import com.anotheria.bootcamp.file_transfer.client.console.InvalidConsoleArgumentsException;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.CloseConnectionCommand;

import java.io.IOException;

public class ExitCommand extends BaseConsoleCommand{

    @Override
    public void applyArgs(String[] args) throws InvalidConsoleArgumentsException {

    }

    @Override
    public void processCommand() {

        try {
            getCommandsHandler().sendCommand(new CloseConnectionCommand());
        } catch (IOException e) {
            getConsoleHandler().error("Error while shut down connection");
        }
        finally {
            getClient().close();
        }

    }

}
