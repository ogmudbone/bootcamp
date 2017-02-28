package com.anotheria.bootcamp.file_transfer.helpers;

import com.anotheria.bootcamp.file_transfer.Token;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TokenTransferHelper {

    public static Token recieveToken(Socket socket) throws IOException {

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        try {
            return ((Token) objectInputStream.readObject());
        } catch (ClassNotFoundException e) {
            return null;
        }

    }

    public static void sendToken(Token token, Socket socket) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(token);
    }

}
