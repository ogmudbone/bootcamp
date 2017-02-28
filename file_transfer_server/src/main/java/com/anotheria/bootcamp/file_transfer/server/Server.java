package com.anotheria.bootcamp.file_transfer.server;

import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;
import com.anotheria.bootcamp.file_transfer.server.commands.CommandServerThread;
import com.anotheria.bootcamp.file_transfer.server.file_transfer.FileTransferServerThread;
import com.anotheria.bootcamp.file_transfer.server.files.FileTransferTaskManager;
import com.anotheria.bootcamp.file_transfer.server.files.FilesLock;

import java.io.IOException;

public class Server {

    private FilesLock filesLock = new FilesLock();
    private FileTransferTaskManager taskManager = new FileTransferTaskManager();
    private DirectoryBrowser directoryBrowser;
    private Logger logger = new Logger();

    private String fileTransferServerAddress;
    private int fileTransferServerPort;

    private Thread fileTransferThread;
    private Thread commandsThread;

    public Server(
            String commandServerAddress, int commandServerPort,
            String fileTransferServerAddress, int fileTransferServerPort,
            String serverDirectoryPath
    ){
        this.fileTransferServerAddress = fileTransferServerAddress;
        this.fileTransferServerPort = fileTransferServerPort;

        CommandServerThread commandServerRunnable =
                new CommandServerThread(commandServerAddress, commandServerPort);
        FileTransferServerThread fileTransferServerRunnable =
                new FileTransferServerThread(fileTransferServerAddress, fileTransferServerPort);

        commandServerRunnable.setServer(this);
        fileTransferServerRunnable.setServer(this);

        try {
            directoryBrowser = new DirectoryBrowser(serverDirectoryPath);
        } catch (IOException e) {
            logger.log("Invalid directory path", e);
        }

        fileTransferThread = new Thread(fileTransferServerRunnable);
        commandsThread = new Thread(commandServerRunnable);

    }

    public void start(){

        if(!commandsThread.isAlive() && !fileTransferThread.isAlive()) {
            fileTransferThread.start();
            commandsThread.start();
        }

    }

    public void stop(){

        if(commandsThread.isAlive() && fileTransferThread.isAlive()) {
            fileTransferThread.interrupt();
            commandsThread.interrupt();
            filesLock.close();
        }

    }

    DirectoryBrowser getDirectoryBrowser() {
        return directoryBrowser;
    }

    FileTransferTaskManager getTaskManager() {
        return taskManager;
    }

    FilesLock getFilesLock() {
        return filesLock;
    }

    String getFileTransferServerAddress() {
        return fileTransferServerAddress;
    }

    int getFileTransferServerPort() {
        return fileTransferServerPort;
    }

    Logger getLogger(){
        return logger;
    }

}
