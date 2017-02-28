package com.anotheria.bootcamp.arguments_parser.parsers;

import com.anotheria.bootcamp.arguments_parser.InvalidArgumentException;
import com.anotheria.bootcamp.arguments_parser.ValidatorInterface;

public class StringValidator implements ValidatorInterface<String> {

    @Override
    public String parseArgument(String argumentString) throws InvalidArgumentException {
        return argumentString;
    }

}
