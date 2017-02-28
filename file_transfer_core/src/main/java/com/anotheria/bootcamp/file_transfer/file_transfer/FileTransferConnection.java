package com.anotheria.bootcamp.file_transfer.file_transfer;

import java.io.*;
import java.net.Socket;

public class FileTransferConnection {

    private Socket socket;

    public FileTransferConnection(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    FileTransferConnection(Socket socket){
        this.socket = socket;
    }

    public void close() throws IOException {
        socket.close();
    }

    public Socket getSocket(){
        return socket;
    }

    public void receiveFile(FileOutputStream fileOutputStream, long fileLength) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        byte[] buffer = new byte[4096];

        int read;
        int remaining = (int) fileLength;
        while((read = dataInputStream.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            remaining -= read;
            fileOutputStream.write(buffer, 0, read);
        }

        fileOutputStream.close();

    }

    public void sendFile(FileInputStream fileInputStream) throws IOException {

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];

        while (fileInputStream.read(buffer) > 0) {
            dataOutputStream.write(buffer);
        }

        fileInputStream.close();

    }

}
