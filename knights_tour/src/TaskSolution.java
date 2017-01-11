import java.util.Stack;

public class TaskSolution {

    private Stack<HorseMove> moves;

    public TaskSolution(Stack<HorseMove> moves) {
        this.moves = moves;
    }

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
