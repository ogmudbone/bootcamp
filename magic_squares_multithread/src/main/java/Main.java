import com.anotheria.bootcamp.magicsquares_multithread.MultiThreadTaskResolver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {


    private static final int DEFAULT_SQUARES_AMOUNT = 6000;
    private static final int SQUARES_SIZE = 5;
    private static final int THREADS_QUANTITY = 25;

    private static int parseSquaresAmountString(String squaresAmountString){

        try {
            return Integer.parseInt(squaresAmountString);
        } catch (NumberFormatException e){
            return DEFAULT_SQUARES_AMOUNT;
        }

    }

    public static void main(String[] args){

        String filePath = ( args.length > 0 ? args[0] : "" ) + "squares.txt";
        int squaresAmount = args.length > 1 ? parseSquaresAmountString(args[1]) : DEFAULT_SQUARES_AMOUNT;

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND... BAD DAY (");
            System.exit(1);
        }

        final int[] iteration = {1};

        PrintWriter finalWriter = writer;

        MultiThreadTaskResolver resolver = new MultiThreadTaskResolver(
                SQUARES_SIZE, THREADS_QUANTITY, squaresAmount,
                solution -> {
                    synchronized (finalWriter) {
                        finalWriter.write("\n Iteration " + iteration[0]++ + ":\n");
                        finalWriter.write(solution.toString());
                    }
                    return true;
                }
        );
        resolver.run();

    }

}
