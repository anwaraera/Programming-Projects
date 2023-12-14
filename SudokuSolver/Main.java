package sudoku;

import java.util.Scanner;

/**
 * DAA - SAT4J Sudoku Solver - Main Class
 *
 * Purpose: This class contains the driver method to solve a sudoku board
 * given in a file. The main() method contains a loop to continue asking for
 * files until the user wants to stop. Appropriate objects of other classes
 * are created and their methods are called to run the program. main() method
 * keep track of the time taken to test and solve each file and output the
 * board if solved.
 *
 * @author Anwara Era
 * @version 4-12-23
 */
public class Main {

    public static void main(String[] args)
    {
        boolean continueLoop = true;    // Loop control

        while(continueLoop) {
            SATSolver solve = new SATSolver();  // Create new SATSolver object
            solve.solveSAT();                   // Call its method to solve the SAT problem

            // Calculate time taken to test
            long startTime = solve.getStartTime();
            long endTime = System.currentTimeMillis();
            long totalTime = endTime-startTime;
            System.out.println("Time Taken (ms): " + totalTime);

            // Check if user wants to continue
            Scanner in = new Scanner(System.in);
            System.out.println("Would you like to test another file? (Y/N): ");
            String answer = in.nextLine();
            if(answer.equals("n") || answer.equals("N"))
            {
                continueLoop = false;   // Setting to false ends loop
                System.out.println("Exiting...");
                System.out.println("Bye!");
            }
        }
    }
}
