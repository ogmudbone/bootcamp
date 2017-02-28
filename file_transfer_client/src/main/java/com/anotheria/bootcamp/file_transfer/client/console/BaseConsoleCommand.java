package com.anotheria.bootcamp.file_transfer.client.console;

import com.anotheria.bootcamp.file_transfer.client.Entity;

public abstract class BaseConsoleCommand extends Entity{

    public abstract void applyArgs(String[] args) throws InvalidConsoleArgumentsException;
    public abstract void processCommand();

}