package com.anotheria.bootcamp.file_transfer.server.file_transfer;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.server.Entity;
import com.anotheria.bootcamp.file_transfer.server.Server;
import com.anotheria.bootcamp.file_transfer.server.files.FileTransferTask;

import java.io.IOException;

class FileTransferTaskThread extends Entity implements Runnable{

    private FileTransferConnection connection;
    private FileTransferTask task;
    private Token token;

    FileTransferTaskThread(FileTransferConnection connection, FileTransferTask task, Token token) {
        this.connection = connection;
        this.task = task;
        this.token = token;
    }

    public void run(){

        try {
            getFilesLock().extendLockForToken(token);
            task.execute(connection, getDirectoryBrowser());
        } catch (IOException e) {
            getLogger().log("Unexpected connection break", e);
        }
        finally {
            getFilesLock().releaseLock(token);
        }

    }

}
