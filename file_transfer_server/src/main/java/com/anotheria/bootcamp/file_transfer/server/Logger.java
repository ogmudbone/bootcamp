package com.anotheria.bootcamp.file_transfer.server;

import java.io.PrintStream;

public class Logger {

    private PrintStream in = System.out;
    private PrintStream err = System.err;

    public void log(String message, LogLevel logLevel){

        switch (logLevel){

            case INFO:
                in.println("INFO : " + message);
                break;
            case WARNING:
                err.println("WARNING : " + message);
                break;
            case ERROR:
                err.println("ERROR : " + message);
                break;
        }

    }

    public void log(String message, Exception e){

        log(message, LogLevel.ERROR);
        err.println("Exception message : " + e.getMessage());
        e.printStackTrace();

    }

    public enum LogLevel{

        INFO,
        WARNING,
        ERROR

    }

}
