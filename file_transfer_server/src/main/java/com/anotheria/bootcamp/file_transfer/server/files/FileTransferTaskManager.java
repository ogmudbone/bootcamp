package com.anotheria.bootcamp.file_transfer.server.files;

import com.anotheria.bootcamp.file_transfer.Token;

import java.util.HashMap;
import java.util.Map;

public class FileTransferTaskManager {

    private final Map<Token, FileTransferTask> tasks = new HashMap<>();
    public static final int TOKEN_BYTES_SIZE = 32;

    private Token generateToken(){

        return Token.newRandomToken(TOKEN_BYTES_SIZE);

    }

    public Token addGetTask(String fileName){

        Token token = generateToken();
        tasks.put(token, new GetFileTransferTask(fileName));
        return token;

    }

    public Token addPutTask(String fileName, long fileSize){

        Token token = generateToken();
        tasks.put(token, new PutFileTransferTask(fileName, fileSize));
        return token;

    }

    public String abortTask(Token token){
        return tasks.remove(token).getFileName();
    }

    public FileTransferTask getTaskByToken(Token token){
        return tasks.remove(token);
    }

}
