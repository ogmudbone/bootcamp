package com.anotheria.bootcamp.arguments_parser;

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException() {
    }

    public InvalidArgumentException(String s) {
        super(s);
    }
}
