# DAA_Projects
## Bruteforce SAT Solver

### Project State:
- Tested with all given input files and the program returned the correct output for all.
- I only had an issue with testing u20.cnf file. I got a number format exception because of the way I was reading the file content in. I set the delimiter to read in lines to ' 0', and line 19 in u20.cnf was being read in as "0-3". Adding a space to line 19 before the 0 did fix the problem and allow me to test it and get the correct output.

### Classes:
- Main:
  - Contains driver method to create appropriate objects and calls their methods to test a file's satisfiablity. Also has a displayData() method to show data on each tested file in a neat table.
- Data:
  - Contains instance variables to describe the qualities of a file. Contains accessor methods for each field.
- FileInfo:
  - Contains fields used to read in a file and store its contents. The readFile() method contains a loop to read in a file name and check if it's valid. The setValues() method reads the file and set field values. The evaluateClauses() method adds all clauses to a list.
- Solver:
  - Contains a HashMap with boolean values used to set the possible assignments for the formula. The setBoolValues() method sets assignments and returns if there are any possible combos left. The solve() method contains loops to test each clause with the assignments and returns true if formula is satisfiable.
