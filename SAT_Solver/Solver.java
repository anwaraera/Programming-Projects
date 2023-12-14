package bruteforce;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DAA - Solver class
 *
 * Purpose: This class contains a FileInfo object, an Arraylist of String arrays,
 * and a HashMap<integer, Boolean>. The constructor initializes the instance objects.
 * The method setBoolValues() sets possible assignments of the HashMap values using
 * a binary addition method. The solve() method contains loops to test the value of
 * the clauses in the formula with possible assignments and returns if a formula is
 * satisfiable or not.
 *
 * @author Anwara Era
 * @version 2-27-23
 */

public class Solver {
    private FileInfo file;
    private ArrayList<String[]> clauseList;
    private HashMap<Integer, Boolean> variableValues;

    /**
     * Constructor
     *
     * Initializes instance variables. Fills HashMap values to false.
     *
     * @param f - FileInfo object
     */
    public Solver(FileInfo f)
    {
        file = f;
        clauseList = file.evaluateClauses();
        variableValues = new HashMap<>();
        for (int i = 1; i <= file.getNumVariables(); i++)
        {
            // Initialize all variables with false/0 values
            variableValues.put(i, false);
        }
    }

    /**
     * Does binary addition to boolean values in the Hashmap to assign all
     * possible assignments of true/false values. Checks Hashmap values to
     * see if there are more possible assignments left. If all values are true,
     * no more possible assignments.
     *
     * @return continueLoop - value to tell if there are any more assignments left
     */
    public boolean setBoolValues()
    {
        int carry = 0;
        if(variableValues.get(variableValues.size())) {     // Checks last value in hashmap first
            variableValues.put(variableValues.size(), false);   // If true, set to false and carry=1
            carry = 1;
        } else
            variableValues.put(variableValues.size(), true);

        for(int i = variableValues.size()-1; i > 0; i--) {  // Iterate through all other hashmap values
            if (carry == 1) {
                if(variableValues.get(i)) {
                    variableValues.put(i, false);
                }
                else {
                    variableValues.put(i, true);
                    carry = 0;
                }
            } else          // Once carry!=1, other hashmap values don't need to be changed
                break;
        }

        boolean continueLoop = false;   // Check to see if all values are true, when they are then no more assignments
        for(int i = 1; i <= variableValues.size(); i++) {
            if(!variableValues.get(i)) {
                continueLoop = true;
                break;
            }
        }
        return continueLoop;
    }

    /**
     * Contains while loop to go through all possible assignments. Inner loop
     * goes through each clause in the formula. Loop inside of that goes through
     * each of the variables in each clause. If a variable is found to be true,
     * the loop is broken out of and the clause is determined to be true. If a
     * clause evaulated to false, the second loop is broken out of and a call to
     * setBoolValues() is made to set the next assignment.
     *
     * @return truthValue - holds whether formula is satisfiable or not
     */
    public boolean solve()
    {
        boolean truthValue = false;
        boolean continueLoop = true;

        while(continueLoop) {   // Loops through all possible assignments
            for(int i = 0; i < clauseList.size(); i++) {    // Loops through each clause in formula
                String[] singleClause = clauseList.get(i);
                truthValue = false;
                for(int j = 0; j < singleClause.length; j++) {  // Loops through all variables in clause
                    if(singleClause[j] == "") {     // Empty clauses evaluate to true
                        truthValue = true;
                        break;
                    }

                    int variable = Integer.parseInt(singleClause[j]);   // Turn string variable to int
                    if(variable < 0) {  // Check if it's negative and evaluate
                        variable *= -1;
                        truthValue = (truthValue || !variableValues.get(variable));
                    }
                    else {
                        truthValue = (truthValue || variableValues.get(variable));
                    }
                    if(truthValue)  // If variable is true, break out of loop and check next clause
                        break;
                }
                if(truthValue == false) {   // if clause is false, break out of loop and test next assignment
                    break;
                }
            }
            if(truthValue == true) {    // If formula is true, break out of loop
                break;
            }
            continueLoop = setBoolValues(); // call to set new assignment and check to continue looping
        }
        return truthValue;
    }
}
