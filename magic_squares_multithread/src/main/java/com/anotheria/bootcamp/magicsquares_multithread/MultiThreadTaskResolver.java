package com.anotheria.bootcamp.magicsquares_multithread;

import com.anotheria.bootcamp.magicsquares_multithread.threads.OnSquareFoundCallback;
import com.anotheria.bootcamp.magicsquares_multithread.threads.ThreadManager;

public class MultiThreadTaskResolver {

    private int squaresSize;
    private int threadsQuantity;
    private int squaresQuantity;
    private OnSquareFoundCallback squareFoundCallback;

    public MultiThreadTaskResolver(int squaresSize, int threadsQuantity, int squaresQuantity, OnSquareFoundCallback squareFoundCallback) {
        this.squaresSize = squaresSize;
        this.threadsQuantity = threadsQuantity;
        this.squaresQuantity = squaresQuantity;
        this.squareFoundCallback = squareFoundCallback;
    }

    public void run(){
        new ThreadManager(threadsQuantity, squaresQuantity, squaresSize, squareFoundCallback).run();
    }

}
