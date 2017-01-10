import java.util.List;

public class Main {

    public static void main(String[] args){

        TaskResolver resolver = new TaskResolver();
        List<TaskSolution> solutions = resolver.getSolutions();

        System.out.println("Calculation done!!! Total count : " + solutions.size());

        int iteration = 0;

        for (TaskSolution solution : solutions) {
            System.out.println();
            System.out.println("Solution " + iteration++ + ": ");
            solution.printSolution();
        }


    }

}
