package com.anotheria.bootcamp.file_transfer.server.commands;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.commands.BaseCommand;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.FileTransferRequestErrors;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.client.FilesListProcessCommand;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.client.GetFileConfirmCommand;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.client.GetFileErrorCommand;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.client.PutFileConfirmCommand;
import com.anotheria.bootcamp.file_transfer.file_transfer_command.server.ServerCommandHandler;
import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;
import com.anotheria.bootcamp.file_transfer.server.Logger;
import com.anotheria.bootcamp.file_transfer.server.files.FileTransferTaskManager;
import com.anotheria.bootcamp.file_transfer.server.files.FilesLock;

import java.io.IOException;

class CommandsHandler extends ServerCommandHandler {

    private FileTransferTaskManager taskManager;
    private FilesLock filesLock;
    private DirectoryBrowser directoryBrowser;
    private Logger logger;

    private String fileTransferServerAddress;
    private int fileTransferServerPort;

    CommandsHandler(FileTransferTaskManager taskManager,
                    FilesLock filesLock,
                    DirectoryBrowser directoryBrowser,
                    Logger logger,
                    String fileTransferServerAddress,
                    int fileTransferServerPort) {

        this.taskManager = taskManager;
        this.filesLock = filesLock;
        this.directoryBrowser = directoryBrowser;
        this.logger = logger;
        this.fileTransferServerAddress = fileTransferServerAddress;
        this.fileTransferServerPort = fileTransferServerPort;
    }

    private void abortFileTransfer(Token token){

        taskManager.abortTask(token);
        filesLock.releaseLock(token);
    }

    private void sendFileTransferCommand(BaseCommand command, Token token){

        try {
            getConnection().sendCommand(command);
        } catch (IOException e) {

            if(token != null) {
                abortFileTransfer(token);
            }

            logger.log("Error sending command to client", e);

        }

    }

    @Override
    public void processFilesListRequest() {

        try {
            getConnection().sendCommand(
                    new FilesListProcessCommand(
                            directoryBrowser.getFileNamesList()
                    )
            );
        } catch (IOException e) {
            logger.log("Error sending command to client", e);
        }

    }

    @Override
    public void processGetFileRequest(String fileName) {

        BaseCommand command;
        Token token = null;

        if(!directoryBrowser.getFile(fileName).isPresent())
            command = new GetFileErrorCommand(FileTransferRequestErrors.ERROR_FILE_NOT_EXISTS);

        else if(filesLock.getLockForFile(fileName) == FilesLock.LockType.LOCK_WRITE)
            command = new GetFileErrorCommand(FileTransferRequestErrors.ERROR_FILE_LOCKED);

        else{

            token = taskManager.addGetTask(fileName);
            filesLock.setLock(fileName, token, FilesLock.LockType.LOCK_READ);
            command = new GetFileConfirmCommand(
                    token, directoryBrowser.getFileSize(fileName),
                    fileTransferServerAddress, fileTransferServerPort
                    );

        }

        sendFileTransferCommand(command, token);

    }

    @Override
    public void processPutFileRequest(String fileName, long fileSize) {

        BaseCommand command;
        Token token = null;

        if(filesLock.getLockForFile(fileName) != FilesLock.LockType.LOCK_NONE)
            command = new GetFileErrorCommand(FileTransferRequestErrors.ERROR_FILE_LOCKED);

        else{

            token = taskManager.addPutTask(fileName, fileSize);
            filesLock.setLock(fileName, token, FilesLock.LockType.LOCK_WRITE);

            command = new PutFileConfirmCommand(
                    token,
                    directoryBrowser.getFile(fileName).isPresent(),
                    fileTransferServerAddress, fileTransferServerPort
            );

        }

        sendFileTransferCommand(command, token);

    }

    @Override
    public void processFileTransferAbort(Token token) {
        abortFileTransfer(token);
    }

    @Override
    public void closeConnection() {
        logger.log("Client close connection", Logger.LogLevel.INFO);
        Thread.currentThread().interrupt();
    }

}
