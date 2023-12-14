package sudoku;

import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/**
 * DAA - SAT4J Sudoku Solver - SATSolver Class
 *
 * Purpose: This class uses a Clauses object to solve the SAT problem.
 * The solveSAT() method call the appropriate methods from the Clauses
 * class to check if the SAT is satisfiable or not and prints a message
 * accordingly. There is also an updateBoard() method that loops through
 * the sudoku board and updates the solved values if the SAT was solved.
 * It also contains a displayBoard() method that outputs the current
 * sudoku board values.
 *
 * @author Anwara Era
 * @version 4-12-23
 */
public class SATSolver {
    private Clauses clauselist = new Clauses();     // Clauses object
    private ISolver solver;                         // ISolver object from SAT4J
    private IProblem problem;                       // IProblem object from SAT4J
    private int[][] sudokuBoard;                    // 2d array that holds the sudoku board
    private long startTime;                         // Time the solving was started

    /**
     * Calls appropriate methods to make the clauses and solve the SAT
     * problem. Updates and displays the new board if it's satisfiable
     * and prints the according message if not satisfiable. Also does
     * exception handling.
     */
    public void solveSAT()
    {
        // Pass solver to IProblem object
        solver = clauselist.makeClauses();
        problem = solver;
        sudokuBoard = clauselist.getBoard();
        startTime = clauselist.getStartTime();

        try {
            // Check if problem can be satisfied
            if(problem.isSatisfiable()) {
                System.out.println();
                System.out.println("Solved!");
                updateBoard();
                displayBoard();
            }
            else {
                System.out.println("Unsolvable =(");
            }
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Uses IProblem object to check which variables were assigned to
     * be true and loops through the sudoku board to set those values.
     */
    public void updateBoard()
    {
        // Loop through all cell indexes in sudoku board with possible values
        for(int row = 1; row <= clauselist.getRowAndColLength(); row++)
        {
            for(int col = 1; col <= clauselist.getRowAndColLength(); col++)
            {
                for(int value = 1; value <= clauselist.getRowAndColLength(); value++)
                {
                    // Make the variable based on indexes and value
                    // Check if that variable is true in SAT assignment
                    int var = clauselist.makeVariable(row, col, value);
                    if (problem.model(var)) {
                        // Assign that value to that index
                        sudokuBoard[row - 1][col - 1] = value;
                    }
                }
            }
        }
    }

    /**
     * Loop through the sudoku board and display the values at those indexes.
     */
    public void displayBoard()
    {
        System.out.println();
        System.out.println("Completed Board:");

        for(int i = 0; i < clauselist.getRowAndColLength(); i++)
        {
            for(int j = 0; j < clauselist.getRowAndColLength(); j++)
            {
                // Check if some digits could be double
                if(clauselist.getRowAndColLength() > 9) {
                    // Pad the single digits with a '0' in the front when
                    // printing so numbers all align
                    if (sudokuBoard[i][j] < 10)
                        System.out.print("0" + sudokuBoard[i][j] + " ");
                    else
                        System.out.print(sudokuBoard[i][j] + " ");
                }
                else
                    System.out.print(sudokuBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * @return startTime - Time stamp of when the solving started
     */
    public long getStartTime()
    {
        return startTime;
    }
}
