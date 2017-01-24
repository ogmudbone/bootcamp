package com.anotheria.bootcamp.magicsquares;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Find solution of normal magic squares.
 * Fills square alternately by rows in columns:
 *  1 row, 1 column, 2 row, 2 column...
 */
public class TaskResolver {

    /**
     * Returns new task state.
     * Solutions will be found for squares
     * with size, specified in arguments
     * @param size size of squares
     * @return new task state ready to receive new solutions
     */
    public TaskState getNewTaskState(int size){
        return new TaskState(size);
    }

    /**
     * Returns new task state.
     * Solutions will be found for squares
     * from specified in argument square.
     *
     * @param square square to set in task state
     * @return new task state ready to receive new solutions
     */
    public TaskState getNewTaskState(Square square){
        return new TaskState(square);
    }

    /**
     * Checks is cell specified in arguments is
     * out of bounds
     * @param cell cell to check
     * @param state current solution state
     * @return true  - cell out of bound
     *         false - cell is in square
     */
    private boolean isLastCell(Coords cell, TaskState state){
        return cell.equals(new Coords(state.size, state.size - 1));
    }

    /**
     * Rolls back filling of cell to previous position
     * Returns previous numbers to numbers set
     * and sets 0 to current cell.
     * Also checks and of solutions.
     *
     * @param cell cell to roll back (last filled cell)
     * @param state current solution state
     * @return true  - more cells available to roll back
     *         false - this cell was last, there will be no more solutions.
     */
    private boolean rollBack(Coords cell, TaskState state){

        // true means, there is no more numbers to set in first cell.
        // That means no more solutions
        if(cell.equals(new Coords(0, 0)) && state.square.get(cell) == state.getMaxNumber())
            return false;

        else {

            state.numbersSet.add(state.square.get(getPrevCell(cell, state)));
            state.numbersSet.sort(Integer::compareTo);
            state.square.set(cell, 0);

            return true;

        }

    }

    /**
     * Returns coordinates of next cell
     * from algorithm fill order.
     *
     * @param cell previous cell
     * @param state current solution state
     * @return next cell coords to fill
     */
    private Coords getNextCell(Coords cell, TaskState state){

        if(cell.getJ() >= cell.getI()){

            if(cell.getJ() != state.size - 1)
                return new Coords(cell.getI(), cell.getJ() + 1);
            else
                return new Coords(cell.getI() + 1, cell.getI());

        }
        else{

            if(cell.getI() != state.size - 1)
                return new Coords(cell.getI() + 1, cell.getJ());
            else
                return new Coords(cell.getJ() + 1, cell.getJ() + 1);

        }

    }

    /**
     * Returns coordinates of previous cell
     * from algorithm fill order.
     *
     * @param cell next cell
     * @param state current solution state
     * @return coordinates of previous cell
     */
    private Coords getPrevCell(Coords cell, TaskState state){

        if(cell.getI() > cell.getJ()){

            if(cell.getI() - cell.getJ() == 1)
                return new Coords(cell.getI() - 1, state.size - 1);
            else
                return new Coords(cell.getI() - 1, cell.getJ());

        }
        else{

            if(cell.getI() == cell.getJ())
                return new Coords(state.size - 1, cell.getJ() - 1);
            else
                return new Coords(cell.getI(), cell.getJ() - 1);

        }

    }

    /**
     * Sets cell in square
     * @param cell coordinates of that cell
     * @param number number to set in cell
     * @param state current solution state
     */
    private void setCell(Coords cell, int number, TaskState state){

        state.numbersSet.remove(
                state.numbersSet.indexOf(number)
        );
        state.square.set(cell, number);

    }

