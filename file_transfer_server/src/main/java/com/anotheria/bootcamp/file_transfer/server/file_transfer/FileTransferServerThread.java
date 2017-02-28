package com.anotheria.bootcamp.file_transfer.server.file_transfer;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferServer;
import com.anotheria.bootcamp.file_transfer.helpers.TokenTransferHelper;
import com.anotheria.bootcamp.file_transfer.server.Entity;
import com.anotheria.bootcamp.file_transfer.server.Logger;
import com.anotheria.bootcamp.file_transfer.server.files.FileTransferTask;

import java.io.IOException;

public class FileTransferServerThread extends Entity implements Runnable{

    private String address;
    private int port;

    public FileTransferServerThread(String address, int port) {
        this.address = address;
        this.port = port;
    }

    private Token getTokenFromConnection(FileTransferConnection connection) throws IOException {
        return TokenTransferHelper.recieveToken(connection.getSocket());
    }

    public void run(){

        try {

            FileTransferServer fileTransferServer = new FileTransferServer(address, port);

            while (!Thread.currentThread().isInterrupted()){

                try {

                    FileTransferConnection connection = fileTransferServer.accept();
                    Token token = getTokenFromConnection(connection);
                    FileTransferTask task = getTaskManager().getTaskByToken(token);

                    if (task != null) {

                        FileTransferTaskThread fileTransferTaskThread =
                                new FileTransferTaskThread(connection, task, token);

                        fileTransferTaskThread.initHandlers(this);

                        new Thread(
                                fileTransferTaskThread
                        ).start();

                    } else {
                        getLogger().log(
                                "Trying to connect to file transfer server with invalid token",
                                Logger.LogLevel.WARNING
                        );
                    }
                }
                catch (IOException e){
                    getLogger().log("Problem setting up new file transfer connection", e);
                }

            }

        } catch (IOException e) {
            getLogger().log("File transfer server init error. Shutting down...", e);
            stop();
        }

    }

}
