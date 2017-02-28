package com.anotheria.bootcamp.file_transfer.server.files;

import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;

import java.io.IOException;

public abstract class FileTransferTask {

    abstract String getFileName();
    public abstract void execute(FileTransferConnection connection, DirectoryBrowser browser) throws IOException;

}
