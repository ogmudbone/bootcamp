package com.anotheria.bootcamp.magicsquares_multithread.threads;

import com.anotheria.bootcamp.magicsquares.TaskSolution;

public interface OnSquareFoundCallback {

    /**
     * Calls, when new magic squares solution were found
     *
     * @param solution new solution
     * @return does need to continue searching solutions
     */
    boolean onSquareFound(TaskSolution solution);

}
