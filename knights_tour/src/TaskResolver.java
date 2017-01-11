import java.util.Stack;

public class TaskResolver {

    /**
     * Rolls back moves, is it stuck.
     * @param state current moves state
     * @return true - if there is more possible moves
     *         false - if no
     */
    private boolean rollBack(TaskState state){

        do state.moveStack.pop().rollBack();
        while(!state.moveStack.isEmpty() && !state.moveStack.peek().hasNextMove());

        return !state.moveStack.isEmpty();

    }

    /**
     * Finds solution of knights tour.
     * Uses previous solution to find next
     * @param prev previous solution. Can be null.
     *             In this case starts from beginning
     * @return solution of knights tour
     */
    public TaskState getSolution(TaskState prev){

        TaskState currentState;

        if(prev == null) {
            currentState = new TaskState();
            currentState.graph = new DescGraph();
            currentState.moveStack = new Stack<>();
            currentState.moveStack.push(
                    new HorseMove(currentState.graph.getFirst())
            );
            currentState.hasSolution = true;
        }
        else {
            currentState = prev.copy();
            if(!(currentState.hasSolution = rollBack(currentState)))
                return currentState;
        }

        while (currentState.moveStack.size() < 64 ||
                !currentState.moveStack.peek().getStandElement().hasNeighbour(currentState.graph.getFirst())){

            if(currentState.moveStack.size() == 64 && !rollBack(currentState)) {
                currentState.hasSolution = false;
                return currentState;
            }

            if(currentState.moveStack.peek().hasNextMove())
                currentState.moveStack.push(
                        new HorseMove(currentState.moveStack.peek().getNextMove())
                );
            else if(!rollBack(currentState)){
                currentState.hasSolution = false;
                return currentState;
            }

        }

        return currentState;

    }

    /**
     *
     */
    public class TaskState{

        DescGraph graph = new DescGraph();
        Stack<HorseMove> moveStack;
        private boolean hasSolution;

        private TaskState(){

        }

        public TaskSolution getSolution(){
            return new TaskSolution(moveStack);
        }

        private TaskState copy(){
            TaskState copy = new TaskState();
            copy.moveStack = HorseMove.copyMoveStack(this.moveStack);
            return copy;
        }

        public boolean isHasSolution() {
            return hasSolution;
        }

    }
    

}
