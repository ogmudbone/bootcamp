package com.anotheria.bootcamp.file_transfer.commands.exceptions;

public class HandlerNotFoundException extends Exception {

    public HandlerNotFoundException() {
    }

    public HandlerNotFoundException(String s) {
        super(s);
    }

    public HandlerNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HandlerNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public HandlerNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

}
