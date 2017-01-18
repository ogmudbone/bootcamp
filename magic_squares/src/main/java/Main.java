import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main
{

    private static final int DEFAULT_SQUARES_AMOUNT = 1000;
    private static final int SQUARES_SIZE = 5;

    private static int parseSquaresAmountString(String squaresAmountString){

        try {
            return Integer.parseInt(squaresAmountString);
        }catch (NumberFormatException e){
            return DEFAULT_SQUARES_AMOUNT;
        }

    }

    public static void main(String[] args){

        String filePath = ( args.length > 0 ? args[0] : "" ) + "squares.txt";
        int squaresAmount = args.length > 1 ? parseSquaresAmountString(args[1]) : DEFAULT_SQUARES_AMOUNT;

        TaskResolver resolver = new TaskResolver();
        TaskResolver.TaskState state = resolver.getNewTaskState(SQUARES_SIZE);
        int iteration = 1;

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(filePath);;
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND... BAD DAY (");
            System.exit(1);
        }

        do{

            resolver.getSolution(state);
            writer.write("\n Iteration " + iteration++ + ":\n");
            writer.write(state.getSolution().toString());

        }while (state.isHasSolution() && iteration < squaresAmount);

    }

}
