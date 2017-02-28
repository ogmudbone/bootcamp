package com.anotheria.bootcamp.arguments_parser.parsers;

import com.anotheria.bootcamp.arguments_parser.InvalidArgumentException;
import com.anotheria.bootcamp.arguments_parser.ValidatorInterface;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class FilePathValidator implements ValidatorInterface<String> {

    public String parseArgument(String argumentString) throws InvalidArgumentException {

        try{
            Paths.get(argumentString);
        }catch(InvalidPathException e){
            throw new InvalidArgumentException("Invalid folder path");
        }

        return argumentString;

    }

}