    /**
     * Tries to find number to set in cell
     * from numbers, that still not in square.
     * Checks integrity of square, if that number be filled in position.
     * If optional is empty, it means, that
     * there is no numbers left to be placed in this cell.
     *
     * @param cell coordinates of number to set
     * @param state current solution state
     * @return number to set in cell.
     *         if empty - there is no number to set in cell
     */
    private Optional<Integer> findNextNumber(Coords cell, TaskState state){

        int rowSum      = 0, colSum           = 0,
            mainDiagSum = 0, secondaryDiagSum = 0;

        for(int k = 0;k < cell.getJ();k++)
            rowSum += state.square.get(cell.getI(), k);
        for(int l = 0;l < cell.getI();l++)
            colSum += state.square.get(l, cell.getJ());

        if (cell.getI() - cell.getJ() == 0)
            for(int o = 1;cell.getI() - o >= 0;o++)
                mainDiagSum += state.square.get(cell.getI() - o, cell.getJ() - o);

        if(cell.getI() + cell.getJ() == state.square.size() - 1)
            for(int t = 0;t < state.square.size();t++)
                secondaryDiagSum += state.square.get(state.square.size() - 1 - t, t);

        int prev = state.square.get(cell.getI(), cell.getJ());
        int finalRowSum = rowSum;
        int finalColSum = colSum;
        int finalMainDiagSum = mainDiagSum;
        int finalSecondaryDiagSum = secondaryDiagSum;

        Stream<Integer> numbersStream =  state.numbersSet.stream().filter(number ->
            number > prev &&
            number <= state.magicNumber - finalRowSum &&
            number <= state.magicNumber - finalColSum &&
            number <= state.magicNumber - finalMainDiagSum &&
            number <= state.magicNumber - finalSecondaryDiagSum
        );

        if(cell.getI() == state.square.size() - 1)
            numbersStream = numbersStream.filter(
                    number -> number == state.magicNumber - finalColSum
            );

        if(cell.getJ() == state.square.size() - 1)
            numbersStream = numbersStream.filter(
                    number -> number == state.magicNumber - finalRowSum
            );

        if(cell.getI() == state.square.size() - 1 && cell.getJ() == state.square.size() - 1)
            numbersStream = numbersStream.filter(
                    number -> number == state.magicNumber - finalMainDiagSum
            );

        if(
                state.square.isEvenSize() ?
                        cell.getI() == state.square.size() / 2 + 1 && cell.getJ() == state.square.size() / 2 - 1 :
                        cell.getI() == state.square.size() / 2     && cell.getJ() == state.square.size() / 2
                )
            numbersStream = numbersStream.filter(
                    number -> number == state.magicNumber - finalSecondaryDiagSum
            );

        return numbersStream.findFirst();

    }

    /**
     * Finds next solution using previous.
     * @param state current solution state
     */
    public void getSolution(TaskState state){

        Coords nextCell;
        Optional<Integer> nextNumber;

        if(state.isNew) {
            state.isNew = false;
            nextCell = new Coords(0,0);
        }
        else{
            if(!state.hasSolution)
                return;
            state.numbersSet.add(state.square.get(state.size - 1, state.size - 1));
            state.numbersSet.sort(Integer::compareTo);
            nextCell = new Coords(state.size - 1, state.size - 1);
        }

        while (!isLastCell(nextCell, state)){

            if( (nextNumber = findNextNumber(nextCell, state)) .isPresent() ) {
                setCell(nextCell, nextNumber.get(), state);
                nextCell = getNextCell(nextCell, state);
            }

            else if(rollBack(nextCell, state)){
                nextCell = getPrevCell(nextCell, state);
            }
            else{
                state.hasSolution = false;
                return;
            }

        }

        state.hasSolution = true;

    }

    /**
     * Represents state of a task solutions
     * algorithm of finding magic squares uses
     * previous solutions to find next.
     * State contains data structures with this solution.
     *
     * New instance of this class can be created using getNewTaskState()
     * method of com.anotheria.bootcamp.magicsquares.TaskResolver object.
     * By passing it to getSolution() method in class above
     * it possible to receive next solutions.
     */
    public class TaskState{

        /**
         * Generates numbers set for square
         * from 1 to n^2 in ascending order.
         *
         * @param size size of square. size^2 numbers be generated
         * @return list of numbers to fill square in ascending order
         */
        private List<Integer> generateNumbersSet(int size){

            int numbersQuantity = size * size;

            List<Integer> numbersSet = new ArrayList<Integer>(numbersQuantity);

            for(int i = 1;i < numbersQuantity + 1;i++)
                numbersSet.add(i);

            return numbersSet;

        }

        /** size of current square */
        private int size;
        /** magic number of square */
        private int magicNumber;
        /** list if numbers, that still not set in square */
        private List<Integer> numbersSet;
        /** square, witch contains numbers. If state has solution this square is magic */
        private Square square;
        /** is this state contains solution */
        private boolean hasSolution = false;
        /** is this state ever was passed in getSolution() method */
        private boolean isNew = true;

        /**
         * Returns maximum number,
         * that square can contain.
         * @return maximum square number.
         */
        private int getMaxNumber(){
            return size * size;
        }

        private TaskState(int size){

            this.size = size;
            magicNumber = size * (size * size + 1) / 2;
            numbersSet = generateNumbersSet(size);
            square = new Square(size);

        }


        private TaskState(Square square){

            size = square.size();
            magicNumber = size * (size * size + 1) / 2;
            numbersSet = generateNumbersSet(size);
            this.square = square;

        }

        /**
         *
         * Returns solution object with toString() method.
         * this object would not be changed by receiving next solution.
         *
         * @return current task solution
         */
        public TaskSolution getSolution(){
            return new TaskSolution(square);
        }

        /**
         * Indicates, is current task state contains solution.
         * @return true  - task state has solution,
         *         false - task state don`t has solution.
         */
        public boolean isHasSolution(){
            return hasSolution;
        }

    }

}
