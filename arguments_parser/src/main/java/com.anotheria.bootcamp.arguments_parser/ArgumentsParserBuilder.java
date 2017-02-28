package com.anotheria.bootcamp.arguments_parser;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsParserBuilder {

    private Map<Integer, ValidatorInterface<?>> argumentsParsers = new HashMap<>();
    private String[] args = new String[0];

    public ArgumentsParserBuilder addParser(int position, ValidatorInterface<?> parser){
        argumentsParsers.put(position, parser);
        return this;
    }

    public ArgumentsParserBuilder setArgs(String[] args) {
        this.args = args;
        return this;
    }

    public ArgumentsParser build() throws InvalidArgumentException{
        return new ArgumentsParser(argumentsParsers, args);
    }

}
