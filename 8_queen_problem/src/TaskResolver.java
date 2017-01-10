import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Calculates possible solutions for 8 queen problem
 * in 64 cell desc.
 */
class TaskResolver {

    /**
     * Returns next coords of cell, witch not been
     * beaten by other queens, that already in stack,
     * horizontally and vertically.
     *
     * NOTE THAT NEW CELL MAY BE BEATEN DIAGONALLY
     *
     * @param queenCells already occupied cells
     * @param prev previous place of current queen
     *
     * @return new possible queen coordinates (diagonal check required)
     *         null - in case if new coords can`t be found
     */
    private CellCoords getNextFreeCoords(CellCoordsStack queenCells, CellCoords prev){

        if(prev.getJ() == 7)
            return null;

        // List of columns numbers, where new queen can stand
        Optional<Integer> jIndex =
                queenCells.getFreeCols().stream().filter(integer -> integer >= prev.getJ() + 1)
                        .findFirst();

        return jIndex.map(integer -> new CellCoords(
                queenCells.size(),
                integer
        )).orElse(null);

    }

    /**
     * Gets next 8 queen problem solution, if it exist
     * using previous one.
     * If prevStack parameter is null - it tries to find it from desc beginning,
     * otherwise - tries to rebuild old solution to achieve new solution.
     *             algorithm guarantee, that next solutions be unique,
     *             so there is possible to achieve all the solutions using previous one.
     *
     * @param prevStack previous solution achieved by this method
     * @return new solution,
     *         returns null if previous be last
     */
    private CellCoordsStack getNextSolution(CellCoordsStack prevStack){

        CellCoordsStack actualStack;
        CellCoords coordsCandidate;

        if(prevStack != null) {
            actualStack = prevStack.copy();
            coordsCandidate = actualStack.pop();
        }
        else{
            actualStack = new CellCoordsStack();
            coordsCandidate = new CellCoords(-1, -1);
        }

        while(actualStack.size() != 8){

            do {
                coordsCandidate = getNextFreeCoords(actualStack, coordsCandidate);

            } while (
                        coordsCandidate != null &&
                        checkDiagonalCollisions(actualStack, coordsCandidate)
                    );

            if(coordsCandidate == null){
                // Current queen deadlocked.
                // Need to move previous figure
                if(actualStack.size() == 0)
                    return null; // First queen is deadlocked. Previous solution been last
                else
                    coordsCandidate = actualStack.pop();

            }
            else {
                // Move to next queen
                actualStack.push(coordsCandidate);
                coordsCandidate = new CellCoords(-1, -1);
            }

        }

        return actualStack;

    }

    /**
     * Finds all solutions of 8 queen problem.
     * @return list of solutions
     */
    List<TaskSolution> getSolutions(){

        List<TaskSolution> solutions = new LinkedList<>();

        CellCoordsStack activeStack = null;

        do{
            activeStack = getNextSolution(activeStack);
            if(activeStack != null) {
                solutions.add(new TaskSolution(activeStack));
            }
        } while (activeStack != null);

        return solutions;

    }

    /**
     * Check diagonal collisions with new cell, specified in arguments.
     *
     * @param queenCells current queens stack
     * @param newCell cell to check collisions
     * @return true - if collision exists, false - if not
     */
    private boolean checkDiagonalCollisions(CellCoordsStack queenCells, CellCoords newCell){

        for (CellCoords occupiedCell : queenCells)
            if(occupiedCell.checkDiagonalCollision(newCell))
                return true;

        return false;

    }

}