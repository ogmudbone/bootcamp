package com.anotheria.bootcamp.file_transfer.server.files;

import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;

import java.io.IOException;

class GetFileTransferTask extends FileTransferTask {

    private String fileName;

    GetFileTransferTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    String getFileName() {
        return fileName;
    }

    @Override
    public void execute(FileTransferConnection connection, DirectoryBrowser directory) throws IOException {
        connection.sendFile(directory.getFileInputStream(fileName));
        connection.close();
    }

}
