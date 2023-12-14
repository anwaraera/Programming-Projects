package sudoku;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import java.io.File;

/**
 * DAA - SAT4J Sudoku Solver - Clauses Class
 *
 * Purpose: This class creates all the clauses in cnf format to pass
 * to the SAT solver to solve sudoku. It contains a FileInfo object that
 * is used to initialize the instance variables needed to create the
 * clauses. There are 8 methods that create clauses based on the
 * appropriate constraints of sudoku. The makeClauses() method calls
 * each of the methods and does exception handling. The class also contains
 * some accessor methods of the instance variables.
 *
 * @author Anwara Era
 * @version 4-12-23
 */
public class Clauses {

    private FileInfo file;          // FileInfo object used to initialize other variables
    private int[][] sudokuBoard;    // Holds values of sudoku board from given file
    private int rowAndColLength;    // Length of the row/column of the board
    private int boxLength;          // Length of the sub-boxes of the board
    private int numGivenValues;     // # of given values in the sudoku file
    private int[] clause;           // int array to pass into ISolver;
    private ISolver solver;         // ISolver object from SAT4J to pass clauses into
    private long startTime;         // Time stamp of when solving starts

    /**
     * Constructor
     *
     * Initializes all instance variables.
     */
    public Clauses()
    {
        file = new FileInfo();
        file.readFile();
        file.setValues();
        file.displayBoard();
        sudokuBoard = file.getBoard();
        rowAndColLength = file.getRowAndColLength();
        boxLength = file.getBoxLength();
        numGivenValues = file.getGivenValues();
        solver = file.getSolver();
        startTime = file.getStartTime();
    }

    /**
     * @return sudokuBoard - Holds values of sudoku board from given file
     */
    public int[][] getBoard()
    {
        return sudokuBoard;
    }

    /**
     * @return rowAndColLength - Length of the row/column of the board
     */
    public int getRowAndColLength()
    {
        return rowAndColLength;
    }

    /**
     * @return startTime - time stamp of starting to solve sudoku
     */
    public long getStartTime() { return startTime; }

    /**
     * Creates a variable to use in the clauses made.
     *
     * @param i - row
     * @param j - column
     * @param k - value
     * @return var - new variable made from ijk values
     */
    public int makeVariable(int i, int j, int k)
    {
        int var;
        if(rowAndColLength < 10)    // if single digits
        {                           // just concatenate numbers together
            //var = Integer.parseInt("" + i + j + k);
            var = (i*100) + (j*10) + k;
        }
        else    // double digits
        {       // use math formula to ensure unique variables for each cell
            var = (i*10000) + (j*100) + k;
        }
        return var;
    }

    /**
     * Calls all methods to generate clauses for sudoku constraints.
     * Methods are all wrapped in try/catch to handle the Contradiction
     * Exception, which is thrown when the clauses contain a contradiction.
     *
     * ContradictionException example: (111) ^ (121) ^ (-111 V -121)
     * The above example causes the exception because it logically can't
     * be true no matter what the variable assignment is.
     *
     * @return solver - ISolver object that holds all passed clauses
     */
    public ISolver makeClauses()
    {
        try {
            givenValuesClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in given values");
        }

        try {
            atLeastOneValueEachCell();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found...");
        }

        try {
            atLeastOneValueRowClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in rows...");
        }

        try {
            atLeastOneValueColClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in columns...");
        }

        try {
            atLeastOneValueBoxClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in sub boxes...");
        }

        try {
            atMostOneValueRowClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in rows...");
        }

        try {
            atMostOneValueColClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in columns...");
        }

        try {
            atMostOneValueBoxClauses();
        } catch (ContradictionException e) {
            System.out.println("Contradiction found in the sub boxes...");
        }
        return solver;
    }

    /**
     * Creates clauses for the constraint of respecting given values
     * in sudoku. Passes clauses made to the ISolver.
     *
     * @throws ContradictionException
     */
    public void givenValuesClauses() throws ContradictionException
    {
        // single variable clauses for this constraint
        clause = new int[1];
        int var;

        // Loop through sudokuBoard array to find any numbers given
        for (int i = 0; i < rowAndColLength; i++) {
            for (int j = 0; j < rowAndColLength; j++) {
                if (sudokuBoard[i][j] != 0) {
                    var = makeVariable(i + 1, j + 1, sudokuBoard[i][j]);
                    clause[0] = var;
                    solver.addClause(new VecInt(clause));
                }
            }
        }
    }

    /**
     * Creates clauses for at least one value in each cell. Passes
     * clauses to the ISolver.
     *
     * @throws ContradictionException
     */
    public void atLeastOneValueEachCell() throws ContradictionException
    {
        int var;

        // Loop through all sudoku cell indexes
        for(int row = 1; row <= rowAndColLength; row++)
        {
            for(int col = 1; col <= rowAndColLength; col++)
            {
                clause = new int[rowAndColLength];  // clause size is size of sudoku
                for(int value = 1; value <= rowAndColLength; value++)
                {
                    var = makeVariable(row, col, value);
                    clause[value-1] = var;
                }
                solver.addClause(new VecInt(clause));   // add clause to ISolver
            }

        }
    }

    /**
     * Creates clauses for at least one value in each row cell. Passes
     * clauses made to the ISolver.
     *
     * 4x4 example clauses: (111, 121, 131, 141), (112, 122, 132, 142), ...
     *
     * @throws ContradictionException
     */
    public void atLeastOneValueRowClauses() throws ContradictionException
    {
        int var;

        // Loop through all sudoku cell indexes
        for(int row = 1; row <= rowAndColLength; row++) {
            for(int value = 1; value <= rowAndColLength; value++) {
                clause = new int[rowAndColLength];  // clause size is size of sudoku
                for (int col = 1; col <= rowAndColLength; col++) {
                    var = makeVariable(row, col, value);
                    clause[col-1] = var;
                }
                solver.addClause(new VecInt(clause));   // Add to ISolver
            }
        }
    }

