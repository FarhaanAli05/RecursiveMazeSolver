import java.util.Scanner;
import java.util.Random;

public class RecursiveMazeSolver {
    
    public static void main(String [] args) {
    	Random rand = new Random();
    	int mazanum = rand.nextInt(6);
    	
        String[][] maze = mazeSelect(mazanum);
        
        int startRow = 0; // Placeholder value
        int startColumn = 0; // Placeholder value
        
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j].contains("$")) {
                    startRow = i;
                    startColumn = j;
                }
            }
        }
        
        String[][] solvedMaze = mazeSelect(mazanum);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Original maze:\n");
        displayMaze(maze, 0, 0);
        System.out.println();
        System.out.print("Would you like to view the step-by-step navigation process (Y/N)? ");
        String stepByStep = scanner.nextLine();
        
        if (findMazePath(maze, stepByStep, startRow, startColumn, solvedMaze, startRow, startColumn)) { // Calls method to navigate through maze – checks if findMazePath returns true
            System.out.println("\nSolved maze:\n");
            displayMaze(solvedMaze, 0, 0); // Ultimately replaces original maze with 'solvedMaze'
        }
        else {
            System.out.println("\nNo solution exists.");
        }
        scanner.close();
    }

    
    public static boolean findMazePath(String[][] unsolvedMaze, String stepByStep, int row, int column, String[][] solvedMaze, int prevRow, int prevColumn) {
        if (unsolvedMaze[row][column].equals("€")) { // Base case – returns true if index contains '€'
            return true;
        }
        
        
        if (isSpaceSafe(unsolvedMaze, row, column)) { // Checks if index is 'safe' to cross using isSpaceSafe method
            if (solvedMaze[row][column].equals("$")) { // Exception: conditional statement ensures the '$' at the beginning of the code does not get overwritten
                unsolvedMaze[row][column] = "█"; // Note: 'unsolvedMaze' is the version of the maze that the user does not see – when the unsolvedMaze array is 'solved', the 'solvedMaze' array is what is displayed to the user
                solvedMaze[row][column] = "$";
            } else {
                unsolvedMaze[row][column] = "█"; // Block is placed on maze rat's path in unsolvedMaze so that the rat does not traverse backward on its path (would ultimately cause infinite recursion)
                solvedMaze[row][column] = "."; // The 'maze rat' leaves periods on its path as it traverses through the maze 
            }
            if (stepByStep.equals("Y") || stepByStep.contains("yes") || stepByStep.equals("y") || stepByStep.contains("Yes")) { // Program prints the maze's step-by-step solution based on user input
                System.out.println();
                displayMaze(solvedMaze, 0, 0);
            }
            else {
            }
            if (findMazePath(unsolvedMaze, stepByStep, row+1, column, solvedMaze, row, column)) { // General cases – recursion is implemented to ensure the maze rat continues to search through indices in the same row until a wall is met
                return true;
            }
            
            if (findMazePath(unsolvedMaze, stepByStep, row, column+1, solvedMaze, row, column)) { // Same recursion logic, but applies to columns – returns true when wall is met
                return true;
            }
            
            if (findMazePath(unsolvedMaze, stepByStep, row-1, column, solvedMaze, row, column)) {
                return true;
            }
            
            if (findMazePath(unsolvedMaze, stepByStep, row, column-1, solvedMaze, row, column)) {
                return true;
            }
            unsolvedMaze[row][column] = " ";
            solvedMaze[row][column] = " "; // If conditionals are not satisfied, unnecessary periods in path are overwritten with spaces
            return false;
        }
        return false;
    }

    //This will recursively print the maze by index.
    public static void displayMaze(String [][]maza, int ro, int colu) { 
    	//This will check to ensure there are still rows to print.
    	if(ro < maza.length) { 
    		//This will check to ensure there are still columns to print.
    		if(colu < maza[ro].length) { 
    			//This will print and recurse through columns.
    			System.out.print(maza[ro][colu]); //Prints the current index.
    			displayMaze(maza, ro, colu+1); //Establishes new variables.
    			return; //Stops the program from moving to the new line component.
    		} 
			//If the program reaches this point, it is time for a new line
    		System.out.println(); //Prints a new line.
    		displayMaze(maza, ro+1, 0); //Moves on to a new row.
    	}
    	else {
    		//If the program reaches this point, it's over.
    	}
    }
    
    //This makes random modifications to the maze.
    public static String[][] mazeSelect(int pikt){
    	
    	//The default maze.
        String [][] mazalec = {{"█", "█", "█", "█", "█", "█", "█", "█", "█", "█"}, 
                {"█", " ", " ", " ", " ", " ", " ", " ", " ", "█"},
                {"█", "█", " ", "█", "█", "█", " ", "█", "█", "█"},
                {"█", "█", " ", " ", " ", "█", " ", " ", " ", "█"},
                {"█", "█", "█", "█", " ", "█", " ", "█", " ", "█"},
                {"█", " ", " ", " ", " ", " ", " ", "█", "█", "█"},
                {"█", "█", "█", "█", "█", "█", " ", " ", "€", "█"},
                {"█", "█", "█", "█", "█", "█", "█", "█", "█", "█"}};
        
        String[][] maze = {{"█", "█", "█", "█", "█", "█", "█", "█", "█", "█"}, 
                              {"█", " ", " ", " ", " ", " ", " ", " ", " ", "█"},
                              {"█", "█", " ", "█", "█", "█", " ", "█", "█", "█"},
                              {"█", " ", " ", " ", " ", "█", " ", " ", " ", "█"},
                              {"█", " ", "█", "█", " ", "█", " ", "█", "█", "█"},
                              {"█", " ", " ", "█", "█", " ", " ", "█", "$", "█"},
                              {"█", "█", " ", " ", "€", "█", " ", " ", " ", "█"},
                              {"█", "█", "█", "█", "█", "█", "█", "█", "█", "█"}};
         
    	if (pikt == 0) {
    		mazalec[1][5] = "█";
    		mazalec[1][4] = "$";
                mazalec[5][1] = "€";
                mazalec[6][8] = " ";
    	}
    	//Unsolvable case.
    	else if (pikt == 1) {
    		mazalec[6][7]= "█";
    		mazalec[2][6]="$";
    	}
    	else if (pikt == 2) {
                mazalec[3][1] = " ";
                mazalec[4][1] = " ";
                mazalec[4][8] = "█";
                mazalec[5][1] = " ";
                mazalec[5][3] = "█";
                mazalec[5][4] = "█";
                mazalec[5][8] = "$";
                mazalec[6][2] = " ";
                mazalec[6][3] = " ";
                mazalec[6][4] = "€";
                mazalec[6][8] = " ";
    	}
    	
    	//This one has a different end location!
    	else if (pikt == 3) {
    		mazalec[2][6]= "█";
    		mazalec[6][8]= "$";
    		mazalec[1][8]= "€";
    	}

    	else if (pikt == 4) {
    		mazalec[2][4]= "$";
    		mazalec[1][5]= "█";
    		mazalec[2][2]= "█";
    	}
    	//Closest to base maze.
    	else {
    		mazalec[1][1]= "$";
    	}

    	//Returns the modified maze to the program.
		return mazalec;
    }

    
    public static boolean isSpaceSafe(String[][] maze, int row, int column) { // Method to ensure the 'maze rat' only crosses suitable indices within maze borders (i.e. rat only crosses ' ', '€' and '$')
        return ((row > 0 && column > 0 && row < maze.length-1 && column < maze[0].length-1) && (maze[row][column].equals(" ") || maze[row][column].equals("€") || maze[row][column].equals("$")));
    }
}