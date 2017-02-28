package com.anotheria.bootcamp.file_transfer.client.commands;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.client.console.ConsoleHandler;
import com.anotheria.bootcamp.file_transfer.client.file_transfer.FileTransferHandler;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.FileTransferRequestErrors;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.client.ClientCommandHandler;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.FileTransferAbortCommand;

import java.io.IOException;

class CommandsResponseHandler extends ClientCommandHandler {

    private ConsoleHandler consoleHandler;
    private FileTransferHandler fileTransferHandler;
    private String nextFileName;

    void setNextFileName(String nextFileName){
        this.nextFileName = nextFileName;
    }

    CommandsResponseHandler(ConsoleHandler consoleHandler, FileTransferHandler fileTransferHandler) {
        this.consoleHandler = consoleHandler;
        this.fileTransferHandler = fileTransferHandler;
    }

    @Override
    public void processFilesList(String[] filesList) {

        consoleHandler.printLine();
        consoleHandler.printLine("Server files:");

        for (String fileName : filesList
             ) {

            consoleHandler.printLine(fileName + ',');

        }

    }

    @Override
    public void processGetFileError(FileTransferRequestErrors errorCode) {

        switch (errorCode){

            case ERROR_FILE_LOCKED:
                consoleHandler.printLine("File is writing now by other user");
                break;
            case ERROR_FILE_NOT_EXISTS:
                consoleHandler.printLine("File not found.");
                break;
        }

    }

    @Override
    public void processPutFileError(FileTransferRequestErrors errorCode) {

        switch (errorCode){

            case ERROR_FILE_LOCKED:
                consoleHandler.printLine("File is writing or reading now by other user");
                break;
            case ERROR_FILE_NOT_EXISTS:
                consoleHandler.printLine("File not found.");
                break;
        }

    }

    @Override
    public void processGetFileConfirm(Token token, long fileSize, String address, int port) {

        try {
            fileTransferHandler.receiveFile(
                nextFileName, token, address, port, fileSize
            );
            consoleHandler.printLine("File transfer complete");
        } catch (IOException e) {
            consoleHandler.error("Error, when trying to receive file.");
        }

    }

    @Override
    public void processPutFileConfirm(Token token, boolean isOverwrite, String address, int port) {

        if(!isOverwrite || consoleHandler.ask("File with such name exists. Overwrite it?")){

            try {
                fileTransferHandler.sendFile(nextFileName, token, address, port);
                consoleHandler.printLine("File transfer complete");
            } catch (IOException e) {
                consoleHandler.error("Error, when trying to send file.");
            }

        }
        else {

            try {
                getConnection().sendCommand(new FileTransferAbortCommand(token));
            } catch (IOException e) {
                consoleHandler.error("Error, when trying to send file.");
            }

        }

    }

}
