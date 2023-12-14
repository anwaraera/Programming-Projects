package bruteforce;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * DAA - BruteForce SAT Solver - Main class
 *
 * Purpose: This class contains the driver method to test a file's
 * satisfiability. The main() method contains a loop to continue testing
 * files until user wants to stop. Appropriate objects of other classes are
 * created and their methods are called to run the program. main() method
 * keeps track of the time taken to test each file and outputs its success.
 * Data is stored in ArrayList of Data objects. Method displayData() is
 * called after all files are done testing and displays a neat table showing
 * the data collected for each tested file.
 *
 * @author Anwara Era
 * @version 2-27-23
 */

public class Main {

    public static void main(String[] args)
    {
        boolean continueLoop = true;    // Loop control
        ArrayList<Data> allData = new ArrayList<>();    // List of data for each file

        while(continueLoop) {
            long startTime = System.currentTimeMillis();
            FileInfo file = new FileInfo();     // Create new File object
            file.readFile();                    // Call methods to read and set values from file
            file.setValues();

            Solver formula = new Solver(file);  // Create new Solver object and pass File object
            boolean solved = formula.solve();   // Call solve() to test file
            if (solved) {                       // Print out if satisfiable or not
                System.out.println("Satisfiable!");
            } else
                System.out.println("Not Satisfiable");

            long endTime = System.currentTimeMillis();
            long totalTime = endTime-startTime;     // Calculate time taken to test
            allData.add(new Data(file.getFileName(), totalTime, solved));

            Scanner in = new Scanner(System.in);    // Check if user wants to continue
            System.out.println("Would you like to test another file? (Y/N): ");
            String answer = in.nextLine();
            if(answer.equals("n") || answer.equals("N")) {
                continueLoop = false;   // Setting to false ends loop
            }
        }
        displayData(allData);   // Call method to output data table
    }

    /**
     * Loops through objects in a list and prints them in a neat table.
     *
     * @param allData - ArrayList of Data objects to loop through and print info
     */
    public static void displayData(ArrayList<Data> allData)
    {
        System.out.println();
        System.out.println("File Name:\tTime(ms):\tSatisfiable:");  // Table header
        System.out.println("-------------------------------------");
        for (Data d : allData)
        {
            System.out.println(d.getFileName() + "\t\t" +
                    d.getTimeTaken() + "\t\t" +
                    d.isSatisfiable());
        }
    }
}
