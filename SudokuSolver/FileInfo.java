package sudoku;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * DAA - SAT4J Sudoku Solver - FileInfo Class
 *
 * Purpose: This class contains instance variables used to read in a file containing
 * a sudoku board and store its contents. The constructor initializes all the instance
 * variables. The readFile() method contains a loop to read in the file name and check
 * if the file exists. The setValues() method read the file and sets some instance
 * variable values. It also initializes and fills in the sudokuBoard array, keeps
 * track of the given values in the board, and prepares the SAT4J solver to take input.
 * The displayBoard() method displays the original board. The class also contains some
 * accessor methods for some instance variables.
 *
 * @author Anwara Era
 * @version 4-12-23
 */
public class FileInfo {

    private Scanner sudokuFile;     // Scanner object used to read file
    private String fileName;        // Holds name of file
    private int boxLength;          // Holds # of cells of the sub-boxes
    private int rowAndColLength;    // Holds # of cells of the rows/cols
    private int maxVariables;       // Max number of variables, ex: 9x9 board->maxVar=x999, 25x25 ->x252525
    private int givenValues;        // Hold # of values given in original board
    private int[][] sudokuBoard;    // Data structure to hold the values for the sudoku board
    private ISolver solver;         // SAT4J solver
    private long startTime;          // Keeping track of time taken to solve

    /**
     * Constructor
     *
     * Initializes all instance variables.
     */
    public FileInfo()
    {
        sudokuFile = null;
        fileName = "";
        boxLength = 0;
        rowAndColLength = 0;
        maxVariables = 0;
        givenValues = 0;
        solver = SolverFactory.newDefault();
    }

    /**
     * Reads in inputted file from user.
     */
    public void readFile() {
        do {
            // Loop to check if filename is valid/exists
            // Scanner prompt to get file name
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the file name or full file path for the sudoku problem to solve: ");
            fileName = in.nextLine();

            try {
                sudokuFile = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Error: File does not exist.");
                System.out.println("Re-enter the file below.");
                fileName = "";
            }
        } while(fileName.equals(""));
    }

    /**
     * Loops through lines of the file, sets boxLength, rowAndColLength, and
     * fill array with the appropriate values.
     */
    public void setValues()
    {
        startTime = System.currentTimeMillis();

        // First two ints in file are the sub-box length
        boxLength = sudokuFile.nextInt();
        sudokuFile.nextInt();

        // Setting the length of the rows and columns
        rowAndColLength = boxLength*boxLength;

        // Setting what the max variable value is
        maxVariables =  Integer.parseInt("" + rowAndColLength + rowAndColLength + rowAndColLength);

        // Initializing the array size
        sudokuBoard = new int[rowAndColLength][rowAndColLength];

        // Nested loop to fill array from the file
        for(int i = 0; i < rowAndColLength; i++)
        {
            for(int j = 0; j < rowAndColLength; j++)
            {
                sudokuBoard[i][j] = sudokuFile.nextInt();

                // Keep track of how many values are given in file
                if (sudokuBoard[i][j] != 0)
                    givenValues++;
            }
        }

        // Prepare solver for taking input by letting it know the # of variables
        solver.newVar(maxVariables);
    }

    /**
     * Display the original board from the values stored in the array.
     */
    public void displayBoard()
    {
        System.out.println("Original Board:");
        for(int i = 0; i < rowAndColLength; i++)
        {
            for(int j = 0; j < rowAndColLength; j++)
            {
                if(sudokuBoard[i][j] == 0)
                {
                    System.out.print("- ");
                }
                else
                {
                    System.out.print(sudokuBoard[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * @return rowAndColLength - Holds # of cells of the rows/cols
     */
    public int getRowAndColLength()
    {
        return rowAndColLength;
    }

    /**
     * @return boxLength - Holds # of cells of the sub-boxes
     */
    public int getBoxLength()
    {
        return boxLength;
    }

    /**
     * @return givenValues - Hold # of values given in original board
     */
    public int getGivenValues()
    {
        return givenValues;
    }

    /**
     * @return sudokuBoard - Data structure to hold the values for the sudoku board
     */
    public int[][] getBoard()
    {
        return sudokuBoard;
    }

    /**
     * @return solver - SAT4J solver
     */
    public ISolver getSolver()
    {
        return solver;
    }

    /**
     * @return startTime - Time stamp of when solving starts
     */
    public long getStartTime() { return startTime; }
}
