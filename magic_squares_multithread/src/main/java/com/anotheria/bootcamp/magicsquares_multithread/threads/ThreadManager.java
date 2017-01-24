package com.anotheria.bootcamp.magicsquares_multithread.threads;

public class ThreadManager {

    private int squaresLeft;
    private int threadsQuantity;
    private int squaresSize;
    private OnSquareFoundCallback callback;

    public ThreadManager(int threadsQuantity, int squaresQuantity, int squaresSize, OnSquareFoundCallback callback){

        this.squaresLeft = squaresQuantity;
        this.threadsQuantity = threadsQuantity;
        this.squaresSize = squaresSize;
        this.callback = callback;

        if(this.threadsQuantity > squaresSize * squaresSize)
            this.threadsQuantity = squaresSize * squaresSize;

    }

    public void run(){

        int numbersQuantity = squaresSize * squaresSize;
        int modulo = numbersQuantity % threadsQuantity;
        int intervalsSize = numbersQuantity / threadsQuantity;

        int prev = 0;

        for(int i = intervalsSize;i <= numbersQuantity;i += intervalsSize){

            if(modulo > 0){
                i++;
                modulo--;
            }

            TaskResolverThreadRunnable resolverRunnable = new TaskResolverThreadRunnable(
                    squaresSize, prev, i,
                    state -> {
                        if(squaresLeft <= 0)
                            return false;
                        else{
                            squaresLeft--;
                            return callback.onSquareFound(state);
                        }
                    }
            );

            Thread resolverThread = new Thread(resolverRunnable);
            resolverThread.start();

            prev = i;

        }

    }


}
