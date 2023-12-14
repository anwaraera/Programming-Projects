package bruteforce;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * DAA - FileInfo class
 *
 * Purpose: This class contains instance variables used to read in a file
 * and store its contents. The constructor initializes all the instance
 * variables. The readFile() method contains a loop to read in the file name
 * and check if the file exists. The setValues() method reads the file and
 * sets some of the instance variable values. The evaluateClauses() method
 * adds all the separated clauses to a list. This class also contains
 * accessor methods for instance variables.
 *
 * @author Anwara Era
 * @version 2-27-23
 */

public class FileInfo {
    private Scanner satFile;                // Scanner object used to read file
    private String fileName;                // Holds name of file
    private int numVariables;               // Holds # of variables in formula
    private int numClauses;                 // Holds # of clauses in formula
    private ArrayList<String> clauses;      // Holds each individual clause in string format
    private ArrayList<String[]> clauseList; // Holds each clause stored as a string array in a list

    /**
     * Constructor
     *
     * Initializes all instance variables
     */
    public FileInfo()
    {
        satFile = null;
        numVariables = 0;
        numClauses = 0;
        clauses = new ArrayList<>();
        clauseList = new ArrayList<>();
    }

    /**
     * Reads in inputted file from user.
     */
    public void readFile() {
        do {    // Loop to check if filename is valid/exists
            // Scanner prompt to get file name
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the file name to find SAT: ");
            fileName = in.nextLine();

            try {
                satFile = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Error: File does not exist.");
                System.out.println("Re-enter the file below.");
                fileName = "";
            }
        } while(fileName == "");
    }

    /**
     * Loops through lines of the file, sets numVariables, numClauses,
     * and fills ArrayList with each clause.
     */
    public void setValues()
    {
        // Loop through all the comment lines in the beginning until
        // it get to the first data line. Assign the numVariables and
        // numClauses from the first data line.
        while (satFile.hasNext()) {
            String line = satFile.nextLine();

            if(line.startsWith("p")) {
                String[] splitstr = line.split(" ");    // Get rid of spaces

                // Store values in variables
                numVariables = Integer.parseInt(splitstr[2]);
                numClauses = Integer.parseInt(splitstr[3]);
                break;  // Leave loop once first data line is found
            }
        }

        satFile.useDelimiter(" 0");     // Set delimiter to ' 0'
        //Add each clause to the arraylist of clauses
        for(int i = 0; i < numClauses; i++)
        {
            String line = satFile.next();
            if (!line.startsWith("c"))  // Ignore any possible comment lines in file
            {
                clauses.add(line);
            }
        }
    }

    /**
     * Creates string array that separates each number in the clause.
     * Adds each string array to a list.
     *
     * @return clauseList - list containing string arrays that hold each number in each clause
     */
    public ArrayList<String[]> evaluateClauses()
    {
        for(int i = 0; i < numClauses; i++)
        {
            // Replace all newlines with nothing and split the string off of the spaces
            String clause[] = clauses.get(i).replaceAll("\n", "").split(" ");
            clauseList.add(clause);     // Add the string array to the list
        }
        return clauseList;
    }

    /**
     * @return filename - holds the name of the file
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @return numVariables - holds number of variables in formula
     */
    public int getNumVariables()
    {
        return numVariables;
    }

    /**
     * @return numClauses - holds number of clauses in formula
     */
    public int getNumClauses()
    {
        return numClauses;
    }
}
