/**
 * Represent 8 queen problem solution.
 * Can print itself in console.
 */
class TaskSolution {

    /**
     * Stack of queens positions from TaskResolver
     */
    private CellCoordsStack coords;

    TaskSolution(CellCoordsStack coords) {
        this.coords = coords.copy();
    }

    /**
     * Prints this solutions to console with no
     * empty lines before or after.
     *  ________________________________
     * |_♕_|___|___|___|___|___|___|___|
     * |___|___|_♕_|___|___|___|___|___|
     * |___|___|___|___|_♕_|___|___|___|
     * |___|___|___|___|___|___|_♕_|___|
     * |___|_♕_|___|___|___|___|___|___|
     * |___|___|___|_♕_|___|___|___|___|
     * |_♕_|___|___|___|___|___|___|___|
     * |___|___|___|___|___|___|___|_♕_|
     *
     * NOTE: IN CONSOLE SYMBOLS IS MONOSPACED,
     *       DESC WOULD NOT LOOK SO UGLY.
     *
     */
    void printSolution(){

        System.out.println( " _______________________________" );

        for(int i = 0;i < 8;i++){

            CellCoords currentRowCell = coords.get(i);
            StringBuilder row = new StringBuilder("|");

            for (int j = 0;j < 8;j++)
                if(currentRowCell.getJ() == j)
                    row.append("_♕_|");
                else
                    row.append("___|");

            System.out.println(row);

        }

    }

}