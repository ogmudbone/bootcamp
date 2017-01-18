import java.util.Stack;

public class TaskSolution {

    private Stack<HorseMove> moves;

    public TaskSolution(Stack<HorseMove> moves) {

        this.moves = new Stack<>();

        for (HorseMove move : moves) {
            HorseMove copyMove = new HorseMove(move.getStandElement());
            this.moves.push(copyMove);
        }

    }

    /**
     * Print solution in console using
     * chess notation.
     */
    public void printSolution(){

        for(HorseMove move : moves){
            System.out.print(new StringBuilder()
                    .append((char)(move.getStandElement().getCoords().getJ() + 97))
                    .append(8 - move.getStandElement().getCoords().getI())
                    .append(", ")
            );
        }

    }

}
