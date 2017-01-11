
public class Main {

    public static void main(String[] args){
        TaskResolver resolver = new TaskResolver();

        TaskResolver.TaskState state = null;

        do{
            state = resolver.getSolution(state);
            System.out.println();
            if(state.isHasSolution()) state.getSolution().printSolution();
        } while (state.isHasSolution());

    }

}
