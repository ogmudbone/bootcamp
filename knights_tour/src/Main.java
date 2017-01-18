import java.io.IOException;

public class Main {

    public static void main(String[] args){

        TaskResolver resolver = new TaskResolver();
        TaskResolver.TaskState state = resolver.getNewTaskState();

        resolver.getSolution(state);

        System.out.println("First solution found!\n");
        state.getSolution().printSolution();

        System.out.println("\n\nPress enter to continue. (Finding next solution may take long time!)");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(state.isHasSolution()){
            resolver.getSolution(state);
            state.getSolution().printSolution();
        }

    }

}
