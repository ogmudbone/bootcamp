package com.anotheria.bootcamp.arguments_parser;

public interface ValidatorInterface<T>{

    T parseArgument(String argumentString) throws InvalidArgumentException;

}
