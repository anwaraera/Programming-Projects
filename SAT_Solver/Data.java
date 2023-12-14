package bruteforce;

/**
 * DAA - Data class
 *
 * Purpose: This class contains instance variables that are associated with
 * the files to be tested. This class is used by Main class to create an
 * ArrayList of Data objects after a file is tested. This class contains a
 * constructor that initializes the instance variables and accessor methods.
 *
 * @author Anwara Era
 * @version 2-27-23
 */

public class Data {
    private String fileName;
    private long timeTaken;
    private boolean satisfiable;

    /**
     * Constructor
     *
     * Initializes all instance variables passed as parameters.
     *
     * @param file - holds file name
     * @param time - holds the time it takes to test a file
     * @param sat - holds if a file formula is satisfiable or not
     */
    public Data(String file, long time, boolean sat)
    {
        fileName = file;
        timeTaken = time;
        satisfiable = sat;
    }

    /**
     * @return fileName - holds name of the file
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @return timeTaken - holds the total time taken to test the file
     */
    public long getTimeTaken()
    {
        return timeTaken;
    }

    /**
     * @return satisfiable - holds whether the file is satisfiable or not
     */
    public boolean isSatisfiable()
    {
        return satisfiable;
    }
}
