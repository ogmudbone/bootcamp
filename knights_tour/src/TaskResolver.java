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
     * Returns new task state to get
     * solutions from beginning
     *
     * @return new task state
     */
    public TaskState getNewTaskState(){

        TaskState newState = new TaskState();

        newState.graph = new DescGraph();
        newState.moveStack = new Stack<>();
        newState.hasSolution = false;
        newState.isNew = true;

        newState.moveStack.push(
                new HorseMove(newState.graph.getFirst())
        );

        return newState;

    }

    /**
     * Finds solution of knights tour.
     * Uses previous solution to find next
     * @param state previous solution. Can be null.
     *             In this case starts from beginning
     */
    public void getSolution(TaskState state){

        if(state.isNew) {
            state.isNew = false;
            state.hasSolution = true;
        }
        else {
            if(!(state.hasSolution = rollBack(state)))
                return;
        }

        while (state.moveStack.size() < 64 ||
                !state.moveStack.peek().getStandElement().hasNeighbour(state.graph.getFirst())){

            if(state.moveStack.size() == 64 && !rollBack(state)) {
                state.hasSolution = false;
                return;
            }

            if(state.moveStack.peek().hasNextMove())
                state.moveStack.push(
                        new HorseMove(state.moveStack.peek().getNextMove())
                );

            else if(!rollBack(state)){
                state.hasSolution = false;
                return;
            }

        }

    }

    /**
     * Represents state of a task solutions
     * Algorithm of finding knights tours uses
     * previous solutions to find next.
     * State contains data structures with this solution.
     *
     * New instance of this class can be created using getNewTaskState
     * method of TaskResolver class.
     * By passing it to getSolution method in class above
     * it possible to receive next solutions
     */
    public class TaskState{

        DescGraph graph = new DescGraph();
        Stack<HorseMove> moveStack;
        private boolean hasSolution;
        private boolean isNew;

        private TaskState(){

        }

        /**
         * Returns solution object with print method.
         * this object would not be changed by receiving next solution.
         *
         * @return current task solution
         */
        public TaskSolution getSolution(){
            return new TaskSolution(this.moveStack);
        }

        /**
         * Indicates, is current task state contains solution.
         * @return true  - task state has solution,
         *         false - task state don`t has solution.
         */
        public boolean isHasSolution() {
            return hasSolution;
        }

    }
    

}
