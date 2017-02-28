package com.anotheria.bootcamp.arguments_parser;

import java.util.Map;
import java.util.Optional;

public class ArgumentsParser {

    private Object[] parsedArguments;

    ArgumentsParser(Map<Integer, ValidatorInterface<?>> argumentsParsers, String[] arguments) throws InvalidArgumentException{

        if(argumentsParsers.size() != arguments.length)
            throw new InvalidArgumentException("Invalid number of arguments. "
                                                + arguments.length + " given, "
                                                + argumentsParsers.size() + " expected.");

        parsedArguments = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++)
            parsedArguments[i] = argumentsParsers.get(i).parseArgument(arguments[i]);

    }

    /**
     * Returns parsed argument with cast to specified
     * by it`s validator class.
     * If type of argument, given to method,
     * is invalid, or index out of bounds - then optional will be empty
     *
     * @param index index of argument to get
     * @param type class of argument to cast in
     * @param <T> class of argument to cast in
     * @return present - if index and type are correct
     *         empty  - if type or index is invalid
     */
    public <T> Optional<T> get(int index, Class<T> type){

        if(index < 0 || index >= parsedArguments.length)
            return Optional.empty();

        Object argument = parsedArguments[index];

        if(type.isInstance(argument))
            return Optional.of((T) argument);
        else
            return Optional.empty();

    }

}
