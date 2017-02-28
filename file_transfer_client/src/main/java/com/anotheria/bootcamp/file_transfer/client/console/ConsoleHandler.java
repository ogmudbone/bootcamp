package com.anotheria.bootcamp.file_transfer.client.console;

import com.anotheria.bootcamp.file_transfer.client.Entity;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleHandler extends Entity{

    private Map<String, BaseConsoleCommand> commands = new HashMap<>();
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    private BaseConsoleCommand getCommand(String name){
        return commands.get(name.toUpperCase());
    }

    public void addCommand(String name, BaseConsoleCommand command){
        commands.put(name.toUpperCase(), command);
    }

    public void printLine(){
        out.println();
    }

    public void printLine(String line){
        out.println(line);
    }

    public void print(String string){
        out.print(string);
    }

    public void error(String message){
        error(message, false);
    }

    public void error(String message, boolean exit){
        err.println(message);
        if (exit) System.exit(1);
    }

    public boolean ask(String message){

        out.println();
        out.println(message);
        out.println("Y - yes; N - no");

        String answer = in.next().toUpperCase();

        if(answer.equals("N"))
            return false;
        if(answer.equals("Y"))
            return true;
        else{
            out.println("Wrong answer!");
            return ask(message);
        }

    }

    public BaseConsoleCommand invokeCommand(String line){

        String[] input = line.split(" ");

        String commandName = input[0];
        String[] args = Arrays.copyOfRange(input, 1, input.length);

        BaseConsoleCommand command = getCommand(commandName);

        try{
            command.setClient(getClient());
            command.applyArgs(args);
        } catch (InvalidConsoleArgumentsException e) {
            out.println("Wrong command arguments");
            if(e.getMessage() != null) out.println(e.getMessage());
        } catch (NullPointerException e){
            out.println("Invalid command");
        }

        return command;

    }

    public BaseConsoleCommand nextCommand(){
        printLine();
        printLine();
        print("Wait for next command : ");
        return invokeCommand(in.nextLine());
    }

}
