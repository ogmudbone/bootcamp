package com.anotheria.bootcamp.file_transfer.server;

import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;
import com.anotheria.bootcamp.file_transfer.server.files.FileTransferTaskManager;
import com.anotheria.bootcamp.file_transfer.server.files.FilesLock;

public abstract class Entity {

    private Server server;

    protected DirectoryBrowser getDirectoryBrowser() {
        return server.getDirectoryBrowser();
    }

    protected FileTransferTaskManager getTaskManager() {
        return server.getTaskManager();
    }

    protected FilesLock getFilesLock() {
        return server.getFilesLock();
    }

    protected String getFileTransferServerAddress() {
        return server.getFileTransferServerAddress();
    }

    protected int getFileTransferServerPort() {
        return server.getFileTransferServerPort();
    }

    protected Logger getLogger(){
        return server.getLogger();
    }

    protected void stop(){
        server.stop();
    }

    public void initHandlers(Entity other){
        this.server = other.server;
    }

    public void setServer(Server server){
        this.server = server;
    }


}
