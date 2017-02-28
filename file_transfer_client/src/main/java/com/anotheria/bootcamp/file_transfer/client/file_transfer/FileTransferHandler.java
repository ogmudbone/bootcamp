package com.anotheria.bootcamp.file_transfer.client.file_transfer;

import com.anotheria.bootcamp.file_transfer.Token;
import com.anotheria.bootcamp.file_transfer.client.Entity;
import com.anotheria.bootcamp.file_transfer.file_transfer.FileTransferConnection;
import com.anotheria.bootcamp.file_transfer.helpers.DirectoryBrowser;
import com.anotheria.bootcamp.file_transfer.helpers.TokenTransferHelper;

import java.io.IOException;

public class FileTransferHandler extends Entity{

    private DirectoryBrowser browser;

    public FileTransferHandler(String folderPath) throws IOException {
        browser = new DirectoryBrowser(folderPath);
    }

    private FileTransferConnection initConnection( Token token, String address, int port) throws IOException {

        FileTransferConnection connection = new FileTransferConnection(address, port);
        TokenTransferHelper.sendToken(token, connection.getSocket());

        return connection;

    }

    public long getFileSize(String fileName){

        return browser.getFileSize(fileName);

    }

    public void sendFile(String fileName, Token token, String address, int port) throws IOException {

        FileTransferConnection connection = initConnection(token, address, port);

        connection.sendFile(
                browser.getFileInputStream(fileName)
        );

        connection.close();

    }

    public boolean fileExists(String fileName){
        return browser.getFile(fileName).isPresent();
    }

    public void receiveFile(String fileName, Token token, String address, int port, long fileLength) throws IOException {

        FileTransferConnection connection = initConnection(token, address, port);

        connection.receiveFile(
                browser.getFileOutputStream(fileName),
                fileLength
        );

        connection.close();

    }

    public String[] getFilesNamesList(){
        return browser.getFileNamesList();
    }

}