    /**
     * Creates clauses for at least one value in each column cell.
     * Passes clauses made to the ISolver.
     *
     * 4x4 example clauses: (111, 211, 311, 411), (112, 212, 312, 412), ...
     *
     * @throws ContradictionException
     */
    public void atLeastOneValueColClauses() throws ContradictionException
    {
        int var;

        // Loop through sudoku cell indexes
        for(int col = 1; col <= rowAndColLength; col++) {
            for(int value = 1; value <= rowAndColLength; value++) {
                clause = new int[rowAndColLength];  // clause size is size of sudoku
                for (int row = 1; row <= rowAndColLength; row++) {
                    var = makeVariable(row, col, value);
                    clause[row-1] = var;
                }
                solver.addClause(new VecInt(clause));
            }
        }
    }

    /**
     * Creates clauses for at least one value in each box cell.
     * Passes clauses to the ISolver.
     *
     * 4x4 example clauses: (111, 121, 211, 221), (112, 122, 212, 222), ...
     *
     * @throws ContradictionException
     */
    public void atLeastOneValueBoxClauses() throws ContradictionException
    {
        int var;

        // Value stays the same for each clause set, outer row and column
        // increase by the size of the sub-boxes, and inner row and column
        // loops iterate through the sub-box indexes to make the clauses
        for(int value = 1; value <= rowAndColLength; value++) {
            for(int row = 1; row <= rowAndColLength; row += boxLength) {
                for(int col = 1; col <= rowAndColLength; col += boxLength) {
                    clause = new int[rowAndColLength];
                    int index = 0;
                    for(int r = row; r < (row+boxLength); r++) {
                        for(int c = col; c < (col+boxLength); c++) {
                            var = makeVariable(r, c, value);
                            clause[index] = var;
                            index++;
                        }
                    }
                    solver.addClause(new VecInt(clause));
                }
            }
        }
    }

    /**
     * Creates clauses for at most one value in each row.
     * Passes clauses to the ISolver.
     *
     * 4x4 example clauses: (-111, -121), (-111, -131), (-111, -141), ...
     * - Loops through all possible combos of 2 variables in a row.
     *
     * @throws ContradictionException
     */
    public void atMostOneValueRowClauses() throws ContradictionException
    {
        int var;

        for(int row = 1; row <= rowAndColLength; row++) {
            for(int value = 1; value <= rowAndColLength; value++) {
                int[] baseClause = new int[rowAndColLength];
                for (int col = 1; col <= rowAndColLength; col++) {
                    var = makeVariable(row, col, value);
                    baseClause[col-1] = var * -1;
                }

                // Loop to make clauses of all possible combos in a row for one value
                for(int i = 0; i < baseClause.length; i++) {
                    clause = new int[2];    // Set array size to 2
                    clause[0] = baseClause[i];
                    for(int j = i+1; j < baseClause.length; j++) {
                        clause[1] = baseClause[j];
                        solver.addClause(new VecInt(clause));
                    }
                }
            }
        }
    }

    /**
     * Creates clauses for at most one value in each column.
     * Passes clauses to the ISolver.
     *
     * 4x4 example clauses: (-111, -211), (-111, -311), (-111, -411), ...
     * - Loops through all possible combos of 2 variables in a column.
     *
     * @throws ContradictionException
     */
    public void atMostOneValueColClauses() throws ContradictionException
    {
        int var;

        for(int col = 1; col <= rowAndColLength; col++) {
            for(int value = 1; value <= rowAndColLength; value++) {
                int[] baseClause = new int[rowAndColLength];
                for (int row = 1; row <= rowAndColLength; row++) {
                    var = makeVariable(row, col, value);
                    baseClause[row-1] = var * -1;
                }

                // Loop to make clauses of all possible combos in a column for one value
                for(int i = 0; i < baseClause.length; i++) {
                    clause = new int[2];    // Set array size to 2
                    clause[0] = baseClause[i];
                    for(int j = i+1; j < baseClause.length; j++) {
                        clause[1] = baseClause[j];
                        solver.addClause(new VecInt(clause));
                    }
                }
            }
        }
    }

    /**
     * Creates clauses for at most one value in each box.
     * Passes clauses to the ISolver.
     *
     * 4x4 example clauses: (-111, -121), (-111, -211), (-111, -221), ...
     *- Loops through all possible combos of 2 variables in a row.
     *
     * @throws ContradictionException
     */
    public void atMostOneValueBoxClauses() throws ContradictionException
    {
        int var;

        for(int value = 1; value <= rowAndColLength; value++) {
            for(int row = 1; row <= rowAndColLength; row += boxLength) {
                for(int col = 1; col <= rowAndColLength; col += boxLength) {
                    int[] baseClause = new int[rowAndColLength];
                    int index = 0;
                    for(int r = row; r < row+boxLength; r++) {
                        for(int c = col; c < col+boxLength; c++) {
                            var = makeVariable(r, c, value);
                            baseClause[index] = var * -1;
                            index++;
                        }
                    }

                    // Loop to make clauses of all possible combos in a sub box for one value
                    for(int i = 0; i < baseClause.length; i++) {
                        clause = new int[2];
                        clause[0] = baseClause[i];
                        for(int j = i+1; j < baseClause.length; j++) {
                            clause[1] = baseClause[j];
                            solver.addClause(new VecInt(clause));
                        }
                    }
                }
            }
        }
    }
}
