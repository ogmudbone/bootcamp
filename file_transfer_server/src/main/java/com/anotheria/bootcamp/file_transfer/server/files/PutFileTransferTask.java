package com.anotheria.bootcamp.file_transfer.server.files;

import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;

import java.io.IOException;

class PutFileTransferTask extends FileTransferTask {

    private String fileName;
    private long fileLength;

    PutFileTransferTask(String fileName, long fileLength) {
        this.fileName = fileName;
        this.fileLength = fileLength;
    }

    @Override
    String getFileName() {
        return fileName;
    }

    @Override
    public void execute(FileTransferConnection connection, DirectoryBrowser browser) throws IOException {
        connection.receiveFile(browser.getFileOutputStream(fileName), fileLength);
        connection.close();
    }

}
