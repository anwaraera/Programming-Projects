# DAA_Projects
## Sudoku Solver Using SAT4J

### Project State:
- Tested with all given input files and the program returned the correct output for all.

### Classes:
- Main:
  - Contains driver method to solve a sudoku board given in a file. Appropriate objects of other classes are created and their methods are called to run the program inside a loop that continues asking for files until the user wants to stop.
- SATSolver:
  - Contains a Clauses object to solve the SAT problem. The solveSAT() method calls the method to make the clauses and determines if the set of clauses are satisfiable or not. It also contains an updateBoard() and displayBoard() method that updates the sudoku board if the SAT was satisfied with the correct values.
- Clauses:
  - Contains methods to create all the clauses that satisfy the constraints of sudoku and pass those clauses to the ISolver object from SAT4J.
- FileInfo:
  - Contains instance variables need to read in a file from the user containing a sudoku board and store its contents in the appropriate data structures
