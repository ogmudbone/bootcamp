
public class TaskSolution {

    private Square square;

    public TaskSolution(Square square){
        this.square = square.copy();
    }

    /**
     *
     * Return string representation
     * of square, filed with magic numbers.
     *  ___________
     * | 15   5  9 |
     * |  4  55  4 |
     * | 66  65  45|
     *  ¯¯¯¯¯¯¯¯¯¯¯
     *
     */
    public String toString(){

        StringBuilder solutionString = new StringBuilder(" ");

        for(int i = 0;i < square.size() * 4 + 2;i++)
            solutionString.append("_");

        solutionString.append(" \n");

        for(int i = 0;i < square.size();i++){

            solutionString.append("| ");

            for(int j = 0;j < square.size();j++){

                solutionString.append("  ");

                if(square.get(i, j) < 10)
                    solutionString.append(" ");

                solutionString.append(square.get(i, j));

            }

            solutionString.append(" |\n");

        }

        solutionString.append(" ");

        for(int i = 0;i < square.size() * 4 + 2;i++)
            solutionString.append("¯");

        solutionString.append(" ");

        return solutionString.toString();

    }

}
