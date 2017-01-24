package com.anotheria.bootcamp.magicsquares_multithread.threads;

import com.anotheria.bootcamp.magicsquares.Square;
import com.anotheria.bootcamp.magicsquares.TaskResolver;

class TaskResolverThreadRunnable implements Runnable{

    private int from;
    private int to;
    private int size;

    private OnSquareFoundCallback onSquareFoundCallback;

    public TaskResolverThreadRunnable(int size, int from, int to, OnSquareFoundCallback onSquareFoundCallback) {
        this.size = size;
        this.from = from;
        this.to = to;
        this.onSquareFoundCallback = onSquareFoundCallback;
    }

    public void run(){

        Square square = new Square(size);
        square.set(0, 0, from);

        TaskResolver resolver = new TaskResolver();
        TaskResolver.TaskState state = resolver.getNewTaskState(square);
        resolver.getSolution(state);

        while(
                state.isHasSolution() &&
                state.getSolution().getSquare().get(0,0) <= to &&
                onSquareFoundCallback.onSquareFound(state.getSolution())
                ){

            resolver.getSolution(state);

        }

    }

}
